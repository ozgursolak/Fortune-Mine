package com.fortunemine.interview.payload.response;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class GeneralResponse {

    @JsonProperty("is_success")
    private Boolean isSuccess;

    @JsonProperty("state")
    private State state;
}
