package com.springbootbbs.repository;

import com.springbootbbs.entiry.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);

	public Boolean existsByUsername(String username);

}
