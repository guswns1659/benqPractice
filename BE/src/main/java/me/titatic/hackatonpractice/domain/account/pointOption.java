package me.titatic.hackatonpractice.domain.account;

import lombok.Getter;

@Getter
public enum pointOption {

    SAVE("적립"), DONATE("기부");

    public String option;

    pointOption(String option) {
        this.option = option;
    }
}
