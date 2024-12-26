package br.com.benignosales.dotaheroes.hero.web;

import br.com.benignosales.dotaheroes.hero.dto.HeroDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface IHeroController {

    ResponseEntity<List<HeroDto>> getHeroByName(String name);

    ResponseEntity<HeroDto> getHeroById(String id);

    ResponseEntity<HeroDto> createHero(HeroDto heroDto, UriComponentsBuilder uriBuilder);
}
