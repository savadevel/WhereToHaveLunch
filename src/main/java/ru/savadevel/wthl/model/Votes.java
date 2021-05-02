package ru.savadevel.wthl.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

@JsonSerialize(as=Votes.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.ANY)
public interface Votes {
    LocalDate getDate();
    Restaurant getRestaurant();
    Integer getVotes();
}
