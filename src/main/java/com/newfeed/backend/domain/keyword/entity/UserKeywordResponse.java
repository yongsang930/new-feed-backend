package com.newfeed.backend.domain.keyword.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserKeywordResponse {

    private Long userId;
    private List<Long> keywordIds;
}
