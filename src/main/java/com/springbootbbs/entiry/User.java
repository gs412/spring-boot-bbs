package com.springbootbbs.entiry;

import com.springbootbbs.repository.AttachRepository;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bbs_users")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(length = 50)
	private String username;

	@Column(length = 50)
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User() {

	}


    public Attach getUserFace(AttachRepository attachRepository) {
        return attachRepository.findTopByOwnerIdAndOwnerTypeAndUser(this.id, Attach.OwnerType.USER_FACE, this);
    }

    public String getUserFaceLink() {
        return "/attach/show_user_face/" + this.id;
    }
}
