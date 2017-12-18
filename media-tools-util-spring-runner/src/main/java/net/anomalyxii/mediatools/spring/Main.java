package net.anomalyxii.mediatools.spring;

import net.anomalyxii.mediatools.spring.configuration.LibraryConfiguration;
import net.anomalyxii.mediatools.api.builders.LibraryBuilder;
import net.anomalyxii.mediatools.api.models.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

import java.net.URI;

/**
 * Main class for running the tools via Spring Boot
 *
 * Created by Anomaly on 16/04/2016.
 */
@SpringBootApplication
@Import(LibraryConfiguration.class)
public class Main implements ApplicationRunner {

    // ******************************
    // Autowired Beans
    // ******************************

    @Autowired LibraryBuilder builder;

    // ******************************
    // ApplicationRunner Methods
    // ******************************

    @Override
    public void run(ApplicationArguments args) throws Exception {

        URI uri = URI.create("file:////ISHIKAWA/Music");
        Library library = builder.withSource(uri).build();

        System.out.println("Loaded " + library.songs().size() + " songs");
        System.out.println("Loaded " + library.albums().size() + " albums");
        System.out.println("Loaded " + library.artists().size() + " artists");

    }

    // ******************************
    // Main Methods
    // ******************************

    public static void main(String... args) throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(Main.class)
                .web(false);

        SpringApplication application = builder.build();
        application.run(args);
    }

}