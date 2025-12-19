package com.newfeed.backend.domain.keyword.repository;

import com.newfeed.backend.domain.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    boolean existsByEnName(String enName);

    Keyword findByEnName(String enName);

    List<Keyword> findByKoNameInOrEnNameIn(List<String> koNames, List<String> enNames);

    List<Keyword> findByIsActiveTrue();

}
