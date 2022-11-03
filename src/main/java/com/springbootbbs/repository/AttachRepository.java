package com.springbootbbs.repository;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.entiry.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AttachRepository extends PagingAndSortingRepository<Attach, Long> {

    Attach findTopByOwnerIdAndOwnerType(Long ownerId, Attach.OwnerType ownerType);

    Attach findTopByOwnerIdAndOwnerTypeAndUser(Long ownerId, Attach.OwnerType ownerType, User user);

    //Attach findTopByOwnerIdAndOwnerTypeAndUserId(Long ownerId, Attach.OwnerType ownerType, Long userId);

    List<Attach> findAllByUser(User user);

    List<Attach> findAllByOwnerIdAndOwnerTypeAndUser(Long ownerId, Attach.OwnerType ownerType, User user);

    @Modifying
    @Transactional
    @Query("update Attach set ownerId = :ownerId where ownerId = :oldOwnerId and ownerType = :ownerType and user = :user")
    void updateOwnerIdByOwnerIdAndOwnerTypeAndUser(
            @Param("ownerId") Long ownerId,
            @Param("oldOwnerId") Long oldOwnerId,
            @Param("ownerType") Attach.OwnerType ownerType,
            @Param("user") User user);

}
