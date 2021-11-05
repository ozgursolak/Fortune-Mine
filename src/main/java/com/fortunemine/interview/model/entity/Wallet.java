package com.fortunemine.interview.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import java.math.BigInteger;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "WALLET")
@Where(clause = "is_deleted = false")
public class Wallet extends AbstractIdEntity {

    @OneToMany(mappedBy = "wallet", cascade = {CascadeType.ALL})
    @Where(clause = "is_deleted = false")
    @JsonIgnore
    private Set<WalletReward> walletRewards;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    @JsonIgnore
    private Player player;

    @Column(name = "LEVEL", nullable = false)
    @Min(value = 0)
    private Integer level;
}
