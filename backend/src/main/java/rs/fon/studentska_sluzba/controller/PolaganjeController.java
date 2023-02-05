package rs.fon.studentska_sluzba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.domain.Polaganje;
import rs.fon.studentska_sluzba.service.PolaganjeService;

import java.util.List;

@RestController
@RequestMapping("api/v1/polaganja")
public class PolaganjeController {

    private final PolaganjeService polaganjeService;

    public PolaganjeController(PolaganjeService polaganjeService) {
        this.polaganjeService = polaganjeService;
    }

    @GetMapping
    public ResponseEntity<List<Polaganje>> getSvaPolaganja() {
        return ResponseEntity.ok(polaganjeService.getSvaPolaganja());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Polaganje> getPolaganje(@PathVariable Long id) {
        return ResponseEntity.ok(polaganjeService.getPolaganjeSaId(id));
    }

    @PostMapping
    public ResponseEntity<Polaganje> dodajPolaganje(@RequestBody Polaganje polaganje) {
        return new ResponseEntity<>(polaganjeService.ubaciPolaganje(polaganje), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiPolaganjeSaId(@RequestParam Long id) {
        if (polaganjeService.obrisiPolaganjeSaId(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/uspesna")
    public ResponseEntity<List<Polaganje>> getSvaUspesnaPolaganja() {
        return ResponseEntity.ok(polaganjeService.getSvaUspesnaPolaganja());
    }

    @GetMapping("/neuspesna")
    public ResponseEntity<List<Polaganje>> getSvaNeuspesnaPolaganja() {
        return ResponseEntity.ok(polaganjeService.getSvaNeuspesnaPolaganja());
    }
}
