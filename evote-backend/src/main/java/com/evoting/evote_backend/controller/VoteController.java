package com.evoting.evote_backend.controller;

import com.evoting.evote_backend.dto.VoteRequestDTO;
import com.evoting.evote_backend.service.interfaces.VoterTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoterTokenService voterTokenService;

    @PostMapping("/vote")
    public ResponseEntity<String> vote(@RequestBody VoteRequestDTO request) {
        voterTokenService.vote(request);
        return ResponseEntity.ok("Vote enregistré avec succès !");
    }
}
