package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity(name = "predmet")
public class Predmet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String naziv;
    @Min(value = 1, message = "Predmet mora vredeti barem 1 ESPB")
    @Max(value = 30, message = "Predmet ne moze vredeti vise od 30 ESPB")
    private Integer ESPB;
}