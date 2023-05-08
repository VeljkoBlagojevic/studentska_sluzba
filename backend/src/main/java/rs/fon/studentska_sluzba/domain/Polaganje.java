package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

/**
 * Entitet u bazi podataka / domenska klasa Polaganje.
 *
 * Domenska klasa koja pamti koji student je polagao koji predmet.
 * Pored toga pamti i vreme i ocenu nakon izlaska na ispit.
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

@Entity(name = "polaganje")
public class Polaganje {
    /**
     * Auto generisan identifikator koji je unikatan i identifikuje objekat u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Datum kada se vrsilo ispitivanje.
     *
     * Ovaj datum ima ogranicenje da mora da se desilo u proslosti.
     */
    @Past(message = "Polaganje mora da se desilo u proslosti")
    private LocalDate datum;

    /**
     * Boolean vrednost da li je polaganje proslo uspesno/prelaznom ocenom.
     */
    private Boolean polozio;

    /**
     * Ocena koja je dobijena na polaganju.
     *
     * Ocena moze biti u rasponu [5-10],
     * sto znaci da je ukljucena i ocena kada student padne polaganje.
     */
    @Min(value = 5, message = "Ocena ne moze biti manja od 5")
    @Max(value = 10, message = "Ocena ne moze biti veca od 10")
    private Integer ocena;

    /**
     * Veza prema objektu studenta koji je vrstio dato polaganje.
     */
    @ManyToOne
    private Student student;

    /**
     * Predmet koji biva polagan.
     */
    @ManyToOne
    private Predmet predmet;
}
