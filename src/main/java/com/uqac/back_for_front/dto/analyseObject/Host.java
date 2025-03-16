package com.uqac.back_for_front.dto.analyseObject;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Host {

    @JacksonXmlProperty(localName = "address")
    public Address address;

    @JacksonXmlElementWrapper(localName = "ports")
    @JacksonXmlProperty(localName = "port")
    public List<NmapPort> ports;

    @Override
    public String toString() {
        return (this.address+": \n"+ this.ports.toString());
    }

}
