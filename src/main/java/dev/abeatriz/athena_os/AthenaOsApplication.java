package dev.abeatriz.athena_os;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class AthenaOsApplication {
    public static void main(String[] args) {
        if (Files.exists(Paths.get(".env"))) {
            System.out.println(".env encontrado - carregando variáveis");
            Dotenv.configure().load().entries().forEach(e ->
                    System.setProperty(e.getKey(), e.getValue()));
        }

        SpringApplication.run(AthenaOsApplication.class, args);
    }
}


