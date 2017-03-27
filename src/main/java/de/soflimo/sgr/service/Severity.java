package de.soflimo.sgr.service;

public enum Severity {
    FEHLER("danger"), WARN("warning"), INFO("info"), SUCCESS("success");

    private String htmlClass;


    Severity (String htmlClass) {
        this.htmlClass = htmlClass;
    }


    public String getHtmlClass () {
        return htmlClass;
    }
}
