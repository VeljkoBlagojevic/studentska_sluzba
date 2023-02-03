package rs.fon.studentska_sluzba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.service.PredmetService;

import java.util.List;

@RestController
@RequestMapping("api/v1/predmeti")
public class PredmetController {

    private final PredmetService predmetService;

    @Autowired
    public PredmetController(PredmetService predmetService) {
        this.predmetService = predmetService;
    }

    @GetMapping("/slusa")
    public ResponseEntity<List<Predmet>> getTrenutnoSlusanePredmete() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        return ResponseEntity.ok(predmetService.getTrenutnoSlusanePredmeteOdStudenta(username));
    }

}
