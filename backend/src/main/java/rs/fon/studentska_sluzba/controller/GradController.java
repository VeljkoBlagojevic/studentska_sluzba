package rs.fon.studentska_sluzba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.controller.dto.GradDTO;
import rs.fon.studentska_sluzba.controller.mapper.GradMapper;
import rs.fon.studentska_sluzba.service.GradService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/gradovi")
public class GradController {

    private final GradService gradService;

    private final GradMapper gradMapper;


    @Autowired
    public GradController(GradService gradService, GradMapper gradMapper) {
        this.gradService = gradService;
        this.gradMapper = gradMapper;
    }

    @GetMapping
    public ResponseEntity<List<GradDTO>> getAll() {
        return ResponseEntity.ok(gradMapper.entitiesToDTOs(gradService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradDTO> getGradSaId(@PathVariable Long id) {
        return ResponseEntity.ok(gradMapper.entityToDTO(gradService.getGradSaId(id)));
    }

    @PostMapping
    public ResponseEntity<GradDTO> ubaciGrad(@RequestBody GradDTO gradDTO) {
        return new ResponseEntity<>(gradMapper.entityToDTO(gradService.ubaciGrad(gradMapper.DTOToEntity(gradDTO))), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiGradSaId(@PathVariable Long id) {
        if (gradService.obrisiGradSaId(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}