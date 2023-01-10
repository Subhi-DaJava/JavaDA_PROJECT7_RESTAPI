package com.nnk.springboot.security_config.oauth2_config;

import com.nnk.springboot.service.user_service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * Customize OAuth2User
 * @author Subhi
 */
public class CustomOAuth2User implements OAuth2User {
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2User.class);

    private OAuth2User oAuth2User;

    public CustomOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        logger.debug("CustomOAth2User is working for getAttributes method");

        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        logger.debug("getAuthorities method from CustomOAth2User is working for login");

        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        logger.debug("getName method from CustomOAth2User is working for login");

        return oAuth2User.getAttribute("login");
    }
}
