-- Tabela que ira armazenar as solicitações de mentoria.

CREATE TABLE tb_mentorship_requests(

    id BIGSERIAL PRIMARY KEY,
    dev_requester_id BIGINT NOT NULL,
    mentor_requested_id BIGINT NOT NULL,
    message TEXT,
    request_status VARCHAR(30) NOT NULL,
    created_at DATE NOT NULL,
    Updated_at DATE,

    CONSTRAINT fk_devRequester_id FOREIGN KEY (dev_requester_id) REFERENCES tb_users(id),
    CONSTRAINT fk_mentorRequested_id FOREIGN KEY (mentor_requested_id) REFERENCES tb_users(id)
);