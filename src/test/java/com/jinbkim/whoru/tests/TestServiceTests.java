package com.jinbkim.whoru.tests;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestServiceTests {

//    @Autowired
//    TestRepository testRepository;
//    @Autowired
//    TestService testService;
//    @Autowired
//    QuestionRepository questionRepository;
//
//    @Test
//    @DisplayName("addTest : 정상적인 테스트 생성시")
//    public void addTestCorrect() {
//        // given
//        TestAddRequestDto testAddRequestDto = new TestAddRequestDtoCorrect();
//
//        // when
//        TestAddResponseDto testAddResponseDto = testService.addTest(testAddRequestDto);
//
//        // then
//        Tests tests = testRepository.findById(testAddResponseDto.getTestId()).get();
//        Assertions.assertEquals(testAddRequestDto.getNickname(), tests.getNickname());
//        Assertions.assertEquals(testAddRequestDto.getQuestions().size(), tests.getQuestionIds().size());
//    }
//
//    @Test
//    @DisplayName("addTest : 닉네임 없이 테스트를 만들때 예외 발생")
//    public void addTestNoNickname() {
//        // given
//        // 닉네임이 없는 테스트 생성
//        TestAddRequestDto testAddRequestDto = new TestAddRequestDtoNoNickname();
//
//        // when
//        // then
//        Assertions.assertThrows(Exception.class, () -> {
//            testService.addTest(testAddRequestDto);
//        });
//    }
//
//    @Test
//    @DisplayName("addTest : 잘못된 질문지로 테스트를 만들때 예외 발생")
//    public void addTestWringQuestions() {
//        // given
//        // 잘못된 질문이 존재하는 테스트 생성
//        TestAddRequestDto testAddRequestDto = new TestAddRequestDtoWrongQuestions();
//
//        // when
//        // then
//        Assertions.assertThrows(Exception.class, () -> {
//            TestAddResponseDto testAddResponseDto = testService.addTest(testAddRequestDto);
//        });
//    }
//
//    @Test
//    @DisplayName("findTest : 정상적인 테스트 조회시")
//    public void findTestCorrect() {
//        // given
//        TestAddRequestDto testAddRequestDto = new TestAddRequestDtoCorrect();
//        TestAddResponseDto testAddResponseDto = testService.addTest(testAddRequestDto);
//
//        // when
//        TestFindResponseDto testFindResponseDto = testService.findTest(testAddResponseDto.getTestId());
//
//        // then
//        List<QuestionDto> questionsRequest = testAddRequestDto.getQuestions();
//        List<QuestionDto> questionsRespose = testFindResponseDto.getQuestions();
//        Assertions.assertEquals(questionsRequest.size(), questionsRespose.size());
//        for(int i=0; i<questionsRequest.size(); i++) {
//            Assertions.assertEquals(questionsRequest.get(i).getQuestion(), questionsRespose.get(i).getQuestion());
//            Assertions.assertEquals(questionsRequest.get(i).getExamples(), questionsRespose.get(i).getExamples());
//        }
//    }
//
//    @Test
//    @DisplayName("findTest : 존재하지 않는 테스트를 조회할때 예외발생")
//    public void findTestWrongTestId() {
//        // given
//
//        // when
//        // then
//        Assertions.assertThrows(Exception.class, () -> {
//            testService.findTest("wrongId");
//        });
//    }
//
//    @Test
//    @DisplayName("gradeTest : 정상적인 테스트 채점시")
//    public void gradeTestCorrect() {
//        // given
//        // 정상적인 테스트 생성
//        TestAddRequestDto testAddRequestDto = new TestAddRequestDtoCorrect();
//        TestAddResponseDto testAddResponseDto = testService.addTest(testAddRequestDto);
//        Tests tests = testRepository.findById((testAddResponseDto.getTestId())).get();
//        // 테스트아이디와, 정답들로 TestGradeRequestDto 생성
//        List<String> answerSubmit = new ArrayList<>();
//        for(String questionId : tests.getQuestionIds())
//            answerSubmit.add(questionRepository.findById(questionId).get().getAnswer());
//        TestGradeRequestDto testGradeRequestDto = new TestGradeRequestDto(tests.getId(), answerSubmit);
//
//        // when
//        TestGradeResponseDto testGradeResponseDto = testService.gradeTest(testGradeRequestDto);
//
//        // then
//        Assertions.assertEquals(tests.getQuestionIds().size(), testGradeResponseDto.getQuestionsCount());
//        Assertions.assertEquals(tests.getQuestionIds().size(), testGradeResponseDto.getAnswersCount());
//    }
//
//    @Test
//    @DisplayName("gradeTest : 테스트의 답의 개수가 다를때의 예외발생")
//    public void gradeTestWrongAnswerCount() {
//        // given
//        // 정상적인 테스트 생성
//        TestAddRequestDto testAddRequestDto = new TestAddRequestDtoCorrect();
//        TestAddResponseDto testAddResponseDto = testService.addTest(testAddRequestDto);
//        Tests tests = testRepository.findById((testAddResponseDto.getTestId())).get();
//        // 테스트아이디와, 정답들로 TestGradeRequestDto 생성
//        List<String> answerSubmit = new ArrayList<>();
//        for(String questionId : tests.getQuestionIds())
//            answerSubmit.add(questionRepository.findById(questionId).get().getAnswer());
//        answerSubmit.add("one more"); // 일부러 틀리게 정답개수를 하나 더 넣음
//        TestGradeRequestDto testGradeRequestDto = new TestGradeRequestDto(tests.getId(), answerSubmit);
//
//        // when
//        // then
//        Assertions.assertThrows(Exception.class, () -> {
//            testService.gradeTest(testGradeRequestDto);
//        });
//    }
//
//    @Test
//    @DisplayName("gradeTest : 존재하지 않는 테스트를 채점 할때 예외발생")
//    public void gradeTestWrongTestId() {
//        // given
//        // 존재하지 않은 테스트 아이디를 가진 테스트 생
//        TestGradeRequestDto testGradeRequestDto = new TestGradeRequestDto("wrondId", null);
//
//        // when
//        // then
//        Assertions.assertThrows(Exception.class, () -> {
//            testService.gradeTest(testGradeRequestDto);
//        });
//    }
//
//    @Test
//    @DisplayName("removeTest : 정상적인 테스트 제거시")
//    public void removeTestCorrect() {
//        // given
//        // 정상적인 테스트 생성
//        TestAddRequestDto testAddRequestDto = new TestAddRequestDtoCorrect();
//        TestAddResponseDto testAddResponseDto = testService.addTest(testAddRequestDto);
//
//        // when
//        // then
//        // 테스트 제거 전 후 확
//        Assertions.assertEquals(testRepository.findById(testAddResponseDto.getTestId()).isEmpty(), false);
//        testService.removeTest(testAddResponseDto.getTestId());
//        Assertions.assertEquals(testRepository.findById(testAddResponseDto.getTestId()).isEmpty(), true);
//    }
//
//    @Test
//    @DisplayName("removeTest : 존재하지 않는 테스트를 제거하려 할때 예외 발생")
//    public void removeTestWrongTestId() {
//        // given
//
//        // when
//        // then
//        // 테스트 제거 전 후 확인
//        Assertions.assertThrows(Exception.class, () -> {
//            testService.removeTest("wrongId");
//        });
//    }
}
