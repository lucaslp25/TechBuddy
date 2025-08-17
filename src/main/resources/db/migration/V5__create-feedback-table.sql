-- Tabela que irá conter todos os feedbacks dos mentores.

CREATE TABLE tb_feedback(

    id BIGSERIAL PRIMARY KEY,
    rating NUMERIC(2,1) NOT NULL,
    comment TEXT,
    session_id BIGINT NOT NULL UNIQUE,
    author_id BIGINT NOT NULL,
    target_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_session_id FOREIGN KEY (session_id) REFERENCES tb_mentorship_sessions(id),
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES tb_users(id),
    CONSTRAINT fk_target_id FOREIGN KEY (target_id) REFERENCES tb_users(id)
);