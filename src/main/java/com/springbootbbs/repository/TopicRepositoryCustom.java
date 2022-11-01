package com.springbootbbs.repository;

import com.springbootbbs.entiry.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicRepositoryCustom {
    Page<Topic> searchTitleByKeywords(List<String> keywordList, Pageable pageable);
}
