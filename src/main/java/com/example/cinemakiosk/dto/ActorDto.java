package com.example.cinemakiosk.dto;

import com.example.cinemakiosk.model.Actor;
import lombok.Data;

@Data
public class ActorDto {
    private String name;
    private byte[] img;

    public static ActorDto toDto(Actor actor) {
        ActorDto dto = new ActorDto();
        dto.setName(actor.getName() + " " + actor.getSurname());
        dto.setImg(actor.getImg());
        return dto;
    }
}
