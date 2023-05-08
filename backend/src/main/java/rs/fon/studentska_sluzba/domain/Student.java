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
import java.util.Set;

/**
 * Entitet u bazi podataka / domenska klasa studenta.
 *
 * Takodje se koristi kao Principal sistema, odnosno predstavlja ulogovanog korisnika.
 * Koristi se da predstavi direktnu vezu sa tabelom student iz baze podataka ali i kao domenski objekat.
 * Pamti sve bitno informacije o studentu kao sto su ime, broj indeksa, email itd.
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

@Entity(name = "student")
public class Student implements UserDetails {

    /**
     * Auto generisan identifikator koji je unikatan i identifikuje objekat u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Broj indeksa koji mora biti u formatu bbbb/gggg
     * NaturalId je sto znaci da je prirodni identifikator i ne moze se menjati u bazi podataka nakon inicijalne kreacije.
     */
    @NaturalId
    @Pattern(regexp = "\\d{4}/(19|20)\\d{2}")
    private String indeks;

    /**
     * Ime studenta (kako je prikazano u licnoj karti).
     *
     * Vrednost imena ne sme biti prazna.
     */
    @NotBlank(message = "Ime ne moze biti prazno")
    private String ime;

    /**
     * Prezime studenta (kako je prikazano u licnoj karti).
     *
     * Vrednost prezime ne sme biti prazna.
     */
    @NotBlank(message = "Prezime ne moze biti prazno")
    private String prezime;

    /**
     * Jedinstveni maticni broj gradjana studenta.
     *
     * Sastoji se od tacno 13 cifara.
     */
    @Size(min = 13, max = 13, message = "JMBG mora imati tacno 13 cifara")
    private String jmbg;

    /**
     * Datum rodjenja studenta.
     */
    private LocalDate datumRodjenja;

    /**
     * Broj mobilnog telefona studenta koji mora biti u formatu +381ddddddddd
     */
    @Pattern(regexp = "\\+381\\d{9}", message = "Mobilni telefon treba da bude u formatu '+3816...'")
    private String brojTelefona;

    /**
     * Studentski email studenta.
     *
     * Ova email adresa je dodeljena od strane univerziteta.
     * Njen tacan format zavisi od imena, prezimena, broja indeksa i fakulteta.
     * I mora biti u datom formatu: vb20190353@student.bg.ac.rs
     */
    @NaturalId
    @Email(regexp = "[a-z]{2}\\d{8}@student.fon.bg.ac.rs", message = "Studentski email nije u adekvatnom formatu")
    private String studentskiEmail;

    /**
     * Slika studenta.
     *
     * Slika gde je prikazan student, njegovo lice, od vrha glave do ramena.
     * Dobro osvetljeno, profesionalno uslikano.
     */
    private String slika;

    /**
     * Ime jednog od roditelja ili staratelja studenta.
     */
    private String imeRoditelja;

    /**
     * Personalna email adresa studenta.
     */
    @Email
    private String licniEmail;

    /**
     * Grad u kome je rodjen student.
     */
    @ManyToOne
    private Grad mestoRodjenja;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "trenutno_slusa",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "predmet_id")
//    )
//    private List<Predmet> trenutnoSlusa;

    /**
     * Spisak predmeta koje je student prijavio.
     *
     * Veza je vise-vise cime se kreira tabela asocijacije sa nazivom prijava.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "prijava",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "predmet_id")
    )
    private Set<Predmet> prijave;

    /**
     * Korisnicko ime studenta.
     *
     * Mora biti u tacnom formatu koje je jednako pocetnom delu studentskog emaila.
     * Naprimer format je vb20190353
     * Prirodni je identifikator sto znaci da se njegova vrednost ne moze menjati u bazi podataka naknadno.
     */
    @NaturalId
    private String username;

    /**
     * Sifra korisnika koja je hesirana u bazi.
     */
    private String password;

    /**
     * Rola koju dati korisnik uziva pri koriscenju sistema
     */
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
