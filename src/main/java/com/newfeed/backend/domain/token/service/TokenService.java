package com.newfeed.backend.domain.token.service;

import com.newfeed.backend.common.error.ErrorCode;
import com.newfeed.backend.common.exception.ApiException;
import com.newfeed.backend.domain.token.ifs.TokenHelperIfs;
import com.newfeed.backend.domain.token.model.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * token 에 대한 도메인로직
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    public TokenDto issueAccessToken(Long userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public Long validationToken(String token){
        var map = tokenHelperIfs.validationTokenWithThrow(token);

        var userId = map.get("userId");
        Objects.requireNonNull(userId, ()->{throw new ApiException(ErrorCode.NULL_POINT);});

        return Long.parseLong(userId.toString());
    }

}
