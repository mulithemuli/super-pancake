package superpancake.contentdetectorservice.config;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class Beans {

    @Bean
    public TikaConfig tikaConfig() {
        try {
            return new TikaConfig();
        } catch (IOException | TikaException exception) {
            throw new RuntimeException("Tika configuration failed", exception);
        }
    }
}
