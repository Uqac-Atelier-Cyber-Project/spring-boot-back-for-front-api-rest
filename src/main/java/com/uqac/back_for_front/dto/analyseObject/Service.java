package com.uqac.back_for_front.dto.analyseObject;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Service {

    @JacksonXmlProperty(isAttribute = true, localName = "name")
    public String name;

    @JacksonXmlProperty(isAttribute = true, localName = "version")
    public String version;
    @Override
    public String toString() {
        return "Service [name=" + name + ", version=" + version + "]";
    }
}
