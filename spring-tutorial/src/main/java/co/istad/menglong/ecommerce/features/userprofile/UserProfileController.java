package co.istad.menglong.ecommerce.features.userprofile;

import co.istad.menglong.ecommerce.features.userprofile.dto.PatchUserProfileRequest;
import co.istad.menglong.ecommerce.features.userprofile.dto.UserProfileResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-profiles")
@RequiredArgsConstructor
@SecurityRequirement(name = "keycloak")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public UserProfileResponse getUserProfile() {
        return userProfileService.getUserProfile();
    }

    @PatchMapping("/me")
    public UserProfileResponse patchUserProfile(
            @RequestBody PatchUserProfileRequest patchUserProfileRequest
    ) {
        return userProfileService.patchUserProfile(patchUserProfileRequest);
    }
}
