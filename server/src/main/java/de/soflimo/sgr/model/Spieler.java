package de.soflimo.sgr.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 */
@Entity
public class Spieler {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String vorname;

    private String nachname;

    private String email;

    @JsonIgnore
    private String password;

    @ManyToOne
    @JsonIgnore
    private Rangliste rangliste;


    public Rangliste getRangliste () {
        return rangliste;
    }


    public void setRangliste (Rangliste rangliste) {
        this.rangliste = rangliste;
    }


    private Integer rang;


    public Spieler () {
    }


    public Spieler (String vorname, String nachname, String email, int rang) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        //this.rang = rang;

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(email);
    }


    public String getName () {

        return vorname + " " + nachname;
    }


    public Long getId () {
        return id;
    }


    public void setId (Long id) {
        this.id = id;
    }


    public String getVorname () {
        return vorname;
    }


    public void setVorname (String vorname) {
        this.vorname = vorname;
    }


    public String getNachname () {
        return nachname;
    }


    public void setNachname (String nachname) {
        this.nachname = nachname;
    }


    public String getEmail () {
        return email;
    }


    public void setEmail (String email) {
        this.email = email;
    }


    public Integer getRang () {
        return rang;
    }


    public void setRang (Integer rang) {
        this.rang = rang;
    }


    public String getPassword () {
        return password;
    }


    public void setPassword (String password) {
        this.password = password;
    }


    @Override
    public boolean equals (Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Spieler spieler = (Spieler) o;

        return !(getEmail() != null ? !getEmail().equals(spieler.getEmail()) : spieler.getEmail() != null);

    }


    @Override
    public int hashCode () {
        return getEmail() != null ? getEmail().hashCode() : 0;
    }


    @Override
    public String toString () {
        return String.format("%s (%s)", getName(), rang);
    }


    public UserDetails mapUserDetails () {
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities () {
                return Arrays.asList(new SimpleGrantedAuthority("USER"));
            }


            @Override
            public String getPassword () {
                return password;
            }


            @Override
            public String getUsername () {
                return email;
            }


            @Override
            public boolean isAccountNonExpired () {
                return true;
            }


            @Override
            public boolean isAccountNonLocked () {
                return true;
            }


            @Override
            public boolean isCredentialsNonExpired () {
                return true;
            }


            @Override
            public boolean isEnabled () {
                return true;
            }
        };

        return userDetails;
    }
}
