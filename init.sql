CREATE TABLE IF NOT EXISTS publishers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS authors (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    bio TEXT
);

CREATE TABLE IF NOT EXISTS genres (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    publication_year INT,
    page_count INT,
    description TEXT,
    publisher_id BIGINT REFERENCES publishers(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS book_authors (
    book_id BIGINT REFERENCES books(id) ON DELETE CASCADE,
    author_id BIGINT REFERENCES authors(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE IF NOT EXISTS book_genres (
    book_id BIGINT REFERENCES books(id) ON DELETE CASCADE,
    genre_id BIGINT REFERENCES genres(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, genre_id)
);

INSERT INTO publishers (name, country) VALUES
('O’Reilly Media', 'USA'),
('Packt Publishing', 'UK'),
('Альпина Паблишер', 'Россия');

INSERT INTO authors (first_name, last_name) VALUES
('Joshua', 'Bloch'),
('Robert', 'Martin'),
('Craig', 'Walls'),
('Erich', 'Gamma');

INSERT INTO genres (name) VALUES
('Software Architecture'),
('Java'),
('Design Patterns'),
('Web Development');

INSERT INTO books (title, isbn, publication_year, publisher_id) VALUES
('Effective Java', '978-0134685991', 2018, 1),
('Clean Code', '978-0132350884', 2008, 1),
('Spring in Action', '978-1617294945', 2018, 2),
('Design Patterns: Elements of Reusable Object-Oriented Software', '978-0201633610', 1994, 1);

INSERT INTO book_authors (book_id, author_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO book_genres (book_id, genre_id) VALUES
(1, 2),
(2, 1),
(3, 2),
(3, 4),
(4, 1),
(4, 3);