CREATE TABLE language_codes (
    id INT NOT NULL PRIMARY KEY,
    code INT NOT NULL
);

CREATE TABLE garbages (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    disposal_note VARCHAR(255),
    category INT NOT NULL,
    language_id INT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_language_codes FOREIGN KEY (languageId) REFERENCES language_codes(id)
);

CREATE INDEX idx_language_codes_id ON language_codes(id);
CREATE INDEX idx_garbages_id ON garbages(id);
CREATE INDEX idx_garbages_name ON garbages(name);
