package co.istad.menglong.ecommerce.features.userprofile;

import co.istad.menglong.ecommerce.features.userprofile.dto.PatchUserProfileRequest;
import co.istad.menglong.ecommerce.features.userprofile.dto.UserProfileResponse;
import co.istad.menglong.ecommerce.security.AuthUtils;
import co.istad.menglong.ecommerce.security.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final Keycloak keycloak;

    private final KeycloakProperties keycloakProperties;

    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse getUserProfile() {

        // Get user profile from keycloak
        String userId = AuthUtils.extractUserId();
        UserResource userResource = keycloak.realm(keycloakProperties.getRealm()).users().get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        log.info("user profile: {}", userRepresentation);

        // Get user profile from database
        UserProfile userProfile = userProfileRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found"));
        return userProfileMapper.buildUserProfileResponse(userRepresentation, userProfile);
    }

    @Override
    public UserProfileResponse patchUserProfile(PatchUserProfileRequest patchUserProfileRequest) {

        String userId = AuthUtils.extractUserId();

        // Patch user profile into database
        UserProfile userProfile = userProfileRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found"));

        userProfileMapper.toEntity(userProfile, patchUserProfileRequest);

        userProfileRepository.save(userProfile);

        // Update user profile into keycloak
        UserResource userResource = keycloak.realm(keycloakProperties.getRealm()).users().get(userId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        userProfileMapper.toUserRepresentation(userRepresentation, patchUserProfileRequest);
        userResource.update(userRepresentation);

        return userProfileMapper.buildUserProfileResponse(userRepresentation, userProfile);
    }
}
