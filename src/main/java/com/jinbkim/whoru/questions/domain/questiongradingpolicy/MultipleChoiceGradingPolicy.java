package com.jinbkim.whoru.questions.domain.questiongradingpolicy;

public class MultipleChoiceGradingPolicy implements GradingPolicy<Integer>{
    @Override
    public boolean isCorrect(Integer answerSubmit, Integer answer) {
        return answer == answerSubmit;
    }
}
