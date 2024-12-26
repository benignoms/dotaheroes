package br.com.benignosales.dotaheroes.hero.exception;

public class HeroNotFoundException extends RuntimeException {

    public static final String HERO_NOT_FOUND = "Hero not found";

    public HeroNotFoundException() {
        super(HERO_NOT_FOUND);
    }
}
