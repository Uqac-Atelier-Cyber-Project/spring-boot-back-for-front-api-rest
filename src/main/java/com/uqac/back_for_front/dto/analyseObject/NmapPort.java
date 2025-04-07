package com.uqac.back_for_front.dto.analyseObject;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Représente un port scanné sur un hôte.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NmapPort {

    @JacksonXmlProperty(isAttribute = true, localName = "portid")
    public int portId;
    private List<CveEntry> cves;
    @JacksonXmlProperty(localName = "state")
    public State state;

    @JacksonXmlProperty(localName = "service")
    public Service service;
    public void setCves(List<CveEntry> cves) {
        this.cves = cves;
    }
    public List<CveEntry> getCves() {
        return cves;
    }
    public NmapPort() {
        cves=new ArrayList<CveEntry>();
        portId=0;
    }

    @Override
    public String toString() {
        return "NmapPort{" +
                "portId=" + portId +
                ", state='" + state + '\'' +
                ", service=" + service +
                ", cves=" + cves.stream()
                .map(CveEntry::toString)
                .collect(Collectors.joining(", ")) +
                '}';
    }
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"name\":\"").append(service.name).append("\",");
        json.append("\"version\":\"").append(service.version).append("\",");
        json.append("\"cves\":[").append(cves.stream()
                .map(CveEntry::toJson)
                .collect(Collectors.joining(","))).append("]");
        json.append("}");
        return json.toString();
    }
}

