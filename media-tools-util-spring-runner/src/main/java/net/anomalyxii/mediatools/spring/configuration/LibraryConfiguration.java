package net.anomalyxii.mediatools.spring.configuration;

import net.anomalyxii.mediatools.api.builders.LibraryBuilder;
import net.anomalyxii.mediatools.api.builders.LibraryBuilderContext;
import net.anomalyxii.mediatools.spring.domain.LibraryBuilderImpl;
import net.anomalyxii.mediatools.spring.domain.RepositoryContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configure the {@link LibraryBuilder}
 */
@Configuration
@EnableJpaRepositories(basePackages = "net.anomalyxii.mediatools.spring.data")
public class LibraryConfiguration {

    // ******************************
    // Beans
    // ******************************

    @Bean
    public static LibraryBuilderContext<Long> repositoryContext() {
        return new RepositoryContext();
    }

    @Bean
    public static LibraryBuilder libraryBuilder(@Autowired RepositoryContext context) {
        return new LibraryBuilderImpl(context);
    }

}
