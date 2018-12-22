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

	// 用户的集合
	private List<User> users = new ArrayList<>();

	@Autowired
	UserRepository userRepository;

	public UserService() {
		// 从数据库查出来的用户名，密码，这是是静态数据(密码是123456)
		users.add(new User("admin", "038bdaf98f2037b31f1e75b5b4c9b26e"));
		users.add(new User("user", "098d2c478e9c11555ce2823231e02ec1"));
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
