package com.fortunemine.interview.controller;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fortunemine.interview.constant.Endpoint;
import com.fortunemine.interview.service.DataService;
import com.fortunemine.interview.service.PlayerService;
import com.fortunemine.interview.payload.response.GeneralResponse;
import com.fortunemine.interview.payload.response.State;

@CrossOrigin(origins = "localhost", maxAge = 3600)
@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final DataService dataService;

    @GetMapping(Endpoint.PLAYER_LEVEL_UP)
    public ResponseEntity<GeneralResponse> playerLevelUp(@PathVariable final UUID playerID) {
        final GeneralResponse response = playerService.levelUp(playerID);

        return ResponseEntity.ok(response);
    }

    @GetMapping(Endpoint.COLLECT_REWARD)
    public ResponseEntity<GeneralResponse> collectReward(@PathVariable final UUID playerID,
        @PathVariable final UUID rewardID) {

        final GeneralResponse response = playerService.collectRewards(playerID, rewardID);

        return ResponseEntity.ok(response);
    }

    @GetMapping(Endpoint.GET_PLAYER_STATE)
    public ResponseEntity<State> getPlayerState(@PathVariable final UUID playerID) {
        final State playerState = playerService.getPlayerState(playerID);

        return ResponseEntity.ok(playerState);
    }

    @GetMapping(Endpoint.INITIALIZE_DB)
    public void initialize() {
        dataService.initializeDB();
    }
}
