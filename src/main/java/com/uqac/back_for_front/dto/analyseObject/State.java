package com.uqac.back_for_front.dto.analyseObject;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class State {
    @JacksonXmlProperty(isAttribute = true, localName = "state")
    public String state;
}
