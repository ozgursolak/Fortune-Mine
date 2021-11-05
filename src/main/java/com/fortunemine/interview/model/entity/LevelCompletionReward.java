package com.fortunemine.interview.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "LEVEL_COMPLETION_REWARD")
@Where(clause = "is_deleted = false")
public class LevelCompletionReward extends AbstractIdEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reward_id", nullable = false)
    private Reward reward;

    @Column(name = "LEVEL", nullable = false)
    @Min(value = 0)
    private Integer level;
}
