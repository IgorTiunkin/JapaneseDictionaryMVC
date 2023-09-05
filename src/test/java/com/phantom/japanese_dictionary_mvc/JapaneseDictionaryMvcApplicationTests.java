package com.phantom.japanese_dictionary_mvc;

import com.phantom.japanese_dictionary_mvc.dto.QuizResultDTO;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.*;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinderFactory;
import com.phantom.japanese_dictionary_mvc.finders.grammar.KanaGrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.RomajiGrammarFinder;
import com.phantom.japanese_dictionary_mvc.mappers.QuizResultQuizResultDTOMapper;
import com.phantom.japanese_dictionary_mvc.models.*;
import com.phantom.japanese_dictionary_mvc.repositories.NoteRepository;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import com.phantom.japanese_dictionary_mvc.services.PeopleService;
import com.phantom.japanese_dictionary_mvc.services.QuizResultsService;
import com.phantom.japanese_dictionary_mvc.util.QuizResultChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class JapaneseDictionaryMvcApplicationTests {
    private final WordFinderFactory wordFinderFactory;
    private final GrammarFinderFactory grammarFinderFactory;
    private final QuizResultChecker quizResultChecker;
    private Random random = new Random();
    private final NoteService noteService;
    private final PeopleService peopleService;
    private final QuizResultsService quizResultsService;
    private final QuizResultQuizResultDTOMapper quizResultQuizResultDTOMapper;

    @Autowired
    JapaneseDictionaryMvcApplicationTests(WordFinderFactory wordFinderFactory, GrammarFinderFactory grammarFinderFactory, QuizResultChecker quizResultChecker, NoteRepository noteRepository, NoteService noteService, PeopleService peopleService, QuizResultsService quizResultsService, QuizResultQuizResultDTOMapper quizResultQuizResultDTOMapper) {
        this.wordFinderFactory = wordFinderFactory;
        this.grammarFinderFactory = grammarFinderFactory;
        this.quizResultChecker = quizResultChecker;
        this.noteService = noteService;
        this.peopleService = peopleService;
        this.quizResultsService = quizResultsService;
        this.quizResultQuizResultDTOMapper = quizResultQuizResultDTOMapper;
    }


    @Test
    void contextLoads() {
    }

    @Test
    public void whenRussian_thenRussianWordFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append((char)('а'+random.nextInt(33)));
        }
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        Assertions.assertTrue(wordFinder instanceof RussianWordFinder);
    }

    @Test
    public void whenEnglish_thenEnglishWordFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append((char)('a'+random.nextInt(26)));
        }
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        Assertions.assertTrue(wordFinder instanceof RomajiWordFinder);
    }
    @Test
    public void whenKana_thenKanaWordFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++){
            stringBuilder.append((char)('\u3040'+ random.nextInt(20)));
            stringBuilder.append((char)('\u30a0'+ random.nextInt(20)));
        }
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        Assertions.assertTrue(wordFinder instanceof KanaWordFinder);
    }


    @Test
    public void whenKanji_thenKanjiWordFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++){
            stringBuilder.append((char)('\u4e00'+ random.nextInt(100)));
        }
        stringBuilder.append("漢字日本語文字言語");
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        WordFinder wordFinder = wordFinderFactory.getInstance(request);
        Assertions.assertTrue(wordFinder instanceof KanjiWordFinder);
    }

    @Test
    public void whenEnglish_thenEnglishGrammarFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append((char)('a'+random.nextInt(26)));
        }
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        GrammarFinder grammarFinder = grammarFinderFactory.getInstance(request);
        Assertions.assertTrue(grammarFinder instanceof RomajiGrammarFinder);
    }

    @Test
    public void whenKana_thenKanaGrammarFinder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++){
            stringBuilder.append((char)('\u3040'+ random.nextInt(20)));
            stringBuilder.append((char)('\u30a0'+ random.nextInt(20)));
        }
        Request request = new Request();
        request.setWord(stringBuilder.toString());
        GrammarFinder grammarFinder = grammarFinderFactory.getInstance(request);
        Assertions.assertTrue(grammarFinder instanceof KanaGrammarFinder);
    }

    @Test
    public void whenUserAnswersNull_thenDefaultCollection () {
        List <QuizTask> mockTask = new ArrayList<>();
        String stubAnswer = "-1";
        for (int i = 0; i < 10; i++) {
            QuizTask quizTask = new QuizTask();
            quizTask.setRightAnswer(stubAnswer);
            mockTask.add(quizTask);
        }
        List <Answer> answers = quizResultChecker.createUserAnswersForCheck(mockTask, null);
        for (int i = 0; i < 10;i++) {
            Assertions.assertEquals(stubAnswer, answers.get(i).getAnswer());
        }

    }

    @Test
    public void whenPartialList_thenNoExceptions () {
        List <QuizTask> mockTask = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            QuizTask quizTask = new QuizTask();
            quizTask.setRightAnswer("-1");
            mockTask.add(quizTask);
        }
        List <Answer> list = new ArrayList<>();
        list.add(null);
        list.add(new Answer("2"));
        list.add(null);
        list.add(new Answer("3"));
        list.add(null);
        list.add(new Answer("4"));
        List <Answer> answers = quizResultChecker.createUserAnswersForCheck(mockTask, list);
        Assertions.assertEquals("-1",answers.get(0).getAnswer());
        Assertions.assertEquals("2",answers.get(1).getAnswer());
        Assertions.assertEquals("-1",answers.get(2).getAnswer());
        Assertions.assertEquals("3",answers.get(3).getAnswer());
        Assertions.assertEquals("-1",answers.get(4).getAnswer());
        Assertions.assertEquals("4",answers.get(5).getAnswer());
        Assertions.assertEquals("-1",answers.get(6).getAnswer());
        Assertions.assertEquals("-1",answers.get(7).getAnswer());
        Assertions.assertEquals("-1",answers.get(8).getAnswer());
        Assertions.assertEquals("-1",answers.get(9).getAnswer());


    }
    @Test
    public void whenRussianWords_thenCorrectFullMatch () {
        WordFinder wordFinder = new RussianWordFinder(noteService);
        Note noteToTest = new Note();
        noteToTest.setTranslation("тест");
        Assertions.assertTrue(wordFinder.checkFullMatch("тест", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тест1", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тес", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("ест", noteToTest));
        noteToTest.setTranslation("тест ");
        Assertions.assertTrue(wordFinder.checkFullMatch("тест", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тест1", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тес", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("ест", noteToTest));
        noteToTest.setTranslation(" тест");
        Assertions.assertTrue(wordFinder.checkFullMatch("тест", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тест1", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тес", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("ест", noteToTest));
        noteToTest.setTranslation(" тест ");
        Assertions.assertTrue(wordFinder.checkFullMatch("тест", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тест1", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тес", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("ест", noteToTest));
        noteToTest.setTranslation("тест-");
        Assertions.assertTrue(wordFinder.checkFullMatch("тест", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тест1", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тес", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("ест", noteToTest));
        noteToTest.setTranslation("тест,");
        Assertions.assertTrue(wordFinder.checkFullMatch("тест", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тест1", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тес", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("ест", noteToTest));
        noteToTest.setTranslation(",тест ");
        Assertions.assertTrue(wordFinder.checkFullMatch("тест", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тест1", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тес", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("ест", noteToTest));
        noteToTest.setTranslation(",тест,");
        Assertions.assertTrue(wordFinder.checkFullMatch("тест", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тест1", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("тес", noteToTest));
        Assertions.assertFalse(wordFinder.checkFullMatch("ест", noteToTest));

    }

   @Test
   public void givenUserAnswers_thenFailedTasks() {
       List <QuizTask> quizTasks = new ArrayList<>();
       List <Answer> userAnswers = new ArrayList<>();
       List <FailedQuizTask> mockFailedQuizTasks = new ArrayList<>();
       for (int i = 0; i < 5; i++) {
           String testQuestion = String.valueOf(random.nextInt(100));
           String testAnswer = String.valueOf(random.nextInt(100));
           QuizTask quizTask = new QuizTask();
           quizTask.setRightAnswer(testAnswer);
           quizTask.setQuestion(testQuestion);
           quizTasks.add(quizTask);
           Answer answer = new Answer();
           if (i%2==0) {
               answer.setAnswer(testAnswer);
           } else {
               answer.setAnswer(testAnswer + "1");
               FailedQuizTask failedQuizTask = new FailedQuizTask();
               failedQuizTask.setFailedQuestion(testQuestion);
               mockFailedQuizTasks.add(failedQuizTask);
           }
           userAnswers.add(answer);
       }
       List <FailedQuizTask> realFailedQuizTasks = quizResultChecker.
               createQuizResultForSave(0, quizTasks, userAnswers, null)
               .getFailedQuizTasks();
       Assertions.assertEquals(mockFailedQuizTasks.size(), realFailedQuizTasks.size());
       for (int i = 0; i < mockFailedQuizTasks.size(); i++) {
           Assertions.assertEquals(mockFailedQuizTasks.get(i).getFailedQuestion(),
                   realFailedQuizTasks.get(i).getFailedQuestion());
       }

   }

   @Test
   public void whenQuizResult_thenQuizDTO () {
       QuizResult quizResult = new QuizResult();
       quizResult.setQuizResultId(1);
       quizResult.setNumberOfRightAnswers(2);
       quizResult.setNumberOfTasks(5);
       quizResult.setDateOfQuiz(LocalDateTime.of(2023, 9, 5, 16, 55, 15));
       FailedQuizTask failedQuizTask = new FailedQuizTask();
       failedQuizTask.setFailedQuizTaskId(2);
       failedQuizTask.setFailedQuestion("Test");
       List <FailedQuizTask> failedQuizTasks = new ArrayList<>();
       failedQuizTasks.add(failedQuizTask);
       quizResult.setFailedQuizTasks(failedQuizTasks);
       QuizResultDTO quizResultDTO = quizResultQuizResultDTOMapper.quizResultToQuizResultDTO(quizResult);
       Assertions.assertEquals("05-09-2023 16:55:15", quizResultDTO.getDateOfQuiz());
       Assertions.assertEquals(1, quizResultDTO.getQuizResultId());
       Assertions.assertEquals(2, quizResultDTO.getNumberOfRightAnswers());
       Assertions.assertEquals(5, quizResultDTO.getNumberOfTasks());
       Assertions.assertEquals(2, quizResultDTO.getFailedQuizTasks().get(0).getFailedQuizTaskId());
       Assertions.assertEquals("Test", quizResultDTO.getFailedQuizTasks().get(0).getFailedQuestion());


   }



}
