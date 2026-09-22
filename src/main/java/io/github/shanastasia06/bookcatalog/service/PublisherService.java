package io.github.shanastasia06.bookcatalog.service;

import io.github.shanastasia06.bookcatalog.dto.AuthorResponseDto;
import io.github.shanastasia06.bookcatalog.dto.PublisherRequestDto;
import io.github.shanastasia06.bookcatalog.dto.PublisherResponseDto;
import io.github.shanastasia06.bookcatalog.entity.Publisher;
import io.github.shanastasia06.bookcatalog.exception.EntityAlreadyExistsException;
import io.github.shanastasia06.bookcatalog.exception.EntityNotFoundException;
import io.github.shanastasia06.bookcatalog.mapper.PublisherMapper;
import io.github.shanastasia06.bookcatalog.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    public List<PublisherResponseDto> findAll(String nameKeyword, String countryKeyword) {
        List<Publisher> publishers;

        boolean hasName = nameKeyword != null && !nameKeyword.isBlank();
        boolean hasCountry = countryKeyword != null && !countryKeyword.isBlank();

        if (hasName && hasCountry) {
            publishers = publisherRepository.findByNameContainingIgnoreCaseAndCountryContainingIgnoreCase(
                    nameKeyword.trim(), countryKeyword.trim());
        } else if (hasName) {
            publishers = publisherRepository.findByNameContainingIgnoreCase(nameKeyword.trim());
        } else if (hasCountry) {
            publishers = publisherRepository.findByCountryContainingIgnoreCase(countryKeyword.trim());
        } else {
            publishers = publisherRepository.findAll();
        }

        return publishers.stream()
                .map(publisherMapper::toDto)
                .toList();
    }

    public PublisherResponseDto findById(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Издатель с ID " + id + " не найден"));
        return publisherMapper.toDto(publisher);
    }

    @Transactional
    public PublisherResponseDto create(PublisherRequestDto publisherRequestDto) {
        if (publisherRepository.existsByNameIgnoreCase(publisherRequestDto.name())) {
            throw new EntityAlreadyExistsException("Издатель с таким именем уже существует");
        }
        Publisher publisher = publisherMapper.toEntity(publisherRequestDto);
        Publisher savedPublisher = publisherRepository.save(publisher);
        return publisherMapper.toDto(savedPublisher);
    }

    @Transactional
    public PublisherResponseDto update(Long id, PublisherRequestDto publisherRequestDto) {
        Publisher existingPublisher = publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Издатель с ID " + id + " не найден"));

        if (!existingPublisher.getName().equalsIgnoreCase(publisherRequestDto.name())
                && publisherRepository.existsByNameIgnoreCase(publisherRequestDto.name())) {
            throw new EntityAlreadyExistsException("Издатель с таким именем уже существует");
        }

        existingPublisher.setName(publisherRequestDto.name());
        existingPublisher.setCountry(publisherRequestDto.country());

        return publisherMapper.toDto(publisherRepository.save(existingPublisher));
    }

    @Transactional
    public void delete(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new EntityNotFoundException("Издательство с ID " + id + " не найдено");
        }
        publisherRepository.deleteById(id);
    }
}
