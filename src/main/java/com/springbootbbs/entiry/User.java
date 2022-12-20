package com.springbootbbs.entiry;

import com.springbootbbs.repository.AttachRepository;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "bbs_user")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(length = 50)
	private String username;

	@Column(length = 50)
	private String password;

	@Column(length = 10)
	private String lang;

	@Column(nullable = false)
	private Boolean banned = false;

	@Enumerated(value = EnumType.STRING)
	@Column(length = 50)
	private IndexOrderBy indexOrderBy = IndexOrderBy.REPLIED_AT;

	@CreationTimestamp
	@Column(nullable = false)
	private Date createdAt;

	public enum IndexOrderBy {
		CREATED_AT,
		REPLIED_AT;
	}

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

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Boolean getBanned() {
		return banned;
	}

	public void setBanned(Boolean banned) {
		this.banned = banned;
	}

	public IndexOrderBy getIndexOrderBy() {
		return indexOrderBy;
	}

	public void setIndexOrderBy(IndexOrderBy indexOrderBy) {
		this.indexOrderBy = indexOrderBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
