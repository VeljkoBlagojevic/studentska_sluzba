package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity(name = "obavestenje")
public class Obavestenje {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate datum;

    @NotBlank(message = "Sadrzaj obavestenja ne sme biti prazno")
    @Size(max = 1000, message = "Sadrzaj obavestenja ne sme prelaziti 1000 karaktera")
    @Column(length = 1000)
    private String sadrzaj;

}
