package kz.samat.fooddeliveryservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main configuration class
 *
 * Created by Samat Abibulla on 2023-02-10
 */
@Configuration
@EnableScheduling
public class AppConfig {

    /**
     * Creates instance of model mapper
     *
     * @return model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
