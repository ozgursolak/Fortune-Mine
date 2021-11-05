package com.fortunemine.interview.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@MappedSuperclass
public abstract class AbstractDateEntity extends AbstractIdEntity {

    @Setter(AccessLevel.NONE)
    @Column(name = "CREATE_DATE")
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createDate;
}