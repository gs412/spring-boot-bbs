package com.springbootbbs;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

	private List<User> users = new ArrayList<>();

	public UserService() {
		users.add(new User("admin", "038bdaf98f2037b31f1e75b5b4c9b26e"));
		users.add(new User("user", "098d2c478e9c11555ce2823231e02ec1"));
	}

	public boolean selectUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}

		return false;
	}

	public String selectPassword(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user.getPassword();
			}
		}
		return "";
	}

}
