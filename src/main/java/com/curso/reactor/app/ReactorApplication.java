package com.curso.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class ReactorApplication implements CommandLineRunner {

    private final static Logger log = LoggerFactory.getLogger(ReactorApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ReactorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<String> nombres = new ArrayList<String>();
        nombres.add("Javier");
        nombres.add("Chamizo");
        nombres.add("");
        nombres.add("Remolacha");
        nombres.add("Bachaquero");

        List<String> nombresVacios = new ArrayList<String>();

        Flux<String> names = Flux.just("Javier", "Chamizo", "", "Banana", "Bachaquero")
                .doOnNext(
                        name -> {
                            if (name.isEmpty()) {
                                throw new RuntimeException("the name is necessary,");
                            }
                        }
                );
        names.subscribe(
                log::warn,
                error -> log.error(error.getMessage().toUpperCase().concat(" please rectify information")),
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("FINALIZO TODO EL PROCESO.");
                    }
                }
        );
    }
}
