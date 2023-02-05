package rs.fon.studentska_sluzba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.service.PredmetService;

import java.util.List;

@RestController
@RequestMapping("api/v1/predmeti")
public class PredmetController {

    private final PredmetService predmetService;

    @Autowired
    public PredmetController(PredmetService predmetService) {
        this.predmetService = predmetService;
    }

    @GetMapping("/nepolozeni")
    public ResponseEntity<List<Predmet>> getSveNepolozenePredmete() {
        return ResponseEntity.ok(predmetService.getSveNepolozenePredmete());
    }
    @GetMapping("/slusa")
    public ResponseEntity<List<Predmet>> getTrenutnoSlusanePredmete() {
        return ResponseEntity.ok(predmetService.getTrenutnoSlusani());
    }
    @PostMapping("/slusa")
    public ResponseEntity<List<Predmet>> izaberiPredmeteZaSlusanje(@RequestBody List<Predmet> predmetiZaSlusanje) {
        return ResponseEntity.ok(predmetService.izaberiPredmeteZaSlusanje(predmetiZaSlusanje));
    }

}
