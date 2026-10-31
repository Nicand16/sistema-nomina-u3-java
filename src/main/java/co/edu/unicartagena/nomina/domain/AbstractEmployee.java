package co.edu.unicartagena.nomina.domain;

import java.util.Objects;

public abstract class AbstractEmployee implements Employee {
    private final String id;
    private final String fullName;

    protected AbstractEmployee(String id, String fullName) {
        this.id = requireText("id", id);
        this.fullName = requireText("nombre", fullName);
    }

    @Override
    public final String id() {
        return id;
    }

    @Override
    public final String fullName() {
        return fullName;
    }

    protected static String requireText(String field, String value) {
        Objects.requireNonNull(value, field);
        if (value.isBlank()) {
            throw new IllegalArgumentException(field + " es obligatorio");
        }
        return value.trim();
    }
}
