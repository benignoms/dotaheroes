package br.com.benignosales.dotaheroes.hero.web;

import br.com.benignosales.dotaheroes.hero.exception.HeroNotFoundException;
import br.com.benignosales.dotaheroes.hero.service.HeroService;
import br.com.benignosales.dotaheroes.hero.web.impl.HeroController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.benignosales.dotaheroes.common.HeroConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebMvcTest(HeroController.class)
public class HeroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private HeroService service;

    @Test
    public void createHero_WithValidData_ReturnsCreated() throws Exception {

        when(service.createHero(VALID_HERO_DTO_WITHOUT_ID)).thenReturn(VALID_HERO_DTO);

        mockMvc.perform(post("/hero")
                        .content(mapper.writeValueAsBytes(VALID_HERO_DTO_WITHOUT_ID))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name")
                        .value(VALID_HERO_WITHOUT_ID.getName()));
    }

    @Test
    public void createHero_WithInvalidData_ReturnsBadRequest() throws Exception {

        mockMvc.perform(post("/hero")
                        .content(mapper.writeValueAsBytes(INVALID_HERO_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/hero")
                        .content(mapper.writeValueAsBytes(EMPTY_HERO_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createHero_WithExistingId_ReturnsConflict() throws Exception {

        when(service.createHero(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/hero")
                        .content(mapper.writeValueAsBytes(VALID_HERO_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getHero_ByExistingId_ReturnsHero() throws Exception {

        when(service.getHeroById(DEFAULT_UUID)).thenReturn(VALID_HERO_DTO);

        mockMvc.perform(get("/hero/" + DEFAULT_UUID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value(VALID_HERO_DTO.name()))
                .andExpect(jsonPath("$.description")
                        .value(VALID_HERO_DTO.description()));
    }

    @Test
    public void getHero_ByUnexistingId_ReturnsNotFound() throws Exception {

        when(service.getHeroById(INVALID_UUID)).thenThrow(HeroNotFoundException.class);

        mockMvc.perform(get("/hero/" + INVALID_UUID))
                .andExpect(status().isNotFound());
    }
}
