package ru.savadevel.wthl.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

@JsonSerialize(as=Votes.class)
public interface Votes {
    LocalDate getDate();
    Restaurant getRestaurant();
    Integer getVotes();
}
