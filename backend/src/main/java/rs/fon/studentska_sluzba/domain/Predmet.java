package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

/**
 * Entitet u bazi podataka / domenska klasa Predmet.
 *
 * Pamti sve bitno informacije o predmetu kao sto su naziv i broj espb bodova.
 * Ispunjava sve kriterijume koje predmet treba da zadovolji po bolonjskoj formulaciji.
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

@Entity(name = "predmet")
public class Predmet {
    /**
     * Auto generisan identifikator koji je unikatan i identifikuje objekat u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Zvanican naziv predmeta.
     */
    private String naziv;

    /**
     * Vrednost ESPB broj bodova koje predmet nosi po bolonji i njenom ustavu.
     *
     * Racuna se na osnovu broja ulozenih sata truda koje prosecan student uloazi u predmet.
     */
    @Min(value = 1, message = "Predmet mora vredeti barem 1 ESPB")
    @Max(value = 30, message = "Predmet ne moze vredeti vise od 30 ESPB")
    private Integer ESPB;
}