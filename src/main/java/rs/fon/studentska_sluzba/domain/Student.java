package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NaturalId
    @Pattern(regexp = "\\d{4}/(19|20)\\d{2}")
    private String indeks;
    @NotBlank(message = "Ime ne moze biti prazno")
    private String ime;
    @NotBlank(message = "Prezime ne moze biti prazno")
    private String prezime;

    @Size(min = 13, max = 13, message = "JMBG mora imati tacno 13 cifara")
    private String jmbg;
    private LocalDate datumRodjenja;
    @Pattern(regexp = "\\+381\\d{9}", message = "Mobilni telefon treba da bude u formatu '+3816...'")
    private String brojTelefona;
    @Email(regexp = "[a-z]{2}\\d{8}@student.fon.bg.ac.rs", message = "Studentski email nije u adekvatnom formatu")
    private String studentskiEmail;
    private String slika;
    private String imeRoditelja;
    @Email
    private String licniEmail;

    private String username;
    private String password;

    @ManyToOne
    private Grad mestoRodjenja;

    @ManyToMany
    @JoinTable(
            name = "trenutno_slusa",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "predmet_id")
    )
    private List<Predmet> trenutnoSlusa;

}
