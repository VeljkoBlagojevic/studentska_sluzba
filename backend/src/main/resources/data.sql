DELETE FROM trenutno_slusa;
DELETE FROM predmet;
DELETE FROM student;
DELETE FROM grad;

INSERT INTO grad (id, naziv, zipcode)
VALUES (1, 'Beograd', 11000),
       (2, 'Nis', 18105),
       (3, 'Omoljica', 26230),
       (4, 'Pancevo', 26101),
       (5, 'Kragujevac', 34110),
       (6, 'Petrovac na Mlavi', 12300),
       (7, 'Novi Sad', 21000);

UPDATE grad_seq SET next_val = 8;


INSERT INTO predmet (id, naziv, ESPB)
VALUES (1, 'Strukture podataka i algoritmi', 6),
       (2, 'Ekonomija', 6),
       (3, 'Elektronsko poslovanje', 6),
       (4, 'Matematika 1', 6),
       (5, 'Programiranje 2', 6),
       (6, 'Programiranje 1', 4);

UPDATE predmet_seq SET next_val = 7;

INSERT INTO student (id, ime, prezime, ime_roditelja, indeks, jmbg, studentski_email, licni_email, broj_telefona,
                     mesto_rodjenja_id, username, PASSWORD, ROLE)
VALUES (1, 'Veljko', 'Blagojevic', 'Borislav', '0353/2019', '0208000860069', 'vb20190353@student.fon.bg.ac.rs',
        'vejko@gmail.com', '+381677116969', 3, 'vb20190353',
        '$2a$12$yLCUlQXTK4LQM1jGzZ0IdeAOs7QPW7B1z62g6o8c.07KbDq5K7Xka', 'USER'),
       (2, 'Nikola', 'Vujcic', 'Nebojsa', '0062/2019', '1201001860051', 'nv20190062@student.fon.bg.ac.rs',
        'nikola@gmail.com', '+381677114512', 6, 'nv20190062',
        '$2a$12$DSxUIJvdJcrIv6iBFxmpGup6.7Mf6MVwiFtCZEM3H7iqvqPDerq6W', 'USER'),
       (0, 'Admin', 'Admin', 'Admin', '0000/2019', '0000000000000', 'aa00000000@student.fon.bg.ac.rs',
        'admin@gmail.com', '+381677777777', 1, 'aa00000000',
        '$2a$12$Y8KKkfMj1/rxHj5Uzq.U9.Ntog8gGp8BoUla9FjIo7r5Vr7UegfXW', 'ADMIN');

UPDATE student_seq SET next_val = 3;

INSERT INTO trenutno_slusa (student_id, predmet_id)
VALUES (1, 1),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 6),
       (1, 5),
       (2, 2),
       (2, 5);