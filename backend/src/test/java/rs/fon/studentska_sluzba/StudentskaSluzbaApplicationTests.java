package rs.fon.studentska_sluzba;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.fon.studentska_sluzba.controller.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class StudentskaSluzbaApplicationTests {


	@Test
	void contextLoads(ApplicationContext context) {
		assertThat(context).isNotNull();
	}

	@Test
	void hasGradControllerConfigured(ApplicationContext context) {
		assertThat(context.getBean(GradController.class)).isNotNull();
	}


	@Test
	void hasMolbaControllerConfigured(ApplicationContext context) {
		assertThat(context.getBean(MolbaController.class)).isNotNull();
	}


	@Test
	void hasObavestenjeControllerConfigured(ApplicationContext context) {
		assertThat(context.getBean(ObavestenjeController.class)).isNotNull();
	}


	@Test
	void hasPolaganjeControllerConfigured(ApplicationContext context) {
		assertThat(context.getBean(PolaganjeController.class)).isNotNull();
	}


	@Test
	void hasPredmetControllerConfigured(ApplicationContext context) {
		assertThat(context.getBean(PredmetController.class)).isNotNull();
	}


}
