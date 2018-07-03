package opt.vacation.jpa.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToIntegerAttributeConverter implements AttributeConverter<Boolean, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Boolean attribute) {
		return (attribute == null) ? null : (attribute ? 1 : 0);
	}

	@Override
	public Boolean convertToEntityAttribute(Integer dbData) {
		return (dbData == null) ? null : ((dbData != 0) ? true : false);
	}

}
