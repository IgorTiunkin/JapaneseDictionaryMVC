package com.phantom.japanese_dictionary_mvc.integration.controllers;

import com.phantom.japanese_dictionary_mvc.controllers.DictionaryController;
import com.phantom.japanese_dictionary_mvc.dto.NoteDTO;
import com.phantom.japanese_dictionary_mvc.integration.BaseIT;
import com.phantom.japanese_dictionary_mvc.replies.DictionaryReply;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import com.phantom.japanese_dictionary_mvc.util.DictionaryReplyConverter;
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
import static org.mockito.Mockito.doReturn;

public class DictionaryControllerIT extends BaseIT {
    private final DictionaryController dictionaryController;

    private final String BASE_PATH = "/dictionary";
    private final String PATH_TO_SHOW = BASE_PATH + "/show";
    private final String PATH_TO_IMPORT = BASE_PATH + "/import";
    private final String PATH_TO_IMPORTING = BASE_PATH + "/importing";

    private final String INDEX_VIEW_NAME = "dictionaries/index";
    private final String MULTISHOW_VIEW_NAME = "dictionaries/multishow";
    private final String IMPORT_VIEW_NAME = "dictionaries/import";

    private final NoteDTO TEST_NOTE_DTO = new NoteDTO("romaji", "kanji", "hiragana", "translation");

    private MockMvc mvc;

    @Autowired
    public DictionaryControllerIT(DictionaryController dictionaryController) {
        this.dictionaryController = dictionaryController;
    }

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(dictionaryController)
                .build();
    }

    @Test
    public void whenEmptyRequest_thenIndex() throws Exception {
        Request request = new Request("", RequestType.TRANSLATION, false);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("request", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
    }

    private DictionaryReply getDictionaryReply(String wordToFind, RequestType requestType, boolean onlyFullMatch) throws Exception {
        Request request = new Request(wordToFind, requestType, onlyFullMatch);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("request", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(MULTISHOW_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(model.get("dictionaryReply").getClass(), DictionaryReply.class);
        assertEquals(model.get("currentPage"), 0);

        DictionaryReply dictionaryReplyResult = (DictionaryReply) model.get("dictionaryReply");
        return dictionaryReplyResult;
    }

    @Test
    public void whenRussianCoffeeAndNotFullMatch_then4Partial() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("кофе", RequestType.TRANSLATION, false);
        assertEquals(3, dictionaryReplyResult.getFullMatchCount());
        assertEquals(1, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(4, dictionaryReplyResult.getNoteDTOS().size());
    }

    @Test
    public void whenRussianCoffeeAndFullMatch_then3Full() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("кофе", RequestType.TRANSLATION, true);
        assertEquals(3, dictionaryReplyResult.getFullMatchCount());
        assertEquals(0, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(3, dictionaryReplyResult.getNoteDTOS().size());
    }

    @Test
    public void whenRussianCoffeeAndWrongRequestType_then0Match() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("кофе", RequestType.KANA, false);
        assertEquals(0, dictionaryReplyResult.getFullMatchCount());
        assertEquals(0, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(0, dictionaryReplyResult.getNoteDTOS().size());
    }

    @Test
    public void whenRussianCoffeeAndDefaultRequestType_then4Partial() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("кофе", RequestType.DEFAULT, false);
        assertEquals(3, dictionaryReplyResult.getFullMatchCount());
        assertEquals(1, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(4, dictionaryReplyResult.getNoteDTOS().size());
    }

    @Test
    public void whenEnglishCoffeeAndNotFullMatch_then4Partial() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("koohii", RequestType.SPELLING, false);
        assertEquals(4, dictionaryReplyResult.getFullMatchCount());
        assertEquals(0, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(4, dictionaryReplyResult.getNoteDTOS().size());
    }

    @Test
    public void whenEnglishCoffeeAndWrongRequestType_then0Match() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("koohii", RequestType.KANJI, false);
        assertEquals(0, dictionaryReplyResult.getFullMatchCount());
        assertEquals(0, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(0, dictionaryReplyResult.getNoteDTOS().size());
    }

    @Test
    public void whenEnglishCoffeeAndDefaultRequestType_then4Match() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("koohii", RequestType.DEFAULT, false);
        assertEquals(4, dictionaryReplyResult.getFullMatchCount());
        assertEquals(0, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(4, dictionaryReplyResult.getNoteDTOS().size());
    }


    @Test
    public void whenKanaCoffeeAndNotFullMatch_then4Partial() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("コーヒー", RequestType.KANA, false);
        assertEquals(1, dictionaryReplyResult.getFullMatchCount());
        assertEquals(3, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(4, dictionaryReplyResult.getNoteDTOS().size());
    }

    @Test
    public void whenKanaCoffeeAndWrongRequestType_then0Match() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("コーヒー", RequestType.SPELLING, false);
        assertEquals(0, dictionaryReplyResult.getFullMatchCount());
        assertEquals(0, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(0, dictionaryReplyResult.getNoteDTOS().size());
    }

    @Test
    public void whenKanaCoffeeAndDefaultRequestType_then4Partial() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("コーヒー", RequestType.DEFAULT, false);
        assertEquals(1, dictionaryReplyResult.getFullMatchCount());
        assertEquals(3, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(4, dictionaryReplyResult.getNoteDTOS().size());
    }

    @Test
    public void whenKanjiOkawari_then1Partial() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("お代わり", RequestType.KANJI, false);
        assertEquals(0, dictionaryReplyResult.getFullMatchCount());
        assertEquals(1, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(1, dictionaryReplyResult.getNoteDTOS().size());
    }

    @Test
    public void whenKanjiOkawariAndWrongType_then0Match() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("お代わり", RequestType.SPELLING, false);
        assertEquals(0, dictionaryReplyResult.getFullMatchCount());
        assertEquals(0, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(0, dictionaryReplyResult.getNoteDTOS().size());
    }

    @Test
    public void whenKanjiOkawariAndDefaultType_then1Partial() throws Exception {
        DictionaryReply dictionaryReplyResult = getDictionaryReply("お代わり", RequestType.DEFAULT, false);
        assertEquals(0, dictionaryReplyResult.getFullMatchCount());
        assertEquals(1, dictionaryReplyResult.getPartialMatchCount());
        assertEquals(1, dictionaryReplyResult.getNoteDTOS().size());
    }

}
