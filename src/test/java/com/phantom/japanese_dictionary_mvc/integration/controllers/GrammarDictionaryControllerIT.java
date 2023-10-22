package com.phantom.japanese_dictionary_mvc.integration.controllers;

import com.phantom.japanese_dictionary_mvc.controllers.GrammarDictionaryController;
import com.phantom.japanese_dictionary_mvc.dto.GrammarNoteDTO;
import com.phantom.japanese_dictionary_mvc.integration.BaseIT;
import com.phantom.japanese_dictionary_mvc.models.GrammarNote;
import com.phantom.japanese_dictionary_mvc.replies.GrammarDictionaryReply;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.GrammarNoteService;
import com.phantom.japanese_dictionary_mvc.util.GrammarDictionaryReplyConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

public class GrammarDictionaryControllerIT extends BaseIT {

    private final GrammarDictionaryController grammarDictionaryController;

    private final String BASE_PATH = "/grammar";
    private final String PATH_TO_SHOW = BASE_PATH + "/show";
    private final String PATH_TO_IMPORT = BASE_PATH + "/import";
    private final String PATH_TO_IMPORTING = BASE_PATH + "/importing";

    private final String INDEX_VIEW_NAME = "grammar/index";
    private final String SHOW_VIEW_NAME = "grammar/multishow";
    private final String IMPORT_VIEW_NAME = "grammar/import";

    private final GrammarNote TEST_GRAMMAR_NOTE = GrammarNote.builder()
            .grammarNoteId(1).source("Test source").rule("Test rule").explanation("Test explanation").example("Test example")
            .build();

    private MockMvc mvc;

    @Autowired
    public GrammarDictionaryControllerIT(GrammarDictionaryController grammarDictionaryController) {
        this.grammarDictionaryController = grammarDictionaryController;
    }

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(grammarDictionaryController)
                .build();
    }

    private GrammarDictionaryReply getDictionaryReply(String wordToFind, RequestType requestType) throws Exception {
        Request request = new Request(wordToFind, requestType, false);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("request", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(SHOW_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(model.get("grammarDictionaryReply").getClass(), GrammarDictionaryReply.class);
        assertEquals(model.get("currentPage"), 0);

        GrammarDictionaryReply dictionaryReplyResult = (GrammarDictionaryReply) model.get("grammarDictionaryReply");
        return dictionaryReplyResult;
    }

    @Test
    public void whenToShite_then3Partial() throws Exception {
        String wordToFind = "to shite";
        GrammarDictionaryReply dictionaryReplyResult = getDictionaryReply(wordToFind, RequestType.SPELLING);
        assertEquals(3, dictionaryReplyResult.getMatchCount());
        assertEquals(3, dictionaryReplyResult.getGrammarNoteDTOS().size());

        List<GrammarNoteDTO> grammarNoteDTOS = dictionaryReplyResult.getGrammarNoteDTOS();

        for (GrammarNoteDTO grammarNoteDTO : grammarNoteDTOS) {
            assertTrue(grammarNoteDTO.getRule().contains(wordToFind)||
                    grammarNoteDTO.getExample().contains(wordToFind));
        }
        assertTrue(grammarNoteDTOS.get(0).getRule().contains(wordToFind));
        assertTrue(!grammarNoteDTOS.get(2).getRule().contains(wordToFind)
        || grammarNoteDTOS.get(2).getExample().contains(wordToFind));

    }




}
