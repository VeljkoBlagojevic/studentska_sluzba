package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.NaturalId;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity(name = "grad")
public class Grad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Mora postojati naziv grada")
    private String naziv;
    @NaturalId
    @Min(value = 10_000, message = "Zipcode mora imati tacno 5 cifara")
    @Max(value = 99_999, message = "Zipcode mora imati tacno 5 cifara")
    private Integer zipcode;

    public Grad(String naziv, Integer zipcode) {
        this.naziv = naziv;
        this.zipcode = zipcode;
    }
}