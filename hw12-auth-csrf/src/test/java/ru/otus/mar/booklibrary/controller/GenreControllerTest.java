package ru.otus.mar.booklibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.rest.GenreController;
import ru.otus.mar.booklibrary.service.GenreService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    private static final GenreDto GENRE_DTO =
            new GenreDto(UUID.fromString("301c28f7-1793-45dd-91a1-8c0ec82d5beb"), "genre_a");

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void listDenied() throws Exception {
        when(service.getAll()).thenReturn(List.of(GENRE_DTO));
        mvc.perform(get("/api/genre"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void list() throws Exception {
        when(service.getAll()).thenReturn(List.of(GENRE_DTO));
        mvc.perform(get("/api/genre"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(GENRE_DTO))))
                .andReturn();
    }

    @Test
    @WithMockUser
    public void apiPost() throws Exception {
        when(service.create(any(GenreDto.class))).thenReturn(GENRE_DTO);
        mvc.perform(post("/api/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(GENRE_DTO))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(GENRE_DTO)))
                .andReturn();
    }

    @Test
    @WithMockUser
    public void apiPut() throws Exception {
        when(service.update(any(GenreDto.class))).thenReturn(GENRE_DTO);
        mvc.perform(put("/api/genre/" + GENRE_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(GENRE_DTO))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(GENRE_DTO)))
                .andReturn();
    }

    @Test
    @WithMockUser
    public void apiDelete() throws Exception {
        mvc.perform(delete("/api/genre/" + GENRE_DTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(GENRE_DTO))
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1)).delete(GENRE_DTO.getId());
    }
}
