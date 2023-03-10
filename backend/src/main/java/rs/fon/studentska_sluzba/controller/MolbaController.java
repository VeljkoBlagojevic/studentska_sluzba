package rs.fon.studentska_sluzba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.controller.dto.MolbaDTO;
import rs.fon.studentska_sluzba.controller.mapper.MolbaMapper;
import rs.fon.studentska_sluzba.service.MolbaService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/molbe")
public class MolbaController {

    private MolbaService molbaService;

    private MolbaMapper molbaMapper;

    public MolbaController(MolbaService molbaService, MolbaMapper molbaMapper) {
        this.molbaService = molbaService;
        this.molbaMapper = molbaMapper;
    }

    @GetMapping
    public ResponseEntity<List<MolbaDTO>> getAll() {
        return ResponseEntity.ok(molbaMapper.entitiesToDTOs(molbaService.findAll()));
    }
    @GetMapping("/uobradi")
    public ResponseEntity<List<MolbaDTO>> getAllUObradi() {
        return ResponseEntity.ok(molbaMapper.entitiesToDTOs(molbaService.findAllUObradi()));
    }

    @GetMapping("/razresene")
    public ResponseEntity<List<MolbaDTO>> getAllRazresene() {
        return ResponseEntity.ok(molbaMapper.entitiesToDTOs(molbaService.findAllRazresene()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MolbaDTO> getMolbaSaId(@PathVariable Long id) {
        return ResponseEntity.ok(molbaMapper.entityToDTO(molbaService.getMolbaSaId(id)));
    }

    @PostMapping("/uobradi")
    public ResponseEntity<MolbaDTO> dodajMolbuUObradi(@RequestBody MolbaDTO molbaDTO) {
        return new ResponseEntity<>(molbaMapper.entityToDTO(molbaService.dodajMolbuUObradi(molbaMapper.DTOToEntity(molbaDTO))), HttpStatus.CREATED);
    }

    @PatchMapping("/razresi")
    public ResponseEntity<MolbaDTO> razresiMolbu(@RequestBody MolbaDTO molbaDTO) {
        System.out.println(molbaDTO);
        return new ResponseEntity<>(molbaMapper.entityToDTO(molbaService.razresiMolbu(molbaMapper.DTOToEntity(molbaDTO))), HttpStatus.CREATED);
    }
    @PatchMapping("/{id}/razresi")
    public ResponseEntity<MolbaDTO> razresiMolbu(@PathVariable Long id, @RequestBody String odgovor) {
        System.out.println("dsafsadfsdfasdfsafdsfdsafdsaf "+odgovor);
        return new ResponseEntity<>(molbaMapper.entityToDTO(molbaService.razresiMolbu(id, odgovor)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiMolbuSaId(@PathVariable Long id) {
        if(molbaService.obrisiMolbuSaId(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
