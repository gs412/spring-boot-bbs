package com.springbootbbs.entiry;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Integer size;

    @Column(length = 255)
    private String content_type;

    @Column
    private Integer owner_id;

    @Column(length = 255)
    private String owner_type;

    @Column
    private Integer user_id;

    @CreationTimestamp
    @Column(nullable = false)
    private Date created_at;

    @UpdateTimestamp
    @Column(nullable = false)
    private Date updated_at;

}
