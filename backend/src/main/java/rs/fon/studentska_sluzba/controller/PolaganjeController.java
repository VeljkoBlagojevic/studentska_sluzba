package rs.fon.studentska_sluzba.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.controller.dto.PolaganjeDTO;
import rs.fon.studentska_sluzba.controller.mapper.PolaganjeMapper;
import rs.fon.studentska_sluzba.service.PolaganjeService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/polaganja")
public class PolaganjeController {

    private final PolaganjeService polaganjeService;

    private final PolaganjeMapper polaganjeMapper;

    public PolaganjeController(PolaganjeService polaganjeService, PolaganjeMapper polaganjeMapper) {
        this.polaganjeService = polaganjeService;
        this.polaganjeMapper = polaganjeMapper;
    }

    @GetMapping
    public ResponseEntity<List<PolaganjeDTO>> getSvaPolaganja() {
        return ResponseEntity.ok(polaganjeMapper.entitiesToDTOs(polaganjeService.getSvaPolaganja()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolaganjeDTO> getPolaganje(@PathVariable Long id) {
        return ResponseEntity.ok(polaganjeMapper.entityToDTO(polaganjeService.getPolaganjeSaId(id)));
    }

    @PostMapping
    public ResponseEntity<PolaganjeDTO> dodajPolaganje(@RequestBody PolaganjeDTO polaganjeDTO) {
        return new ResponseEntity<>(polaganjeMapper.entityToDTO(polaganjeService.ubaciPolaganje(polaganjeMapper.DTOToEntity(polaganjeDTO))), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiPolaganjeSaId(@PathVariable Long id) {
        System.out.println("pera");
        if (polaganjeService.obrisiPolaganjeSaId(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/uspesna")
    public ResponseEntity<List<PolaganjeDTO>> getSvaUspesnaPolaganja() {
        return ResponseEntity.ok(polaganjeMapper.entitiesToDTOs(polaganjeService.getSvaUspesnaPolaganja()));
    }

    @GetMapping("/neuspesna")
    public ResponseEntity<List<PolaganjeDTO>> getSvaNeuspesnaPolaganja() {
        return ResponseEntity.ok(polaganjeMapper.entitiesToDTOs(polaganjeService.getSvaNeuspesnaPolaganja()));
    }
    @GetMapping("/neuspesna/pageable")
    public ResponseEntity<Page<PolaganjeDTO>> getSvaNeuspesnaPolaganja(
            @RequestParam (required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam (required = false, defaultValue = "3") Integer pageSize) {
        Page<PolaganjeDTO> svaNeuspesnaPolaganjaDTOs = polaganjeService.getSvaNeuspesnaPolaganja(pageNumber, pageSize).map(polaganjeMapper::entityToDTO);
        return ResponseEntity.ok(svaNeuspesnaPolaganjaDTOs);
    }
}
