package br.com.librumbr.services;

import br.com.librumbr.models.Reader;
import br.com.librumbr.repositories.ReaderRepository;
import br.com.librumbr.web.dto.ReaderCreateDTO;
import br.com.librumbr.web.dto.ReaderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;

    public List<ReaderResponseDTO> findAll() {
        return readerRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ReaderResponseDTO findById(int id) {
        return toResponse(getOrThrow(id));
    }

    public ReaderResponseDTO create(ReaderCreateDTO dto) {
        Reader entity = toEntity(dto);
        Reader saved = readerRepository.save(entity);
        return toResponse(saved);
    }

    public ReaderResponseDTO update(int id, ReaderCreateDTO dto) {
        Reader entity = getOrThrow(id);
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        Reader saved = readerRepository.save(entity);
        return toResponse(saved);
    }

    public void delete(int id) {
        if (!readerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Leitor não encontrado");
        }
        readerRepository.deleteById(id);
    }

    private Reader getOrThrow(int id) {
        return readerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leitor não encontrado"));
    }

    private Reader toEntity(ReaderCreateDTO dto) {
        return new Reader(dto.getName(), dto.getEmail(), dto.getPhoneNumber());
    }

    private ReaderResponseDTO toResponse(Reader r) {
        return ReaderResponseDTO.builder()
                .id(r.getId())
                .name(r.getName())
                .email(r.getEmail())
                .phoneNumber(r.getPhoneNumber())
                .build();
    }
}
