package es.unileon.happycow.handler;

import java.io.Serializable;

/**
 *
 * @author dorian
 */
public enum Category implements Serializable{
    FOOD,
    HEALTH,
    HOUSE,
    BEHAVIOUR;

    public static String getName(Category cat) {
        String name = "";
        switch (cat) {
            case BEHAVIOUR:
                name = "Comportamiento";
                break;
            case FOOD:
                name = "Alimentación";
                break;
            case HEALTH:
                name = "Salud";
                break;
            case HOUSE:
                name = "Refugio";
                break;
        }
        return name;
    }

    public static Category getEnum(String value) {
        Category cat = Category.FOOD;
        if (value.compareToIgnoreCase("Alimentación") == 0 || value.compareToIgnoreCase("Alimentacion") == 0) {
            cat = Category.FOOD;
        } else if (value.compareToIgnoreCase("Salud") == 0) {
            cat = Category.HEALTH;
        } else if (value.compareToIgnoreCase("Refugio") == 0) {
            cat = Category.HOUSE;
        } else if (value.compareToIgnoreCase("Comportamiento") == 0) {
            cat = Category.BEHAVIOUR;
        }
        return cat;
    }

    public static Category getEnum(int value) {
        switch (value) {
            case 0:
                return Category.FOOD;
            case 1:
                return Category.HEALTH;
            case 2:
                return Category.HOUSE;
            case 3:
                return Category.BEHAVIOUR;
            default:
                return Category.FOOD;
        }
    }

    public static String[] getArrayString() {
        String result[] = new String[4];
        result[Category.FOOD.ordinal()] = getName(Category.FOOD);
        result[Category.HEALTH.ordinal()] = getName(Category.HEALTH);
        result[Category.HOUSE.ordinal()] = getName(Category.HOUSE);
        result[Category.BEHAVIOUR.ordinal()] = getName(Category.BEHAVIOUR);
        return result;
    }
}
