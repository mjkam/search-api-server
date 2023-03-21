package com.mjkam.search.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "keyword_count", indexes = {
        @Index(name = "idx_keyword", columnList = "keyword"),
        @Index(name = "idx_count", columnList = "count")
})
@NoArgsConstructor
@Getter
public class KeywordCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "count")
    private Long count;
}
