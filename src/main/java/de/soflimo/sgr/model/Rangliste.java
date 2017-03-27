package de.soflimo.sgr.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 *
 */
@Entity
public class Rangliste {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @OneToMany(mappedBy = "rangliste", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderColumn(name = "rang")
    @org.hibernate.annotations.ListIndexBase(1)
    private List<Spieler> spieler = new ArrayList<>();

    @Version
    private Date version;

    private String name;

    private transient List<Integer> kannFordern = new ArrayList<>();

    private transient Integer meinRang;

    private transient String userName;


    public Integer getMeinRang () {

        return meinRang;
    }


    public void setMeinRang (Integer meinRang) {
        this.meinRang = meinRang;
    }


    public List<Integer> getKannFordern () {

        return kannFordern;
    }


    public List<Spieler> getSpieler () {

        return spieler;
    }


    public void setVersion (Date version) {
        this.version = version;
    }


    public Date getVersion () {
        return version;
    }


    public String getName () {
        return name;
    }


    public void setName (String name) {
        this.name = name;
    }


    public Long getId () {
        return id;
    }


    public void setId (Long id) {
        this.id = id;
    }


    public void add (Spieler spieler) {
        if (spieler != null) {
            this.spieler.add(spieler);
            spieler.setRangliste(this);
        }
    }


    public void setUserName (String userName) {
        this.userName = userName;
    }


    public String getUserName () {
        return userName;
    }


    @Override
    public String toString () {
        return "Rangliste{" +
            "spieler=" + spieler +
            ", version=" + version +
            '}';
    }
}
