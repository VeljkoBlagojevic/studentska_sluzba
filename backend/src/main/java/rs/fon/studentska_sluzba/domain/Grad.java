package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.NaturalId;

/**
 * Entitet u bazi podataka / domenska klasa grada.
 *
 * Koristi se da predstavi direktnu vezu sa tabelom garda iz baze podataka ali i kao domenski objekat.
 * Pamti sve bitno informacije o gradu kao sto su naziv i zipcode.
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

@Entity(name = "grad")
public class Grad {
    /**
     * Auto generisan identifikator koji je unikatan i identifikuje objekat u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Naziv grada; mora se popuniti tj. ne sme biti null.
     */
    @NotBlank(message = "Mora postojati naziv grada")
    private String naziv;

    /**
     * Celobrojna vrenost izmedju 10_000 i 99_999 koja predstavlja zipcode odnosno postanski broj grada.
     */
    @NaturalId
    @Min(value = 10_000, message = "Zipcode mora imati tacno 5 cifara")
    @Max(value = 99_999, message = "Zipcode mora imati tacno 5 cifara")
    private Integer zipcode;
}