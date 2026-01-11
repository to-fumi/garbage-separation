INSERT INTO roles(role) VALUES
    ('user'),
    ('admin')
ON CONFLICT(role) DO NOTHING;
