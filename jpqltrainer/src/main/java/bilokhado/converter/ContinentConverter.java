package bilokhado.converter;

import bilokhado.enumeration.Continent;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converter for the Continent enumeration
 */
@Converter(autoApply = true)
public class ContinentConverter implements AttributeConverter<Continent, String> {
    @Override
    public String convertToDatabaseColumn(Continent attribute) {
        if (attribute == null)
            return null;
        return attribute.name;
    }

    @Override
    public Continent convertToEntityAttribute(String dbData) {
        if (dbData ==null)
            return null;
        return Continent.parseContinent(dbData);
    }
}
