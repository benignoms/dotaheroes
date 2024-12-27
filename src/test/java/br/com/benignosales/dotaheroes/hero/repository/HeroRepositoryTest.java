package br.com.benignosales.dotaheroes.hero.repository;

import br.com.benignosales.dotaheroes.hero.entity.Hero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static br.com.benignosales.dotaheroes.common.HeroConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class HeroRepositoryTest {

    @Autowired
    private IHeroRepository heroRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createHero_WithValidData_ReturnsPlanet() {

        Hero insertedHero = heroRepository.saveAndFlush(VALID_HERO_WITHOUT_ID);
        Hero sut = testEntityManager.find(Hero.class, insertedHero.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(VALID_HERO_WITHOUT_ID.getName());
        assertThat(sut.getType()).isEqualTo(VALID_HERO_WITHOUT_ID.getType());
        assertThat(sut.getDescription()).isEqualTo(VALID_HERO_WITHOUT_ID.getDescription());
    }

    @Test
    public void createHero_WithInvalidData_ThrowsException() {

        assertThatThrownBy(()-> heroRepository.saveAndFlush(INVALID_HERO))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getHero_ByExistingId_ReturnsHero() {

        Hero insertedHero = testEntityManager.persistAndFlush(VALID_HERO_WITHOUT_ID);
        Optional<Hero> hero = heroRepository.findById(insertedHero.getId());
        assertThat(hero).isPresent();
        assertThat(hero.get().getName()).isEqualTo(VALID_HERO_WITHOUT_ID.getName());

    }

    @Test
    public void getHero_ByUnexistingId_ReturnsEmpty() {

        Hero insertedHero = testEntityManager.persistAndFlush(VALID_HERO_WITHOUT_ID);
        Optional<Hero> hero = heroRepository.findById("000");
        assertThat(hero).isEmpty();
    }
}
