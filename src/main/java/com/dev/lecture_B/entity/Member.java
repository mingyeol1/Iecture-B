package com.dev.lecture_B.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickName;
    private String password;
    private String email;

    private Set<RoleSet> RoleSet = new HashSet<>();
    private boolean del;

    //addRole 역할 추가.
    public void addRole (RoleSet role){
        this.RoleSet.add(role);
    }
}
