package br.com.librumbr.services;

import br.com.librumbr.web.dto.BrasilApiResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BookService {

        private final WebClient webClient;

        public BookService(WebClient.Builder webClientBuilder) {
            this.webClient = webClientBuilder
                    .baseUrl("https://brasilapi.com.br/api/isbn/v1")
                    .defaultHeader("Content-Type", "application/json")
                    .build();
        }

        public Mono<BrasilApiResponseDTO> getBookByIsbn(String isbn) {
            return webClient.get()
                    .uri("/{isbn}", isbn)
                    .retrieve()
                    .bodyToMono(BrasilApiResponseDTO.class);
        }
}
