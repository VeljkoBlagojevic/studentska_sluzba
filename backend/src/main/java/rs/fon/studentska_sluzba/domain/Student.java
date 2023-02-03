package rs.fon.studentska_sluzba.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity(name = "student")
public class Student implements UserDetails {

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

    @NaturalId
    @Email(regexp = "[a-z]{2}\\d{8}@student.fon.bg.ac.rs", message = "Studentski email nije u adekvatnom formatu")
    private String studentskiEmail;
    private String slika;
    private String imeRoditelja;
    @Email
    private String licniEmail;

    @ManyToOne
    private Grad mestoRodjenja;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trenutno_slusa",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "predmet_id")
    )
    private List<Predmet> trenutnoSlusa;

    @NaturalId
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
