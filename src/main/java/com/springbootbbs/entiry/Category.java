package com.springbootbbs.entiry;

import javax.persistence.*;

@Entity
@Table(name = "bbs_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 30)
    private String nameCn;

    @Column(length = 30)
    private String nameEn;

    @Column(length = 20)
    private String tab;

    @Column
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String name) {
        this.nameCn = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String name) {
        this.nameEn = name;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
