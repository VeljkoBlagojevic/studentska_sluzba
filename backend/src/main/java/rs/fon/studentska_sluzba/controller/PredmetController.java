package rs.fon.studentska_sluzba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.controller.dto.PredmetDTO;
import rs.fon.studentska_sluzba.controller.mapper.PredmetMapper;
import rs.fon.studentska_sluzba.domain.NepolozeniPredmet;
import rs.fon.studentska_sluzba.service.PredmetService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/predmeti")
public class PredmetController {

    private final PredmetService predmetService;

    private final PredmetMapper predmetMapper;

    @Autowired
    public PredmetController(PredmetService predmetService, PredmetMapper predmetMapper) {
        this.predmetService = predmetService;
        this.predmetMapper = predmetMapper;
    }

    @GetMapping("/nepolozeni")
    public ResponseEntity<List<NepolozeniPredmet>> getSveNepolozenePredmete() {
        return ResponseEntity.ok(predmetService.getSveNepolozenePredmete());
    }
    @GetMapping("/slusa")
    public ResponseEntity<List<PredmetDTO>> getTrenutnoSlusanePredmete() {
        return ResponseEntity.ok(predmetMapper.entitiesToDTOs(predmetService.getTrenutnoSlusani()));
    }
    @PostMapping("/slusa")
    public ResponseEntity<NepolozeniPredmet> izaberiPredmeteZaSlusanje(@RequestBody NepolozeniPredmet nepolozeniPredmet) {
        return ResponseEntity.ok(predmetService.dodajZaSlusanje(nepolozeniPredmet));
    }

}
