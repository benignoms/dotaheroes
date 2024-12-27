package br.com.benignosales.dotaheroes.hero.service;

import br.com.benignosales.dotaheroes.hero.dto.HeroDto;
import br.com.benignosales.dotaheroes.hero.entity.Hero;
import br.com.benignosales.dotaheroes.hero.exception.HeroNotFoundException;
import br.com.benignosales.dotaheroes.hero.repository.IHeroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeroService implements IHeroService {

    private final IHeroRepository heroRepository;
    private final ObjectMapper mapper;

    @Autowired
    public HeroService(IHeroRepository heroRepository,
                       ObjectMapper mapper) {
        this.heroRepository = heroRepository;
        this.mapper = mapper;
    }

    @Override
    public HeroDto getHeroById(String id) {

        return this.mapper.convertValue(this.heroRepository.findById(id)
                .orElseThrow(HeroNotFoundException::new), HeroDto.class);
    }

    @Override
    public List<HeroDto> getHeroThatNameContains(String name) {

        return heroRepository.findByNameContaining(name)
                .stream()
                .map(HeroDto::toDto)
                .toList();
    }


    @Override
    public HeroDto createHero(HeroDto heroDto) {

        Hero heroEntity = heroDto.toEntity();
        Hero newHero = this.heroRepository.saveAndFlush(heroEntity);
        return mapper.convertValue(newHero, HeroDto.class);
    }
}
