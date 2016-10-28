package bilokhado.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The class represents City entity
 */
@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "ID")
    int id;

    @Column(name = "CountryCode")
    String countryCode;

    @Column(name = "Name")
    String name;

    @Column(name = "District")
    String district;

    @Column(name = "Population")
    int population;

    @Override
    public String toString() {
        return "City[" + "id=" + id + ", countryCode='" + countryCode + '\'' + ", name='" + name + '\'' + ']';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
