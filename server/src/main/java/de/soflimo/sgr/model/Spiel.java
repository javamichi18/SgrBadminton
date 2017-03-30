package de.soflimo.sgr.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.soflimo.sgr.service.Meldung;
import de.soflimo.sgr.service.Severity;

/**
 *
 */
@Entity
public class Spiel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Spieler spieler1;

    @OneToOne
    private Spieler spieler2;

    private Date gespieltAm;

    private String satz1;

    private String satz2;

    private String satz3;

    private transient FormularInput input = new FormularInput();

    private Date gefordertAm;

    private boolean gespielt;

    private String gewinner;

    private Verbesserung verbesserungSpieler1;

    private Verbesserung verbesserungSpieler2;

    @Version
    private Integer version;

    private static final Logger log = LoggerFactory.getLogger(Spiel.class);

    private transient List<Meldung> meldungen;


    public Spiel () {
    }


    public Spiel (Spieler spieler1, Spieler geforderterSpieler) {
        this.spieler1 = spieler1;
        this.spieler2 = geforderterSpieler;
        this.gefordertAm = new Date();
        this.gespielt = false;
    }


    public String getErgebnis () {

        StringBuilder buffy = new StringBuilder().append(satz1).append(" / ").append(satz2);
        if (satz3 != null)
            buffy.append(" / ").append(satz3);

        return buffy.toString();
    }


    public Spieler getSpieler1 () {
        return spieler1;
    }


    public void setSpieler1 (Spieler spieler1) {
        this.spieler1 = spieler1;
    }


    public Spieler getSpieler2 () {
        return spieler2;
    }


    public void setSpieler2 (Spieler spieler2) {
        this.spieler2 = spieler2;
    }


    public String getGespieltAmString () {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (gespieltAm != null)
            return sdf.format(gespieltAm);
        else
            return null;
    }


    public Long getId () {
        return id;
    }


    public void setId (Long id) {
        this.id = id;
    }


    public String getSatz3 () {
        return satz3;
    }


    public String getSatz2 () {
        return satz2;
    }


    public String getSatz1 () {
        return satz1;
    }


    public String getDatumForderung () {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (gefordertAm != null)
            return sdf.format(gefordertAm);
        else
            return null;
    }


    public void setGefordertAm (Date gefordertAm) {
        this.gefordertAm = gefordertAm;
    }


    @JsonIgnore
    public Date getGefordertAm () {
        return gefordertAm;
    }


    public boolean isGespielt () {
        return gespielt;
    }


    public void setGespielt (boolean gespielt) {
        this.gespielt = gespielt;
    }


    public Date getGespieltAm () {
        return gespieltAm;
    }


    public void setGespieltAm (Date gespieltAm) {
        this.gespieltAm = gespieltAm;
    }


    void setSatz1 (String satz1) {
        this.satz1 = satz1;
    }


    void setSatz2 (String satz2) {
        this.satz2 = satz2;
    }


    void setSatz3 (String satz3) {
        this.satz3 = satz3;
    }


    public String getGewinner () {
        return gewinner;
    }


    public void setGewinner (String gewinner) {
        this.gewinner = gewinner;
    }


    public boolean berechneGewinner () {

        int n = 0;
        n += berechneSatz(satz1, 1);
        n += berechneSatz(satz2, 2);
        if (satz3 != null)
            n += berechneSatz(satz3, 3);

        if (n < 0)
            this.gewinner = "Spieler1";
        else if (n > 0)
            this.gewinner = "Spieler2";
        else {
            log.warn("ungueltiges Ergebnis: " + this);
            this.gewinner = "Unentschieden";
        }
        boolean gueltigesErgebnis = (-2 <= n) && (n <= 2);

        return gueltigesErgebnis && (meldungen == null || meldungen.size() == 0);
    }


    private int berechneSatz (String satz, Integer satzNr) {

        String[] split = satz.split(":");
        if (split == null || split.length != 2) {
            log.warn("ungueltiger satz: " + satz);
            addMeldung("satz" + satzNr + " : ungueltiger wert", Severity.WARN);
            return 0;
        }

        try {
            Integer spieler1 = Integer.valueOf(split[0]);
            Integer spieler2 = Integer.valueOf(split[1]);
            if (spieler1 == null) {
                log.warn("ungueltiger wert spieler1" + spieler1);
                addMeldung("satz" + satzNr + ".spieler1 : ungueltiger wert", Severity.WARN);
                return 0;
            }
            if (spieler2 == null) {
                log.warn("ungueltiger wert spieler2" + spieler1);
                addMeldung("satz" + satzNr + ".spieler2 : ungueltiger wert", Severity.WARN);
                return 0;
            }

            if (spieler1 < 0 || spieler1 > 30) {
                log.warn("ungueltiger wert spieler1" + spieler1);
                addMeldung("satz" + satzNr + ".spieler1 : ungueltiger wert", Severity.WARN);
                return 0;
            }
            if (spieler2 < 0 || spieler2 > 30) {
                log.warn("ungueltiger wert spieler2" + spieler2);
                addMeldung("satz" + satzNr + ".spieler2: ungueltiger wert", Severity.WARN);
                return 0;
            }
            if (!(spieler1 >= 21 || spieler2 >= 21)) {
                log.warn("ungueltiger wert satz" + satzNr + ": kein Gewinner (>21) " + spieler1 + " - " + spieler2);
                addMeldung("satz" + satzNr + " : kein Gewinner (Wert >= 21)", Severity.WARN);
                return 0;
            }
            if (!(spieler1 > spieler2 || spieler2 > spieler1)) {
                log.warn("ungueltiger wert satz" + satzNr + ": kein Gewinner " + spieler1 + " - " + spieler2);
                addMeldung("satz" + satzNr + " : kein Gewinner ", Severity.WARN);
                return 0;
            }

            int ergebnis = spieler2.compareTo(spieler1);

            return ergebnis;
        } catch (NumberFormatException nfe) {
            log.warn("numberFormatException: ungueltiger satz: " + satz + " / " + nfe.getMessage());
            return 0;
        }
    }


    @Override
    public String toString () {

        StringBuilder buffy = new StringBuilder("Spiel{").append(spieler1).append(" - ").append(spieler2).append(": ")
            .append(satz1).append("/")
            .append(satz2).append("/");
        if (satz3 != null)
            buffy.append(satz3);
        if (gespieltAm != null)
            buffy.append(" (").append(gespieltAm).append(")");

        return buffy.toString();
    }


    public void addMeldung (String text, Severity severity) {

        if (meldungen == null)
            meldungen = new ArrayList<>();
        meldungen.add(new Meldung(text, severity));
    }


    public List<Meldung> getMeldungen () {
        return meldungen;
    }


    public Verbesserung getVerbesserungSpieler1 () {
        return verbesserungSpieler1;
    }


    public void setVerbesserungSpieler1 (Verbesserung verbesserungSpieler1) {
        this.verbesserungSpieler1 = verbesserungSpieler1;
    }


    public Verbesserung getVerbesserungSpieler2 () {
        return verbesserungSpieler2;
    }


    public void setVerbesserungSpieler2 (Verbesserung verbesserungSpieler2) {
        this.verbesserungSpieler2 = verbesserungSpieler2;
    }


    public void uebernehmeFormularDaten (Spiel spielIn) {

        if (spielIn != null && spielIn.getInput() != null) {
            FormularInput in = spielIn.getInput();

            setSatz1(spielIn.input.getSatz1_1() + ":" + spielIn.input.getSatz1_2());
            setSatz2(spielIn.input.getSatz2_1() + ":" + spielIn.input.getSatz2_2());
            if (in.getSatz3_1() != null && in.getSatz3_2() != null)
                setSatz3(spielIn.input.getSatz3_1() + ":" + spielIn.input.getSatz3_2());
        }
    }


    public FormularInput getInput () {
        return input;
    }


    public void setInput (FormularInput input) {
        this.input = input;
    }


    public class FormularInput {

        public FormularInput () {
        }


        private String satz1_1;

        private String satz1_2;

        private String satz2_1;

        private String satz2_2;

        private String satz3_1;


        public String getSatz3_2 () {
            return satz3_2;
        }


        public void setSatz3_2 (String satz3_2) {
            this.satz3_2 = satz3_2;
        }


        public String getSatz3_1 () {
            return satz3_1;
        }


        public void setSatz3_1 (String satz3_1) {
            this.satz3_1 = satz3_1;
        }


        public String getSatz2_2 () {
            return satz2_2;
        }


        public void setSatz2_2 (String satz2_2) {
            this.satz2_2 = satz2_2;
        }


        public String getSatz2_1 () {
            return satz2_1;
        }


        public void setSatz2_1 (String satz2_1) {
            this.satz2_1 = satz2_1;
        }


        public String getSatz1_2 () {
            return satz1_2;
        }


        public void setSatz1_2 (String satz1_2) {
            this.satz1_2 = satz1_2;
        }


        public String getSatz1_1 () {
            return satz1_1;
        }


        public void setSatz1_1 (String satz1_1) {
            this.satz1_1 = satz1_1;
        }


        private String satz3_2;

    }
}
