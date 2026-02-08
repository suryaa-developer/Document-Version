package com.Document.DocAudit.service;

import com.Document.DocAudit.entity.UserEntity;
import com.Document.DocAudit.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserOAuthService extends OidcUserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest)
            throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(userRequest);

        System.out.println("🔥 OIDC SERVICE CALLED 🔥");

        String provider =
                userRequest.getClientRegistration().getRegistrationId();

        String providerId = oidcUser.getSubject(); // == sub
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        String picture = oidcUser.getPicture();

        System.out.println("PROVIDER    : " + provider);
        System.out.println("PROVIDER ID : " + providerId);
        System.out.println("EMAIL       : " + email);

        UserEntity user =
                userRepository
                        .findByProviderAndProviderId(provider, providerId)
                        .orElseGet(UserEntity::new);

        user.setProvider(provider);
        user.setProviderId(providerId);
        user.setEmail(email);
        user.setName(name);
        user.setPictureUrl(picture);

        UserEntity saved = userRepository.save(user);
        System.out.println("✅ SAVED USER ID: " + saved.getUserId());

        return oidcUser;
    }

}
