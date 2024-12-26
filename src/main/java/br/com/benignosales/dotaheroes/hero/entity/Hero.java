package br.com.benignosales.dotaheroes.hero.entity;

import br.com.benignosales.dotaheroes.hero.enumeration.HeroType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Table(name = "hero")
@Entity(name = "Hero")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HeroType type;
}
