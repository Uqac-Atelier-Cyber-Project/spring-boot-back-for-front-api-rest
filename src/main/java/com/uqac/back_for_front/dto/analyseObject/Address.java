package com.uqac.back_for_front.dto.analyseObject;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Address {

    @JacksonXmlProperty(localName = "addr", isAttribute = false) // Specify the element name
    public String ip;

    @JacksonXmlProperty(localName = "addrtype", isAttribute = false) // Map addrtype if necessary
    public String addrtype;

}
