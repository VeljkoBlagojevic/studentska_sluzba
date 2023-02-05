package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity(name = "nepolozeni_predmet")
public class NepolozeniPredmet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean trenutnoSlusa;

    @ManyToOne
    private Student student;
    @ManyToOne
    private Predmet predmet;

}
