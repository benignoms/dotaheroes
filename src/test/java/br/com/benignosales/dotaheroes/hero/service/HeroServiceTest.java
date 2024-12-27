package br.com.benignosales.dotaheroes.hero.service;


import br.com.benignosales.dotaheroes.hero.dto.HeroDto;
import br.com.benignosales.dotaheroes.hero.entity.Hero;
import br.com.benignosales.dotaheroes.hero.exception.HeroNotFoundException;
import br.com.benignosales.dotaheroes.hero.repository.IHeroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static br.com.benignosales.dotaheroes.common.HeroConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {

    @InjectMocks
    private HeroService heroService;
    @Mock
    private IHeroRepository heroRepository;
    @Mock
    private ObjectMapper mapper;

    @Test
    public void createHero_WithValidData_ReturnsHero() {

        when(heroRepository.saveAndFlush(any(Hero.class)))
                .thenReturn(VALID_HERO_WITH_ID);
        when(mapper.convertValue(VALID_HERO_WITH_ID, HeroDto.class))
                .thenReturn(VALID_HERO_DTO);

        HeroDto heroDto = heroService.createHero(VALID_HERO_DTO_WITHOUT_ID);

        assertThat(heroDto.id()).isNotEmpty();
        assertThat(heroDto.name()).isEqualTo(VALID_HERO_WITH_ID.getName());

    }

    @Test
    public void createHero_WithInvalidData_ThrowsException() {

        when(heroRepository.saveAndFlush(INVALID_HERO))
                .thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> heroService.createHero(INVALID_HERO_DTO))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    public void findHeroById_WithValidId_ReturnsHero() {

        when(heroRepository.findById(DEFAULT_UUID)).thenReturn(Optional.of(VALID_HERO_WITH_ID));
        when(mapper.convertValue(VALID_HERO_WITH_ID, HeroDto.class)).thenReturn(VALID_HERO_DTO);
        HeroDto heroById = heroService.getHeroById(DEFAULT_UUID);

        assertThat(heroById.name()).isEqualTo(VALID_HERO_DTO.name());
        assertThat(heroById.id()).isEqualTo(VALID_HERO_DTO.id());
    }

    @Test
    public void findHeroById_WithInvalidId_ThrowsException() {

        when(heroRepository.findById(INVALID_UUID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> heroService.getHeroById(INVALID_UUID)).
                isInstanceOf(HeroNotFoundException.class);
    }

    @Test
    public void findHeroByName_ThatContainsValidText_ReturnsHero() {

        String validPartText = VALID_HERO_WITH_ID.getName().substring(5);
        when(heroRepository.findByNameContaining(validPartText))
                .thenReturn(List.of(VALID_HERO_WITH_ID));
        List<HeroDto> herosThaNameContains = heroService.getHeroThatNameContains(validPartText);
        assertThat(herosThaNameContains).isNotEmpty();
        assertThat(herosThaNameContains.size()).isEqualTo(1);
        assertThat(herosThaNameContains.get(0).name()).isEqualTo(VALID_HERO_WITH_ID.getName());
    }

    @Test
    public void findHeroByName_ThatContainsInvalidText_ReturnsHero() {

        String invalidPartText = "00000";
        when(heroRepository.findByNameContaining(invalidPartText))
                .thenReturn(new ArrayList<>());
        List<HeroDto> emptyList = heroService.getHeroThatNameContains(invalidPartText);
        assertThat(emptyList).isEmpty();

    }
}
