package br.com.benignosales.dotaheroes.hero.service;

import br.com.benignosales.dotaheroes.hero.dto.HeroDto;
import br.com.benignosales.dotaheroes.hero.entity.Hero;

import java.util.List;

public interface IHeroService {

    HeroDto getHeroById(String id);

    List<HeroDto> getHeroThatNameContains(String name);

    HeroDto createHero(HeroDto heroDto);
}
