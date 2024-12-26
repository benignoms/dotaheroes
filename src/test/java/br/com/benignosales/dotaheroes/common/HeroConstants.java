package br.com.benignosales.dotaheroes.common;

import br.com.benignosales.dotaheroes.hero.dto.HeroDto;
import br.com.benignosales.dotaheroes.hero.entity.Hero;
import br.com.benignosales.dotaheroes.hero.enumeration.HeroType;

public class HeroConstants {

    public static final String DEFAULT_UUID = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";
    public static final String INVALID_UUID = "fa8b0-29f63c4c3454";
    public static final Hero VALID_HERO_WITHOUT_ID = Hero.builder()
            .name("Shadow Shaman")
            .description("Invoca poderosas serpentes sentinelas para causar dano")
            .type(HeroType.INTELIGENCE)
            .build();

    public static final Hero VALID_HERO_WITH_ID = Hero.builder()
            .id(DEFAULT_UUID)
            .name("Shadow Shaman")
            .description("Invoca poderosas serpentes sentinelas para causar dano")
            .type(HeroType.INTELIGENCE)
            .build();
    public static final Hero INVALID_HERO = Hero.builder()
            .name(null)
            .description("Invoca poderosas serpentes sentinelas para causar dano")
            .type(HeroType.INTELIGENCE)
            .build();

    public static final HeroDto VALID_HERO_DTO = new HeroDto(
            DEFAULT_UUID
            ,"Shadow Shaman"
            ,"Invoca poderosas serpentes sentinelas para causar dano"
            ,HeroType.INTELIGENCE.name());

    public static final HeroDto VALID_HERO_DTO_WITHOUT_ID = new HeroDto(
            null
            ,"Shadow Shaman"
            ,"Invoca poderosas serpentes sentinelas para causar dano"
            ,HeroType.INTELIGENCE.name());

    public static final HeroDto INVALID_HERO_DTO = new HeroDto(
            null
            , null
            ,"Invoca poderosas serpentes sentinelas para causar dano"
            ,HeroType.INTELIGENCE.name());

    public static final HeroDto EMPTY_HERO_DTO = HeroDto.builder().build();
}
