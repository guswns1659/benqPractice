package me.titatic.hackatonpractice.domain;

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
    private final Set<Feed> feeds = new HashSet<>();

    public void addFeed(Feed feed) {
        this.getFeeds().add(feed);
    }
}
