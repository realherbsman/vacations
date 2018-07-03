package opt.vacation.jpa.entities.converters;

import java.util.UUID;

import javax.persistence.AttributeConverter;

public class UUIDToStringAttributeConverter implements AttributeConverter<UUID, String> {

	@Override
	public String convertToDatabaseColumn(UUID attribute) {
		return attribute != null ? attribute.toString() : null;
	}

	@Override
	public UUID convertToEntityAttribute(String dbData) {
		return dbData != null ? UUID.fromString(dbData) : null;
	}

}
