package rs.fon.studentska_sluzba.controller.dto;

public record NepolozeniPredmetDTO(
        Long id,
        Boolean trenutnoSlusa,
        PredmetDTO predmet) {
}
