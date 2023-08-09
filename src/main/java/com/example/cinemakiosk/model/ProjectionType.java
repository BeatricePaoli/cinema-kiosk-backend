package com.example.cinemakiosk.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProjectionType {
    is2D("2D"),
    is3D("3D");

    private String codice;

    ProjectionType(String codice) {
        this.codice = codice;
    }

    @JsonValue
    public String getCodice() {
        return codice;
    }
}
