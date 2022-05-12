package com.study.realworld.auth.provider;

import com.study.realworld.auth.MemberContext;
import com.study.realworld.auth.token.AjaxAuthenticationToken;
import com.study.realworld.domain.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        MemberContext context = (MemberContext) userDetailsService.loadUserByUsername(username);

        Member member = context.getMember();

        // password confirm
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new BadCredentialsException("password is incorrect");
        }

        return new AjaxAuthenticationToken(member, null, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AjaxAuthenticationToken.class.equals(authentication);
    }
}
