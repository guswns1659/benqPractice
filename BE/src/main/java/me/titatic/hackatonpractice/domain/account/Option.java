package me.titatic.hackatonpractice.domain.account;

import lombok.Getter;

@Getter
public enum Option {

    SAVE("적립"), DONATE("기부");

    public String option;

    Option(String option) {
        this.option = option;
    }
}
