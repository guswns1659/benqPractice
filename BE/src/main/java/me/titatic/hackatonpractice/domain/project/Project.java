package me.titatic.hackatonpractice.domain.project;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.titatic.hackatonpractice.domain.account.Account;
import me.titatic.hackatonpractice.domain.restaurant.Image;
import me.titatic.hackatonpractice.domain.corporation.Corporation;

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

    private LocalDate deadLine;

    private String description;

    private Integer targetMoney;

    private Integer currentMoney;

    private Integer donators;

    @ElementCollection
    @CollectionTable(name = "project_image", joinColumns = @JoinColumn(name = "project_id"))
    @AttributeOverrides({
        @AttributeOverride(name = "url", column = @Column(name = "project_image"))
    })
    private final Set<Image> images = new HashSet<>();

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

    public void addImage(Image image) {
        this.images.add(image);
    }
}
