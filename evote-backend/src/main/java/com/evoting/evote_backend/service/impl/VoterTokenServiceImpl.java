package com.evoting.evote_backend.service.impl;

import com.evoting.evote_backend.dto.VoteRequestDTO;
import com.evoting.evote_backend.entity.Option;
import com.evoting.evote_backend.entity.VoterToken;
import com.evoting.evote_backend.repository.OptionRepository;
import com.evoting.evote_backend.repository.VoterTokenRepository;
import com.evoting.evote_backend.service.interfaces.VoterTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoterTokenServiceImpl implements VoterTokenService {

    private final VoterTokenRepository voterTokenRepository;
    private final OptionRepository optionRepository;

    @Override
    @Transactional
    public void vote(VoteRequestDTO request) {
        VoterToken voterToken = voterTokenRepository.findByToken(request.token())
                .orElseThrow(() -> new IllegalArgumentException("Token invalide ou inexistant."));

        if (voterToken.isUsed()) {
            throw new IllegalStateException("Ce lien de vote a déjà été utilisé.");
        }

        Option option = optionRepository.findById(request.optionId())
                .orElseThrow(() -> new IllegalArgumentException("Option invalide."));

        // Vérifier que l'option appartient à la même élection
        if (!option.getElection().getId().equals(voterToken.getElection().getId())) {
            throw new IllegalArgumentException("Cette option ne correspond pas à cette élection.");
        }

        voterToken.setSelectedOption(option);
        voterToken.setUsed(true);
        voterTokenRepository.save(voterToken);
    }
}

