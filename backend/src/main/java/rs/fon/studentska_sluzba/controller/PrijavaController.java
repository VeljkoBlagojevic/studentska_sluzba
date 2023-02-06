package rs.fon.studentska_sluzba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.controller.dto.PredmetDTO;
import rs.fon.studentska_sluzba.controller.mapper.PredmetMapper;
import rs.fon.studentska_sluzba.service.PrijavaService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/v1/prijave")
public class PrijavaController {
    private final PrijavaService prijavaService;

    private final PredmetMapper predmetMapper;


    public PrijavaController(PrijavaService prijavaService, PredmetMapper predmetMapper) {
        this.prijavaService = prijavaService;
        this.predmetMapper = predmetMapper;
    }

    @GetMapping
    public ResponseEntity<Set<PredmetDTO>> getPrijave() {
        return ResponseEntity.ok(new HashSet<>(predmetMapper.entitiesToDTOs(prijavaService.getPrijave().stream().toList())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PredmetDTO> getPrijavaSaId(@PathVariable Long id) {
        return ResponseEntity.ok(predmetMapper.entityToDTO(prijavaService.getPrijavaSaId(id)));
    }

    @PostMapping
    public ResponseEntity<Void> dodajPrijavu(@RequestBody PredmetDTO predmetDTO) {
        prijavaService.dodajPrijavu(predmetMapper.DTOToEntity(predmetDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiPrijavuSaId(@PathVariable Long id) {
        if (prijavaService.obrisiPrijavuSaId(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
