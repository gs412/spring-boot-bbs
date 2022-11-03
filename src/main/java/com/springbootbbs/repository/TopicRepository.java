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

public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {

    Page<Topic> findAllByDeletedOrderByIdDesc(Pageable page, Boolean deleted);

    @Query(value="select t.* from bbs_topic t " +
            "left join bbs_user u on t.user_id = u.id " +
            "where t.deleted=0 and u.banned=0 order by t.id desc",
            countQuery = "select count(t.id) from bbs_topic t " +
                    "left join bbs_user u on t.user_id = u.id " +
                    "where t.deleted=0 and u.banned=0",
            nativeQuery = true)
    Page<Topic> findAllForIndex(Pageable page);

    Page<Topic> findAllByCategoryTabAndDeletedOrderByStickDescIdDesc(Pageable page, String categoryTab, Boolean deleted);

    Page<Topic> findAllByTitleLikeAndDeletedOrderByIdDesc(Pageable page, String title, Boolean deleted);

    List<Topic> findAllByUser(User user);

    int countByCategoryIdAndDeleted(Long categoryId, Boolean deleted);

    @Query(value="select * from bbs_topic where title like :kw1 and deleted=0 order by replies desc",
            countQuery = "select count(*) from bbs_topic where title like :kw1 and deleted=0",
            nativeQuery = true)
    Page<Topic> searchTitleByKeywords(@Param("kw1") String kw1, Pageable pageable);

    @Query(value="select * from bbs_topic where (title like :kw1 or title like :kw2) and deleted=0 order by IF(title like :kw1, 100, 0) + IF(title like :kw2, 99, 0) desc, replies desc",
            countQuery = "select count(*) from bbs_topic where (title like :kw1 or title like :kw2) and deleted=0",
            nativeQuery = true)
    Page<Topic> searchTitleByKeywords(@Param("kw1") String kw1, @Param("kw2") String kw2, Pageable pageable);

    @Query(value="select * from bbs_topic where (title like :kw1 or title like :kw2 or title like :kw3) and deleted=0 " +
            "order by IF(title like :kw1, 100, 0) + IF(title like :kw2, 99, 0) + IF(title like :kw3, 98, 0) desc, replies desc",
            countQuery = "select count(*) from bbs_topic where (title like :kw1 or title like :kw2 or title like :kw3) and deleted=0",
            nativeQuery = true)
    Page<Topic> searchTitleByKeywords(@Param("kw1") String kw1, @Param("kw2") String kw2, @Param("kw3") String kw3, Pageable pageable);

    @Query(value="select * from bbs_topic where (title like :kw1 or title like :kw2 or title like :kw3 or title like :kw4) and deleted=0 " +
            "order by IF(title like :kw1, 100, 0) + IF(title like :kw2, 99, 0) + IF(title like :kw3, 98, 0) + IF(title like :kw4, 97, 0) desc, replies desc",
            countQuery = "select count(*) from bbs_topic where (title like :kw1 or title like :kw2 or title like :kw3 or title like :kw4) and deleted=0",
            nativeQuery = true)
    Page<Topic> searchTitleByKeywords(@Param("kw1") String kw1, @Param("kw2") String kw2, @Param("kw3") String kw3, @Param("kw4") String kw4, Pageable pageable);

    @Query(value="select * from bbs_topic where (title like :kw1 or title like :kw2 or title like :kw3 or title like :kw4 or title like :kw5) and deleted=0 " +
            "order by IF(title like :kw1, 100, 0) + IF(title like :kw2, 99, 0) + IF(title like :kw3, 98, 0) + IF(title like :kw4, 97, 0) + IF(title like :kw5, 96, 0) desc, replies desc",
            countQuery = "select count(*) from bbs_topic where (title like :kw1 or title like :kw2 or title like :kw3 or title like :kw4 or title like :kw5) and deleted=0",
            nativeQuery = true)
    Page<Topic> searchTitleByKeywords(@Param("kw1") String kw1, @Param("kw2") String kw2, @Param("kw3") String kw3, @Param("kw4") String kw4, @Param("kw5") String kw5, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Topic set category=:targetCategory where category=:sourceCategory")
    void moveCategoryToCategory(Category sourceCategory, Category targetCategory);

}
