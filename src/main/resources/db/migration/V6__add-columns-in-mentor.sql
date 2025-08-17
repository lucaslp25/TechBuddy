-- vou adicionar as colunas de contagem de feedbacks e a media dos feedbacks na tabela do mentor.

ALTER TABLE tb_mentor_profiles
    ADD COLUMN average_rating NUMERIC(2,1) NOT NULL DEFAULT 0.0;

ALTER TABLE tb_mentor_profiles
    ADD COLUMN total_mentorships INTEGER NOT NULL DEFAULT 0;
