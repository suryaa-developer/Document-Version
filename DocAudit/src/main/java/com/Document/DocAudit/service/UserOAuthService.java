package com.Document.DocAudit.service;

import com.Document.DocAudit.entity.UserEntity;
import com.Document.DocAudit.enums.UserStatus;
import com.Document.DocAudit.repository.UserRepository;
import com.Document.DocAudit.securityConfig.CustomUserPrincipal;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserOAuthService extends OidcUserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest)
            throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(userRequest);

        String provider =
                userRequest.getClientRegistration().getRegistrationId();

        String providerId = oidcUser.getSubject(); // == sub
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        String picture = oidcUser.getPicture();

        UserEntity user =
                userRepository
                        .findByProviderAndProviderId(provider, providerId)
                        .orElseGet(()-> {
                            UserEntity newUser = new UserEntity();
                            newUser.setProvider(provider);
                            newUser.setProviderId(providerId);
                            newUser.setEmail(email);
                            newUser.setName(name);
                            newUser.setPictureUrl(picture);
                            newUser.setCreatedAt(LocalDateTime.now());
                            newUser.setLastLoginAt(LocalDateTime.now());
                            newUser.setStatus(UserStatus.ACTIVE);
                            return  newUser;
                        });
        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new OAuth2AuthenticationException("User account is blocked");
        }

        user.setEmail(email);
        user.setName(name);
        user.setPictureUrl(picture);
        user.setLastLoginAt(LocalDateTime.now());

        UserEntity saved = userRepository.save(user);

        return new CustomUserPrincipal(saved,oidcUser);
    }

}
