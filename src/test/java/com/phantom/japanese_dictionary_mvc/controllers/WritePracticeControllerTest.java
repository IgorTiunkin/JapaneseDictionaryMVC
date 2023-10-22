package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.replies.GrammarDictionaryReply;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.requests.WritePracticeRequest;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WritePracticeControllerTest extends BaseControllerTest{

    @Mock
    private NoteService noteService;

    @InjectMocks
    private WritePracticeController writePracticeController;

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(writePracticeController)
                .build();
    }

    private final String BASE_PATH = "/write-test";
    private final String PATH_TO_SHOW = BASE_PATH + "/show";

    private final String INDEX_VIEW_NAME = "writepractice/index";
    private final String SHOW_VIEW_NAME = "writepractice/multishow";

    private final Note TEST_NOTE_YAMAI = Note.builder()
            .id(1).translation("болезнь").romadji("yamai").kanji("病").hiragana("やまい")
            .build();
    private final Note TEST_NOTE_JOUDAN = Note.builder()
            .id(2).translation("шутка").romadji("joudan").kanji("冗談").hiragana("じょうだん")
            .build();


    @Test
    public void whenIndex_thenIndex() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_PATH).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(model.get("writePracticeRequest").getClass(), WritePracticeRequest.class);
    }

    @Test
    public void whenCorrectRequest_thenCorrectShow() throws Exception {
        WritePracticeRequest request = new WritePracticeRequest();
        Mockito.doReturn(List.of(TEST_NOTE_JOUDAN)).when(noteService).getRandomVariants(Mockito.anyInt());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("writePracticeRequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(SHOW_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        List<Note> noteList = (List<Note>) model.get("writePracticeList");
        Assertions.assertEquals(TEST_NOTE_JOUDAN, noteList.get(0));
    }

    @Test
    public void whenIncorrectRequest_thenIndex() throws Exception {
        WritePracticeRequest request = new WritePracticeRequest(RequestType.KANA, 0);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("writePracticeRequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
    }

}