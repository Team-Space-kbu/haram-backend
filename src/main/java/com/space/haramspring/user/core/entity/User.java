package com.space.haramspring.user.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {
    private String id; // 유저아이디
    private char[] password; //유저 비밀번호
    private boolean disabled; //유저 활성상
    private Role role; //유저 권한
    private String joinDate; //가입날자
    private String loginDate; //마지막 로그인 날자

    @Builder
    public User(String id, char[] password, boolean disabled, Role role, String joinDate, String loginDate) {
        this.id = id;
        this.password = password;
        this.disabled = disabled;
        this.role = role;
        this.joinDate = joinDate;
        this.loginDate = loginDate;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
