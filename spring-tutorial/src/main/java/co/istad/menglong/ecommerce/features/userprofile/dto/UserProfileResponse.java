package co.istad.menglong.ecommerce.features.userprofile.dto;

import lombok.Builder;

@Builder
public record UserProfileResponse(
        String userId,
        String email,
        String firstName,
        String lastName,
        String gender,
        String biography,
        String profilePicture,
        String jobTitle,
        String salary,
        String phoneNumber,
        String githubLink,
        String facebookLink
) {
}
