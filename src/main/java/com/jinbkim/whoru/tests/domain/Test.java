package com.jinbkim.whoru.tests.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Test {
    @Id
    private String id;

    private String nickname;
    private List<String> questionIds;
}
