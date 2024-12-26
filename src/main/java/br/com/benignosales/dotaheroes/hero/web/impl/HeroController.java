package br.com.benignosales.dotaheroes.hero.web.impl;

import br.com.benignosales.dotaheroes.hero.dto.HeroDto;
import br.com.benignosales.dotaheroes.hero.service.IHeroService;
import br.com.benignosales.dotaheroes.hero.web.IHeroController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/hero")
public class HeroController implements IHeroController {

    private IHeroService heroService;

    public HeroController(IHeroService heroService) {

        this.heroService = heroService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<HeroDto>> getHeroByName(@RequestParam String name) {

        return ResponseEntity.ok(heroService.getHeroThatNameContains(name));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<HeroDto> getHeroById(@PathVariable String id) {

        return ResponseEntity.ok(heroService.getHeroById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<HeroDto> createHero(@RequestBody @Valid HeroDto heroDto,
                                              UriComponentsBuilder uriBuilder) {

        HeroDto insertedHeroDto = heroService.createHero(heroDto);
        URI uri = uriBuilder.path("/hero/{id}").buildAndExpand(insertedHeroDto.id()).toUri();

        return ResponseEntity.created(uri).body(insertedHeroDto);
    }
}
