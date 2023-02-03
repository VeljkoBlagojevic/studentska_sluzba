package rs.fon.studentska_sluzba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.domain.Grad;
import rs.fon.studentska_sluzba.service.GradService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gradovi")
public class GradController {

    private final GradService gradService;

    @Autowired
    public GradController(GradService gradService) {
        this.gradService = gradService;
    }

    @GetMapping
    public ResponseEntity<List<Grad>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return ResponseEntity.ok(gradService.findAll());
    }

    @PostMapping
    public ResponseEntity<Grad> post(@RequestBody Grad grad) {
        return new ResponseEntity<>(gradService.ubaciGrad(grad), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteGrad(@RequestParam Long id) {
        if (gradService.deleteGrad(id))
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}