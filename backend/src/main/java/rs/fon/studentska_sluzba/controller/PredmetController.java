package rs.fon.studentska_sluzba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.controller.dto.NepolozeniPredmetDTO;
import rs.fon.studentska_sluzba.controller.dto.PredmetDTO;
import rs.fon.studentska_sluzba.controller.mapper.NepolozeniPredmetMapper;
import rs.fon.studentska_sluzba.controller.mapper.PredmetMapper;
import rs.fon.studentska_sluzba.service.PredmetService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/predmeti")
public class PredmetController {

    private final PredmetService predmetService;

    private final NepolozeniPredmetMapper nepolozeniPredmetMapper;

    private final PredmetMapper predmetMapper;

    @Autowired
    public PredmetController(PredmetService predmetService, NepolozeniPredmetMapper nepolozeniPredmetMapper, PredmetMapper predmetMapper) {
        this.predmetService = predmetService;
        this.nepolozeniPredmetMapper = nepolozeniPredmetMapper;
        this.predmetMapper = predmetMapper;
    }

    @GetMapping("/nepolozeni")
    public ResponseEntity<List<NepolozeniPredmetDTO>> getSveNepolozenePredmete() {
        return ResponseEntity.ok(nepolozeniPredmetMapper.entitiesToDTOs(predmetService.getSveNepolozenePredmete()));
    }

    @GetMapping("/slusa")
    public ResponseEntity<List<PredmetDTO>> getTrenutnoSlusanePredmete() {
        return ResponseEntity.ok(predmetMapper.entitiesToDTOs((predmetService.getTrenutnoSlusani())));
    }

    @PatchMapping("/slusa")
    public ResponseEntity<NepolozeniPredmetDTO> izaberiPredmetZaSlusanje(@RequestBody NepolozeniPredmetDTO nepolozeniPredmetDTO) {
        return ResponseEntity.ok(nepolozeniPredmetMapper.entityToDTO(predmetService.dodajZaSlusanje(nepolozeniPredmetMapper.DTOToEntity(nepolozeniPredmetDTO))));
    }

}
