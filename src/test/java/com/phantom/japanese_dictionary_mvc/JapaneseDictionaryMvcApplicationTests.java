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

    @Test
    void contextLoads() {
    }


}
