CREATE TABLE IF NOT EXISTS language_codes (
    id INT NOT NULL PRIMARY KEY,
    code INT NOT NULL
);

CREATE TABLE IF NOT EXISTS garbages (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    disposal_note VARCHAR(255),
    category INT NOT NULL,
    language_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_language_codes FOREIGN KEY (language_id) REFERENCES language_codes(id)
);

CREATE INDEX IF NOT EXISTS idx_language_codes_id ON language_codes(id);
CREATE INDEX IF NOT EXISTS idx_garbages_id ON garbages(id);
CREATE INDEX IF NOT EXISTS idx_garbages_name ON garbages(name);
