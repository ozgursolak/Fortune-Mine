package com.fortunemine.interview.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GeneralResponse {

    @JsonProperty("state")
    private final State state;

    @JsonProperty("is_success")
    private final Boolean isSuccess;
}
