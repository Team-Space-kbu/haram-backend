package com.space.haramspring.user.dto;

import com.space.haramspring.core.entity.users.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDetailsDto {

    private String userId; // 유저 아이디
    private CharSequence password; //유저 비밀번호
    private Role role; // 유저 권한

    @Builder
    public UserDetailsDto(String userId, CharSequence password, Role role) {
        this.userId = userId;
        this.password = password;
        this.role = role;
    }
}
