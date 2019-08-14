package com.nikita.simpleProject.model.second;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SomeInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "org_name")
    private String orgName;
    @Column(name = "number")
    private Long number;
    @Column(name = "balance")
    private Integer balance;
}
