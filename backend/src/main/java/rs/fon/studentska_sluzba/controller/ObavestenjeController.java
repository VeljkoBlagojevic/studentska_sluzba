package rs.fon.studentska_sluzba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.domain.Obavestenje;
import rs.fon.studentska_sluzba.service.ObavestenjeService;

import java.util.List;

@RestController
@RequestMapping("api/v1/obavestenja")
public class ObavestenjeController {

    private final ObavestenjeService obavestenjeService;

    public ObavestenjeController(ObavestenjeService obavestenjeService) {
        this.obavestenjeService = obavestenjeService;
    }

    @GetMapping
    public ResponseEntity<List<Obavestenje>> getSvaObavestenja() {
        return ResponseEntity.ok(obavestenjeService.getSvaObavestenja());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obavestenje> getObavestenje(@PathVariable Long id) {
        return ResponseEntity.ok(obavestenjeService.getObavestenjeSaId(id));
    }

    @PostMapping
    public ResponseEntity<Obavestenje> post(@RequestBody Obavestenje obavestenje) {
        return new ResponseEntity<>(obavestenjeService.ubaciObavestenje(obavestenje), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiObavestenjeSaId(@PathVariable Long id) {
        if (obavestenjeService.obrisiObavestenjeSaId(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
