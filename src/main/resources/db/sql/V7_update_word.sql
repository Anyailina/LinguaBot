--liquibase formatted sql
--changeset annill:change create_at update_at column


ALTER TABLE words
    ALTER COLUMN create_at SET DATA TYPE TIMESTAMP;

ALTER TABLE words
    ALTER COLUMN update_at SET DATA TYPE TIMESTAMP;

ALTER TABLE words
    ALTER COLUMN create_at SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE words
    ALTER COLUMN update_at SET DEFAULT CURRENT_TIMESTAMP;
