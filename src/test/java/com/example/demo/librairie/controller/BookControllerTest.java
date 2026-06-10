package com.example.demo.librairie.controller;

import com.example.demo.librairie.entity.Author;
import com.example.demo.librairie.entity.Book;
import com.example.demo.librairie.entity.Genre;
import com.example.demo.librairie.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ActiveProfiles("test")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private ObjectMapper objectMapper;
    private Book sampleBook;
    private UUID sampleId;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        sampleId = UUID.randomUUID();

        sampleBook = Book.builder()
                .id(sampleId)
                .title("Le Petit Prince")
                .isbn("978-2-07-040850-4")
                .description("Un classique de la littérature")
                .url("http://example.com/petit-prince")
                .creationDate(LocalDate.of(1943, 4, 6))
                .publicationDate(LocalDate.of(1943, 4, 6))
                .genres(List.of(
                        Genre.builder()
                                .id(UUID.randomUUID())
                                .name("Fiction")
                                .build()
                ))
                .authors(List.of(
                        Author.builder()
                                .id(UUID.randomUUID())
                                .fullName("Antoine de Saint-Exupéry")
                                .firstname("Antoine")
                                .lastname("de Saint-Exupéry")
                                .build()
                ))
                .build();
    }

    @Test
    void getAll() throws Exception {
        when(bookService.getAll()).thenReturn(List.of(sampleBook));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Le Petit Prince"));

        verify(bookService, times(1)).getAll();
    }

    @Test
    void getById() throws Exception {
        when(bookService.getById(sampleId)).thenReturn(sampleBook);

        mockMvc.perform(get("/api/books/{id}", sampleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Le Petit Prince"))
                .andExpect(jsonPath("$.isbn").value("978-2-07-040850-4"));

        verify(bookService, times(1)).getById(sampleId);
    }

    @Test
    void getById_notFound() throws Exception {
        UUID unknownId = UUID.randomUUID();
        when(bookService.getById(unknownId))
                .thenThrow(new RuntimeException("Book not found")); // ← on throw, pas Optional.empty()

        mockMvc.perform(get("/api/books/{id}", unknownId))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).getById(unknownId);
    }

    @Test
    void getByTitle() throws Exception {
        when(bookService.getLivreByTitle("Petit")).thenReturn(List.of(sampleBook));

        mockMvc.perform(get("/api/books/search/title")
                        .param("title", "Petit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Le Petit Prince"));

        verify(bookService, times(1)).getLivreByTitle("Petit");
    }

    @Test
    void getByGender() throws Exception {
        UUID genreId = UUID.randomUUID();
        when(bookService.getLivreByGenre(genreId)).thenReturn(List.of(sampleBook));

        mockMvc.perform(get("/api/books/search/gender/{genderId}", genreId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Le Petit Prince"));

        verify(bookService, times(1)).getLivreByGenre(genreId);
    }
    @Test
    void getByDate() throws Exception {
        LocalDate date = LocalDate.of(1943, 4, 6);
        when(bookService.getLivreByDate(date)).thenReturn(List.of(sampleBook));

        mockMvc.perform(get("/api/books/search/date")
                        .param("date", "1943-04-06"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Le Petit Prince"));

        verify(bookService, times(1)).getLivreByDate(date);
    }
    @Test
    void create() throws Exception {
        when(bookService.createLivre(any(Book.class))).thenReturn(sampleBook);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Le Petit Prince"))
                .andExpect(jsonPath("$.isbn").value("978-2-07-040850-4"));

        verify(bookService, times(1)).createLivre(any(Book.class));
    }

    @Test
    void update() throws Exception {
        Book updatedBook = Book.builder()
                .id(sampleId)
                .title("Le Petit Prince - Édition spéciale")
                .isbn("978-2-07-040850-4")
                .description("Édition anniversaire")
                .publicationDate(LocalDate.of(1993, 4, 6))
                .build();

        when(bookService.updateLivre(eq(sampleId), any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/{id}", sampleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Le Petit Prince - Édition spéciale"));

        verify(bookService, times(1)).updateLivre(eq(sampleId), any(Book.class));
    }

    @Test
    void delete() throws Exception {
        doNothing().when(bookService).deleteLivre(sampleId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", sampleId))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteLivre(sampleId);
    }
}