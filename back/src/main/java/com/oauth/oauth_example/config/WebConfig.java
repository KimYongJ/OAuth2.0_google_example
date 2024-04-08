package com.oauth.oauth_example.config;

import com.oauth.oauth_example.handler.CustomAuthenticationSuccessHandler;
import com.oauth.oauth_example.service.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
/*
 * [ @Configuration 설명 ]
 * - 해당 어노테이션을 사용하면 클래스가 스프링의 설정 정보를 포함하고있음을 나타낸다.(스프링 컨테이너에 의해 빈 설정을 위한 것으로 간주됨)
 * - 해당 어노테이션을 쓴 클래스는 자동으로 빈으로 등록됨.(@Component 어노테이션 사용 불필요)
 * - 이 클래스 안에 정의된 함수 중 @Bean 어노테이션이 붙은 메소드들로 부터 반환되는 객체들은 스프링 빈으로 등록된다. ( 함수 반환 객체가 빈으로 등록됨에 유의 )
 * */
@Configuration
/*
 * [ @EnableWebSecurity 설명 ]
 * - 이 어노테이션은 Spring Security 설정을 활성화한다. 이 어노테이션이 없으면 스프링 시큐리티의 사용자 정의 보안 구성을 적용할 수 없다.
 * - SecurityConfigurerAdapter를 상속받아 보안 설정을 커스텀할 수 있게 하며 어느 클래스이던 SecurityConfigurerAdapter를 상속받아 구현만 하면 된다.
 * - 어플리케이션에서 이 어노테이션은 한번만 써야한다. 여러번 쓰면 스프링이 어떤 것을 우선으로 할지 결정하지 못하므로 한번만 쓰는것을 권장 한다.
 * */
@EnableWebSecurity
public class WebConfig implements WebMvcConfigurer {
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final OAuth2UserServiceImpl oAuth2UserServiceImpl;
    /*
     * [ authenticationManager ]
     * - AuthenticationManager는 스프링 시큐리티에서 자동으로 Bean으로 등록되지 않기 때문에 직접 주입할 수 없다. 직접 생성해줘야한다.
     * */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    /*
     * addCorsMappings 함수에 정의된 CORS 정책은 모든 HTTP 요청 처리에 적용됨( 이 함수가 없다면 로컬에서 리액트 요청을 받을 수 업음 )
     * 특히 각 컨트롤러나 컨트롤러 메소드에 개별적으로 적용되는 @CrossOrigin 어노테이션 보다 우선순위가 높음
     * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해
                .allowedOrigins("http://localhost:3000") // 클라이언트 애플리케이션(리액트)의 호스트를 지정
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메소드 지정
                .allowedHeaders("*") // 허용할 헤더 지정
                .allowCredentials(true); // 쿠키를 포함한 요청 허용
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpsecurity) throws Exception { // 이 메소드는 스프링 시큐리트의 핵심 구성 요소 중 하나이다. 이 메소드는 스프링 부트의 자동 구성과정 중 스프링 시큐리티에 의해 호출됨
        httpsecurity
                .authorizeRequests()
                .anyRequest().permitAll()// 그 외 모든 요청은 인증이 필요함
                .and()
                .cors() // 스프링 시큐리티 사용시 스프링MVC와는 별도의 컴포넌트이기 때문에 각각 cors설정을 해줘야 한다.
                .and()
                .oauth2Login() // OAuth2 로그인을 활성화합니다.
                .successHandler(customAuthenticationSuccessHandler)
                .userInfoEndpoint()                     // 사용자 정보를 가져오기 위한 설정을 시작합니다. 이설정을 통해 userService() 호출해 oAuth2UserServiceImpl 사용
                .userService(oAuth2UserServiceImpl);     // 사용자 정보를 가져오기 위해 OAuth2UserService 커스터마이징한 OAuth2UserServiceImpl를 호출하도록함

        return httpsecurity.build();
    }
}
