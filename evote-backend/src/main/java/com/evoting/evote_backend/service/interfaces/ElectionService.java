package com.evoting.evote_backend.service.interfaces;

import com.evoting.evote_backend.dto.ElectionRequestDTO;
import com.evoting.evote_backend.dto.ElectionResultDTO;
import com.evoting.evote_backend.dto.VoterTokenResponseDTO;

import java.util.List;

public interface ElectionService {
    List<VoterTokenResponseDTO> createElection(ElectionRequestDTO dto, String username);
    List<ElectionResultDTO> getElectionResults(Long electionId);

}
