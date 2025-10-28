package com.dev.lecture_B.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
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

    public Member(String nickName, String password, String email) {
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.del = false;   //기본값은 false로 설정 추후 아이디 삭제할 때 true로 변경
    }

    public void changeMember(String nickName, String email){
        if (nickName != null){
            this.nickName =nickName;
        }
        if (email != null){
            this.email = email;
        }

    }
}
