package rs.fon.studentska_sluzba.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

/**
 * Entitet u bazi podataka / domenska klasa molbe.
 *
 * Koristi se da predstavi direktnu vezu sa tabelom molbe iz baze podataka ali i kao domenski objekat.
 * Pamti sve bitno informacije o gradu kao sto je sta je student pitao, kada, vezano za sta itd.
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

@Entity(name = "molba")
public class Molba {

    /**
     * Auto generisan identifikator koji je unikatan i identifikuje objekat u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Pitanje koje student zeli da mu se razresi kroz molbu.
     *
     * Studentovo pitanje unutar molbe, koje ne sme biti prazno.
     */
    @NotBlank(message = "Pitanje ne moze biti prazno")
    private String pitanje;

    /**
     * Datum kada je postavljeno pitanje.
     *
     * Datum moze biti samo u sadasnjosti ili proslosti
     */
    @PastOrPresent(message = "Datum pitanja ne moze biti u buducnosti")
    private LocalDate datumPitanja;

    /**
     * Odgovor na pitanje iz molbe.
     *
     * Administrator daje odgovor na pitanje iz molbe nakon sto ju je razresi ili
     * uputi studenta na dodatne korektivne akcije.
     */
    private String odgovor;

    /**
     * Datum kad je odovoreno da molbu.
     *
     * Vreme i datum kada je administrator odgovorio na pitanje koje je student postavio u molbi.
     * Dati datum i vreme ne sme biti u buducnosti.
     */
    @PastOrPresent(message = "Datum odgovora ne moze biti u buducnosti")
    private LocalDate datumOdgovora;

    /**
     * Tip Molbe koji zatrazuje student.
     *
     * Moze imati vise vrednosti kao sto su PROMENA_PODATAKA_O_STUDENTU, PONISTAVANJE_OCENE
     */
    @Enumerated(EnumType.STRING)
    private TipMolbe tipMolbe;

    /**
     * U kom se statusu trenutno nalazi molba.
     *
     * Prati u kom stanju se u datom trenutku nalazi molba; da li je razresena ili i dalje u obradi.
     */
    @Enumerated(EnumType.STRING)
    private StatusMolbe statusMolbe;

    /**
     * Student koji je podneo molbu.
     *
     * Predstavlja entitet studenta koji je postavio pitanje i podneo molbu za razresavanje.
     */
    @ManyToOne
    private Student student;

}
