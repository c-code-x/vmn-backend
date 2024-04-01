package com.vdc.vmnbackend.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.vdc.vmnbackend.service.impl.UserDetailsServiceImpl;
import com.vdc.vmnbackend.utility.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;

/**
 * Configuration class for setting up security configurations, including authentication and authorization.
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration {

    private static final String[] PUBLIC_URLS = {
            "/auth/**",
            "/invite/verify-invite",
            "/invite/accept-invitation",
    };

    private final String jwtKey;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Constructor for SecurityConfiguration.
     *
     * @param jwtKey             The JWT key used for token signing and verification.
     * @param userDetailsService The service for loading user details for authentication.
     */
    public SecurityConfiguration(@Value("${jwt.key}") String jwtKey, UserDetailsServiceImpl userDetailsService) {
        this.jwtKey = jwtKey;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configures the security filter chain for API endpoints.
     *
     * @param http The HttpSecurity object for configuring security.
     * @return A SecurityFilterChain for API endpoints.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_URLS)
                        .permitAll()
                        .requestMatchers(CommonConstants.INVITE_URLS)
                        .hasAnyAuthority(CommonConstants.SUPER_ADMIN_ROLE,
                                CommonConstants.ADMIN_ROLE)
                        .anyRequest()
                        .authenticated()
                )
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandler()))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }


    /**
     * Creates an AccessDeniedHandler bean.
     *
     * @return An AccessDeniedHandler implementation.
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) for the application.
     *
     * @return A CorsConfigurationSource for CORS configuration.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        log.info("CORS config {}", source.getCorsConfigurations());
        return source;
    }


    /**
     * Creates a BCryptPasswordEncoder bean for password encoding.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(9);
    }

    /**
     * Creates a JwtEncoder bean for JWT token encoding.
     *
     * @return A JwtEncoder instance.
     */
    @Bean
    JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey.getBytes()));
    }

    /**
     * Creates a JwtDecoder bean for JWT token decoding.
     *
     * @return A JwtDecoder instance.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] bytes = jwtKey.getBytes();
        SecretKeySpec originalKey = new SecretKeySpec(bytes, 0, bytes.length, "RSA");
        return NimbusJwtDecoder.withSecretKey(originalKey).macAlgorithm(MacAlgorithm.HS512).build();
    }

    /**
     * Creates an AuthenticationManager bean for authentication.
     *
     * @return An AuthenticationManager instance.
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(authProvider);
    }
}
