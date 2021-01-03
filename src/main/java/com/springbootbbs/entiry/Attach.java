package com.springbootbbs.entiry;

import com.springbootbbs.libs.Utils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "bbs_attach")
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

    @ManyToOne
    @JoinColumn(nullable = false, name="user_id")
    private User user;

    @CreationTimestamp
    @Column(nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updatedAt;

    @Transient
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

    public void setPath(String path) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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


    // 下面的方法跟属性关系不大

    public String getSuffix() {
        return this.name.substring(this.name.lastIndexOf(".") + 1);
    }

    public void setFile(MultipartFile file) {
        this.multipartFile = file;
    }

    public Boolean upload() {
        MultipartFile file = this.multipartFile;

        this.setName(file.getOriginalFilename());
        this.setSize(file.getSize());
        this.setContentType(file.getContentType());

        String datePath = new SimpleDateFormat("yyyy/MM/dd/HHmmss").format(new Date());
        Random rand = new Random();
        Integer randInt = rand.nextInt(9999 - 1000 + 1) + 1000;
        String filePath = this.getOwnerType() + "/" + datePath + "_" + randInt.toString() + "." + this.getSuffix();

        this.setPath(filePath);

        String newFileStr = this.getBaseDir() +  filePath;
        File newFile = new File(newFileStr);

        File newFilePath = new File(newFile.getParentFile().getAbsolutePath());
        if (!newFilePath.exists() || !newFilePath.isDirectory()) {
            newFilePath.mkdirs();
        }

        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            System.out.println(e.toString());
            System.out.println("上传第文件失败");
            return false;
        }

        return true;
    }

    public Boolean deleteFileFromDisk() {
        File file = new File(this.getAbsolutePath());
        return file.delete();
    }

    public String getBaseDir() {
        return Utils.getBasePath() + "/upload/";
    }

    public String getAbsolutePath() {
        return getBaseDir() + this.getPath();
    }

    public String getLink() {
        return "/attach/show/" + this.id;
    }

    public static String getLinkById(Long id) {
        return "/attach/show/" + id;
    }
}
