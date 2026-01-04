INSERT INTO language_codes(code) VALUES
    ('ja'),
    ('en')
ON CONFLICT(code) DO NOTHING;
