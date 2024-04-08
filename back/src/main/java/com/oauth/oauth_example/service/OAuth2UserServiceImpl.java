package com.oauth.oauth_example.service;

import com.oauth.oauth_example.dto.OAuthAttributes;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 인증 제공자(예 :google, naver..)로 부터 토큰을 정상 수령하여 인증이 완료되면 OAuth2UserService의 loadUser함수를 호출합니다.
 * loadUser 함수를 커스터마이징하여 인증된 정보를 통해 권한지정 및 회원 가입을 진행합니다.
 * */
@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService { // DefaultOAuth2UserService 클래스는  OAuth2UserService<OAuth2UserRequest, OAuth2User> 인터페이스를 구현한 구현체입니다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User           = super.loadUser(userRequest);
        String registrationId           = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName    = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes      = null;
        if("google".equals(registrationId)){
            attributes      = OAuthAttributes.googleMemberInfo(userNameAttributeName, oAuth2User.getAttributes());
        }

        String email  = attributes.getEmail();
        // email을 통해 멤버를 찾으며 없을 경우 member 데이터 신규 저장
//        if( !memberRepository.existsByEmail(email) )
//        {
//            insertNewMember(email);
//        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROL_USER");
        return new DefaultOAuth2User(
                Collections.singleton(authority),
                attributes.getAttributes(),
                userNameAttributeName);
    }
}
