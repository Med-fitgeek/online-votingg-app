package com.evoting.evote_backend.service.impl;

import com.evoting.evote_backend.dto.*;
import com.evoting.evote_backend.entity.*;
import com.evoting.evote_backend.repository.*;
import com.evoting.evote_backend.service.interfaces.ElectionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;
    private final UserRepository userRepository;
    private final VoterTokenRepository voterTokenRepository;
    private final OptionRepository optionRepository;

    @Transactional
    @Override
    public List<VoterTokenResponseDTO> createElection(ElectionRequestDTO dto, String username) {
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // 1. Créer l’élection
        Election election = Election.builder()
                .title(dto.getTitle())
                .createdBy(creator)
                .build();

        // 2. Créer les options
        List<Option> options = new ArrayList<>();
        for (String label : dto.getOptions()) {
            Option option = Option.builder()
                    .label(label)
                    .election(election)
                    .build();
            options.add(option);
        }
        election.setOptions(options);

        // 3. Générer les tokens de vote
        List<VoterToken> voterTokens = new ArrayList<>();
        List<VoterTokenResponseDTO> responseTokens = new ArrayList<>();

        for (String voterName : dto.getVoters()) {
            UUID token = UUID.randomUUID();
            VoterToken voterToken = VoterToken.builder()
                    .token(token)
                    .used(false)
                    .election(election)
                    .build();
            voterTokens.add(voterToken);

            responseTokens.add(new VoterTokenResponseDTO(voterName, token));
        }
        election.setVoterTokens(voterTokens);

        // 4. Sauvegarder l’élection, les options, et les tokens
        electionRepository.save(election);

        return responseTokens;
    }

    @Override
    public List<ElectionResultDTO> getElectionResults(Long electionId) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new RuntimeException("Élection introuvable"));

        List<Option> options = optionRepository.findByElection(election);

        return options.stream()
                .map(option -> new ElectionResultDTO(
                        option.getId(),
                        option.getLabel(),
                        voterTokenRepository.countBySelectedOption(option)
                ))
                .toList();
    }

}
