package com.springbootbbs.service;

import com.springbootbbs.entiry.User;
import com.springbootbbs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User的service,这里面的数据时静态数据，不查询数据库了
 * @author lenovo
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		return userRepository.save(user);
	}

	// 判断用户名是否存在
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	// 根据用户返回查询的密码
	public String selectPassword(String username) {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			return "";
		} else {
			return user.getPassword();
		}
	}

}
