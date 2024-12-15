package com.MangoEduardo.DND.homebrew.API.Config;

import com.sendgrid.SendGrid;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ApiConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv
                .configure()
                .load();
    }

    @Bean
    public SendGrid sendGrid() {
        return new SendGrid(dotenv().get("SENDGRID_API_KEY"));
    }
}
