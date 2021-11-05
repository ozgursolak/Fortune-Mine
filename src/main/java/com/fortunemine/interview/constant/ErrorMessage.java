package com.fortunemine.interview.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessage {

    public final String USER_NOT_FOUND = "User with id %s is not found";
    public final String REWARD_NOT_FOUND = "Reward with id %s is not found";
    public final String LEVEL_NOT_INCREASED = "Level is not increased successfully";
    public final String REWARD_NOT_COLLECTED = "Reward is not collected successfully";
}
