package com.jinbkim.whoru.questions.domain.questiongradingpolicy;

public class OXGradingPolicy implements GradingPolicy<Boolean>{
    @Override
    public boolean isCorrect(Boolean answerSubmit, Boolean answer) {
        return answer == answerSubmit;
    }
}
