package br.com.benignosales.dotaheroes.hero.repository;

import br.com.benignosales.dotaheroes.hero.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IHeroRepository extends JpaRepository<Hero, String> {

    List<Hero> findByNameContaining(String heroName);

}
