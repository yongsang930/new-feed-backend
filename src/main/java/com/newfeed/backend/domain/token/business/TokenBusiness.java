package com.newfeed.backend.domain.token.business;

import com.newfeed.backend.common.annotation.Business;
import com.newfeed.backend.domain.token.controller.model.TokenResponse;
import com.newfeed.backend.domain.token.converter.TokenConverter;
import com.newfeed.backend.domain.token.service.TokenService;
import com.newfeed.backend.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Business
public class TokenBusiness {

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    /**
     * 1. user entity user Id 추출
     * 2. access, refresh token 발행
     * 3. converter -> token response로 변경
     */
    public TokenResponse issueToken(UserEntity userEntity){

//        return Optional.ofNullable(userEntity)
//            .map(ue -> {
//                return ue.getId();
//            })
//            .map(userId -> {
//                var accessToken = tokenService.issueAccessToken(userId);
//                var refreshToken = tokenService.issueRefreshToken(userId);
//                return tokenConverter.toResponse(accessToken, refreshToken);
//            })
//            .orElseThrow(
//                ()-> new ApiException(ErrorCode.NULL_POINT)
//            );
        return null;
    }

    public Long validationAccessToken(String accessToken){
        var userId = tokenService.validationToken(accessToken);
        return userId;
    }

}
