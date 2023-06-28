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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
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
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("========OAuth2SuccessHandler 실행========");

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserDto userDto = userRequestMapper.toDto(oAuth2User);

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

            //user 저장
            userRepository.save(user);

            //토큰 생성
            Token token = tokenService.generateToken(email, "USER");

            //최초 User
            boolean isNewUser = true;

            //응답 헤더에 Auth, Refresh 추가
            writeTokenResponse(response, token, isNewUser);

            //리다이렉트할 uri 생성
            String uri = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect")
                    .build().toString();

            //클라이언트로 리다이렉트
//            response.sendRedirect(uri);
            getRedirectStrategy().sendRedirect(request,response,uri);

        }else{
            //토큰 생성
            Token token = tokenService.generateToken(email, "USER");

            //이미 있는 User
            boolean isNewUser = false;

            //응답 헤더에 Auth, Refresh 추가
            writeTokenResponse(response, token, isNewUser);

            //리다이렉트할 uri 생성
            String uri = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect")
                    .build().toString();

            //클라이언트로 리다이렉트
//            response.sendRedirect(uri);
            getRedirectStrategy().sendRedirect(request,response,uri);
        }
    }

    private void writeTokenResponse(HttpServletResponse response, Token token, boolean isNewUser) throws IOException {
//        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.addHeader("Is-New-User", String.valueOf(isNewUser));

    }
}
