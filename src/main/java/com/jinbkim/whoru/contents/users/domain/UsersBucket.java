package com.jinbkim.whoru.contents.users.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
public class UsersBucket {
    @Id
    private String id;
    private String nickname;
    private String password;
    private String testId;
}
