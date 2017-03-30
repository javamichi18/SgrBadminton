package de.soflimo.sgr.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@ConfigurationProperties(prefix = "forderungen")
public class ForderungsModell {

    private List<String> spieler = new ArrayList<String>();


    public List<String> getSpieler () {
        return spieler;
    }


    public List<Integer> getForderungen (Integer rang) {

        if (rang != null && spieler != null && spieler.size() >= rang) {
            String val = spieler.get(rang);

            if(val != null && val.length() > 0) {
                String[] split = val.split(",");
                List<Integer> ret = new ArrayList<>();
                for (String elem : split)
                    ret.add(Integer.valueOf(elem));

                return ret;
            }

        }

        return null;
    }
}
