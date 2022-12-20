package com.springbootbbs.repository;

import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicRepositoryCustom {
    Page<Topic> searchTitleByKeywords(List<String> keywordList, Pageable pageable);

    Page<Topic> findAllForIndex(Pageable page, User.IndexOrderBy orderBy);

    Page<Topic> findAllForIndexByCategory(String categoryTab, Pageable page, User.IndexOrderBy orderBy);

    Page<Topic> searchTitleByKeywords1(List<String> keywordList, Pageable page);
}
