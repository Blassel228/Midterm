package com.example.controller;

import com.example.AuthorController;
import com.example.AuthorService;
import com.example.model.Author;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private ObjectMapper objectMapper;

    private Author author;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        author = new Author("LOLKEK");
        author.setId(1L);

        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    public void testCreateAuthor() throws Exception {
        when(authorService.saveAuthor(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(author.getId()))
                .andExpect(jsonPath("$.name").value(author.getName()));

        verify(authorService, times(1)).saveAuthor(any(Author.class));
    }

    @Test
    public void testGetAuthorById() throws Exception {
        when(authorService.getAuthorById(author.getId())).thenReturn(Optional.of(author));

        mockMvc.perform(get("/authors/{id}", author.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(author.getId()))
                .andExpect(jsonPath("$.name").value(author.getName()));

        verify(authorService, times(1)).getAuthorById(author.getId());
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        when(authorService.updateAuthor(any(Long.class), any(Author.class))).thenReturn(author);

        mockMvc.perform(put("/authors/{id}", author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(author.getId()))
                .andExpect(jsonPath("$.name").value(author.getName()));

        verify(authorService, times(1)).updateAuthor(eq(author.getId()), any(Author.class));
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        doNothing().when(authorService).deleteAuthor(author.getId());

        mockMvc.perform(delete("/authors/{id}", author.getId()))
                .andExpect(status().isOk());

        verify(authorService, times(1)).deleteAuthor(author.getId());
    }
}
