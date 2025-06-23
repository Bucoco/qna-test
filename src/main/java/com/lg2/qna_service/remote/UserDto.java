package com.lg2.qna_service.remote;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String profileImage;
}
