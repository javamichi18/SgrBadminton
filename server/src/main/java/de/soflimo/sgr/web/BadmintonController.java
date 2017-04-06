package de.soflimo.sgr.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.soflimo.sgr.model.Rangliste;
import de.soflimo.sgr.model.Spiel;
import de.soflimo.sgr.service.BadmintonService;

@RestController
@EnableAutoConfiguration
@CrossOrigin
public class BadmintonController {

    @Autowired
    private BadmintonService service;


    @RequestMapping("/rest/rangliste")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Rangliste getRangeliste () {

        return service.getRangliste("johannes@aol.com");
    }


    @RequestMapping("/rest/forderungen")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Spiel> getOffeneForderungen () {

        return service.getOffeneForderungen();
    }


    @RequestMapping("/rest/spiele")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Spiel> getDurchgefuehrteSpiele () {

        return service.getDurchgefuehrteSpiele();
    }


    @RequestMapping("/rest/spiel/{spielId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Spiel getSpiel (@PathVariable Long spielId) {

        return service.getSpiel(spielId);
    }


    @RequestMapping(value = "/rest/fordere/{idGegner}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Spiel fordereSpieler (@PathVariable("idGegner") String idGegner) {

        String currentPrincipalName = getAngemeldeterBenutzer();

        Spiel forderung = service.erstelleForderung(currentPrincipalName, Long.valueOf(idGegner));

        return forderung;
    }


    @RequestMapping(value = "/rest/ergebnis", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Spiel speichereSpielErgebnis (@RequestBody final Spiel spiel) {

        Spiel spielErgebnis = service.speichereSpiel(spiel);

        return spielErgebnis;
    }


    private String getAngemeldeterBenutzer () {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}