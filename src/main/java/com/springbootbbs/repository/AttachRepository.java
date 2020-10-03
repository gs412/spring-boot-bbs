package com.springbootbbs.repository;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.entiry.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttachRepository extends PagingAndSortingRepository<Attach, Long> {

    Attach findByOwnerIdAndOwnerTypeAndUser(Long ownerId, Attach.OwnerType ownerType, User user);

}
