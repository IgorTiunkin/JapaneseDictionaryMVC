package com.phantom.japanese_dictionary_mvc.integration;


import com.phantom.japanese_dictionary_mvc.services.NoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Sql({
        "classpath:sql/data.sql"
})
public class NoteServiceIT {

    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:13.1-alpine");

    @BeforeAll
    static void startContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }

    private final NoteService noteService;

    @Autowired
    public NoteServiceIT(NoteService noteService) {
        this.noteService = noteService;
    }

    @Test
    public void when_then() {
        assertEquals(1,1);
    }

}
