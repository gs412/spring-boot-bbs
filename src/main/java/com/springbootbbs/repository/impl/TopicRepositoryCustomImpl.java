package com.springbootbbs.repository.impl;

import com.springbootbbs.entiry.Topic;
import com.springbootbbs.repository.TopicRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TopicRepositoryCustomImpl implements TopicRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Topic> searchTitleByKeywords(List<String> keywordList, Pageable pageable) {
        String whereStr = keywordList.stream().map((word) -> "t.title like '%" + word + "%'").collect(Collectors.joining(" or "));
        String orderByStr = keywordList.stream().map((word) -> "IF(t.title like '%" + word + "%', " + (100 - keywordList.indexOf(word)) + ", 0)").collect(Collectors.joining(" + "));
        TypedQuery<Topic> query = entityManager.createQuery("select t.id, t.title from Topic t " +
                "left join User u on t.user.id = u.id " +
                "where (?1) and t.deleted=false and u.banned=false " +
                "order by " + orderByStr + " desc, t.replies desc", Topic.class).setParameter(1, whereStr);

        int totalRows = query.getResultList().size();
        Page<Topic> result = new PageImpl<>(query.getResultList(), pageable, totalRows);
        return result;
    }

    // 暂时废弃，这种方式太难实现
    @Override
    public Page<Topic> searchTitleByKeywords1(List<String> keywordList, Pageable pageable) {
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
        System.out.println(cQuery.toString());

        List<Order> orderList = new ArrayList<>();
        TypedQuery<Topic> query = entityManager.createQuery(cQuery);
        int totalRows = query.getResultList().size();

        Page<Topic> result = new PageImpl<>(query.getResultList(), pageable, totalRows);

        return result;
    }
}
