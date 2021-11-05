package com.fortunemine.interview.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Entity
@Table(name = "PLAYER")
@Where(clause = "is_deleted = false")
public class Player extends AbstractDateEntity {

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    @JsonIgnore
    private Wallet wallet;

    @Column(name = "FULL_NAME", nullable = false, columnDefinition = "varchar(70)")
    @JsonProperty("fullname")
    private String fullName;

    @Column(name = "EMAIL", unique = true, nullable = false, columnDefinition = "varchar(50)")
    @Email
    @JsonIgnore
    private String email;
}
