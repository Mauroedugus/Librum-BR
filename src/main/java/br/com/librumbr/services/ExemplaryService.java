package br.com.librumbr.services;

import br.com.librumbr.models.Book;
import br.com.librumbr.models.Exemplary;
import br.com.librumbr.models.mapper.ModelMapperUtil;
import br.com.librumbr.repositories.BookRentalRepository;
import br.com.librumbr.repositories.BookRepository;
import br.com.librumbr.repositories.ExemplaryRepository;
import br.com.librumbr.web.dto.BookResponseDTO;
import br.com.librumbr.web.dto.ExemplaryCreateDTO;
import br.com.librumbr.web.dto.ExemplaryResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ExemplaryService {

    @Autowired
    private ExemplaryRepository exemplaryRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookRentalRepository bookRentalRepository;

    @Transactional
    public ExemplaryResponseDTO createExemplary(ExemplaryCreateDTO dto) {
        Book book = bookRepository.findById(Integer.parseInt(dto.getBookId()))
            .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Exemplary exemplary = ModelMapperUtil.parseObject(dto, Exemplary.class);

        String inventoryNumber = generateInventoryNumber(book.getId());

        exemplary.setId(null);
        exemplary.setBook(book);
        exemplary.setInventoryNumber(inventoryNumber);

        return ModelMapperUtil.parseObject(exemplaryRepository.save(exemplary), ExemplaryResponseDTO.class);
    }

    @Transactional
    public ExemplaryResponseDTO updateExemplary(int id, ExemplaryCreateDTO updateExemplary) {
        Exemplary oldExemplary = exemplaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exemplary not found"));

        oldExemplary.setStatus(updateExemplary.getStatus());

        if (updateExemplary.getBookRentalId() != null) {
            var bookRental = bookRentalRepository.findById(Integer.parseInt(updateExemplary.getBookRentalId()))
                    .orElseThrow(() -> new EntityNotFoundException("BookRental not found"));
            oldExemplary.setBookRental(bookRental);
        } else {
            oldExemplary.setBookRental(null); // Desvincula se for null
        }

        return ModelMapperUtil.parseObject(exemplaryRepository.save(oldExemplary), ExemplaryResponseDTO.class);
    }

    public void deleteById(int id) {exemplaryRepository.deleteById(id);}
    
    public Exemplary findById(int id) {
        return exemplaryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Exemplary not found with id: " + id));
    }

    public List<ExemplaryResponseDTO> findAllExemplars() {
        var exemplars = exemplaryRepository.findAll();
        var exemplarsDTO = exemplars.stream().map(p -> ModelMapperUtil.parseObject(p, ExemplaryResponseDTO.class));

        return exemplarsDTO.toList();
    }

    private String generateInventoryNumber(int bookId) {
        int index = getNextTomboIndex(bookId);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String date = today.format(formatter);

        return "L" + bookId + "-D" + date + "-I" + index;
    }

    private int getNextTomboIndex(int bookId) {
        List<Exemplary> exemplars = exemplaryRepository.findByBookId(bookId);

        return exemplars.stream()
                .map(Exemplary::getInventoryNumber)
                .map(this::extractIndexFromTombo)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }

    private int extractIndexFromTombo(String inventoryNumber) {
        try {
            String[] parts = inventoryNumber.split("-I");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Format invalid for inventory number: " + inventoryNumber);
            }
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Format inventory number invalid: " + inventoryNumber, e);
        }
    }


}
