package com.uqac.back_for_front.dto.analyseObject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Représente l'état d'un port (ouvert, fermé, filtré...)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class State {

    @JacksonXmlProperty(isAttribute = true, localName = "state")
    private String state;

    // Constructeur par défaut
    public State() {
        this.state = "unknown"; // Par défaut, l'état est "unknown" pour plus de sécurité
    }

    // Constructeur avec paramètre
    public State(String state) {
        this.state = (state != null && !state.isEmpty()) ? state : "unknown";
    }

    @JsonValue
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = (state != null && !state.isEmpty()) ? state : "unknown";
    }

    // Méthode statique pour la désérialisation
    @JsonCreator
    public static State fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return new State("unknown"); // Par défaut, l'état est "unknown" pour une entrée invalide
        }
        return new State(value);
    }

    @Override
    public String toString() {
        return state;
    }
}
