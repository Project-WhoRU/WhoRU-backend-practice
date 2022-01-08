package com.jinbkim.whoru.questions.domain.questiongradingpolicy;

public class ShortAnswerGradingPolicy implements GradingPolicy<String>{
    @Override
    public boolean isCorrect(String answerSubmit, String answer) {
        return answer.equals(answerSubmit);
    }
}
