package com.mjkam.search.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "keyword_count", indexes = {@Index(name = "idx_count", columnList = "count")})
@NoArgsConstructor
@Getter
public class KeywordCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String keyword;

    private Long count;

    public KeywordCount(String keyword) {
        this.keyword = keyword;
        this.count = 1L;
    }
}
