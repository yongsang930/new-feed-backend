package com.newfeed.backend.domain.keyword.model;

import lombok.Getter;
import java.util.List;

@Getter
public class KeywordCreateRequest {
    private List<String> names;   // 입력한 키워드 문자열 리스트
}

