package br.com.librumbr.services;

import br.com.librumbr.models.Book;
import br.com.librumbr.models.BookRental;
import br.com.librumbr.models.Exemplary;
import br.com.librumbr.models.Reader;
import br.com.librumbr.models.mapper.ModelMapperUtil;
import br.com.librumbr.repositories.BookRentalRepository;
import br.com.librumbr.repositories.ExemplaryRepository;
import br.com.librumbr.repositories.ReaderRepository;
import br.com.librumbr.web.dto.BookRentalCreateDTO;
import br.com.librumbr.web.dto.BookRentalResponseDTO;
import br.com.librumbr.web.dto.ExemplaryResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookRentalService {

    @Autowired
    private BookRentalRepository bookRentalRepository;
    @Autowired
    private ExemplaryRepository exemplaryRepository;
    @Autowired
    private ReaderRepository readerRepository;

    @Transactional
    public BookRentalResponseDTO createBookRental(BookRentalCreateDTO dto) {
        List<Exemplary> exemplars = exemplaryRepository.findAllById(
                dto.getExemplaryIds().stream()
                        .toList()
        );
        if (exemplars.size() != dto.getExemplaryIds().size()) {
            throw new EntityNotFoundException("All or some exemplars not found");
        }

        Reader reader = readerRepository.findById(dto.getReaderId())
                .orElseThrow(() -> new EntityNotFoundException("Reader not found"));

        BookRental bookRental = new BookRental();
        bookRental.setExemplars(exemplars);
        bookRental.setReader(reader);
        bookRental.setStatus("active");
        bookRental.setStartDate(LocalDate.now());
        bookRental.setPredictedDate(LocalDate.now().plusDays(15));

        for (Exemplary exemplary : exemplars) {
            exemplary.setBookRental(bookRental);
            exemplary.setStatus("unavailable");
        }

        BookRental savedRental = bookRentalRepository.save(bookRental);
        return convertToDto(savedRental);
    }

    @Transactional
    public BookRentalResponseDTO updateBookRental(int id, BookRentalCreateDTO updateBookRental) {
        BookRental oldBookRental = bookRentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book rental not found"));

        if(updateBookRental.getPredictedDate() != null) {
            oldBookRental.setPredictedDate(updateBookRental.getPredictedDate());
        }

        if(updateBookRental.getFinalDate() != null) {
            oldBookRental.setStatus("finished");
            oldBookRental.setFinalDate(updateBookRental.getFinalDate());
        }

        return ModelMapperUtil.parseObject(bookRentalRepository.save(oldBookRental), BookRentalResponseDTO.class);
    }

    public void deleteById(int id) {
        BookRental rental = bookRentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookRental não encontrado"));

        List<Exemplary> exemplars = exemplaryRepository.findByBookRentalId(id);
        for (Exemplary exemplary : exemplars) {
            exemplary.setBookRental(null);
            exemplary.setStatus("available");
        }

        exemplaryRepository.saveAll(exemplars);
        bookRentalRepository.deleteById(id);
    }

    public BookRentalResponseDTO findById(int id) {
        BookRental bookRental = bookRentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book rental not found with id: " + id));
        return convertToDto(bookRental);
    }

    public List<BookRentalResponseDTO> findAllBookRental() {
        return bookRentalRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private BookRentalResponseDTO convertToDto(BookRental rental) {
        BookRentalResponseDTO dto = ModelMapperUtil.parseObject(rental, BookRentalResponseDTO.class);

        if (rental.getExemplars() != null && !rental.getExemplars().isEmpty()) {
            dto.setExemplaryIds(
                    rental.getExemplars().stream()
                            .map(e -> e.getId())
                            .toList()
            );
        }

        return dto;
    }

}
