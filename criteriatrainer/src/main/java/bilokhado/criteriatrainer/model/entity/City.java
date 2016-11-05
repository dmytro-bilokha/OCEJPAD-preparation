package bilokhado.criteriatrainer.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The class represents City entity
 */
@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "CountryCode")
    private Country country;

    @Column(name = "Name")
    private String name;

    @Column(name = "District")
    private String district;

    @Column(name = "Population")
    private int population;

    @Override
    public String toString() {
        return "City[" + "id=" + id + ", name='" + name + '\'' + " , population=" + population + ']';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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
