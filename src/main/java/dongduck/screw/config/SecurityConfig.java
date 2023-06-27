package dongduck.screw.config;

import dongduck.screw.config.oauth.CustomOAuth2UserService;
import dongduck.screw.config.oauth.OAuth2SuccessHandler;
import dongduck.screw.config.token.JwtAuthFilter;
import dongduck.screw.config.token.TokenService;
import dongduck.screw.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    //CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    //시큐리티에서 더블슬래시(//)허용
    @Bean
    public HttpFirewall defaultHttpFirewall(){
        return new DefaultHttpFirewall();
    }

    //비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) throws Exception{
        web.httpFirewall(defaultHttpFirewall());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource());
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //인가 코드의 전송 과정은 Spring Security에서 내부적으로 처리
        http.oauth2Login()
                .userInfoEndpoint().userService(customOAuth2UserService)//인가코드를 보낸 후에 받아온 accessToken으로 사용자 정보 받아오기
                .and()
                .successHandler(oAuth2SuccessHandler) //사용자에게 jwt토큰 발급하기
                .failureHandler(((request, response, exception) -> {
                    log.info("exception발생 : " + exception.getMessage());
                }));

        http.addFilterBefore(new JwtAuthFilter(tokenService,userRepository), UsernamePasswordAuthenticationFilter.class);
    }



}