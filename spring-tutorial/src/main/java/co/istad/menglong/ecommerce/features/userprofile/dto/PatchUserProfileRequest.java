package co.istad.menglong.ecommerce.features.userprofile.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record PatchUserProfileRequest(
        @Size(max = 255)
        String firstName,
        @Size(max = 255)
        String lastName,
        @Size(max = 255)
        String gender,
        @Size(max = 255)
        String biography,
        @Size(max = 255)
        String profilePicture,
        @Size(max = 255)
        String jobTitle,
        @Min(0)
        String salary,
        @Size(max = 255)
        String phoneNumber,
        @Size(max = 255)
        String githubLink,
        @Size(max = 255)
        String facebookLink
) {
}
