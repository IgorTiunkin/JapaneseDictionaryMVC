package com.phantom.japanese_dictionary_mvc;

import com.phantom.japanese_dictionary_mvc.dto.QuizResultDTO;
import com.phantom.japanese_dictionary_mvc.finders.dictionary.*;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.GrammarFinderFactory;
import com.phantom.japanese_dictionary_mvc.finders.grammar.KanaGrammarFinder;
import com.phantom.japanese_dictionary_mvc.finders.grammar.RomajiGrammarFinder;
import com.phantom.japanese_dictionary_mvc.mappers.QuizResultQuizResultDTOMapper;
import com.phantom.japanese_dictionary_mvc.models.*;
import com.phantom.japanese_dictionary_mvc.replies.GrammarDictionaryReply;
import com.phantom.japanese_dictionary_mvc.repositories.NoteRepository;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import com.phantom.japanese_dictionary_mvc.services.PeopleService;
import com.phantom.japanese_dictionary_mvc.services.QuizResultsService;
import com.phantom.japanese_dictionary_mvc.replies.DictionaryReply;
import com.phantom.japanese_dictionary_mvc.util.GrammarDictionaryReplyConverter;
import com.phantom.japanese_dictionary_mvc.util.QuizResultChecker;
import com.phantom.japanese_dictionary_mvc.util.DictionaryReplyConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class JapaneseDictionaryMvcApplicationTests {
    private final WordFinderFactory wordFinderFactory;
    private final GrammarFinderFactory grammarFinderFactory;
    private final QuizResultChecker quizResultChecker;
    private Random random = new Random();
    private final NoteService noteService;
    private final QuizResultQuizResultDTOMapper quizResultQuizResultDTOMapper;
    private final DictionaryReplyConverter dictionaryReplyConverter;
    private final GrammarDictionaryReplyConverter grammarDictionaryReplyConverter;

    @Autowired
    JapaneseDictionaryMvcApplicationTests(WordFinderFactory wordFinderFactory, GrammarFinderFactory grammarFinderFactory,
                                          QuizResultChecker quizResultChecker, NoteService noteService,
                                          QuizResultQuizResultDTOMapper quizResultQuizResultDTOMapper,
                                          DictionaryReplyConverter dictionaryReplyConverter,
                                          GrammarDictionaryReplyConverter grammarDictionaryReplyConverter) {
        this.wordFinderFactory = wordFinderFactory;
        this.grammarFinderFactory = grammarFinderFactory;
        this.quizResultChecker = quizResultChecker;
        this.noteService = noteService;
        this.quizResultQuizResultDTOMapper = quizResultQuizResultDTOMapper;
        this.dictionaryReplyConverter = dictionaryReplyConverter;
        this.grammarDictionaryReplyConverter = grammarDictionaryReplyConverter;
    }


    @Test
    void contextLoads() {
    }

    /*






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
    public void whenKofe_then8FullAnd3Partial () {
        Request request = new Request();
        request.setWord("Кофе");
        request.setRequestType(RequestType.TRANSLATION);
        DictionaryReply dictionaryReply = dictionaryReplyConverter.getDictionaryReplyForCurrentPage(request, 0);
        Assertions.assertEquals(8, dictionaryReply.getFullMatchCount());
        Assertions.assertEquals(3, dictionaryReply.getPartialMatchCount());
        request.setOnlyFullMatch(true);
        dictionaryReply = dictionaryReplyConverter.getDictionaryReplyForCurrentPage(request, 0);
       Assertions.assertEquals(8, dictionaryReply.getFullMatchCount());
       Assertions.assertEquals(0, dictionaryReply.getPartialMatchCount());
    }

    @Test
    public void whenBoku_then2FullAnd27Partial () {
        Request request = new Request();
        request.setWord("Boku");
        request.setRequestType(RequestType.SPELLING);
        DictionaryReply dictionaryReply = dictionaryReplyConverter.getDictionaryReplyForCurrentPage(request,0);
        Assertions.assertEquals(2, dictionaryReply.getFullMatchCount());
        Assertions.assertEquals(27, dictionaryReply.getPartialMatchCount());
        request.setOnlyFullMatch(true);
        dictionaryReply = dictionaryReplyConverter.getDictionaryReplyForCurrentPage(request,0);
        Assertions.assertEquals(2, dictionaryReply.getFullMatchCount());
        Assertions.assertEquals(0, dictionaryReply.getPartialMatchCount());
    }

    @Test
    public void whenBakari_then26Matches () {
        Request request = new Request();
        request.setWord("bakari");
        request.setRequestType(RequestType.SPELLING);
        GrammarDictionaryReply grammarDictionaryReply = grammarDictionaryReplyConverter.getGrammarDictionaryReplyForCurrentPage(request, 0);
        Assertions.assertEquals(26, grammarDictionaryReply.getMatchCount());
        Assertions.assertEquals(2, grammarDictionaryReply.getIndexOfLastPage());

        grammarDictionaryReply = grammarDictionaryReplyConverter.getGrammarDictionaryReplyForCurrentPage(request, 1);
        Assertions.assertEquals(26, grammarDictionaryReply.getMatchCount());
        Assertions.assertEquals(2, grammarDictionaryReply.getIndexOfLastPage());

    }

    @Test
    public void whenToShite_then22Matches () {
        Request request = new Request();
        request.setWord("to shite");
        request.setRequestType(RequestType.SPELLING);
        GrammarDictionaryReply grammarDictionaryReply = grammarDictionaryReplyConverter.getGrammarDictionaryReplyForCurrentPage(request, 0);
        Assertions.assertEquals(22, grammarDictionaryReply.getMatchCount());
        Assertions.assertEquals(2, grammarDictionaryReply.getIndexOfLastPage());

        grammarDictionaryReply = grammarDictionaryReplyConverter.getGrammarDictionaryReplyForCurrentPage(request, 1);
        Assertions.assertEquals(22, grammarDictionaryReply.getMatchCount());
        Assertions.assertEquals(2, grammarDictionaryReply.getIndexOfLastPage());
    }*/


}
