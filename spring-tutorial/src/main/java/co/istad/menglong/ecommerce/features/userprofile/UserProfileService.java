package co.istad.menglong.ecommerce.features.userprofile;

import co.istad.menglong.ecommerce.features.userprofile.dto.PatchUserProfileRequest;
import co.istad.menglong.ecommerce.features.userprofile.dto.UserProfileResponse;

public interface UserProfileService {

    UserProfileResponse getUserProfile();

    UserProfileResponse patchUserProfile(PatchUserProfileRequest patchUserProfileRequest);
}
