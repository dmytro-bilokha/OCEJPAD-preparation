package bilokhado.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The class represents an entity of Country's language
 */
@IdClass(LanguagePK.class)
@Entity
@Table(name = "countrylanguage")
public class Language {

    @Id
    @ManyToOne
    @JoinColumn(name = "CountryCode")
    private Country country;

    @Id
    @Column(name = "Language")
    private String name;

    @Column(name = "IsOfficial")
    private Character official;

    @Column(name = "Percentage")
    private float percentage;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Language[");
        sb.append("country=").append(country == null ? "NULL" : country.getCode());
        sb.append(", name='").append(name).append('\'');
        sb.append(", official=").append(official);
        sb.append(", percentage=").append(percentage);
        sb.append(']');
        return sb.toString();
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

    public Character getOfficial() {
        return official;
    }

    public void setOfficial(Character official) {
        this.official = official;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

}
