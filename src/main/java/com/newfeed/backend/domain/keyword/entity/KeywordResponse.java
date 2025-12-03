package com.newfeed.backend.domain.keyword.entity;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KeywordResponse {

    private Long keywordId;
    private String name;
}
