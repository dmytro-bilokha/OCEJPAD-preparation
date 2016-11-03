package bilokhado.enumeration;

/**
 * Enumeration represents continents in the database.
 */
public enum Continent {
    Asia("Asia"),
    Europe("Europe"),
    North_America("North America"),
    Africa("Africa"),
    Oceania("Oceania"),
    Antarctica("Antarctica"),
    South_America("South America");

    public final String name;
    Continent(String name) {
        this.name = name;
    }

    public static Continent parseContinent(String name) {
        for (Continent continent : Continent.values()) {
            if (continent.name.equalsIgnoreCase(name))
                return continent;
        }
        throw new IllegalArgumentException("Unable to find Continent for string value: " + name);
    }

}
