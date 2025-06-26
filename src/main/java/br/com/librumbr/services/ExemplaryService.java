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

        exemplary.setId(null);
        exemplary.setBook(book);
        //A função de gerar número de tombo será chamada neste momento, assim setando o valor
        //Quando isso for feito, deverá retirar o campo do crateDTO

        return ModelMapperUtil.parseObject(exemplaryRepository.save(exemplary), ExemplaryResponseDTO.class);
    }

    @Transactional
    public ExemplaryResponseDTO updateExemplary(int id, ExemplaryCreateDTO updateExemplary) {
        Exemplary oldExemplary = exemplaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exemplary not found"));

        oldExemplary.setStatus(updateExemplary.getStatus());
        oldExemplary.setInventoryNumber(updateExemplary.getInventoryNumber());

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


}
