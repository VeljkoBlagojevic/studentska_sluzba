package rs.fon.studentska_sluzba.service;

import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Obavestenje;
import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.ObavestenjeRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * Sadrzi poslovnu logiku koja je povezana sa modelom Obavestenja.
 * <p>
 * Sve operacije promene, dodavanja, prikazivanja i pretrage obavestenja se obavlja unutar ovog servisa.
 *
 * @author VeljkoBlagojevic
 */
@Service
public class ObavestenjeService {

    /**
     * Objekat repozitorijuma cija je uloga perzistencija obavestenja u bazu podataka.
     */
    private final ObavestenjeRepository obavestenjeRepository;

    /**
     * Logger koji sacuvava sve potrebne informacije o izvrsenim koracima.
     */
    private final Logger logger;

    /**
     * Parametrizovani konstruktor koji automatski injektuje sve zavinosti uz pomoc Springa.
     *
     * @param obavestenjeRepository Klasa koja implementira sve neophodne operacije sa radom i pristupom baze podataka.
     * @param logger Klasa za logovanje neophodnih informacija.
     */
    public ObavestenjeService(ObavestenjeRepository obavestenjeRepository, Logger logger) {
        this.obavestenjeRepository = obavestenjeRepository;
        this.logger = logger;
    }

    /**
     * Metoda vraca sva obavestenja koja sacuvana u sistemu.
     * Svaki korisnik ima pristup citanju svih obavestenja.
     * Lista obavestenja je sortirana u obrnutom hronoloskom poretku.
     *
     * @return lista obavestenja sortirana od najnovije ka najstarije.
     */
    public List<Obavestenje> getSvaObavestenja() {
        return obavestenjeRepository.findAll().stream()
                .sorted(Comparator.comparing(Obavestenje::getDatum).reversed())
                .toList();
    }

    /**
     * Metoda vraca obavestenje koja sadrzi identifikator koji je jednak prosledjenom parametru.
     * Ukoliko ne postoji baca se neproveravani izuzetak.
     *
     * @throws EntityNotFoundException Ukoliko ne postoji obavestenje sa datim id-em.
     *
     * @param id Identifikator po kojem se pretrazuje.
     * @return Obavestenje sa datim id-em.
     */
    public Obavestenje getObavestenjeSaId(Long id) {
        return obavestenjeRepository.findById(id).orElseThrow(() -> {
            EntityNotFoundException exception = new EntityNotFoundException();
            logger.error(exception);
            return exception;
        });
    }

    /**
     * Postavljanje novog obavestenja.
     * <p>
     * Metoda perzistira novo obavestenje koje je prosledjeno od strane administraotra.
     * @param obavestenje Obavestenje koje se zeli sacuvati.
     * @return Sacuvano obavestenje sa azuriranim informacijama o datumu i id-u.
     */
    public Obavestenje ubaciObavestenje(Obavestenje obavestenje) {
        obavestenje.setDatum(LocalDate.now());
        return obavestenjeRepository.save(obavestenje);
    }

    /**
     * Brise obavestenje sa prosledjenim id-em.
     * <p>
     * Ukoliko obavestenje ne postoji sa datim identifikatorom, metoda vraca false vrednost.
     * Ali, ako postoji i uspesno je izvrseno brisanje objekta sa datim atributom, metoda vraca true.
     *
     * @param id jedinstveni primarni kljuc po kojem se identifikuje obavestenje za brisanje.
     * @return Uspesnost brisanja.
     */
    public boolean obrisiObavestenjeSaId(Long id) {
        if (obavestenjeRepository.existsById(id)) {
            obavestenjeRepository.deleteById(id);
            logger.info("Uspesno obrisano obavestenje sa id: " + id);
            return true;
        }
        logger.error(new EntityNotFoundException("Ne postoji obavestenje sa id " + id));
        return false;
    }
}
