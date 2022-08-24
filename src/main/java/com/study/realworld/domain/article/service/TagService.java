package com.study.realworld.domain.article.service;

import com.study.realworld.domain.article.dto.TagFindAllResponseDTO;
import com.study.realworld.domain.article.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagFindAllResponseDTO findAll() {
        return TagFindAllResponseDTO.builder()
                .tags(tagRepository.findAllDistinctBody())
                .build();
    }
}
