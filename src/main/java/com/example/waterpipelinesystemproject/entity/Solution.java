package com.example.waterpipelinesystemproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class Solution {

    @Id
    @Column
    private Integer problemId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "problem_id")
    private SubProblem subProblem;

    private Integer cost;
}
