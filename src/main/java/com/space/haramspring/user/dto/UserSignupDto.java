package com.space.haramspring.user.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class UserSignupDto {

    private Long userSeqNum; // 유저 고유 번호
    private String userId; // 유저아이디
    private String email; // 유저 이메일
    private String nickname; // 유저 닉네임
    private char[] password; //유저 비밀번호

    public UserSignupDto(Long userSeqNum, String userId, String email, String nickname, char[] password) {
        this.userSeqNum = userSeqNum;
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
