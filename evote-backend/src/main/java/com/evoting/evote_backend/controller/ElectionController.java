package com.evoting.evote_backend.controller;

import com.evoting.evote_backend.dto.ElectionRequestDTO;
import com.evoting.evote_backend.dto.OptionResultDTO;
import com.evoting.evote_backend.dto.VoterTokenResponseDTO;
import com.evoting.evote_backend.service.interfaces.ElectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elections")
@RequiredArgsConstructor
public class ElectionController {

    private final ElectionService electionService;

    // TEMPORAIRE : id du créateur en paramètre pour tester sans JWT
    @PostMapping("/create")
    public ResponseEntity<List<VoterTokenResponseDTO>> createElection(
            @RequestBody ElectionRequestDTO request,
            @RequestParam("creatorId") Long creatorId
    ) {
        List<VoterTokenResponseDTO> tokens = electionService.createElection(request, creatorId);
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<List<OptionResultDTO>> getResults(@PathVariable Long id) {
        List<OptionResultDTO> results = electionService.getElectionResults(id);
        return ResponseEntity.ok(results);
    }

}
