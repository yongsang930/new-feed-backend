package com.newfeed.backend.domain.keyword.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserKeywordUpdateRequest {

    private List<Long> keywordIds;

}