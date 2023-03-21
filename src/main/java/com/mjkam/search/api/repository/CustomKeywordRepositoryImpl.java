package com.mjkam.search.api.repository;

import com.mjkam.search.api.domain.KeywordCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
@Transactional
public class CustomKeywordRepositoryImpl implements CustomKeywordRepository {
    @PersistenceContext
    private EntityManager em;

    public void incrementCount(String keyword) {
        int result =
                em.createQuery("UPDATE KeywordCount m SET m.count = m.count + 1 WHERE m.keyword = :keyword")
                        .setParameter("keyword", keyword)
                        .executeUpdate();

        if (result == 0) {
            em.persist(new KeywordCount(keyword));
        }
    }
}
