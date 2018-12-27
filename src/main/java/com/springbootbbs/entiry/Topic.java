package com.springbootbbs.entiry;


import com.springbootbbs.libs.Utils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "topics")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(length = 100)
	private String title;

	@Column
	private Integer replies;

	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	@Column(nullable = false)
	private Date createdAt;

	@Column(nullable = false)
	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getTitle(Integer len) {
		return Utils.subTextString(getTitle(), len);
	}

	public String getTitle(Integer len, String suffix) {
		return Utils.subTextString(getTitle(), len, suffix);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getReplies() {
		return replies;
	}

	public void setReplies(Integer replies) {
		this.replies = replies;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
