package rs.fon.studentska_sluzba.logging.json;

import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class GsonConfiguration {

    @Bean
    public GsonBuilderCustomizer typeAdapterRegistration() {
        return builder -> {
            builder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
            builder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
            builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
            builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
            builder.setLenient();
            builder.setPrettyPrinting();
        };
    }
}