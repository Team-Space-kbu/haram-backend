package com.space.haramspring.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserJwtDto {

    private String userId; // 유저아이디

    @Builder
    public UserJwtDto(String userId) {

        this.userId = userId;
    }
}
