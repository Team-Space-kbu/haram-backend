package com.space.haramspring.user.core.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_USER", "관리자"),
    STUDENT_COUNCIL("ROLE_USER", "총학생"),
    CLUB_OFFICERS("ROLE_USER", "동아리임원");
    private final String key;
    private final String title;
}
