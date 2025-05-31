package com.smartcompare.comparison.application;

import com.smartcompare.comparison.domain.Comparison;
import com.smartcompare.comparison.domain.dto.ComparisonDTO;
import com.smartcompare.comparison.domain.exception.ComparisonNotFoundException;
import com.smartcompare.comparison.infrastructure.ComparisonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComparisonService {
    private final ComparisonRepository comparisonRepository;

    @Transactional(readOnly = true)
    public ComparisonDTO findById(Long id) {
        Comparison comparison = comparisonRepository.findById(id)
                .orElseThrow(() -> new ComparisonNotFoundException("Comparación no encontrada: " + id));
        return toDTO(comparison);
    }

    @Transactional(readOnly = true)
    public List<ComparisonDTO> findAll() {
        return comparisonRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ComparisonDTO> findAllPaged(Pageable pageable) {
        return comparisonRepository.findAll(pageable)
                .map(this::toDTO);
    }

    @Transactional
    public ComparisonDTO create(ComparisonDTO dto) {
        Comparison comparison = Comparison.builder()
                .productIds(dto.getProductIds())
                .date(LocalDateTime.now())
                .userId(dto.getUserId())
                .build();
        Comparison saved = comparisonRepository.save(comparison);
        return toDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!comparisonRepository.existsById(id)) {
            throw new ComparisonNotFoundException("Comparación no encontrada: " + id);
        }
        comparisonRepository.deleteById(id);
    }

    private ComparisonDTO toDTO(Comparison comparison) {
        return ComparisonDTO.builder()
                .id(comparison.getId())
                .productIds(comparison.getProductIds())
                .date(comparison.getDate())
                .userId(comparison.getUserId())
                .build();
    }
}

