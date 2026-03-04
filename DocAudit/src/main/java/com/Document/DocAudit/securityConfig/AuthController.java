package com.Document.DocAudit.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/get-my-token")
    public String generateToken(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "Please Login via OAuth2 first at /oauth2/authorization/google";
        }

        // Extract the actual username/email from the OAuth2 user
        String username = authentication.getName();

        // If you want the email specifically:
        if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            username = oAuth2User.getAttribute("email"); // or "sub"
        }

        return jwtUtils.generateToken(username);
    }
}
