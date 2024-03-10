package duikt.practice.otb.entity.addition;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public enum City {

    KYIV("Kyiv"), DNIPRO("Dnipro"), KHARKIV("Kharkiv"),
    WARSAW("Warsaw");

    private final String name;

    City(String name) {
        this.name = name;
    }

    public static boolean isValidCity(String name) {
        for (City city : City.values()) {
            if (city.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static City stringToEnum(String cityName) {
        for (City city : City.values()) {
            if (city.getName().equalsIgnoreCase(cityName)) {
                return city;
            }
        }
        throw new IllegalArgumentException("No city with this name" + cityName);
    }
}