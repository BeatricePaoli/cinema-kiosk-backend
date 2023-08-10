package com.example.cinemakiosk.dto;

import com.example.cinemakiosk.model.ProjectionType;
import com.example.cinemakiosk.model.Show;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
public class ShowDto {
    private Long id;
    private Date date;
    private String startTime;
    private String language;
    private ProjectionType projectionType;
    private MovieDto movie;
    private ScreenDto screen;
    private List<SeatDto> seatsTaken;

    public static ShowDto toDto(Show show) {
        ShowDto dto = new ShowDto();
        BeanUtils.copyProperties(show, dto);
        dto.setMovie(MovieDto.toDto(show.getMovie()));
        dto.setScreen(ScreenDto.toDto(show.getScreen()));
        return dto;
    }
}
