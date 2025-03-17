package com.uqac.back_for_front.dto.analyseObject;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un port scanné sur un hôte.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NmapPort {

    @JacksonXmlProperty(isAttribute = true, localName = "portid")
    public int portid;
    private List<CveEntry> cves;
    @JacksonXmlProperty(localName = "state")
    public State state;

    @JacksonXmlProperty(localName = "service")
    public Service service;
    public void setCves(List<CveEntry> cves) {
        this.cves = cves;
    }

    public NmapPort() {
        cves=new ArrayList<CveEntry>();
        portid=0;
    }

    @Override
    public String toString() {
        if (service != null && state!=null && cves !=null) {
            return (this.portid+" "+this.state.getState()+" "+this.service+" "+this.cves.toString());
            // Utiliser serviceString...
        } else {
            // Gérer le cas où port.service est null, par exemple avec une valeur par défaut
            return "CVE non reconnue";
        }

    }
}
