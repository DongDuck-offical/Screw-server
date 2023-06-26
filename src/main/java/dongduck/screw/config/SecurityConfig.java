package dongduck.screw.config;

import dongduck.screw.config.oauth.CustomOAuth2UserService;
import dongduck.screw.config.oauth.OAuth2SuccessHandler;
import dongduck.screw.config.token.TokenService;
import dongduck.screw.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;



    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Preflight Request OPTIONS 메소드 요청을 허용
        http.authorizeRequests()
                        .mvcMatchers(HttpMethod.OPTIONS,"/**").permitAll();

        //인가 코드의 전송 과정은 Spring Security에서 내부적으로 처리
        http.oauth2Login() //oauth2 로그인 프로세스
                .userInfoEndpoint().userService(customOAuth2UserService)//인가코드를 보낸 후에 받아온 accessToken으로 사용자 정보 받아오기
                .and()
                .successHandler(oAuth2SuccessHandler); //사용자에게 jwt토큰 발급하기


        http.addFilterBefore(new JwtAuthFilter(tokenService,userRepository), UsernamePasswordAuthenticationFilter.class);
    }



}