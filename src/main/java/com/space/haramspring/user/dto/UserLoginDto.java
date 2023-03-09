package com.space.haramspring.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginDto {
    private String userId;
    private CharSequence password;
}
