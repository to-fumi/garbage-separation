CREATE TABLE IF NOT EXISTS language_codes (
    id SERIAL PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS garbages (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    disposal_note VARCHAR(255),
    category VARCHAR(30) NOT NULL,
    language_code VARCHAR(10) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_language_codes
        FOREIGN KEY (language_code)
        REFERENCES language_codes(code)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);
