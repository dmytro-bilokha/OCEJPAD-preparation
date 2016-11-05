package bilokhado.criteriatrainer.model.converter;

import javax.persistence.AttributeConverter;

/**
 * The converter to convert 'T' to true, 'F' to false and vice versa
 */
public class BooleanCharacterConverter implements AttributeConverter<Boolean, Character> {

    private static final Character TRUE_CHAR = Character.valueOf('T');
    private static final Character FALSE_CHAR = Character.valueOf('F');

    @Override
    public Character convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null)
            return null;
        if (attribute)
            return TRUE_CHAR;
        else
            return FALSE_CHAR;
    }

    @Override
    public Boolean convertToEntityAttribute(Character dbData) {
        if (dbData == null)
            return null;
        if (TRUE_CHAR.equals(Character.toUpperCase(dbData)))
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }
}
