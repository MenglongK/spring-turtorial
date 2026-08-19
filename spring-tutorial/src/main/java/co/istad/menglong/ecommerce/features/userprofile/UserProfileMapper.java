package co.istad.menglong.ecommerce.features.userprofile;

import co.istad.menglong.ecommerce.features.userprofile.dto.PatchUserProfileRequest;
import co.istad.menglong.ecommerce.features.userprofile.dto.UserProfileResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public abstract class UserProfileMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract void toUserRepresentation(@MappingTarget UserRepresentation userRepresentation, PatchUserProfileRequest patchUserProfileRequest);

    @org.mapstruct.AfterMapping
    protected void updateCustomAttributes(@MappingTarget UserRepresentation userRepresentation, PatchUserProfileRequest request) {
        java.util.Map<String, java.util.List<String>> attributes = userRepresentation.getAttributes();
        if (attributes == null) {
            attributes = new java.util.HashMap<>();
            userRepresentation.setAttributes(attributes);
        }
        if (request.gender() != null) {
            attributes.put("gender", java.util.List.of(request.gender()));
        }
        if (request.biography() != null) {
            attributes.put("biography", java.util.List.of(request.biography()));
        }
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract void toEntity(@MappingTarget UserProfile userProfile, PatchUserProfileRequest patchUserProfileRequest);

    public UserProfileResponse buildUserProfileResponse(UserRepresentation userRepresentation, UserProfile userProfile) {
        return UserProfileResponse.builder()
                .userId(userRepresentation.getId())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .email(userRepresentation.getEmail())
                .gender(userRepresentation.firstAttribute("gender"))
                .biography(userRepresentation.firstAttribute("biography"))
                .profilePicture(userProfile.getProfilePicture())
                .phoneNumber(userProfile.getPhoneNumber())
                .jobTitle(userProfile.getJobTitle())
                .salary(String.valueOf(userProfile.getSalary()))
                .githubLink(userProfile.getGithubLink())
                .facebookLink(userProfile.getFacebookLink())
                .build();
    }
}
