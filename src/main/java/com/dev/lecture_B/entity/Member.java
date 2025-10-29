package com.dev.lecture_B.entity;

import jakarta.persistence.*;
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
    @Column(unique = true)
    private String nickname;
    private String password;
    @Column(unique = true)
    private String email;

    private Set<RoleSet> RoleSet = new HashSet<>();
    private boolean del;

    //addRole 역할 추가.
    public void addRole (RoleSet role){
        this.RoleSet.add(role);
    }

    public Member(String nickname, String password, String email) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.del = false;   //기본값은 false로 설정 추후 아이디 삭제할 때 true로 변경
    }

    public void changeMember(String nickname, String email){
        if (nickname != null){
            this.nickname =nickname;
        }
        if (email != null){
            this.email = email;
        }

    }
}
