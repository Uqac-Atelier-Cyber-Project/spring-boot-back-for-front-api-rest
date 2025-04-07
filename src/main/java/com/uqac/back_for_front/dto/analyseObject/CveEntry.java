package com.uqac.back_for_front.dto.analyseObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Représente une entrée de vulnérabilité
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CveEntry {

    @JacksonXmlProperty(isAttribute = false, localName = "id")
    private String id;

    @JsonIgnore
    private String description;

    // Constructeur vide (obligatoire pour Jackson)
    public CveEntry() {
        this.id = "";
        this.description = "";
    }

    // Constructeur avec paramètres
    public CveEntry(String id, String description) {
        this.id = id;
        this.description = description;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "CveEntry{" +
                "id='" + id + '\'' +
                ", link='https://cve.mitre.org/cgi-bin/cvename.cgi?name=" + id + '\'' +
                '}';
    }

    // Méthode toJson (optionnelle)
    public String toJson() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"link\":\"https://cve.mitre.org/cgi-bin/cvename.cgi?name=" + id + "\"" +
                "}";
    }
}
