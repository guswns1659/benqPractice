package me.titatic.hackatonpractice.domain.account;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.titatic.hackatonpractice.domain.project.ProjectAccount;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer todayCount;

    private Integer ecoPoint;

    @OneToMany(mappedBy = "account")
    private final Set<ProjectAccount> projectAccounts = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "account_feed", joinColumns = @JoinColumn(name = "account_id"))
    @AttributeOverrides({
        @AttributeOverride(name = "likeCount", column = @Column(name = "feed_likeCount"))
    })
    private final Set<Challenge> challenges = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "account_point_history", joinColumns = @JoinColumn(name = "account_id"))
    private final Set<PointHistory> pointHistories = new HashSet<>();

    public void addChallenge(Challenge challenge) {
        this.getChallenges().add(challenge);
    }

    public void addPointHistory(PointHistory pointHistory) {
        this.getPointHistories().add(pointHistory);
    }
}
