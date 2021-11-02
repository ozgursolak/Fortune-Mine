package com.fortunemine.interview.payload.response;

import javax.validation.constraints.NotBlank;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fortunemine.interview.model.entity.Player;
import com.fortunemine.interview.model.entity.Reward;
import com.fortunemine.interview.model.entity.Wallet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class State {

    @JsonProperty("player")
    @NotBlank
    private final Player player;

    @JsonProperty("wallet")
    @NotBlank
    private final Wallet wallet;

    @JsonProperty("rewards")
    private final Set<Reward> rewards;
}