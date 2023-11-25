package com.phantom.japanese_dictionary_mvc.controllers;

import com.phantom.japanese_dictionary_mvc.dto.NoteDTO;
import com.phantom.japanese_dictionary_mvc.replies.DictionaryReply;
import com.phantom.japanese_dictionary_mvc.requests.Request;
import com.phantom.japanese_dictionary_mvc.requests.RequestType;
import com.phantom.japanese_dictionary_mvc.services.NoteService;
import com.phantom.japanese_dictionary_mvc.util.DictionaryExcelImporter;
import com.phantom.japanese_dictionary_mvc.util.DictionaryReplyConverter;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DictionaryControllerTest extends BaseControllerTest{

    @Mock
    private DictionaryReplyConverter dictionaryReplyConverter;

    @Mock
    private DictionaryExcelImporter dictionaryExcelImporter;

    @InjectMocks
    private DictionaryController dictionaryController;

    private final String BASE_PATH = "/dictionary";
    private final String PATH_TO_SHOW = BASE_PATH + "/show";
    private final String PATH_TO_IMPORT = BASE_PATH + "/import";
    private final String PATH_TO_IMPORTING = BASE_PATH + "/importing";

    private final String INDEX_VIEW_NAME = "dictionaries/index";
    private final String MULTISHOW_VIEW_NAME = "dictionaries/multishow";
    private final String IMPORT_VIEW_NAME = "dictionaries/import";

    private final NoteDTO TEST_NOTE_DTO = NoteDTO.builder()
            .romadji("romaji").kanji("kanji").hiragana("hiragana").translation("translation")
            .build();

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(dictionaryController)
                .build();
    }

    @Test
    public void whenIndex_thenIndex() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BASE_PATH).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(model.get("request").getClass(), Request.class);
        assertEquals(model.get("types").getClass(), RequestType[].class);
    }

    @Test
    public void whenCorrectRequest_thenCorrectShow() throws Exception {
        Request request = new Request("test", RequestType.TRANSLATION, false);
        DictionaryReply dictionaryReply = new DictionaryReply();
        dictionaryReply.setFullMatchCount(1);
        dictionaryReply.setPartialMatchCount(2);
        dictionaryReply.setIndexOfLastPage(0);
        dictionaryReply.setNoteDTOS(List.of(TEST_NOTE_DTO));
        doReturn(dictionaryReply).when(dictionaryReplyConverter).getDictionaryReplyForCurrentPage(request, 0);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("request", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(MULTISHOW_VIEW_NAME))
                .andReturn();

        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(model.get("dictionaryReply").getClass(), DictionaryReply.class);
        assertEquals(model.get("currentPage"), 0);

        DictionaryReply dictionaryReplyResult = (DictionaryReply) model.get("dictionaryReply");
        assertEquals(dictionaryReplyResult, dictionaryReply);
    }

    @Test
    public void whenIncorrectRequest_thenIndex() throws Exception {
        Request request = new Request("", RequestType.TRANSLATION, false);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(PATH_TO_SHOW).accept(MediaType.TEXT_HTML)
                .flashAttr("request", request))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andReturn();
    }

    @Test
    public void whenImportFile_thenImportFile() throws Exception {
        doNothing().when(dictionaryExcelImporter).importExcelToDB(any());
        mvc.perform(MockMvcRequestBuilders.get(PATH_TO_IMPORT).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(IMPORT_VIEW_NAME));
    }

    @Test
    public void whenFileEmpty_thenDictionaryImport() throws Exception {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes()
        );
        doThrow(EmptyFileException.class).when(dictionaryExcelImporter).importExcelToDB(any());
        mvc.perform(MockMvcRequestBuilders.multipart(PATH_TO_IMPORTING).file(file).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:" + PATH_TO_IMPORT));
    }

    @Test
    public void whenFileIsNotEmpty_thenDictionary() throws Exception {

        Workbook workBook = new XSSFWorkbook();
        Sheet sheet = workBook.createSheet("Test");
        Row headerRow = sheet.createRow(0);
        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("test romaji");
        row.createCell(1).setCellValue("test kanji");
        row.createCell(2).setCellValue("test kana");
        row.createCell(3).setCellValue("test translation");

        byte[] bytes = null;

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workBook.write(bos);
            bytes = bos.toByteArray();
        }

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                "",
                bytes
        );

        doNothing().when(dictionaryExcelImporter).importExcelToDB(any());
        mvc.perform(MockMvcRequestBuilders.multipart(PATH_TO_IMPORTING).file(file).accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:" + BASE_PATH));
    }

}