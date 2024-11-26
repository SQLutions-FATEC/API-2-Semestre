USE `avaliador`;

INSERT INTO periodo (semestre, ano) VALUES (1, 2024), (2,2024), (1, 2025), (2, 2025);

INSERT INTO tipo_usuario (descricao) VALUES ('professor'), ('aluno');

INSERT INTO usuario (nome, ra, email, senha, tipo, equipe)
SELECT 'Professor', NULL, 'professor@fatec.sp.gov.br', '1', 1, NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM usuario
    WHERE email = 'professor@fatec.sp.gov.br'
);
