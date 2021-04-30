package ru.savadevel.wthl.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.repository.VoteRepository;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
    public static final String REST_URL = "/rest/profile";

    private final VoteRepository voteRepository;

    public ProfileController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping("/vote")
    public Restaurant getVote() {
        return null;
    }
}
