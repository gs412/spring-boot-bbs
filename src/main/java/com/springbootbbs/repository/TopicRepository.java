package com.springbootbbs.repository;

import com.springbootbbs.entiry.Category;
import com.springbootbbs.entiry.Topic;
import com.springbootbbs.entiry.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TopicRepository extends PagingAndSortingRepository<Topic, Long>, TopicRepositoryCustom {

    Page<Topic> findAllByDeletedOrderByIdDesc(Pageable page, Boolean deleted);

    @Query(value="select t.* from bbs_topic t " +
            "left join bbs_user u on t.user_id = u.id " +
            "where t.deleted=0 and u.banned=0 order by t.stick desc, t.id desc",
            countQuery = "select count(t.id) from bbs_topic t " +
                    "left join bbs_user u on t.user_id = u.id " +
                    "where t.deleted=0 and u.banned=0",
            nativeQuery = true)
    Page<Topic> findAllForIndex(Pageable page);

    @Query(value="select t.* from bbs_topic t " +
            "left join bbs_user u on t.user_id = u.id " +
            "left join bbs_category c on t.category_id = c.id " +
            "where t.deleted=0 and u.banned=0 and c.tab=:tab order by t.stick desc, t.id desc",
            countQuery = "select count(t.id) from bbs_topic t " +
                    "left join bbs_user u on t.user_id = u.id " +
                    "left join bbs_category c on t.category_id = c.id " +
                    "where t.deleted=0 and u.banned=0 and c.tab=:tab",
            nativeQuery = true)
    Page<Topic> findAllForIndexByCategory(@Param("tab") String categoryTab, Pageable page);

    Page<Topic> findAllByTitleLikeAndDeletedOrderByIdDesc(Pageable page, String title, Boolean deleted);

    List<Topic> findAllByUser(User user);

    List<Topic> findAllByDeleted(Boolean deleted);

    int countByCategoryIdAndDeleted(Long categoryId, Boolean deleted);

    @Transactional
    @Modifying
    @Query("update Topic set category=:targetCategory where category=:sourceCategory")
    void moveCategoryToCategory(Category sourceCategory, Category targetCategory);

}
