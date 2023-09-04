package com.phantom.japanese_dictionary_mvc;

import com.phantom.japanese_dictionary_mvc.finders.dictionary.*;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinderFactory;
import com.phantom.japanese_dictionary_mvc.finders.grammar.KanaGrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.RomajiGrammarFinder;
import com.phantom.japanese_dictionary_mvc.models.Answer;
import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.models.Person;
import com.phantom.japanese_dictionary_mvc.models.QuizTask;
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

    @Autowired
    JapaneseDictionaryMvcApplicationTests(WordFinderFactory wordFinderFactory, GrammarFinderFactory grammarFinderFactory, QuizResultChecker quizResultChecker, NoteRepository noteRepository, NoteService noteService, PeopleService peopleService, QuizResultsService quizResultsService) {
        this.wordFinderFactory = wordFinderFactory;
        this.grammarFinderFactory = grammarFinderFactory;
        this.quizResultChecker = quizResultChecker;
        this.noteService = noteService;
        this.peopleService = peopleService;
        this.quizResultsService = quizResultsService;
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
    public void whenUserId3_thenSizeOfAnswers2() {
        Person user = new Person();
        user.setPersonId(3);
        Assertions.assertEquals(2, quizResultsService.getQuizResultsByUser(user).size());
    }

}
