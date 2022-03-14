package com.jinbkim.whoru.contents.users.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Users {
    @Id
    protected String id;
    protected String nickname;
    protected String password;
    protected String testId;
}
