package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * Entitet u bazi podataka / domenska klasa obavestenja.
 *
 * Koristi se za sirinje vesti od strane administratora prema svim studentima sistema.
 * Predstavlja direktnu vezu sa tabelom obavestenja iz baze podataka ali i kao domenski objekat.
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

@Entity(name = "obavestenje")
public class Obavestenje {

    /**
     * Auto generisan identifikator koji je unikatan i identifikuje objekat u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Vreme postavljanja obavestenja.
     */
    private LocalDate datum;

    /**
     * Sadrzaj obavestenja, tj sama vest.
     *
     * Sadrzaj ne sme biti prazan ali takodje ne sme biti duzi od 1000 karaktera.
     */
    @NotBlank(message = "Sadrzaj obavestenja ne sme biti prazno")
    @Size(max = 1000, message = "Sadrzaj obavestenja ne sme prelaziti 1000 karaktera")
    @Column(length = 1000)
    private String sadrzaj;

}
