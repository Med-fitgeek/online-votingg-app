package com.evoting.evote_backend.service.impl;

import com.evoting.evote_backend.dto.*;
import com.evoting.evote_backend.entity.*;
import com.evoting.evote_backend.mapper.ElectionMapper;
import com.evoting.evote_backend.repository.*;
import com.evoting.evote_backend.service.interfaces.ElectionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;
    private final UserRepository userRepository;
    private final VoterTokenRepository voterTokenRepository;
    private final OptionRepository optionRepository;
    private final ElectionMapper electionMapper;


    @Transactional
    @Override
    public List<VoterTokenResponseDTO> createElection(ElectionRequestDTO dto, String username) {
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if(dto.getStartDate().isAfter(dto.getEndDate())
                || dto.getStartDate().isBefore(LocalDateTime.now() )
                || dto.getEndDate().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("La date des elections est invalide.");

        // 1. Créer l’élection
        Election election = Election.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
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
    @Transactional
    public String updateElection(Long id, ElectionRequestDTO dto, String username) {
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Election election = electionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Élection introuvable"));

        // Vérifie que l’utilisateur est bien le créateur
        if (!election.getCreatedBy().getId().equals(creator.getId()))
            throw new SecurityException("Vous n’êtes pas autorisé à modifier cette élection.");

        // Vérifie que l’élection n’a pas encore commencé
        if (election.getStartDate().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Impossible de modifier une élection déjà commencée.");

        // Met à jour les infos principales
        election.setTitle(dto.getTitle());
        election.setDescription(dto.getDescription());
        election.setStartDate(dto.getStartDate());
        election.setEndDate(dto.getEndDate());

        // Mise à jour des options
        List<Option> newOptions = new ArrayList<>();
        for (String label : dto.getOptions()) {
            Option option = Option.builder()
                    .label(label)
                    .election(election)
                    .build();
            newOptions.add(option);
        }
        election.setOptions(newOptions);

        // Mise à jour des votants (nouvelles personnes)
        List<VoterToken> newVoters = new ArrayList<>();
        for (String name : dto.getVoters()) {
            VoterToken token = VoterToken.builder()
                    .token(UUID.randomUUID())
                    .used(false)
                    .election(election)
                    .build();
            newVoters.add(token);
        }
        election.setVoterTokens(newVoters);

        electionRepository.save(election);
        return "Élection mise à jour avec succès.";
    }

    @Override
    @Transactional
    public void deleteElection(Long id, String username) {
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Election election = electionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Élection introuvable"));

        // Vérifie que l'utilisateur est bien le créateur
        if (!election.getCreatedBy().getId().equals(creator.getId())) {
            throw new SecurityException("Vous n'êtes pas autorisé à supprimer cette élection.");
        }

        // Vérifie que l’élection n’a pas encore commencé
        if (election.getStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Impossible de supprimer une élection déjà commencée.");
        }

        // Supprime l’élection (avec cascade pour options et tokens)
        electionRepository.delete(election);
    }

    @Override
    public ElectionResponseDTO getElectionById(Long id, String username) {
        Election election = electionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Élection introuvable"));

        if (!election.getCreatedBy().getUsername().equals(username)) {
            throw new SecurityException("Accès interdit à cette élection.");
        }

        return electionMapper.toDto(election);
    }

    @Override
    public List<ElectionResponseDTO> getElectionsByCreator(String username) {
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        List<Election> elections = electionRepository.findByCreatedBy(creator);
        return electionMapper.toDtoList(elections);
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
