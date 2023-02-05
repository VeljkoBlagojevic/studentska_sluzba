package rs.fon.studentska_sluzba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.service.PredmetService;
import rs.fon.studentska_sluzba.service.StudentService;

import java.util.Set;

@RestController
@RequestMapping("api/v1/prijave")
public class PrijavaController {
    private final PredmetService predmetService;

    private final StudentService studentService;

    public PrijavaController(PredmetService predmetService, StudentService studentService) {
        this.predmetService = predmetService;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Set<Predmet>> getPrijave() {
        return ResponseEntity.ok(predmetService.getPrijave());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Predmet> getPrijavaSaId(@PathVariable Long id) {
        return ResponseEntity.ok(predmetService.getPrijavaSaId(id));
    }

    @PostMapping
    public ResponseEntity<Void> dodajPrijavu(@RequestBody Predmet predmet) {
        studentService.dodajPrijavu(predmet);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiPrijavuSaId(@PathVariable Long id) {
        if (predmetService.obrisiPrijavuSaId(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
