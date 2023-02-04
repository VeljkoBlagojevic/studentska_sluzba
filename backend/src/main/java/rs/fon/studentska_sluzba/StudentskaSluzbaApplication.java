package rs.fon.studentska_sluzba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class StudentskaSluzbaApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentskaSluzbaApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer configure(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry){
				registry.addMapping("/*").allowedOrigins("http://localhost:3000");
			}
		};
	}
}
