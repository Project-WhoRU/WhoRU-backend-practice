package com.jinbkim.whoru.questions.domain.questiongradingpolicy;

public interface GradingPolicy<T> {
    boolean isCorrect(T answerSubmit, T answer);
}
