package com.springbootbbs.entiry;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "attaches")
public class Attach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 255)
    private String name;

    @Column(length = 255)
    private String path;

    @Column
    private Long size;

    @Column(length = 255)
    private String contentType;

    @Column
    private Long ownerId;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 255)
    private OwnerType ownerType;

    @Column
    private Long userId;

    @CreationTimestamp
    @Column(nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedAt;

    private MultipartFile multipartFile;

    public enum OwnerType {
        USER_FACE;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    private void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    private void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    private void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getOwneId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public OwnerType getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(OwnerType ownerType) {
        this.ownerType = ownerType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getcreatedAt() {
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


    public void setFile(MultipartFile file) {
        this.multipartFile = file;
    }

    public void upload() {
        MultipartFile file = this.multipartFile;

        this.setName(file.getOriginalFilename());
        this.setSize(file.getSize());
        this.setContentType(file.getContentType());

        
    }
}
