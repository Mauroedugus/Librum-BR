INSERT INTO categories (name) VALUES ('Ficção Científica');

INSERT INTO publishers (name) VALUES ('Editora Exemplo');

INSERT INTO authors (name) VALUES ('Isaac Asimov');

INSERT INTO books (isbn, url_cover, title, id_category, id_publisher, synopsis, year, page_count, quantity)
VALUES (
    '9781234567897',
    'https://example.com/capa.jpg',
    'Fundação',
    1, -- id_category
    1, -- id_publisher
    'Uma das maiores obras de ficção científica...',
    1951,
    255,
    10
);
