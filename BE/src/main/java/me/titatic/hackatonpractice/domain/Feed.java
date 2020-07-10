package me.titatic.hackatonpractice.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.locationtech.jts.geom.Point;

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
public class Feed {

    private Integer likeCount;

    @Column
    private Point point;
}
