package com.core.base.corebase.domain.organization;

import com.core.base.corebase.domain.common.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "organization")
public class Organization extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "logo_file_name")
    private String logoFileName;

    private String ceo;

    @Column(name = "tel_number")
    private String telNumber;

    private String address;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Team> teams = new ArrayList<>();

    protected Organization() {
        // For JPA
    }

    public Organization(String name, String logoFileName, String ceo, String telNumber, String address) {
        this.name = name;
        this.logoFileName = logoFileName;
        this.ceo = ceo;
        this.telNumber = telNumber;
        this.address = address;
    }

    public void update(String name, String logoFileName, String ceo, String telNumber, String address) {
        if (name != null) {
            this.name = name;
        }
        this.logoFileName = logoFileName; // Allow null
        this.ceo = ceo; // Allow null
        this.telNumber = telNumber; // Allow null
        this.address = address; // Allow null
    }

    public void addTeam(Team team) {
        teams.add(team);
        team.setOrganization(this);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
        team.setOrganization(null);
    }
}
