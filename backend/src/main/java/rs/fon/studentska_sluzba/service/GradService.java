package rs.fon.studentska_sluzba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Grad;
import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.GradRepository;

import java.util.List;
import java.util.Optional;

/**
 * Sadrzi poslovnu logiku sa radom sa gradovima.
 * <p>
 * Klasa sluzi da manipulise, upravlja sa modelom i podacima vezanim sa gradovima.
 * Omogucavama dodavanje, citanje, brisanje i menjanje informacija gradova.
 *
 * @author VeljkoBlagojevic
 */
@Service
public class GradService {

    /**
     * Broker baze podataka koji je posrednik ka tabeli Grad.
     */
    private final GradRepository gradRepository;

    /**
     * Logger koji sacuvava sve potrebne informacije o izvrsenim koracima.
     */
    private final Logger logger;

    /**
     * Parametrizovani konstruktor koji automatski injektuje sve zavinosti uz pomoc Springa.
     * @param gradRepository Klasa koja implementira sve neophodne operacije sa radom tabelom Grad u bazi podataka.
     * @param logger Klasa za logovanje informacija.
     */
    @Autowired
    public GradService(GradRepository gradRepository, Logger logger) {
        this.gradRepository = gradRepository;
        this.logger = logger;
    }

    /**
     * Metoda vraca listu svih gradova koji se nalazi u bazi podataka.
     * @return lista svih gradova
     */
    public List<Grad> findAll() {
        return gradRepository.findAll();
    }

    /**
     * Metoda koja vraca listu svih gradova koji se nalaze u bazi ali uz pomoc paginacije.
     * <p>
     * Metoda vraca Page parameter koji u sebi sadrzi listu gradova, kao i sve potrebne informacije za prolazenje kroz stranice.
     * @param pageNo Zeljeni redni broj stranice sa podacima.
     * @param pageSize Broj gradova po jednoj stranici.
     * @return Kolekciju gradova upakovan u Pageable format.
     */
    public Page<Grad> findAll(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return gradRepository.findAll(pageable);
    }

    /**
     * Vraca referencu na grad koji ima prosledjeni identifikator.
     *
     * @throws EntityNotFoundException Ukoliko ne postoji grad ciji je id jednak prosledjenom argumentu.
     * @param id Identifikator po kojem se trazi odredjeni grad.
     * @return Grad kojem je atribuut id jednak prosledjenom podatku.
     */
    public Grad getGradSaId(Long id) {
        return gradRepository.findById(id).orElseThrow(() -> {
            EntityNotFoundException exception = new EntityNotFoundException("Nije pronadjen grad sa id = " + id);
            logger.error(exception);
            return exception;
        });
    }

    /**
     * Perzistiranje grada u sistem kroz trajno cuvanje.
     * <p>
     * Novi grad se cuva u bazi podataka, a ukoliko grad vec postoji sa datim identifikatorom,
     * tada se data referenca updateuje sa prosledjenim podacima.
     * <p>
     * Dodavanje grada je moguce samo od strane korisnika sa Rolom 'ADMIN'
     *
     * @throws Exception ukoliko cuvanje u bazu ne bude uspesno i dodje do greske unutar Brokera Baze Podataka
     *
     * @param grad Grad koji se treba perzistirati
     * @return Perzistirani grad
     */
    public Grad ubaciGrad(Grad grad) {
        try {
            Grad sacuvaniGrad = gradRepository.save(grad);
            logger.info("Uspesno sacuvan grad = " + sacuvaniGrad);
            return sacuvaniGrad;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
    }

    /**
     * Brise grad sa prosledjenim identifikatorom iz baze podataka.
     * <p>
     * Ukoliko grad ne postoji sa datim id-em, metoda vraca false.
     * U suprotnom, ukoliiko nadje objekat sa datim atributom, i uspesno ga obrise,
     * metoda ce vratiti true vrednost.
     * <p>
     * Brisanje grada je dozvoljeno samo koristniku sa Rolom 'ADMIN'
     *
     * @param id Identifikator po kojem se trazi grad za brisanje.
     * @return Uspesnost brisanja.
     */
    public boolean obrisiGradSaId(Long id) {
        Optional<Grad> optionalGrad = gradRepository.findById(id);
        if (optionalGrad.isPresent()) {
            gradRepository.deleteById(id);
            logger.info("Grad obrisan sa id = " + id);
            return true;
        }
        logger.error(new EntityNotFoundException("Nije pronadjen grad sa id = " + id));
        return false;
    }


}
