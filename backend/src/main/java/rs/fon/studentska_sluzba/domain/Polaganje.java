package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity(name = "polaganje")
public class Polaganje {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Past(message = "Polaganje mora da se desilo u proslosti")
    private LocalDate datum;

    private Boolean polozio;

    @Min(value = 5, message = "Ocena ne moze biti manja od 5")
    @Max(value = 10, message = "Ocena ne moze biti veca od 10")
    private Integer ocena;

    @ManyToOne
    private Student student;
    @ManyToOne
    private Predmet predmet;
}
