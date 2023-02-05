-- DELETE
-- FROM nepolozeni_predmet;
-- DELETE
-- FROM predmet;
-- DELETE
-- FROM student;
-- DELETE
-- FROM grad;
-- DELETE
-- FROM obavestenje;
-- DELETE
-- FROM prijava;
--
-- INSERT INTO grad (id, naziv, zipcode)
-- VALUES (1, 'Beograd', 11000),
--        (2, 'Nis', 18105),
--        (3, 'Omoljica', 26230),
--        (4, 'Pancevo', 26101),
--        (5, 'Kragujevac', 34110),
--        (6, 'Petrovac na Mlavi', 12300),
--        (7, 'Novi Sad', 21000);
--
-- UPDATE grad_seq
-- SET next_val = 8;
--
--
-- INSERT INTO predmet (id, naziv, ESPB)
-- VALUES (1, 'Strukture podataka i algoritmi', 6),
--        (2, 'Ekonomija', 6),
--        (3, 'Elektronsko poslovanje', 6),
--        (4, 'Matematika 1', 6),
--        (5, 'Programiranje 2', 6),
--        (6, 'Programiranje 1', 4);
--
-- UPDATE predmet_seq
-- SET next_val = 7;
--
--
-- INSERT INTO student (id, ime, prezime, ime_roditelja, indeks, jmbg, studentski_email, licni_email, broj_telefona, slika,
--                      mesto_rodjenja_id, username, PASSWORD, ROLE)
-- VALUES (1, 'Veljko', 'Blagojevic', 'Borislav', '0353/2019', '0208000860069', 'vb20190353@student.fon.bg.ac.rs',
--         'vejko@gmail.com', '+381677116969',
--         'https://media.licdn.com/dms/image/D4D03AQGfDciO9qccSg/profile-displayphoto-shrink_800_800/0/1665691382240?e=1680739200&v=beta&t=OmDSbHTkRPAUegG52SrowRDi8GiqjSDvE7c6OCQvL6k',
--         3, 'vb20190353',
--         '$2a$12$yLCUlQXTK4LQM1jGzZ0IdeAOs7QPW7B1z62g6o8c.07KbDq5K7Xka', 'USER'),
--        (2, 'Nikola', 'Vujcic', 'Nesa', '0062/2019', '1201001860051', 'nv20190062@student.fon.bg.ac.rs',
--         'nikola@gmail.com', '+381677114512',
--         'https://media.licdn.com/dms/image/D4E03AQERPaxNYTUzXg/profile-displayphoto-shrink_800_800/0/1667431634582?e=1680739200&v=beta&t=KXdvm7fvNZBwtoiPuZ1xT3gZKLlrRWMsn1dudYf-Ir0',
--         6, 'nv20190062',
--         '$2a$12$DSxUIJvdJcrIv6iBFxmpGup6.7Mf6MVwiFtCZEM3H7iqvqPDerq6W', 'USER'),
--        (0, 'Admin', 'Admin', 'Admin', '0000/2019', '0000000000000', 'aa00000000@student.fon.bg.ac.rs',
--         'admin@gmail.com', '+381677777777', 'https://balkan-hub.com/prodavnica/483-Niara_thickbox/admin.jpg', 1,
--         'aa00000000',
--         '$2a$12$Y8KKkfMj1/rxHj5Uzq.U9.Ntog8gGp8BoUla9FjIo7r5Vr7UegfXW', 'ADMIN');
--
-- UPDATE student_seq
-- SET next_val = 3;
--
--
-- INSERT INTO nepolozeni_predmet(id, trenutno_slusa, predmet_id, student_id)
-- VALUES (1, TRUE, 1, 2);
-- INSERT INTO nepolozeni_predmet(id, trenutno_slusa, predmet_id, student_id)
-- VALUES (2, FALSE, 3, 2);
-- INSERT INTO nepolozeni_predmet(id, trenutno_slusa, predmet_id, student_id)
-- VALUES (3, TRUE, 2, 1);
-- INSERT INTO nepolozeni_predmet(id, trenutno_slusa, predmet_id, student_id)
-- VALUES (4, TRUE, 5, 1);
--
-- UPDATE nepolozeni_predmet_seq
-- SET next_val = 5;
--
-- /*Data for the table `obavestenje` */
--
-- INSERT INTO obavestenje (id, datum, sadrzaj)
-- VALUES (1, '2020-05-05', 'Maske morate da nosite'),
--        (2, '2019-01-02', 'Upis ocena za predmet UIS je u sali C025');
--
-- /*Data for the table `obavestenje_seq` */
--
-- UPDATE obavestenje_seq
-- SET next_val = 3;
--
-- INSERT INTO prijava (student_id, predmet_id)
-- VALUES (1, 2),
--        (2, 1);

-- OVDE JE AUTOGENERISANO

/*
SQLyog Community v13.2.0 (64 bit)
MySQL - 10.4.27-MariaDB : Database - studentska_sluzba
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
-- CREATE
-- DATABASE /*!32312 IF NOT EXISTS*/`studentska_sluzba` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE
`studentska_sluzba`;

    /*Data for the table `obavestenje` */

insert into `obavestenje`(`id`, `datum`, `sadrzaj`)
values (1, '2020-05-05', 'Maske morate da nosite'),
       (2, '2019-01-02', 'Upis ocena za predmet UIS je u sali C025'),
       (3, '2022-08-11',
        'Studenti na teret budžeta kojima je odobrena molba za prijavu dodatnih bodova, a nisu prosledili dokaz o uplati u skladu sa instrukcijama dobijenim u odgovoru, moraju to učiniti najkasnije do petka, 28. oktobra 2022. godine. U suprotnom smatraće se da su ');

/*Data for the table `obavestenje_seq` */

insert into `obavestenje_seq`(`next_val`)
values (4);

/*Data for the table `grad` */

insert into `grad`(`id`, `naziv`, `zipcode`)
values (1, 'Beograd', 11000),
       (2, 'Nis', 18105),
       (3, 'Omoljica', 26230),
       (4, 'Pancevo', 26101),
       (5, 'Kragujevac', 34110),
       (6, 'Petrovac na Mlavi', 12300),
       (7, 'Novi Sad', 21000);

/*Data for the table `grad_seq` */

insert into `grad_seq`(`next_val`)
values (8);


    /*Data for the table `predmet` */

insert into `predmet`(`id`, `espb`, `naziv`)
values (1, 6, 'Strukture podataka i algoritmi'),
       (2, 6, 'Ekonomija'),
       (3, 6, 'Elektronsko poslovanje'),
       (4, 6, 'Matematika 1'),
       (5, 6, 'Programiranje 2'),
       (6, 4, 'Programiranje 1');

/*Data for the table `predmet_seq` */

insert into `predmet_seq`(`next_val`)
values (7);

/*Data for the table `student` */

insert into `student`(`id`, `broj_telefona`, `datum_rodjenja`, `ime`, `ime_roditelja`, `indeks`, `jmbg`, `licni_email`,
                      `password`, `prezime`, `role`, `slika`, `studentski_email`, `username`, `mesto_rodjenja_id`)
values (0, '+381677777777', NULL, 'Admin', 'Admin', '0000/2019', '0000000000000', 'admin@gmail.com',
        '$2a$12$Y8KKkfMj1/rxHj5Uzq.U9.Ntog8gGp8BoUla9FjIo7r5Vr7UegfXW', 'Admin', 'ADMIN',
        'https://balkan-hub.com/prodavnica/483-Niara_thickbox/admin.jpg', 'aa00000000@student.fon.bg.ac.rs',
        'aa00000000', 1),
       (1, '+381677116969', NULL, 'Veljko', 'Borislav', '0353/2019', '0208000860069', 'vejko@gmail.com',
        '$2a$12$yLCUlQXTK4LQM1jGzZ0IdeAOs7QPW7B1z62g6o8c.07KbDq5K7Xka', 'Blagojevic', 'USER',
        'https://media.licdn.com/dms/image/D4D03AQGfDciO9qccSg/profile-displayphoto-shrink_800_800/0/1665691382240?e=1680739200&v=beta&t=OmDSbHTkRPAUegG52SrowRDi8GiqjSDvE7c6OCQvL6k',
        'vb20190353@student.fon.bg.ac.rs', 'vb20190353', 3),
       (2, '+381677114512', NULL, 'Nikola', 'Nesa', '0062/2019', '1201001860051', 'nikola@gmail.com',
        '$2a$12$DSxUIJvdJcrIv6iBFxmpGup6.7Mf6MVwiFtCZEM3H7iqvqPDerq6W', 'Vujcic', 'USER',
        'https://media.licdn.com/dms/image/D4E03AQERPaxNYTUzXg/profile-displayphoto-shrink_800_800/0/1667431634582?e=1680739200&v=beta&t=KXdvm7fvNZBwtoiPuZ1xT3gZKLlrRWMsn1dudYf-Ir0',
        'nv20190062@student.fon.bg.ac.rs', 'nv20190062', 6);

/*Data for the table `student_seq` */

insert into `student_seq`(`next_val`)
values (3);




/*Data for the table `nepolozeni_predmet` */

insert into `nepolozeni_predmet`(`id`, `trenutno_slusa`, `predmet_id`, `student_id`)
values (1, true, 1, 2),
       (2, false, 3, 2),
       (3, true, 2, 1),
       (4, true, 5, 1);

/*Data for the table `nepolozeni_predmet_seq` */

insert into `nepolozeni_predmet_seq`(`next_val`)
values (5);



/*Data for the table `polaganje` */

insert into `polaganje`(`id`, `datum`, `ocena`, `polozio`, `predmet_id`, `student_id`)
values (0, '2022-03-16', 5, false, 2, 2),
       (1, '2022-06-15', 6, true, 6, 1),
       (2, '2022-12-15', 8, true, 1, 1),
       (3, '2023-01-04', 9, true, 3, 1),
       (4, '2021-09-01', 5, false, 4, 1),
       (5, '2022-07-13', 9, true, 4, 1),
       (6, '2019-11-29', 5, false, 2, 2),
       (7, '2021-07-15', 9, true, 4, 2),
       (9, '2022-06-07', 5, false, 5, 2),
       (10, '2023-01-27', 8, true, 2, 2),
       (11, '2023-01-12', 9, true, 5, 2),
       (12, '2022-10-12', 7, true, 6, 2),
       (13, '2022-03-16', 5, false, 1, 2);

/*Data for the table `polaganje_seq` */

insert into `polaganje_seq`(`next_val`)
values (14);



/*Data for the table `prijava` */

insert into `prijava`(`student_id`, `predmet_id`)
values (1, 2),
       (1, 5),
       (2, 1);



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
