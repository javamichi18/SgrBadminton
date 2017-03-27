package de.soflimo.sgr;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import de.soflimo.sgr.config.TomcatConfiguration;
import de.soflimo.sgr.model.Rangliste;
import de.soflimo.sgr.model.Spiel;
import de.soflimo.sgr.model.Spieler;
import de.soflimo.sgr.repository.RanglisteRepository;
import de.soflimo.sgr.repository.SpielRepository;
import de.soflimo.sgr.repository.SpielerRepository;

@SpringBootApplication
@Import(TomcatConfiguration.class)
@Controller
//@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class SgrBadmintonApplication extends WebMvcConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SgrBadmintonApplication.class);


    public static void main (String[] args) {
        SpringApplication.run(SgrBadmintonApplication.class, args);
    }


    @Override
    public void addViewControllers (ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("/sgrbadminton/login");
    }


    @Bean
    public CommandLineRunner demo (SpielerRepository repository, SpielRepository spielRepository,
        RanglisteRepository ranglisteRepository) {
        return (args) -> {

            Iterable<Spieler> all = repository.findAll();
            if (all == null || !all.iterator().hasNext()) {

                Rangliste rangliste = new Rangliste();
                rangliste.setName("sgr");
                rangliste = ranglisteRepository.save(rangliste);

                //rangliste.add(new Spieler("Thomas", "Härtel", "thomas@web.de", 1));
                rangliste.add(new Spieler("Thomas", "H", "thomas@web.de", 1));
                rangliste.add(new Spieler("Matthias", "Spiel", "matthias.s@gmx.de", 2));
                rangliste.add(new Spieler("Max", "H", "max@h.de", 3));
                rangliste.add(new Spieler("Karl-Heinz", "S", "k@gmx.de", 4));
                rangliste.add(new Spieler("Johannes", "B", "johannes@aol.com", 5));
                rangliste.add(new Spieler("Reinhard", "J", "reinhard@gmx.de", 6));
                rangliste.add(new Spieler("Werner", "H", "werner@gmx.de", 7));
                rangliste.add(new Spieler("Michael", "U", "michi@gmx.de", 8));
                rangliste.add(new Spieler("Thomas", "S", "thomas.s@gmx.de", 9));
                rangliste.add(new Spieler("Wolfram", "F", "wolfram@gmx.de", 10));
                rangliste.add(new Spieler("Sabine", "P", "sabine@gmx.de", 11));
                rangliste.add(new Spieler("Thomas", "M", "thomas.m@gmx.de", 12));
                rangliste.add(new Spieler("Michael", "D", "michael@gmx.de", 13));
                rangliste.add(new Spieler("Uwe", "S", "uwe@gmx.de", 14));
                rangliste.add(new Spieler("Dirk", "W", "dirk@gmx.de", 15));
                rangliste.add(new Spieler("Claudia", "S", "claudia@gmx.de", 16));
                rangliste.add(new Spieler("Jörg", "H", "joerg@gmx.de", 17));
                rangliste.add(new Spieler("Ruth", "F", "ruth@gmx.de", 18));
                rangliste.add(new Spieler("Gerlinde", "F", "gerlinde@gmx.de", 19));
                rangliste.add(new Spieler("Und", "Pumuckl", "pumuckl@gmx.de", 20));

                rangliste = ranglisteRepository.save(rangliste);

                log.info("Spieler found with findAll():");

                Iterable<Spieler> spielerList = repository.findAll();
                for (Spieler spieler : spielerList)
                    log.info(spieler.toString());

                Spiel spiel = new Spiel(rangliste.getSpieler().get(0), rangliste.getSpieler().get(1));
                spiel.setGespielt(true);
                spiel.setGespieltAm(new Date());
                spiel.getInput().setSatz1_1("21");
                spiel.getInput().setSatz1_2("15");
                spiel.getInput().setSatz2_1("21");
                spiel.getInput().setSatz2_2("16");
                spiel.uebernehmeFormularDaten(spiel);
                spiel.berechneGewinner();
                spielRepository.save(spiel);
            }
        };
    }
}
