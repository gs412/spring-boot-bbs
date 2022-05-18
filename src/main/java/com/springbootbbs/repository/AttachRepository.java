package com.springbootbbs.repository;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.entiry.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttachRepository extends PagingAndSortingRepository<Attach, Long> {

    Attach findTopByOwnerIdAndOwnerType(Long ownerId, Attach.OwnerType ownerType);

    Attach findTopByOwnerIdAndOwnerTypeAndUser(Long ownerId, Attach.OwnerType ownerType, User user);

    //Attach findTopByOwnerIdAndOwnerTypeAndUserId(Long ownerId, Attach.OwnerType ownerType, Long userId);

    List<Attach> findAllByOwnerIdAndOwnerTypeAndUser(Long ownerId, Attach.OwnerType ownerType, User user);

    @Query("update bbs_attach set owner_id = :ownerId where owner_id = :oldOwnerId and owner_type = :ownerType and user_id = :#{#user.getId()}")
    void updateOwnerIdByOwnerIdAndOwnerTypeAndUser(
            @Param("ownerId") Long ownerId,
            @Param("oldOwnerId") Long oldOwnerId,
            @Param("ownerType") Attach.OwnerType ownerType,
            @Param("user") User user);

}
