package com.oauth.oauth_example.handler;

import com.oauth.oauth_example.dto.OAuthAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    /**
     * [ OAuth2 로그인에서 Authentication 객체 생성 ]
     * OAuth2 로그인 프로세스에서 사용자가 외부 인증 제공자를 통해 성공적으로 인증을 완료하면,
     * OAuth2UserServiceImpl 클래스의 loadUser 함수의 반환 값(OAuth2User)이 Authentication의 principal로 설정됩니다
     * Authentication 객체는 사용자의 주요 정보(예: 이름, 이메일, 프로필 사진 URL 등)를 포함할 수 있습니다.
     * */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication
    ) throws IOException, ServletException
    {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String registrationId   = oauthToken.getAuthorizedClientRegistrationId();
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();// Authentication 객체로부터 권한 정보 get
//        String authorityString  = authorities.isEmpty() ? "" : authorities.iterator().next().getAuthority();// 권한 정보가 비어있지 않은 경우, 첫 번째 권한을 String으로 추출
        OAuth2User oAuth2User   = (OAuth2User) authentication.getPrincipal();
        String email        = "";
        if("google".equals(registrationId)){
            email           = OAuthAttributes.getGoogleEmail(oAuth2User.getAttributes());
        }

//        String accessToken  = tokenProvider.createAccessToken(email, authorityString); // email을 통해 사용자의 권한을 가져와 accessToken을 생성
//        tokenProvider.saveCookie(response,"accessToken",accessToken, 1); // 응답에 토큰을 저장
        response.setStatus(HttpServletResponse.SC_OK);
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/success");          // 로그인 성공 후 내가만든 success 페이지 리디렉션

    }
}
