package rs.fon.studentska_sluzba.controller.mapper;

import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.controller.dto.StudentDTO;
import rs.fon.studentska_sluzba.domain.Grad;
import rs.fon.studentska_sluzba.domain.Student;

@Component
public class StudentMapper implements Mapper<Student, StudentDTO> {
    @Override
    public StudentDTO entityToDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getIme(),
                student.getPrezime(),
                student.getJmbg(),
                student.getIndeks(),
                student.getDatumRodjenja(),
                student.getBrojTelefona(),
                student.getStudentskiEmail(),
                student.getLicniEmail(),
                student.getSlika(),
                student.getImeRoditelja(),
                student.getMestoRodjenja().getNaziv(),
                student.getUsername()
        );
    }

    @Override
    public Student DTOToEntity(StudentDTO studentDTO) {
        if(studentDTO == null) return new Student();
        return Student.builder()
                .id(studentDTO.id())
                .ime(studentDTO.ime())
                .prezime(studentDTO.prezime())
                .jmbg(studentDTO.jmbg())
                .indeks(studentDTO.indeks())
                .datumRodjenja(studentDTO.datumRodjenja())
                .brojTelefona(studentDTO.brojTelefona())
                .studentskiEmail(studentDTO.studentskiEmail())
                .licniEmail(studentDTO.licniEmail())
                .slika(studentDTO.slika())
                .imeRoditelja(studentDTO.imeRoditelja())
                .mestoRodjenja(Grad.builder().naziv(studentDTO.mestoRodjenja()).build())
                .username(studentDTO.username())
                .build();
    }
}
