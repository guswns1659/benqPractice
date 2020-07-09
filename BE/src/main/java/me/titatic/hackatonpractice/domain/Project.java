package me.titatic.hackatonpractice.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Corporation corporation;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private final Set<ProjectAccount> projectAccounts = new HashSet<>();

    public void addCorporation(Corporation corporation) {
        this.corporation = corporation;
    }

    public void addAccount(Account account) {
        ProjectAccount projectAccount = ProjectAccount.builder()
            .project(this)
            .account(account)
            .build();

        this.projectAccounts.add(projectAccount);
    }
}
