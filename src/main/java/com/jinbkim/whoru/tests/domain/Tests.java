package com.jinbkim.whoru.tests.domain;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@Builder
public class Tests {
    @Id
    private String id;

    @NotBlank
    private String nickname;

    private List<String> questionIds;
}
