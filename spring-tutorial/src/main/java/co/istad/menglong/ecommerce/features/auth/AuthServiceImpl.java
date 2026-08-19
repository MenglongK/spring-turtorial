package co.istad.menglong.ecommerce.features.auth;

import co.istad.menglong.ecommerce.features.auth.dto.LoginRequest;
import co.istad.menglong.ecommerce.features.auth.dto.LoginResponse;
import co.istad.menglong.ecommerce.features.auth.dto.RegisterRequest;
import co.istad.menglong.ecommerce.features.auth.dto.RegisterResponse;
import co.istad.menglong.ecommerce.features.userprofile.UserProfile;
import co.istad.menglong.ecommerce.features.userprofile.UserProfileRepository;
import co.istad.menglong.ecommerce.security.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserProfileRepository userProfileRepository;

    private final Keycloak keycloak;

    private final KeycloakProperties keycloakProperties;

    private final AuthMapper authMapper;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        // Validate password
        if (!registerRequest.password().equals(registerRequest.confirmedPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "passwords don't match");
        }

        // Create keycloak UserRepresentation
        UserRepresentation user = new UserRepresentation();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());
        user.setEnabled(true);
        user.setEmailVerified(false);


        // prepare customized attributes (gender, biography)
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("gender", List.of(registerRequest.gender()));
        attributes.put("biography", List.of(registerRequest.biography()));
        attributes.put("phoneNumber", List.of(registerRequest.phoneNumber()));
        user.setAttributes(attributes);

        // set password
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registerRequest.password());
        user.setCredentials(List.of(credential));

        UsersResource usersResource = keycloak.realm(keycloakProperties.getRealm()).users();

        // Start saving user into keycloak via API

        try (Response response = usersResource.create(user)) {
            if (response.getStatus() != HttpStatus.CREATED.value()) {
                String errorMessage = "Failed to create user in Keycloak. Status: " + response.getStatus();
                if (response.hasEntity()) {
                    errorMessage += ", Details: " + response.readEntity(String.class);
                }
                log.error(errorMessage);
                throw new ResponseStatusException(HttpStatus.valueOf(response.getStatus()), errorMessage);
            }

            log.info("Response status: {}", response.getStatus());
            UserRepresentation createdUser = usersResource.search(user.getUsername()).getFirst();
            log.info("Created user: {}", createdUser.getId());

            // Start saving user into database
            UserProfile userProfile = new UserProfile();
            userProfile.setUserId(createdUser.getId());
            userProfileRepository.save(userProfile);
            return RegisterResponse.builder().keycloakUserId(createdUser.getId()).username(createdUser.getUsername()).email(createdUser.getEmail()).firstName(createdUser.getFirstName()).lastName(createdUser.getLastName()).phoneNumber(createdUser.firstAttribute("phoneNumber")).gender(createdUser.firstAttribute("gender")).biography(createdUser.firstAttribute("biography")).build();
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder().grantType(OAuth2Constants.PASSWORD).serverUrl(keycloakProperties.getServerUrl()).realm(keycloakProperties.getRealm()).clientId(keycloakProperties.getClientId()).clientSecret(keycloakProperties.getClientSecret()).username(loginRequest.username()).password(loginRequest.password()).build();
            log.info("Login status: {}", keycloak.isClosed());
            return authMapper.toLoginResponse(keycloak.tokenManager().getAccessToken());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }
}
