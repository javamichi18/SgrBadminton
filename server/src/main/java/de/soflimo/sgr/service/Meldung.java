package de.soflimo.sgr.service;

import java.util.UUID;

/**
 *
 */
public class Meldung {

    private static final long serialVersionUID = 4364375413900996114L;

    private String text;

    private Severity severity = Severity.FEHLER;

    private String uuid;


    public Meldung (String text, Severity severity) {

        this.text = text;
        this.severity = severity;
        this.uuid = UUID.randomUUID().toString();
    }


    public String getText () {
        return text;
    }


    public Severity getSeverity () {
        return severity;
    }


    public String getType () {
        return severity != null ? severity.getHtmlClass() : "info";
    }


    public String getUuid () {
        return uuid;
    }


    @Override
    public String toString () {
        StringBuilder result = new StringBuilder("Meldung [" + severity + "]: " + text + " ;");
        return result.toString();
    }


    @Override
    public boolean equals (Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Meldung meldung = (Meldung) o;

        if (text != null ? !text.equals(meldung.text) : meldung.text != null)
            return false;
        return severity == meldung.severity;

    }


    @Override
    public int hashCode () {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (severity != null ? severity.hashCode() : 0);
        return result;
    }
}
