package com.springbootbbs.controller.admin;

import com.springbootbbs.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController extends com.springbootbbs.controller.BaseController {

    @Autowired
	AdminRepository adminRepository;

}
