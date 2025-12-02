package com.newfeed.backend.domain.token.converter;

import com.newfeed.backend.common.annotation.Converter;
import com.newfeed.backend.common.error.ErrorCode;
import com.newfeed.backend.common.exception.ApiException;
import com.newfeed.backend.domain.token.controller.model.TokenResponse;
import com.newfeed.backend.domain.token.model.TokenDto;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Converter
public class TokenConverter {

    public TokenResponse toResponse(
        TokenDto accessToken,
        TokenDto refreshToken
    ){
        Objects.requireNonNull(accessToken, ()->{throw new ApiException(ErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreshToken, ()->{throw new ApiException(ErrorCode.NULL_POINT);});

        return TokenResponse.builder()
            .accessToken(accessToken.getToken())
            .accessTokenExpiredAt(accessToken.getExpiredAt())
            .refreshToken(refreshToken.getToken())
            .refreshTokenExpiredAt(refreshToken.getExpiredAt())
            .build();
    }
}
