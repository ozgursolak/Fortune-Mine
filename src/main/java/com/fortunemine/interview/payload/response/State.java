package com.fortunemine.interview.payload.response;

import javax.validation.constraints.NotBlank;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fortunemine.interview.model.entity.Player;

@Getter
@RequiredArgsConstructor
public class State {

    @JsonProperty("player")
    @NotBlank
    private final Player player;

    @JsonProperty("wallet")
    private final Map<String, Integer> walletInfoMap;

    @JsonProperty("rewards")
    private final List<Map<String, Object>> rewardMap;
}