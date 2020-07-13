package me.titatic.hackatonpractice.domain.account;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PointHistory {

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    private String projectName;

    private Integer ecoPoint;

    @Enumerated(EnumType.STRING)
    private Option pointOption;
}