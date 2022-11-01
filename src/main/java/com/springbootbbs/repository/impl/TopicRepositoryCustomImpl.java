package com.springbootbbs.repository.impl;

import com.springbootbbs.entiry.Topic;
import com.springbootbbs.repository.TopicRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class TopicRepositoryCustomImpl implements TopicRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Topic> searchTitleByKeywords(List<String> keywordList, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Topic> cQuery = cb.createQuery(Topic.class);
        Root<Topic> topic = cQuery.from(Topic.class);

        Path<String> titlePath = topic.get("title");
        Expression<String> titleExpression = cb.lower(titlePath);

        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder.Case<Object> selectCase = cb.selectCase();
        for (String keyword : keywordList) {
            System.out.println(keyword);
            predicates.add(cb.like(titleExpression, "%" + keyword.toLowerCase() + "%"));

            selectCase = selectCase.when(cb.like(titleExpression, "%" + keyword.toLowerCase() + "%"), keywordList.indexOf(keyword) + 10);
            Expression<Object> sStr = selectCase.otherwise(0);
            cQuery = cQuery.orderBy(cb.desc(sStr));
        }
        cQuery.select(topic).where(cb.or(predicates.toArray(new Predicate[0])));


        List<Order> orderList = new ArrayList<>();
        TypedQuery<Topic> query = entityManager.createQuery(cQuery);
        int totalRows = query.getResultList().size();

        Page<Topic> result = new PageImpl<>(query.getResultList(), pageable, totalRows);

        return result;
    }
}
