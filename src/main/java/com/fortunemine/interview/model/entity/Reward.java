package com.fortunemine.interview.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "REWARD")
@Where(clause = "is_deleted = false")
public class Reward extends AbstractIdEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;
}
