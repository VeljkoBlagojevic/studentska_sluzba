package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entitet u bazi podataka / domenska klasa nepolozeni predmet.
 *
 * Koristi se za predstavljanje kao asocijativna klasa izmedju studenta i predmeta.
 * Takodje je i entitet u bazi podataka.
 *
 * @author VeljkoBlagojevic
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity(name = "nepolozeni_predmet")
public class NepolozeniPredmet {
    /**
     * Auto generisan identifikator koji je unikatan i identifikuje objekat u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Informacija da li je student trenutno izabrao predmet.
     *
     * Podatak o tome da li je student u tekucoj skolskoj godini izabrao da slusa nepolozeni predmet.
     */
    private Boolean trenutnoSlusa;

    /**
     * Student nad kojim se pamti njegov nepolozeni predmet.
     *
     * Veza prema studentu nad kojim se pamti njegov nepollozeni predmet.
     */
    @ManyToOne
    private Student student;

    /**
     * Predment nad kojim se vrsi evidencija slusanja.
     *
     * Veza prema predmetu nad kojim se vrsi evidencija slusanja.
     *
     */
    @ManyToOne
    private Predmet predmet;

}
