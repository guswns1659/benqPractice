package me.titatic.hackatonpractice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.locationtech.jts.geom.Point;

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
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Point point;
}
