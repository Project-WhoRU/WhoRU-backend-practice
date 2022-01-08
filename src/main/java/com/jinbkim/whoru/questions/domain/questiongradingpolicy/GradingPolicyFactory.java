package com.jinbkim.whoru.questions.domain.questiongradingpolicy;

import com.jinbkim.whoru.questions.domain.question.QuestionType;

public class GradingPolicyFactory {
    public static GradingPolicy createGradingPolicy(QuestionType type) {
        switch (type) {
            case MULTIPLE_CHOICE:
                return new MultipleChoiceGradingPolicy();
            case OX:
                return new OXGradingPolicy();
            case SHORT_ANSWER:
                return new ShortAnswerGradingPolicy();
            default:
                return null;
        }
    }
}
