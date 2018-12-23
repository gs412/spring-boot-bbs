package com.springbootbbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User的service,这里面的数据时静态数据，不查询数据库了
 * @author lenovo
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 创建用户
	 */
	public User createUser(User user) {
		return userRepository.save(user);
	}

	// 判断是否用户名是否存在
	public boolean selectUsername(String username) {
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
