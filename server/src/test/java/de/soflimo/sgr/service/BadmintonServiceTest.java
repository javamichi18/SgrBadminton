package de.soflimo.sgr.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.soflimo.sgr.SgrBadmintonApplication;
import de.soflimo.sgr.model.Rangliste;
import de.soflimo.sgr.model.Spiel;
import de.soflimo.sgr.model.Spieler;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SgrBadmintonApplication.class)
public class BadmintonServiceTest {

    @Autowired
    private BadmintonService badmintonService;


    @Before
    public void setUp () throws Exception {

    }


    @BeforeClass
    public static void setProfile () {
        System.setProperty("spring.profiles.active", "test");
    }


    @Test
    public void testGetRangliste () throws Exception {

        Rangliste rangliste = badmintonService.getRangliste(null);
        assertEquals(20, rangliste.getSpieler().size());
        assertEquals(0, rangliste.getKannFordern().size());

        Spieler spieler3 = rangliste.getSpieler().get(2);
        Rangliste ranglisteFuer3 = badmintonService.getRangliste(spieler3.getEmail());
        assertEquals(1, ranglisteFuer3.getKannFordern().size());
        assertEquals(new Integer(2), ranglisteFuer3.getKannFordern().iterator().next());
    }


    @Test
    public void testErstelleForderung () throws Exception {

        Spiel forderung = erstelleForderung();
        assertNotNull(forderung);
        assertNotNull(forderung.getId());

        Spiel spiel2 = badmintonService
            .erstelleForderung(forderung.getSpieler1().getEmail(), forderung.getSpieler2().getId());
        assertNotNull(spiel2);
        assertNotNull(spiel2.getId());
        assertEquals(1, spiel2.getMeldungen().size());
        Meldung meldung = spiel2.getMeldungen().get(0);
        assertEquals(Severity.WARN, meldung.getSeverity());
        assertTrue(meldung.getText().contains("Forderung existiert bereits"));
    }


    private Spiel erstelleForderung () {
        List<Spieler> spieler = badmintonService.getRangliste(null).getSpieler();
        Spieler spieler1 = spieler.get(0);
        Spieler spieler2 = spieler.get(1);

        return badmintonService.erstelleForderung(spieler1.getEmail(), spieler2.getId());
    }


    @Test
    public void testGetOffeneForderungen () throws Exception {

        List<Spiel> offeneForderungen = badmintonService.getOffeneForderungen();
        assertNotNull(offeneForderungen);
    }


    @Test
    public void testGetDurchgefuehrteSpiele () throws Exception {

        List<Spiel> spiele = badmintonService.getDurchgefuehrteSpiele();
        int n = spiele.size();
        Spiel spielNeu = erstelleForderung();
        spielNeu.getInput().setSatz1_1("21");
        spielNeu.getInput().setSatz1_2("11");
        spielNeu.getInput().setSatz2_1("21");
        spielNeu.getInput().setSatz2_2("13");
        Spiel spielGespeichert = badmintonService.speichereSpiel(spielNeu);
        spiele = badmintonService.getDurchgefuehrteSpiele();

        assertEquals(n + 1, spiele.size());
    }


    @Test
    public void testSpeichereSpiel () throws Exception {

        Spiel spiel = erstelleForderung();
        assertFalse(spiel.isGespielt());
        assertNull(spiel.getGespieltAm());
        spiel.getInput().setSatz1_1("21");
        spiel.getInput().setSatz1_2("11");
        spiel.getInput().setSatz2_1("21");
        spiel.getInput().setSatz2_2("12");

        Spiel spielGespeichert = badmintonService.speichereSpiel(spiel);
        assertTrue(spielGespeichert.isGespielt());
        assertNotNull(spielGespeichert.getGespieltAm());
        assertNotNull(spielGespeichert.getGespieltAmString());

        spiel.getInput().setSatz1_1("12");
        spiel.getInput().setSatz1_2("21");
        spiel.getInput().setSatz2_1("13");
        spiel.getInput().setSatz2_2("21");
        Spiel spiel2 = badmintonService.speichereSpiel(spiel);
        assertEquals(1, spiel2.getMeldungen().size());
        assertEquals("21:11", spiel2.getSatz1());
        assertEquals("21:12", spiel2.getSatz2());

        Meldung meldung = spiel2.getMeldungen().get(0);
        assertEquals(Severity.FEHLER, meldung.getSeverity());
        assertEquals("Das Spiel wurde bereits gespeichert - neuen Daten wurden nicht übernommen.", meldung.getText());
    }
}
