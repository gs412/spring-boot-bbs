package com.springbootbbs.repository;

import com.springbootbbs.entiry.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	public User findByUsername(String username);

	public Boolean existsByUsername(String username);

	Page<User> findAllByBanned(Pageable page, Boolean banned);

}
