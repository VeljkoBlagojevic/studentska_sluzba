package rs.fon.studentska_sluzba.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity(name = "molba")
public class Molba {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Pitanje ne moze biti prazno")
    private String pitanje;

    @PastOrPresent(message = "Datum pitanja ne moze biti u buducnosti")
    private LocalDate datumPitanja;

    private String odgovor;

    @PastOrPresent(message = "Datum odgovora ne moze biti u buducnosti")
    private LocalDate datumOdgovora;

    @Enumerated(EnumType.STRING)
    private TipMolbe tipMolbe;

    @Enumerated(EnumType.STRING)
    private StatusMolbe statusMolbe;

    @ManyToOne
    private Student student;

}
