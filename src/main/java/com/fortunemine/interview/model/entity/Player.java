package com.fortunemine.interview.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "PLAYER")
@Where(clause = "is_deleted = false")
public class Player extends AbstractDateEntity {

    @OneToMany(mappedBy = "player", cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE })
    @Where(clause = "is_deleted = false")
    private Set<PlayerReward> playerRewards;

    @Column(name = "FULL_NAME", nullable = false, columnDefinition = "varchar(70)")
    @JsonProperty("fullname")
    private String fullName;

    @Column(name = "EMAIL", unique = true, nullable = false, columnDefinition = "varchar(50)")
    @Email
    @JsonIgnore
    private String email;
}
