package com.vdc.vmnbackend.service.impl;

import com.vdc.vmnbackend.dto.res.TokenResDTO;
import com.vdc.vmnbackend.utility.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl {
    private final JwtEncoder encoder;

    @Autowired
    public TokenServiceImpl(JwtEncoder encoder) {
        this.encoder = encoder;
    }
    public TokenResDTO generateToken(Authentication authentication) {
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(s->s.replace(CommonConstants.SECURITY_SCOPE_PREFIX,""))
                .collect(Collectors.joining(" "));
        Instant now = Instant.now();
        String accessToken = getJwtToken(authentication.getName(),scope, now, Instant.now().plus(1, ChronoUnit.HOURS));
        String refreshToken = getJwtToken(authentication.getName(),scope, now, Instant.now().plus(30, ChronoUnit.DAYS));
        return new TokenResDTO(accessToken, refreshToken);
    }
    private String getJwtToken(String subject, String scope, Instant now, Instant expiryTime) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expiryTime)
                .subject(subject)
                .claim("scope", scope)
                .build();
        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);
        return encoder.encode(encoderParameters).getTokenValue();
    }
}

