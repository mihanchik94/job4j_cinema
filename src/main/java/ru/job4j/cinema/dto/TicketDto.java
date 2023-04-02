package ru.job4j.cinema.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class TicketDto {
    private int sessionId;
    private String filmName;
    private String hallName;
    private int rowNumber;
    private int placeNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TicketDto() {
    }

    public TicketDto(int sessionId, String filmName, String hallName, int rowNumber, int placeNumber, LocalDateTime startTime, LocalDateTime endTime) {
        this.sessionId = sessionId;
        this.filmName = filmName;
        this.hallName = hallName;
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
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

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketDto ticketDto = (TicketDto) o;
        return sessionId == ticketDto.sessionId
                && rowNumber == ticketDto.rowNumber
                && placeNumber == ticketDto.placeNumber
                && Objects.equals(filmName, ticketDto.filmName) && Objects.equals(hallName, ticketDto.hallName)
                && Objects.equals(startTime, ticketDto.startTime) && Objects.equals(endTime, ticketDto.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, filmName, hallName, rowNumber, placeNumber, startTime, endTime);
    }
}