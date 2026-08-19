package co.istad.menglong.ecommerce.features.auth;

import co.istad.menglong.ecommerce.features.auth.dto.LoginResponse;
import org.keycloak.representations.AccessTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(source = "token", target = "accessToken")
    LoginResponse toLoginResponse(AccessTokenResponse accessTokenResponse);
}
