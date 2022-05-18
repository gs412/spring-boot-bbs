package com.springbootbbs.repository;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.entiry.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AttachRepository extends PagingAndSortingRepository<Attach, Long> {

    Attach findTopByOwnerIdAndOwnerType(Long ownerId, Attach.OwnerType ownerType);

    Attach findTopByOwnerIdAndOwnerTypeAndUser(Long ownerId, Attach.OwnerType ownerType, User user);

    //Attach findTopByOwnerIdAndOwnerTypeAndUserId(Long ownerId, Attach.OwnerType ownerType, Long userId);

    List<Attach> findAllByOwnerIdAndOwnerTypeAndUser(Long ownerId, Attach.OwnerType ownerType, User user);

}
