package rs.fon.studentska_sluzba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.controller.dto.PredmetDTO;
import rs.fon.studentska_sluzba.controller.dto.StudentDTO;
import rs.fon.studentska_sluzba.controller.mapper.PredmetMapper;
import rs.fon.studentska_sluzba.controller.mapper.StudentMapper;
import rs.fon.studentska_sluzba.service.PrijavaService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("api/v1/prijave")
public class PrijavaController {
    private final PrijavaService prijavaService;

    private final PredmetMapper predmetMapper;
    private final StudentMapper studentMapper;

    public PrijavaController(PrijavaService prijavaService, PredmetMapper predmetMapper, StudentMapper studentMapper) {
        this.prijavaService = prijavaService;
        this.predmetMapper = predmetMapper;
        this.studentMapper = studentMapper;
    }

    @GetMapping
    public ResponseEntity<Set<PredmetDTO>> getPrijave() {
        return ResponseEntity.ok(predmetMapper.entitiesToDTOs(prijavaService.getPrijave()));
    }

    @GetMapping("/admin")
    public ResponseEntity<Map<StudentDTO, Set<PredmetDTO>>> getPrijaveZaAdmina() {
        Map<StudentDTO, Set<PredmetDTO>> map = new HashMap<>();
        prijavaService.getPrijaveZaAdmina().forEach((student, predmeti) ->
            map.put(studentMapper.entityToDTO(student), predmetMapper.entitiesToDTOs(predmeti)));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PredmetDTO> getPrijavaSaId(@PathVariable Long id) {
        return ResponseEntity.ok(predmetMapper.entityToDTO(prijavaService.getPrijavaSaId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiPrijavuSaId(@PathVariable Long id) {
        if (prijavaService.obrisiPrijavuSaId(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Void> dodajPrijavu(@RequestBody PredmetDTO predmetDTO) {
        prijavaService.dodajPrijavu(predmetMapper.DTOToEntity(predmetDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
