package com.Document.DocAudit.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class UserController {

    @GetMapping("/user")
    public OAuth2User GetUser(@AuthenticationPrincipal OAuth2User AuthUser){
        return AuthUser;
    }

}
