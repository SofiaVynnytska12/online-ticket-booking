package duikt.practice.otb.entity.addition;

import lombok.Getter;

@Getter
public enum City {

    KYIV("Kyiv"), DNIPRO("Dnipro"),KHARKIV("Kharkiv"),
    WARSAW("Warsaw");

    private final String name;

    City(String name) {
        this.name = name;
    }

}
