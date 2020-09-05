package com.springbootbbs.repository;

import com.springbootbbs.entiry.Admin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends PagingAndSortingRepository<Admin, Long> {

    public Admin findByUsername(String username);

    public Boolean existsByUsername(String username);

}
