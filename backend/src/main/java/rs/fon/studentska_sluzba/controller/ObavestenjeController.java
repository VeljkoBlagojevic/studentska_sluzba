package rs.fon.studentska_sluzba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.controller.dto.ObavestenjeDTO;
import rs.fon.studentska_sluzba.controller.mapper.ObavestenjeMapper;
import rs.fon.studentska_sluzba.service.ObavestenjeService;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("api/v1/obavestenja")
public class ObavestenjeController {

    private final ObavestenjeService obavestenjeService;

    private final ObavestenjeMapper obavestenjeMapper;

    public ObavestenjeController(ObavestenjeService obavestenjeService, ObavestenjeMapper obavestenjeMapper) {
        this.obavestenjeService = obavestenjeService;
        this.obavestenjeMapper = obavestenjeMapper;
    }

    @GetMapping
    public ResponseEntity<List<ObavestenjeDTO>> getSvaObavestenja() {
        return ResponseEntity.ok(obavestenjeMapper.entitiesToDTOs(obavestenjeService.getSvaObavestenja()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObavestenjeDTO> getObavestenje(@PathVariable Long id) {
        return ResponseEntity.ok(obavestenjeMapper.entityToDTO(obavestenjeService.getObavestenjeSaId(id)));
    }

    @PostMapping
    public ResponseEntity<ObavestenjeDTO> post(@RequestBody ObavestenjeDTO obavestenjeDTO) {
        return new ResponseEntity<>(obavestenjeMapper.entityToDTO(obavestenjeService.ubaciObavestenje(obavestenjeMapper.DTOToEntity(obavestenjeDTO))), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiObavestenjeSaId(@PathVariable Long id) {
        if (obavestenjeService.obrisiObavestenjeSaId(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
