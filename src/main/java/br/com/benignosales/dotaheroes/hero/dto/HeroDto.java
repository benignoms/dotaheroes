package br.com.benignosales.dotaheroes.hero.dto;

import br.com.benignosales.dotaheroes.hero.entity.Hero;
import br.com.benignosales.dotaheroes.hero.enumeration.HeroType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record HeroDto(String id,
                      @NotEmpty String name,
                      String description,
                      String type) {

    public Hero toEntity() {

        return Hero.builder()
                .id(id)
                .name(name)
                .description(description)
                .type(HeroType.valueOf(type))
                .build();
    }

    public static HeroDto toDto(Hero hero) {
        return HeroDto.builder()
                .id(hero.getId())
                .name(hero.getName())
                .type(hero.getType().name())
                .description(hero.getDescription())
                .build();
    }
}
