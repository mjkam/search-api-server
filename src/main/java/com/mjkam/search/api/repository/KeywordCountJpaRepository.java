package com.mjkam.search.api.repository;

import com.mjkam.search.api.domain.KeywordCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KeywordCountJpaRepository extends JpaRepository<KeywordCount, Long> {
    @Query("SELECT m FROM KeywordCount m ORDER BY m.count DESC")
    List<KeywordCount> findTop10OrderByCountDesc(Pageable pageable);

    @Modifying
    @Query("UPDATE KeywordCount m SET m.count = m.count + 1 WHERE m.keyword = :keyword")
    void updateKeywordCount(@Param("keyword") String keyword);
}
