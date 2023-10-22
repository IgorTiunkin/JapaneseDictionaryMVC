package com.phantom.japanese_dictionary_mvc.integration.controllers;

import com.phantom.japanese_dictionary_mvc.controllers.WritePracticeController;
import com.phantom.japanese_dictionary_mvc.integration.BaseIT;
import com.phantom.japanese_dictionary_mvc.models.Note;
import com.phantom.japanese_dictionary_mvc.requests.WritePracticeRequest;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

public class WritePracticeControllerIT extends BaseIT {

    private final WritePracticeController writePracticeController;

    private final String BASE_PATH = "/write-test";
    private final String PATH_TO_SHOW = BASE_PATH + "/show";

    private final String INDEX_VIEW_NAME = "writepractice/index";
    private final String SHOW_VIEW_NAME = "writepractice/multishow";


    private MockMvc mvc;

    @Autowired
    public WritePracticeControllerIT(WritePracticeController writePracticeController) {
        this.writePracticeController = writePracticeController;
    }

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(writePracticeController)
                .build();
    }

    @Test
    public void whenRequest_thenCorrectShow() throws Exception {
        WritePracticeRequest request = new WritePracticeRequest();
        request.setQuantity(3);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("writePracticeRequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(SHOW_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        List<Note> noteList = (List<Note>) model.get("writePracticeList");
        Assertions.assertEquals(3, noteList.size());
    }

    @Test
    public void whenDefaultRequest_thenCorrectShow() throws Exception {
        WritePracticeRequest request = new WritePracticeRequest();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("writePracticeRequest", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(SHOW_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        List<Note> noteList = (List<Note>) model.get("writePracticeList");
        Assertions.assertEquals(4, noteList.size());//currently only 4 rows in table
    }

}
