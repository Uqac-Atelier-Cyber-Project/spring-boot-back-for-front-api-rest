package com.uqac.back_for_front.dto.analyseObject;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CveEntry {

    private String id;
    private String description;

}
