package com.jinbkim.whoru.contents.questionlist.service;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.questionlist.domain.Question;
import com.jinbkim.whoru.contents.questionlist.domain.QuestionList;
import com.jinbkim.whoru.contents.questionlist.repository.QuestionListRepository;
import com.jinbkim.whoru.contents.questionlist.repository.QuestionRepository;
import com.jinbkim.whoru.contents.questionlist.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.questionlist.web.dto.QuestionSolveDto;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionListService {

    private final QuestionListRepository questionListRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    // create
    public QuestionList createQuestionList() {
        QuestionList questionList = new QuestionList();
        questionListRepository.save(questionList);
        return questionList;
    }

    public QuestionSolveDto createQuestionSolveDto(Integer currentPage, GradeResult gradeResult) {
        Users users = gradeResult.getUsers();
        QuestionList questionList = users.getQuestionList();
        Integer totalPage = questionList.getQuestions().size();
        Question question = questionList.getQuestions().get(currentPage - 1);
        QuestionSolveDto questionSolveDto = QuestionSolveDto.builder()
            .totalPage(gradeResult.getQuestionsCount())
            .question(question)
            .currentPage(currentPage)
            .build();

        if (currentPage != 1)  // 첫번째 페이지가 아닐때만 이전페이지로 이동 가능
        {
            questionSolveDto.setPrevPage(currentPage - 1);
        }
        if (currentPage == totalPage)  // 마지막 페이지일때
        {
            questionSolveDto.setIsLastPage(true);
        } else  // 마지막 페이지가 아닐때 다음페이지로 이동 가능
        {
            questionSolveDto.setNextPage(currentPage + 1);
        }

        return questionSolveDto;
    }

    public Question createQuestion(QuestionDto QuestionDto) {
        Question question = Question.builder()
            .type(QuestionDto.getType())
            .question(QuestionDto.getQuestion())
            .examples(QuestionDto.getExamples())
            .answer(QuestionDto.getAnswer())
            .build();
        questionRepository.save(question);
        return question;
    }


    // update
    public void addQuestion(QuestionList questionList, Question question) {
        questionList.addQuestion(question);
        questionListRepository.save(questionList);
    }


    // delete
    public void deleteQuestionList(Users users) {
        this.questionListRepository.delete(users.getQuestionList());
        users.setQuestionList(null);
        this.userRepository.save(users);
    }
}