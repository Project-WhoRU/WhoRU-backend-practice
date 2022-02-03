package com.jinbkim.whoru.tests.domain;

import java.util.ArrayList;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    private String nickname;
    private List<String> questionIds = new ArrayList<>();
    private Boolean completed = false;
}
