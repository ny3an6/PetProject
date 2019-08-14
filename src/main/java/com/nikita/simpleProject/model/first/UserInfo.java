package com.nikita.simpleProject.model.first;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "org_name")
    private String orgName;
    @Column(name = "agreement")
    private String agreement;
    @Column(name = "specification")
    private String specification;
    @Column(name = "number")
    private String number;
    @Column(name = "balance")
    private String balance;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User userName;
}