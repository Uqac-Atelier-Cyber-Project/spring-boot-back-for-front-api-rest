package com.uqac.back_for_front.dto.analyseObject;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class NmapPort {


    @JacksonXmlProperty(isAttribute = true, localName = "portid")
    public int portid;
    private List<CveEntry> cves;
    @JacksonXmlProperty(localName = "state")
    public State state;

    @JacksonXmlProperty(localName = "service")
    public Service service;

    @Override
    public String toString() {
        return (this.portid+" "+this.state.state+" "+this.service+" "+this.cves.toString());
    }
}
