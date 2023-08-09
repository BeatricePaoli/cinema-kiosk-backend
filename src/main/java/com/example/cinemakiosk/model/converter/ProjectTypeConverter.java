package com.example.cinemakiosk.model.converter;

import com.example.cinemakiosk.model.ProjectionType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ProjectTypeConverter implements AttributeConverter<ProjectionType, String> {

    @Override
    public String convertToDatabaseColumn(ProjectionType sl) {
        if (sl == null) {
            return null;
        }
        return sl.getCodice();
    }

    @Override
    public ProjectionType convertToEntityAttribute(String codice) {
        if (codice == null) {
            return null;
        }

        return Stream.of(ProjectionType.values())
                .filter(c -> c.getCodice().equals(codice))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
