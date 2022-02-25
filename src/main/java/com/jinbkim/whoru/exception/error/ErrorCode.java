package com.jinbkim.whoru.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    FOUND_NULL_VALUE(400, "NULL값이 존재하면 안됩니다"),
    MULTIPLE_CHOICE_NONE_EXAMPLES(400, "다지선다 문제에서 예시가 없으면 안됩니다"),
    TEST_DOESNT_EXIST(404, "테스트 아이디를 조회할 수 없습니다"),
    ANSWER_SIZE_IS_WRONG(400, "문제의 수와 제출한 정답의 수가 다릅니다"),
    GRADE_RESULT_DOESNT_EXIST(404, "채점 결과를 조회할 수 없습니다"),
    QUESTION_DOESNT_EXIST(404, "문제를 조회할 수 없습니다");

    private int status;
    private String message;
}