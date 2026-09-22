# Book catalog

---
## API Endpoints

The base URL for all endpoints is `http://localhost:8080`.

### Books (`/api/v1/books`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/books` | Retrieve all books |
| `GET` | `/api/v1/books/{id}` | Get a book by ID |
| `POST` | `/api/v1/books` | Create a new book |
| `PUT` | `/api/v1/books/{id}` | Update an existing book |
| `DELETE` | `/api/v1/books/{id}` | Delete a book by ID |

### Authors (`/api/v1/authors`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/authors` | Retrieve all authors |
| `GET` | `/api/v1/authors/{id}` | Get an author by ID |
| `POST` | `/api/v1/authors` | Create a new author |
| `PUT` | `/api/v1/authors/{id}` | Update an author by ID |
| `DELETE` | `/api/v1/authors/{id}` | Delete an author by ID |

### Publishers (`/api/v1/publishers`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/publishers` | Retrieve all publishers |
| `GET` | `/api/v1/publishers/{id}` | Get a publisher by ID |
| `POST` | `/api/v1/publishers` | Create a new publisher |
| `PUT` | `/api/v1/publishers/{id}` | Update a publisher by ID |
| `DELETE` | `/api/v1/publishers/{id}` | Delete a publisher by ID |

### Genres (`/api/v1/genres`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/genres` | Retrieve all genres |
| `GET` | `/api/v1/genres/{id}` | Get a genre by ID |
| `POST` | `/api/v1/genres` | Create a new genre |
| `PUT` | `/api/v1/genres/{id}` | Update a genre by ID |
| `DELETE` | `/api/v1/genres/{id}` | Delete a genre by ID |

---

### Request & Response Examples

#### Create a Book
`POST /api/v1/books`

**Request Body:**
```json
{
  "title": "Effective Java",
  "isbn": "978-0134685991",
  "publicationYear": 2018,
  "publisherId": 1,
  "authorIds": [1],
  "genreIds": [2]
}
```
**Response (201 Created):**
```json
{
  "id": 1,
  "title": "Effective Java",
  "isbn": "978-0134685991",
  "publicationYear": 2018,
  "publisher": {
    "id": 1,
    "name": "O'Reilly Media",
    "country": "USA"
  },
  "authors": [
    {
      "id": 1,
      "firstName": "Joshua",
      "lastName": "Bloch"
    }
  ],
  "genres": [
    {
      "id": 2,
      "name": "Java"
    }
  ]
}
```

---
## DB

🐘 **PostgreSQL** DB structure for managing books, authors, genres and publishers. 

**DB name:** book_catalog_db

### Tables:
**publishers**

|Attribute|Type        |Constraints|Description                |
|---------|------------|-----------|---------------------------|
|id       |BIGSERIAL   |Primary Key|Unique publisher identifier|
|name     |VARCHAR(255)|NOT NULL   |Publisher name             |
|country  |VARCHAR(100)|           |Country of origin          |

**authors**

|Attribute |Type        |Constraints|Description             |
|----------|------------|-----------|------------------------|
|id        |BIGSERIAL   |Primary Key|Unique author identifier|
|first_name|VARCHAR(100)|NOT NULL   |Author's first name     |
|last_name |VARCHAR(100)|NOT NULL   |Author's last name      |
|bio       |TEXT        |           |Short biography         |

**genres**

|Attribute|Type        |Constraints     |Description            |
|---------|------------|----------------|-----------------------|
|id       |BIGSERIAL   |Primary Key     |Unique genre identifier|
|name     |VARCHAR(100)|NOT NULL, UNIQUE|Genre name             |

**books**

|Attribute       |Type        |Constraints|Description                                   |
|----------------|------------|-----------|----------------------------------------------|
|id              |BIGSERIAL   |Primary Key|Unique book identifier                        |
|title           |VARCHAR(255)|NOT NULL   |Title of the book                             |
|isbn            |VARCHAR(20) |UNIQUE     |International Standard Book Number            |
|publication_year|INT         |           |Year published                                |
|page_count      |INT         |           |Total number of pages                         |
|description     |TEXT        |           |Brief overview/summary                        |
|publisher_id    |BIGINT      |Foreign Key|References publishers(id) (ON DELETE SET NULL)|

**book_authors** (N:N)

|Attribute|Type  |Constraints|Description                               |
|---------|------|-----------|------------------------------------------|
|book_id  |BIGINT|PK, FK     |References books(id) (ON DELETE CASCADE)  |
|author_id|BIGINT|PK, FK     |References authors(id) (ON DELETE CASCADE)|

**book_genres** (N:N)

|Attribute|Type  |Constraints|Description                              |
|---------|------|-----------|-----------------------------------------|
|book_id  |BIGINT|PK, FK     |References books(id) (ON DELETE CASCADE) |
|genre_id |BIGINT|PK, FK     |References genres(id) (ON DELETE CASCADE)|

### Relationships

- `publishers` → `books`: One-To-Many (1:N)
- `books` ↔ `authors`: Many-To-Many (N:N) uses `book_authors`
- `books` ↔ `genres`: Many-to-Many (N:N) uses `book_genres`