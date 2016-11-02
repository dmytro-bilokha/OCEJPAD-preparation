package bilokhado.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

/**
 * Class represents the Country entity
 */
@Entity
@Table(name = "country")
public class Country {

    @Id
    @Column(name = "Code")
    private String code;

    @Column(name = "Name")
    private String name;

    @Column(name = "Continent")
    private String continent;

    @Column(name = "Region")
    private String region;

    @Column(name = "SurfaceArea")
    private String surfaceArea;

    @Column(name = "IndepYear")
    private Short independencyYear;

    @Column(name = "Population")
    private Integer population;

    @Column(name = "LifeExpectancy")
    private Float lifeExpectancy;

    @Column(name = "GNP")
    private Double gnp;

    @Column(name = "GNPOld")
    private Double oldGnp;

    @Column(name = "LocalName")
    private String localName;

    @Column(name = "GovernmentForm")
    private String governmentForm;

    @Column(name = "HeadOfState")
    private String headOfState;

    @OneToOne
    @JoinColumn(name = "Capital")
    private City capital;

    @Column(name = "Code2")
    private String shortCode;

    @OneToMany(mappedBy = "country")
    private List<City> cities;

    @OneToMany(mappedBy = "country")
    @MapKeyColumn(name = "Language")
    private Map<String, Language> languageMap;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Country[");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", population=").append(population);
        sb.append(", lifeExpectancy=").append(lifeExpectancy);
        sb.append(", gnp=").append(gnp);
        sb.append(", numOfCities=").append(cities == null ? "NULL" : cities.size());
        sb.append(']');
        return sb.toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(String surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public Short getIndependencyYear() {
        return independencyYear;
    }

    public void setIndependencyYear(Short independencyYear) {
        this.independencyYear = independencyYear;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Float getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(Float lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public Double getGnp() {
        return gnp;
    }

    public void setGnp(Double gnp) {
        this.gnp = gnp;
    }

    public Double getOldGnp() {
        return oldGnp;
    }

    public void setOldGnp(Double oldGnp) {
        this.oldGnp = oldGnp;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getGovernmentForm() {
        return governmentForm;
    }

    public void setGovernmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    public String getHeadOfState() {
        return headOfState;
    }

    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }

    public City getCapital() {
        return capital;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public Map<String, Language> getLanguageMap() {
        return languageMap;
    }

    public void setLanguageMap(Map<String, Language> languageMap) {
        this.languageMap = languageMap;
    }
}
