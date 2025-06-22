-- CREATE TABLE IF NOT EXISTS ChatMessage (
--     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     username VARCHAR(255) NOT NULL,
--     email VARCHAR(255)  NOT NULL,
--     password VARCHAR(255) NOT NULL, -- This must not be NULL
--     role VARCHAR(50) NOT NULL,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
--     );

CREATE TABLE chat_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id VARCHAR(255),
    role VARCHAR(50),
    content CLOB,
    timestamp TIMESTAMP
);