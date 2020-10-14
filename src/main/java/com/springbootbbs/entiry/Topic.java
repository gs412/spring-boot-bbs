package com.springbootbbs.entiry;


import com.springbootbbs.libs.Utils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Boolean deleted = false;

    @CreationTimestamp
	@Column(nullable = false)
	private Date createdAt;

    @UpdateTimestamp
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
