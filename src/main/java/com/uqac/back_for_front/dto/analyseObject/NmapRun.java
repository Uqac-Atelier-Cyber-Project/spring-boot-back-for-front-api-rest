package com.uqac.back_for_front.dto.analyseObject;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class NmapRun {


    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "host")
    public List<Host> hosts;
}
