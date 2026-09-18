# Book catalog

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