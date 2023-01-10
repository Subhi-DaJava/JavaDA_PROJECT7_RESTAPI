package com.nnk.springboot.security_config.oauth2_config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
/**
 * Customize DefaultOAuth2UserService
 * @author Subhi
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest useRequest) {
        logger.debug("loadUser(from CustomOAuth2UserService) method starts here.");

        OAuth2User oAuth2User = super.loadUser(useRequest);
        logger.info("Return successfully oAuth2User from CustomOAuth2UserService");
        return new CustomOAuth2User(oAuth2User);

    }

}
