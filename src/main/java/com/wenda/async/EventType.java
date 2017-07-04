package com.wenda.async;

/**
 * Created by 49540 on 2017/6/30.
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    Register(4),
    FOLLOW(5),
    UNFOLLOW(6);

    private int value;
    EventType(int value) { this.value = value; }
    public int getValue() { return value; }
}

