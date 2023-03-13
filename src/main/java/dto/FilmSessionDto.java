package dto;

import com.jayway.jsonpath.internal.path.PredicateContextImpl;

import java.time.LocalDateTime;

public class FilmSessionDto {
    private String filmName;
    private String hallName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;

    public FilmSessionDto() {
    }

    public FilmSessionDto(String filmName, String hallName, LocalDateTime startTime, LocalDateTime endTime, int price) {
        this.filmName = filmName;
        this.hallName = hallName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}