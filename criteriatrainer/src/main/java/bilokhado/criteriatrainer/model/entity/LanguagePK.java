package bilokhado.criteriatrainer.model.entity;

import java.io.Serializable;

/**
 * The class represents composite primary key for the Language entity
 */
public class LanguagePK implements Serializable {

    private String country;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanguagePK that = (LanguagePK) o;

        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
