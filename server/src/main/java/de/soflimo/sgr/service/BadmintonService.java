package de.soflimo.sgr.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.soflimo.sgr.model.ForderungsModell;
import de.soflimo.sgr.model.Rangliste;
import de.soflimo.sgr.model.Spiel;
import de.soflimo.sgr.model.Spieler;
import de.soflimo.sgr.model.Verbesserung;
import de.soflimo.sgr.repository.RanglisteRepository;
import de.soflimo.sgr.repository.SpielRepository;
import de.soflimo.sgr.repository.SpielerRepository;

/**
 *
 */
@Service
public class BadmintonService {

    public static final String SGR = "sgr";

    @Autowired
    private SpielerRepository spielerRepository;

    @Autowired
    private SpielRepository spielRepository;

    @Autowired
    private ForderungsModell forderungsModell;

    @Autowired
    private RanglisteRepository ranglisteRepository;

    private static final Logger log = LoggerFactory.getLogger(BadmintonService.class);


    public Rangliste getRangliste (String angemeldeterBenutzer) {

        Rangliste rangliste = ranglisteRepository.findByName(SGR);
        if (angemeldeterBenutzer != null) {
            Spieler user = spielerRepository.findByEmail(angemeldeterBenutzer);
            if (user != null) {
                rangliste.setMeinRang(user.getRang());
                rangliste.setUserName(user.getName());
                List<Integer> forderungen = forderungsModell.getForderungen(user.getRang());
                if (forderungen != null && forderungen.size() > 0)
                    rangliste.getKannFordern().addAll(forderungen);
            }
        }

        return rangliste;
    }


    public Spiel erstelleForderung (String spielerFordert, Long geforderterSpielerId) {

        Spieler spieler1 = spielerRepository.findByEmail(spielerFordert);
        Spieler geforderterSpieler = spielerRepository.findOne(geforderterSpielerId);

        Spiel spiel = new Spiel(spieler1, geforderterSpieler);
        if (spieler1 == null) {
            spiel.addMeldung("Spieler nicht gefunden: " + spielerFordert, Severity.FEHLER);
            return spiel;
        }
        if (geforderterSpieler == null) {
            spiel.addMeldung("Spieler nicht gefunden: " + geforderterSpielerId, Severity.FEHLER);
            return spiel;
        }

        List<Integer> moeglicheForderungen = forderungsModell.getForderungen(spieler1.getRang());
        if (moeglicheForderungen != null && geforderterSpieler.getRang() != null && !moeglicheForderungen
            .contains(geforderterSpieler.getRang())) {
            spiel.addMeldung("Forderung nicht möglich! " + spieler1 + " - " + geforderterSpieler, Severity.WARN);
            return spiel;
        }

        List<Spiel> offeneForderungen = spielRepository.findByGespieltFalseOrderByGefordertAm();
        for (Spiel spielOffen : offeneForderungen) {
            if (spielOffen.getSpieler1().equals(spieler1) && spielOffen.getSpieler2().equals(geforderterSpieler)) {
                spielOffen.addMeldung("Offene Forderung existiert bereits: " + spieler1 + " - " + geforderterSpieler,
                    Severity.WARN);
                return spielOffen;
            }
        }

        spiel = spielRepository.save(spiel);

        return spiel;
    }


    public List<Spiel> getOffeneForderungen () {

        List<Spiel> spiele = spielRepository.findByGespieltFalseOrderByGefordertAm();

        return spiele;
    }


    public Spiel speichereSpiel (Spiel spielIn) {

        log.info("speichereSpiel: " + spielIn);
        if (spielIn == null || spielIn.getId() == null) {
            return null;
        }

        // TODO: validierungen

        Spiel spiel = spielRepository.findOne(spielIn.getId());
        if (spiel == null) {
            log.warn("cannot find spiel with ID " + spiel.getId());
            return null;
        }

        if (spiel.isGespielt()) {
            spiel.addMeldung("Das Spiel wurde bereits gespeichert - neuen Daten wurden nicht übernommen.",
                Severity.FEHLER);
            return spiel;
        }

        spiel.uebernehmeFormularDaten(spielIn);
        spiel.setGespielt(true);
        if (spielIn.getGespieltAm() != null)
            spiel.setGespieltAm(spielIn.getGespieltAm());
        else
            spiel.setGespieltAm(new Date());
        if (spiel.berechneGewinner()) {
            spiel = spielRepository.save(spiel);
            log.info("Spiel gespeichert: " + spiel);
            spiel.addMeldung("Spiel wurde gespeichert", Severity.SUCCESS);

            if (aendereRangliste(spiel)) {
                spiel.addMeldung("Rangliste wurde angepasst.", Severity.SUCCESS);
                String gewinner = spiel.getGewinner();
                if ("Spieler1".equals(gewinner)) {
                    spiel.setVerbesserungSpieler1(Verbesserung.PLUS);
                    spiel.setVerbesserungSpieler2(Verbesserung.MINUS);
                }
                if ("Spieler2".equals(gewinner)) {
                    spiel.setVerbesserungSpieler1(Verbesserung.MINUS);
                    spiel.setVerbesserungSpieler2(Verbesserung.PLUS);
                }
            } else {
                spiel.addMeldung("Rangliste bleibt unverändert.", Severity.INFO);
                spiel.setVerbesserungSpieler1(Verbesserung.NEUTRAL);
                spiel.setVerbesserungSpieler2(Verbesserung.NEUTRAL);

            }
            spielRepository.save(spiel);
        } else
            spiel.addMeldung("Spiel wurde nicht gespeichert.", Severity.FEHLER);

        if (spielIn != null && spielIn.getInput() != null)
            spiel.setInput(spielIn.getInput());

        return spiel;
    }


    private boolean aendereRangliste (Spiel spiel) {

        String win = spiel.getGewinner();
        Integer rang1 = spiel.getSpieler1().getRang();
        Integer rang2 = spiel.getSpieler2().getRang();

        if ("Spieler1".equals(win) && rang2 < rang1) {
            Rangliste rangliste = ranglisteRepository.findByName(SGR);
            int rangNeu = rang2 - 1;
            log.info("aendere Rangliste: " + spiel.getSpieler1() + " --> Rang " + rang2);

            List<Spieler> spielerList = rangliste.getSpieler();
            spielerList.remove(spiel.getSpieler1());
            spielerList.add(rangNeu, spiel.getSpieler1());

            ranglisteRepository.save(rangliste);
            log.info("rangliste geaendert: " + rangliste);
            return true;
        } else if ("Spieler2".equals(win) && rang1 < rang2) {
            Rangliste rangliste = ranglisteRepository.findByName(SGR);
            int rangNeu = rang1 - 1;
            log.info("aendere Rangliste: " + spiel.getSpieler2() + " --> Rang " + rang1);

            List<Spieler> spielerList = rangliste.getSpieler();
            spielerList.remove(spiel.getSpieler2());
            spielerList.add(rangNeu, spiel.getSpieler2());

            ranglisteRepository.save(rangliste);
            log.info("rangliste geaendert: " + rangliste);
            return true;
        }

        return false;
    }


    public List<Spiel> getDurchgefuehrteSpiele () {

        List<Spiel> gespielteSpiele = spielRepository.findByGespieltTrueOrderByGespieltAmDesc();

        return gespielteSpiele;
    }


    public Spiel getSpiel (Long spielId) {
        return spielRepository.findOne(spielId);
    }
}
