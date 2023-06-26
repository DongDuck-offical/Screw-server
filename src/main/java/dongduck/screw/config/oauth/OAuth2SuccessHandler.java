package dongduck.screw.config.oauth;

import dongduck.screw.config.oauth.util.UserDto;
import dongduck.screw.config.oauth.util.UserRequestMapper;
import dongduck.screw.config.token.Token;
import dongduck.screw.config.token.TokenService;
import dongduck.screw.domain.enumType.RoleType;
import dongduck.screw.domain.user.User;
import dongduck.screw.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

//여기서 로그인이 완료된 사용자에게 jwt토큰을 발급한다.
@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("========OAuth2SuccessHandler 실행========");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();


        UserDto userDto = userRequestMapper.toDto(oAuth2User);

        //회원가입
        String email = userDto.getEmail();
        String name = userDto.getName();
        String profileImageUrl = userDto.getPicture();
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());

        User findUser = userRepository.findByEmail(email);

        //최초 로그인일 경우에 회원을 저장함과 동시에 지역과 선호 스포츠를 선택하는 페이지로 리다이렉트한다.
        if(findUser == null) {
            User user = User.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .profileImageUrl(profileImageUrl)
                    .role(RoleType.ROLE_USER).build();

            userRepository.save(user);


        }

        Token token = tokenService.generateToken(email, "USER");

        //응답 헤더에 Auth, Refresh 추가
        writeTokenResponse(response, token);

        String uri = UriComponentsBuilder.fromUriString("http://localhost:3000")
                .queryParam("email",email)
                .queryParam("token",token)
                .build().toString();

        //이 부분은 원래 프론트 서버로 redirect해야함

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
        response.sendRedirect(uri);

    }

    private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

//        var writer = response.getWriter();
//        writer.println(objectMapper.writeValueAsString(token));
//        writer.flush();

    }
}
