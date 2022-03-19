package com.jinbkim.whoru.contents.users.service;

import com.jinbkim.whoru.contents.questionlist.domain.QuestionList;
import com.jinbkim.whoru.contents.questionlist.repository.QuestionListRepository;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.repository.UserRepository;
import com.jinbkim.whoru.contents.users.web.dto.LoginDto;
import com.jinbkim.whoru.contents.users.web.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final QuestionListRepository questionListRepository;

    // create
    public Users createUser(SignUpDto user, QuestionList questionList) {
        Users users = Users.builder()
            .nickname(user.getNickname())
            .password(user.getPassword1())
            .questionList(questionList)
            .build();
        userRepository.save(users);
        return users;
    }


    // read
    public boolean isLoginPossible(LoginDto user) {
        Users users = this.userRepository.findByNickname(user.getNickname());
        return users != null && users.getNickname().equals(user.getNickname())
            && users.getPassword()
            .equals(user.getPassword());
    }


    // update
    public void setQuestionList(Users user, QuestionList questionList) {
        user.setQuestionList(questionList);
        userRepository.save(user);
    }

    public void questionListComplete(Users users) {
        QuestionList questionList = users.getQuestionList();
        questionList.setComplete(true);
        questionListRepository.save(questionList);
        userRepository.save(users);
    }
}