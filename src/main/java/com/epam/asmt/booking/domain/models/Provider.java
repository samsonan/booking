package com.epam.asmt.booking.domain.models;

import lombok.Getter;

@Getter
public enum Provider {
    PROVIDER_1("provider1"), PROVIDER_2("provider2");

    private final String key;

    Provider(String key) {
        this.key = key;
    }
}
