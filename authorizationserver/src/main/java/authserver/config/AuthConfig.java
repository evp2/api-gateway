package authserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//Optional configuration for SQL backend client registration
@Profile({"dev"})
@Configuration
@EnableWebSecurity
public class AuthConfig {

  @Bean
  JdbcRegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
    return new JdbcRegisteredClientRepository(jdbcTemplate);
  }

  @Bean
  JdbcOAuth2AuthorizationConsentService jdbcOAuth2AuthorizationConsentService(
      JdbcOperations jdbcOperations, RegisteredClientRepository repository) {
    return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, repository);
  }

  @Bean
  JdbcOAuth2AuthorizationService jdbcOAuth2AuthorizationService(
      JdbcOperations jdbcOperations, RegisteredClientRepository rcr) {
    return new JdbcOAuth2AuthorizationService(jdbcOperations, rcr);
  }

  // Utility method to initialize SQL tables only need to run this once
  @Bean
  CommandLineRunner commandLineRunner(
      @Value("${GATEWAY_SERVER_URI}") String gatewayUri,
      @Value("${OAUTH_CLIENT_ID}") String client,
      @Value("${OAUTH_CLIENT_SECRET}") String secret,
      @Value("${USER_PASSWORD}") String userSecret,
      UserDetailsManager userDetailsManager,
      JdbcRegisteredClientRepository registeredClientRepository) {
    return (args) -> {
      List<UserDetails> users = List.of(
          User.withDefaultPasswordEncoder().username("admin").password(userSecret).roles("admin,read").build(),
          User.withDefaultPasswordEncoder().username("user").password(userSecret).roles("user,read").build()
      );
      users.forEach((user) -> {
        if (!userDetailsManager.userExists(user.getUsername())) {
          userDetailsManager.createUser(user);
        }
      });
      if (registeredClientRepository.findByClientId(client) == null) {
        registeredClientRepository.save(
            RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(client)
                .clientSecret(secret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantTypes(grantTypes -> grantTypes.addAll(Set.of(
                    AuthorizationGrantType.CLIENT_CREDENTIALS,
                    AuthorizationGrantType.AUTHORIZATION_CODE,
                    AuthorizationGrantType.REFRESH_TOKEN)))
                .redirectUri(gatewayUri + "/login/oauth2/code/spring")
                .postLogoutRedirectUri(gatewayUri + "/logout")
                .scopes(scopes -> scopes.addAll(Set.of("user.read", "user.write", OidcScopes.OPENID)))
                .build()
        );
      }
    };
  }
}
