package opt.vacation.jpa.entities.converters;

import java.math.BigDecimal;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DoubleToBigDecimalAttributeConverter implements AttributeConverter<Double, BigDecimal> {

	@Override
	public BigDecimal convertToDatabaseColumn(Double attribute) {
		return (attribute == null) ? null : BigDecimal.valueOf(attribute);
	}

	@Override
	public Double convertToEntityAttribute(BigDecimal dbData) {
		return (dbData == null) ? null : dbData.doubleValue();
	}

}
