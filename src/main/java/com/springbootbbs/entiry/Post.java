package com.springbootbbs.entiry;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private Boolean isFirst;

    @Column(nullable = false)
    private Boolean deleted = false;

	@ManyToOne
	@JoinColumn(nullable = false, name = "topic_id")
	private Topic topic;

	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Boolean isFirst) {
		this.isFirst = isFirst;
	}

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
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

	public String getContentHtml() {
		String new_content = content.replace("<", "&lt;");
		new_content = new_content.replace(">", "&gt;");
		new_content = new_content.replace("\n", "<br>");
		new_content = new_content.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		new_content = new_content.replace(" ", "&nbsp;");

		return new_content;
	}
}
