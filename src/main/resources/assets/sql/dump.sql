USE `avaliador`;

INSERT INTO criterio (nome, descricao) VALUES
('Proatividade', 'Característica de quem busca identificar ou resolver os problemas por antecipação - com antecedencia - presteza - diligência'),
('Autonomia', 'Competência para gerir sua própria vida - fazendo uso de seus próprios meios - vontades ou princípios'),
('Colaboração', 'Ato ou efeito de colaborar - concurso - ajuda - auxílio - trabalhar em colaboração'),
('Entrega de resultado', 'Ação ou resultado de entregar - passar valor para alguém ou algo - transmissão');

INSERT INTO periodo (semestre, ano) VALUES (1, 2024), (2,2024), (1, 2025), (2, 2025);

INSERT INTO sprint (descricao, data_inicio, data_fim, periodo) VALUES
('Sprint 1', '2024-03-04', '2024-03-24', 1), ('Sprint 2', '2024-03-25', '2024-04-14', 1),('Sprint 3', '2024-04-15', '2024-05-05', 1), ('Sprint 4', '2024-05-06', '2024-05-26', 1),
('Sprint 1', '2024-09-09', '2024-09-29', 2), ('Sprint 2', '2024-09-30', '2024-10-20', 2), ('Sprint 3', '2024-10-21', '2024-11-10', 2), ('Sprint 4', '2024-11-11', '2024-12-01', 2),
('Sprint 1', '2025-03-04', '2025-03-24', 3), ('Sprint 2', '2025-03-25', '2025-04-14', 3),('Sprint 3', '2025-04-15', '2025-05-05', 3), ('Sprint 4', '2025-05-06', '2025-05-26', 3),
('Sprint 1', '2025-09-09', '2025-09-29', 4), ('Sprint 2', '2025-09-30', '2025-10-20', 4), ('Sprint 3', '2025-10-21', '2025-11-10', 4), ('Sprint 4', '2025-11-11', '2025-12-01', 4);

INSERT INTO equipe (nome, github) VALUES ('SQLutions', 'https://github.com/SQLutions-FATEC'), ('Atomic Delta', 'https://github.com/Atomic-Delta'),
('Radônios', 'https://github.com/Radonios'), ('Zircônia', 'https://github.com/Zirconia');

INSERT INTO pontuacao (valor, sprint, equipe) VALUES
-- Equipe 1
(83, 2, 1), (82, 3, 1), (81, 4, 1), (80, 5, 1), (79, 6, 1), (78, 7, 1), (77, 8, 1),
(76, 9, 1), (75, 10, 1), (74, 11, 1), (73, 12, 1), (72, 13, 1), (71, 14, 1), (70, 15, 1), (69, 16, 1),
-- Equipe 2
(73, 2, 2), (72, 3, 2), (72, 4, 2), (72, 5, 2), (72, 6, 2), (72, 7, 2), (72, 8, 2),
(72, 9, 2), (72, 10, 2), (72, 11, 2), (72, 12, 2), (72, 13, 2), (72, 14, 2), (72, 15, 2), (72, 16, 2),
-- Equipe 3
(64, 1, 3), (63, 2, 3), (62, 3, 3), (62, 4, 3), (62, 5, 3), (62, 6, 3), (62, 7, 3), (62, 8, 3),
(62, 9, 3), (62, 10, 3), (62, 11, 3), (62, 12, 3), (62, 13, 3), (62, 14, 3), (62, 15, 3), (62, 16, 3),
-- Equipe 4
(54, 1, 4), (53, 2, 4), (52, 3, 4), (52, 4, 4), (52, 5, 4), (52, 6, 4), (52, 7, 4), (52, 8, 4),
(52, 9, 4), (52, 10, 4), (52, 11, 4), (52, 12, 4), (52, 13, 4), (52, 14, 4), (52, 15, 4), (52, 16, 4);

INSERT INTO tipo_usuario (descricao) VALUES ('professor'), ('aluno');

INSERT INTO criterio_periodo (criterio_id, periodo_id) VALUES
(1, 1), (2, 1), (3, 1),
(2, 2), (3, 2), (4, 2),
(1, 3), (2, 3), (3, 3), (4, 3),
(1, 4), (2, 4), (3, 4), (4, 4);

INSERT INTO equipe_periodo (equipe_id, periodo_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4),
(2, 1), (2, 2), (2, 3),
(3, 1), (3, 2), (3, 3), (3, 4),
(4, 1), (4, 2), (4, 3), (4, 4);

INSERT INTO usuario (nome, ra, email, senha, tipo, equipe) VALUES
-- Professor
('Professor', NULL, 'professor@fatec.sp.gov.br', '1', 1, NULL),
-- Equipe 1
('Augusto', 1, 'aluno@fatec.sp.gov.br', '1', 2, 1), ('Bryan', 2, 'bryan@example.com', '1', 2, 1), ('Cainã', 3, 'caina@example.com', '1', 2, 1), ('Davi', 4, 'davi@example.com', '1', 2, 1), ('Enzo', 5, 'enzo@example.com', '1', 2, 1),
('Gloria', 6, 'gloria@example.com', '1', 2, 1), ('João', 7, 'joao@example.com', '1', 2, 1), ('Lucas', 8, 'lucas@example.com', '1', 2, 1), ('Tiago', 9, 'tiago@example.com', '1', 2, 1),
-- Equipe 2
('Ana Silva', 10, 'ana@example.com', '1', 2, 2), ('Carlos Souza', 11, 'carlos@example.com', '1', 2, 2), ('Maria Oliveira', 12, 'maria@example.com', '1', 2, 2), ('João Santos', 13, 'joaos@example.com', '1', 2, 2),
('Laura Costa', 14, 'laura@example.com', '1', 2, 2),
-- Equipe 3
('Pedro Almeida', 15, 'pedro@example.com', '1', 2, 3), ('Fernanda Lima', 16, 'fernanda@example.com', '1', 2, 3), ('Gabriel Rocha', 17, 'gabriel@example.com', '1', 2, 3), ('Juliana Martins', 18, 'juliana@example.com', '1', 2, 3),
('Lucas Pereira', 19, 'lucasp@example.com', '1', 2, 3),
-- Equipe 4
('Beatriz Gomes', 20, 'beatriz@example.com', '1', 2, 4), ('Thiago Ribeiro', 21, 'thiago@example.com', '1', 2, 4), ('Camila Ferreira', 22, 'camila@example.com', '1', 2, 4), ('Rafael Mendes', 23, 'rafael@example.com', '1', 2, 4),
('Paula Nascimento', 24, 'paula@example.com', '1', 2, 4);

INSERT INTO nota (valor, avaliador, avaliado, criterio, periodo, sprint) VALUES
-- Sprint 1
-- -- Equipe 1
-- -- -- Aluno 1
(1, 2, 2, 1, 1, 1), (1, 2, 3, 1, 1, 1), (1, 2, 4, 1, 1, 1), (1, 2, 5, 1, 1, 1), (1, 2, 6, 1, 1, 1), (1, 2, 7, 1, 1, 1), (1, 2, 8, 1, 1, 1), (1, 2, 9, 1, 1, 1), (1, 2, 10, 1, 1, 1),
(1, 2, 2, 2, 1, 1), (1, 2, 3, 2, 1, 1), (1, 2, 4, 2, 1, 1), (1, 2, 5, 2, 1, 1), (1, 2, 6, 2, 1, 1), (1, 2, 7, 2, 1, 1), (1, 2, 8, 2, 1, 1), (1, 2, 9, 2, 1, 1), (1, 2, 10, 2, 1, 1),
(1, 2, 2, 3, 1, 1), (1, 2, 3, 3, 1, 1), (1, 2, 4, 3, 1, 1), (1, 2, 5, 3, 1, 1), (1, 2, 6, 3, 1, 1), (1, 2, 7, 3, 1, 1), (1, 2, 8, 3, 1, 1), (1, 2, 9, 3, 1, 1), (1, 2, 10, 3, 1, 1),
-- -- -- Aluno 2
(1, 3, 2, 1, 1, 1), (1, 3, 3, 1, 1, 1), (1, 3, 4, 1, 1, 1), (1, 3, 5, 1, 1, 1), (1, 3, 6, 1, 1, 1), (1, 3, 7, 1, 1, 1), (1, 3, 8, 1, 1, 1), (1, 3, 9, 1, 1, 1), (1, 3, 10, 1, 1, 1),
(1, 3, 2, 2, 1, 1), (1, 3, 3, 2, 1, 1), (1, 3, 4, 2, 1, 1), (1, 3, 5, 2, 1, 1), (1, 3, 6, 2, 1, 1), (1, 3, 7, 2, 1, 1), (1, 3, 8, 2, 1, 1), (1, 3, 9, 2, 1, 1), (1, 3, 10, 2, 1, 1),
(1, 3, 2, 3, 1, 1), (1, 3, 3, 3, 1, 1), (1, 3, 4, 3, 1, 1), (1, 3, 5, 3, 1, 1), (1, 3, 6, 3, 1, 1), (1, 3, 7, 3, 1, 1), (1, 3, 8, 3, 1, 1), (1, 3, 9, 3, 1, 1), (1, 3, 10, 3, 1, 1),
-- -- -- Aluno 3
(1, 4, 2, 1, 1, 1), (1, 4, 3, 1, 1, 1), (1, 4, 4, 1, 1, 1), (1, 4, 5, 1, 1, 1), (1, 4, 6, 1, 1, 1), (1, 4, 7, 1, 1, 1), (1, 4, 8, 1, 1, 1), (1, 4, 9, 1, 1, 1), (1, 4, 10, 1, 1, 1),
(1, 4, 2, 2, 1, 1), (1, 4, 3, 2, 1, 1), (1, 4, 4, 2, 1, 1), (1, 4, 5, 2, 1, 1), (1, 4, 6, 2, 1, 1), (1, 4, 7, 2, 1, 1), (1, 4, 8, 2, 1, 1), (1, 4, 9, 2, 1, 1), (1, 4, 10, 2, 1, 1),
(1, 4, 2, 3, 1, 1), (1, 4, 3, 3, 1, 1), (1, 4, 4, 3, 1, 1), (1, 4, 5, 3, 1, 1), (1, 4, 6, 3, 1, 1), (1, 4, 7, 3, 1, 1), (1, 4, 8, 3, 1, 1), (1, 4, 9, 3, 1, 1), (1, 4, 10, 3, 1, 1),
-- -- -- Aluno 4
(1, 5, 2, 1, 1, 1), (1, 5, 3, 1, 1, 1), (1, 5, 4, 1, 1, 1), (1, 5, 5, 1, 1, 1), (1, 5, 6, 1, 1, 1), (1, 5, 7, 1, 1, 1), (1, 5, 8, 1, 1, 1), (1, 5, 9, 1, 1, 1), (1, 5, 10, 1, 1, 1),
(1, 5, 2, 2, 1, 1), (1, 5, 3, 2, 1, 1), (1, 5, 4, 2, 1, 1), (1, 5, 5, 2, 1, 1), (1, 5, 6, 2, 1, 1), (1, 5, 7, 2, 1, 1), (1, 5, 8, 2, 1, 1), (1, 5, 9, 2, 1, 1), (1, 5, 10, 2, 1, 1),
(1, 5, 2, 3, 1, 1), (1, 5, 3, 3, 1, 1), (1, 5, 4, 3, 1, 1), (1, 5, 5, 3, 1, 1), (1, 5, 6, 3, 1, 1), (1, 5, 7, 3, 1, 1), (1, 5, 8, 3, 1, 1), (1, 5, 9, 3, 1, 1), (1, 5, 10, 3, 1, 1),
-- -- -- Aluno 5
(1, 6, 2, 1, 1, 1), (1, 6, 3, 1, 1, 1), (1, 6, 4, 1, 1, 1), (1, 6, 5, 1, 1, 1), (1, 6, 6, 1, 1, 1), (1, 6, 7, 1, 1, 1), (1, 6, 8, 1, 1, 1), (1, 6, 9, 1, 1, 1), (1, 6, 10, 1, 1, 1),
(1, 6, 2, 2, 1, 1), (1, 6, 3, 2, 1, 1), (1, 6, 4, 2, 1, 1), (1, 6, 5, 2, 1, 1), (1, 6, 6, 2, 1, 1), (1, 6, 7, 2, 1, 1), (1, 6, 8, 2, 1, 1), (1, 6, 9, 2, 1, 1), (1, 6, 10, 2, 1, 1),
(1, 6, 2, 3, 1, 1), (1, 6, 3, 3, 1, 1), (1, 6, 4, 3, 1, 1), (1, 6, 5, 3, 1, 1), (1, 6, 6, 3, 1, 1), (1, 6, 7, 3, 1, 1), (1, 6, 8, 3, 1, 1), (1, 6, 9, 3, 1, 1), (1, 6, 10, 3, 1, 1),
-- -- -- Aluno 6
(1, 7, 2, 1, 1, 1), (1, 7, 3, 1, 1, 1), (1, 7, 4, 1, 1, 1), (1, 7, 5, 1, 1, 1), (1, 7, 6, 1, 1, 1), (1, 7, 7, 1, 1, 1), (1, 7, 8, 1, 1, 1), (1, 7, 9, 1, 1, 1), (1, 7, 10, 1, 1, 1),
(1, 7, 2, 2, 1, 1), (1, 7, 3, 2, 1, 1), (1, 7, 4, 2, 1, 1), (1, 7, 5, 2, 1, 1), (1, 7, 6, 2, 1, 1), (1, 7, 7, 2, 1, 1), (1, 7, 8, 2, 1, 1), (1, 7, 9, 2, 1, 1), (1, 7, 10, 2, 1, 1),
(1, 7, 2, 3, 1, 1), (1, 7, 3, 3, 1, 1), (1, 7, 4, 3, 1, 1), (1, 7, 5, 3, 1, 1), (1, 7, 6, 3, 1, 1), (1, 7, 7, 3, 1, 1), (1, 7, 8, 3, 1, 1), (1, 7, 9, 3, 1, 1), (1, 7, 10, 3, 1, 1),
-- -- -- Aluno 7
(1, 8, 2, 1, 1, 1), (1, 8, 3, 1, 1, 1), (1, 8, 4, 1, 1, 1), (1, 8, 5, 1, 1, 1), (1, 8, 6, 1, 1, 1), (1, 8, 7, 1, 1, 1), (1, 8, 8, 1, 1, 1), (1, 8, 9, 1, 1, 1), (1, 8, 10, 1, 1, 1),
(1, 8, 2, 2, 1, 1), (1, 8, 3, 2, 1, 1), (1, 8, 4, 2, 1, 1), (1, 8, 5, 2, 1, 1), (1, 8, 6, 2, 1, 1), (1, 8, 7, 2, 1, 1), (1, 8, 8, 2, 1, 1), (1, 8, 9, 2, 1, 1), (1, 8, 10, 2, 1, 1),
(1, 8, 2, 3, 1, 1), (1, 8, 3, 3, 1, 1), (1, 8, 4, 3, 1, 1), (1, 8, 5, 3, 1, 1), (1, 8, 6, 3, 1, 1), (1, 8, 7, 3, 1, 1), (1, 8, 8, 3, 1, 1), (1, 8, 9, 3, 1, 1), (1, 8, 10, 3, 1, 1),
-- -- -- Aluno 8
(1, 9, 2, 1, 1, 1), (1, 9, 3, 1, 1, 1), (1, 9, 4, 1, 1, 1), (1, 9, 5, 1, 1, 1), (1, 9, 6, 1, 1, 1), (1, 9, 7, 1, 1, 1), (1, 9, 8, 1, 1, 1), (1, 9, 9, 1, 1, 1), (1, 9, 10, 1, 1, 1),
(1, 9, 2, 2, 1, 1), (1, 9, 3, 2, 1, 1), (1, 9, 4, 2, 1, 1), (1, 9, 5, 2, 1, 1), (1, 9, 6, 2, 1, 1), (1, 9, 7, 2, 1, 1), (1, 9, 8, 2, 1, 1), (1, 9, 9, 2, 1, 1), (1, 9, 10, 2, 1, 1),
(1, 9, 2, 3, 1, 1), (1, 9, 3, 3, 1, 1), (1, 9, 4, 3, 1, 1), (1, 9, 5, 3, 1, 1), (1, 9, 6, 3, 1, 1), (1, 9, 7, 3, 1, 1), (1, 9, 8, 3, 1, 1), (1, 9, 9, 3, 1, 1), (1, 9, 10, 3, 1, 1),
-- -- -- Aluno 9
(1, 10, 2, 1, 1, 1), (1, 10, 3, 1, 1, 1), (1, 10, 4, 1, 1, 1), (1, 10, 5, 1, 1, 1), (1, 10, 6, 1, 1, 1), (1, 10, 7, 1, 1, 1), (1, 10, 8, 1, 1, 1), (1, 10, 9, 1, 1, 1), (1, 10, 10, 1, 1, 1),
(1, 10, 2, 2, 1, 1), (1, 10, 3, 2, 1, 1), (1, 10, 4, 2, 1, 1), (1, 10, 5, 2, 1, 1), (1, 10, 6, 2, 1, 1), (1, 10, 7, 2, 1, 1), (1, 10, 8, 2, 1, 1), (1, 10, 9, 2, 1, 1), (1, 10, 10, 2, 1, 1),
(1, 10, 2, 3, 1, 1), (1, 10, 3, 3, 1, 1), (1, 10, 4, 3, 1, 1), (1, 10, 5, 3, 1, 1), (1, 10, 6, 3, 1, 1), (1, 10, 7, 3, 1, 1), (1, 10, 8, 3, 1, 1), (1, 10, 9, 3, 1, 1), (1, 10, 10, 3, 1, 1),
-- -- Equipe 2
-- -- -- Aluno 1
(3, 11, 11, 1, 1, 1), (3, 11, 12, 1, 1, 1), (3, 11, 13, 1, 1, 1), (3, 11, 14, 1, 1, 1), (3, 11, 15, 1, 1, 1),
(3, 11, 11, 2, 1, 1), (3, 11, 12, 2, 1, 1), (3, 11, 13, 2, 1, 1), (3, 11, 14, 2, 1, 1), (3, 11, 15, 2, 1, 1),
(3, 11, 11, 3, 1, 1), (3, 11, 12, 3, 1, 1), (3, 11, 13, 3, 1, 1), (3, 11, 14, 3, 1, 1), (3, 11, 15, 3, 1, 1),
-- -- -- Aluno 2
(3, 12, 11, 1, 1, 1), (3, 12, 12, 1, 1, 1), (3, 12, 13, 1, 1, 1), (3, 12, 14, 1, 1, 1), (3, 12, 15, 1, 1, 1),
(3, 12, 11, 2, 1, 1), (3, 12, 12, 2, 1, 1), (3, 12, 13, 2, 1, 1), (3, 12, 14, 2, 1, 1), (3, 12, 15, 2, 1, 1),
(3, 12, 11, 3, 1, 1), (3, 12, 12, 3, 1, 1), (3, 12, 13, 3, 1, 1), (3, 12, 14, 3, 1, 1), (3, 12, 15, 3, 1, 1),
-- -- -- Aluno 3
(3, 13, 11, 1, 1, 1), (3, 13, 12, 1, 1, 1), (3, 13, 13, 1, 1, 1), (3, 13, 14, 1, 1, 1), (3, 13, 15, 1, 1, 1),
(3, 13, 11, 2, 1, 1), (3, 13, 12, 2, 1, 1), (3, 13, 13, 2, 1, 1), (3, 13, 14, 2, 1, 1), (3, 13, 15, 2, 1, 1),
(3, 13, 11, 3, 1, 1), (3, 13, 12, 3, 1, 1), (3, 13, 13, 3, 1, 1), (3, 13, 14, 3, 1, 1), (3, 13, 15, 3, 1, 1),
-- -- -- Aluno 4
(3, 14, 11, 1, 1, 1), (3, 14, 12, 1, 1, 1), (3, 14, 13, 1, 1, 1), (3, 14, 14, 1, 1, 1), (3, 14, 15, 1, 1, 1),
(3, 14, 11, 2, 1, 1), (3, 14, 12, 2, 1, 1), (3, 14, 13, 2, 1, 1), (3, 14, 14, 2, 1, 1), (3, 14, 15, 2, 1, 1),
(3, 14, 11, 3, 1, 1), (3, 14, 12, 3, 1, 1), (3, 14, 13, 3, 1, 1), (3, 14, 14, 3, 1, 1), (3, 14, 15, 3, 1, 1),
-- -- -- Aluno 5
(3, 15, 11, 1, 1, 1), (3, 15, 12, 1, 1, 1), (3, 15, 13, 1, 1, 1), (3, 15, 14, 1, 1, 1), (3, 15, 15, 1, 1, 1),
(3, 15, 11, 2, 1, 1), (3, 15, 12, 2, 1, 1), (3, 15, 13, 2, 1, 1), (3, 15, 14, 2, 1, 1), (3, 15, 15, 2, 1, 1),
(3, 15, 11, 3, 1, 1), (3, 15, 12, 3, 1, 1), (3, 15, 13, 3, 1, 1), (3, 15, 14, 3, 1, 1), (3, 15, 15, 3, 1, 1),
-- Sprint 2
-- -- Equipe 1
-- -- -- Aluno 1
(2, 2, 2, 1, 1, 2), (2, 2, 3, 1, 1, 2), (2, 2, 4, 1, 1, 2), (2, 2, 5, 1, 1, 2), (2, 2, 6, 1, 1, 2), (2, 2, 7, 1, 1, 2), (2, 2, 8, 1, 1, 2), (2, 2, 9, 1, 1, 2), (2, 2, 10, 1, 1, 2),
(2, 2, 2, 2, 1, 2), (2, 2, 3, 2, 1, 2), (2, 2, 4, 2, 1, 2), (2, 2, 5, 2, 1, 2), (2, 2, 6, 2, 1, 2), (2, 2, 7, 2, 1, 2), (2, 2, 8, 2, 1, 2), (2, 2, 9, 2, 1, 2), (2, 2, 10, 2, 1, 2),
(2, 2, 2, 3, 1, 2), (2, 2, 3, 3, 1, 2), (2, 2, 4, 3, 1, 2), (2, 2, 5, 3, 1, 2), (2, 2, 6, 3, 1, 2), (2, 2, 7, 3, 1, 2), (2, 2, 8, 3, 1, 2), (2, 2, 9, 3, 1, 2), (2, 2, 10, 3, 1, 2),
-- -- Aluno 2
(2, 3, 2, 1, 1, 2), (2, 3, 3, 1, 1, 2), (2, 3, 4, 1, 1, 2), (2, 3, 5, 1, 1, 2), (2, 3, 6, 1, 1, 2), (2, 3, 7, 1, 1, 2), (2, 3, 8, 1, 1, 2), (2, 3, 9, 1, 1, 2), (2, 3, 10, 1, 1, 2),
(2, 3, 2, 2, 1, 2), (2, 3, 3, 2, 1, 2), (2, 3, 4, 2, 1, 2), (2, 3, 5, 2, 1, 2), (2, 3, 6, 2, 1, 2), (2, 3, 7, 2, 1, 2), (2, 3, 8, 2, 1, 2), (2, 3, 9, 2, 1, 2), (2, 3, 10, 2, 1, 2),
(2, 3, 2, 3, 1, 2), (2, 3, 3, 3, 1, 2), (2, 3, 4, 3, 1, 2), (2, 3, 5, 3, 1, 2), (2, 3, 6, 3, 1, 2), (2, 3, 7, 3, 1, 2), (2, 3, 8, 3, 1, 2), (2, 3, 9, 3, 1, 2), (2, 3, 10, 3, 1, 2),
-- -- -- Aluno 3
(2, 4, 2, 1, 1, 2), (2, 4, 3, 1, 1, 2), (2, 4, 4, 1, 1, 2), (2, 4, 5, 1, 1, 2), (2, 4, 6, 1, 1, 2), (2, 4, 7, 1, 1, 2), (2, 4, 8, 1, 1, 2), (2, 4, 9, 1, 1, 2), (2, 4, 10, 1, 1, 2),
(2, 4, 2, 2, 1, 2), (2, 4, 3, 2, 1, 2), (2, 4, 4, 2, 1, 2), (2, 4, 5, 2, 1, 2), (2, 4, 6, 2, 1, 2), (2, 4, 7, 2, 1, 2), (2, 4, 8, 2, 1, 2), (2, 4, 9, 2, 1, 2), (2, 4, 10, 2, 1, 2),
(2, 4, 2, 3, 1, 2), (2, 4, 3, 3, 1, 2), (2, 4, 4, 3, 1, 2), (2, 4, 5, 3, 1, 2), (2, 4, 6, 3, 1, 2), (2, 4, 7, 3, 1, 2), (2, 4, 8, 3, 1, 2), (2, 4, 9, 3, 1, 2), (2, 4, 10, 3, 1, 2),
-- -- -- Aluno 4
(2, 5, 2, 1, 1, 2), (2, 5, 3, 1, 1, 2), (2, 5, 4, 1, 1, 2), (2, 5, 5, 1, 1, 2), (2, 5, 6, 1, 1, 2), (2, 5, 7, 1, 1, 2), (2, 5, 8, 1, 1, 2), (2, 5, 9, 1, 1, 2), (2, 5, 10, 1, 1, 2),
(2, 5, 2, 2, 1, 2), (2, 5, 3, 2, 1, 2), (2, 5, 4, 2, 1, 2), (2, 5, 5, 2, 1, 2), (2, 5, 6, 2, 1, 2), (2, 5, 7, 2, 1, 2), (2, 5, 8, 2, 1, 2), (2, 5, 9, 2, 1, 2), (2, 5, 10, 2, 1, 2),
(2, 5, 2, 3, 1, 2), (2, 5, 3, 3, 1, 2), (2, 5, 4, 3, 1, 2), (2, 5, 5, 3, 1, 2), (2, 5, 6, 3, 1, 2), (2, 5, 7, 3, 1, 2), (2, 5, 8, 3, 1, 2), (2, 5, 9, 3, 1, 2), (2, 5, 10, 3, 1, 2),
-- -- -- Aluno 5
(2, 6, 2, 1, 1, 2), (2, 6, 3, 1, 1, 2), (2, 6, 4, 1, 1, 2), (2, 6, 5, 1, 1, 2), (2, 6, 6, 1, 1, 2), (2, 6, 7, 1, 1, 2), (2, 6, 8, 1, 1, 2), (2, 6, 9, 1, 1, 2), (2, 6, 10, 1, 1, 2),
(2, 6, 2, 2, 1, 2), (2, 6, 3, 2, 1, 2), (2, 6, 4, 2, 1, 2), (2, 6, 5, 2, 1, 2), (2, 6, 6, 2, 1, 2), (2, 6, 7, 2, 1, 2), (2, 6, 8, 2, 1, 2), (2, 6, 9, 2, 1, 2), (2, 6, 10, 2, 1, 2),
(2, 6, 2, 3, 1, 2), (2, 6, 3, 3, 1, 2), (2, 6, 4, 3, 1, 2), (2, 6, 5, 3, 1, 2), (2, 6, 6, 3, 1, 2), (2, 6, 7, 3, 1, 2), (2, 6, 8, 3, 1, 2), (2, 6, 9, 3, 1, 2), (2, 6, 10, 3, 1, 2),
-- -- -- Aluno 6
(2, 7, 2, 1, 1, 2), (2, 7, 3, 1, 1, 2), (2, 7, 4, 1, 1, 2), (2, 7, 5, 1, 1, 2), (2, 7, 6, 1, 1, 2), (2, 7, 7, 1, 1, 2), (2, 7, 8, 1, 1, 2), (2, 7, 9, 1, 1, 2), (2, 7, 10, 1, 1, 2),
(2, 7, 2, 2, 1, 2), (2, 7, 3, 2, 1, 2), (2, 7, 4, 2, 1, 2), (2, 7, 5, 2, 1, 2), (2, 7, 6, 2, 1, 2), (2, 7, 7, 2, 1, 2), (2, 7, 8, 2, 1, 2), (2, 7, 9, 2, 1, 2), (2, 7, 10, 2, 1, 2),
(2, 7, 2, 3, 1, 2), (2, 7, 3, 3, 1, 2), (2, 7, 4, 3, 1, 2), (2, 7, 5, 3, 1, 2), (2, 7, 6, 3, 1, 2), (2, 7, 7, 3, 1, 2), (2, 7, 8, 3, 1, 2), (2, 7, 9, 3, 1, 2), (2, 7, 10, 3, 1, 2),
-- -- -- Aluno 7
(2, 8, 2, 1, 1, 2), (2, 8, 3, 1, 1, 2), (2, 8, 4, 1, 1, 2), (2, 8, 5, 1, 1, 2), (2, 8, 6, 1, 1, 2), (2, 8, 7, 1, 1, 2), (2, 8, 8, 1, 1, 2), (2, 8, 9, 1, 1, 2), (2, 8, 10, 1, 1, 2),
(2, 8, 2, 2, 1, 2), (2, 8, 3, 2, 1, 2), (2, 8, 4, 2, 1, 2), (2, 8, 5, 2, 1, 2), (2, 8, 6, 2, 1, 2), (2, 8, 7, 2, 1, 2), (2, 8, 8, 2, 1, 2), (2, 8, 9, 2, 1, 2), (2, 8, 10, 2, 1, 2),
(2, 8, 2, 3, 1, 2), (2, 8, 3, 3, 1, 2), (2, 8, 4, 3, 1, 2), (2, 8, 5, 3, 1, 2), (2, 8, 6, 3, 1, 2), (2, 8, 7, 3, 1, 2), (2, 8, 8, 3, 1, 2), (2, 8, 9, 3, 1, 2), (2, 8, 10, 3, 1, 2),
-- -- -- Aluno 8
(2, 9, 2, 1, 1, 2), (2, 9, 3, 1, 1, 2), (2, 9, 4, 1, 1, 2), (2, 9, 5, 1, 1, 2), (2, 9, 6, 1, 1, 2), (2, 9, 7, 1, 1, 2), (2, 9, 8, 1, 1, 2), (2, 9, 9, 1, 1, 2), (2, 9, 10, 1, 1, 2),
(2, 9, 2, 2, 1, 2), (2, 9, 3, 2, 1, 2), (2, 9, 4, 2, 1, 2), (2, 9, 5, 2, 1, 2), (2, 9, 6, 2, 1, 2), (2, 9, 7, 2, 1, 2), (2, 9, 8, 2, 1, 2), (2, 9, 9, 2, 1, 2), (2, 9, 10, 2, 1, 2),
(2, 9, 2, 3, 1, 2), (2, 9, 3, 3, 1, 2), (2, 9, 4, 3, 1, 2), (2, 9, 5, 3, 1, 2), (2, 9, 6, 3, 1, 2), (2, 9, 7, 3, 1, 2), (2, 9, 8, 3, 1, 2), (2, 9, 9, 3, 1, 2), (2, 9, 10, 3, 1, 2),
-- -- -- Aluno 9
(2, 10, 2, 1, 1, 2), (2, 10, 3, 1, 1, 2), (2, 10, 4, 1, 1, 2), (2, 10, 5, 1, 1, 2), (2, 10, 6, 1, 1, 2), (2, 10, 7, 1, 1, 2), (2, 10, 8, 1, 1, 2), (2, 10, 9, 1, 1, 2), (2, 10, 10, 1, 1, 2),
(2, 10, 2, 2, 1, 2), (2, 10, 3, 2, 1, 2), (2, 10, 4, 2, 1, 2), (2, 10, 5, 2, 1, 2), (2, 10, 6, 2, 1, 2), (2, 10, 7, 2, 1, 2), (2, 10, 8, 2, 1, 2), (2, 10, 9, 2, 1, 2), (2, 10, 10, 2, 1, 2),
(2, 10, 2, 3, 1, 2), (2, 10, 3, 3, 1, 2), (2, 10, 4, 3, 1, 2), (2, 10, 5, 3, 1, 2), (2, 10, 6, 3, 1, 2), (2, 10, 7, 3, 1, 2), (2, 10, 8, 3, 1, 2), (2, 10, 9, 3, 1, 2), (2, 10, 10, 3, 1, 2),
-- -- Equipe 2
-- -- -- Aluno 1
(2, 11, 11, 1, 1, 2), (2, 11, 12, 1, 1, 2), (2, 11, 13, 1, 1, 2), (2, 11, 14, 1, 1, 2), (2, 11, 15, 1, 1, 2),
(2, 11, 11, 2, 1, 2), (2, 11, 12, 2, 1, 2), (2, 11, 13, 2, 1, 2), (2, 11, 14, 2, 1, 2), (2, 11, 15, 2, 1, 2),
(2, 11, 11, 3, 1, 2), (2, 11, 12, 3, 1, 2), (2, 11, 13, 3, 1, 2), (2, 11, 14, 3, 1, 2), (2, 11, 15, 3, 1, 2),
-- -- -- Aluno 2
(2, 12, 11, 1, 1, 2), (2, 12, 12, 1, 1, 2), (2, 12, 13, 1, 1, 2), (2, 12, 14, 1, 1, 2), (2, 12, 15, 1, 1, 2),
(2, 12, 11, 2, 1, 2), (2, 12, 12, 2, 1, 2), (2, 12, 13, 2, 1, 2), (2, 12, 14, 2, 1, 2), (2, 12, 15, 2, 1, 2),
(2, 12, 11, 3, 1, 2), (2, 12, 12, 3, 1, 2), (2, 12, 13, 3, 1, 2), (2, 12, 14, 3, 1, 2), (2, 12, 15, 3, 1, 2),
-- -- -- Aluno 3
(2, 13, 11, 1, 1, 2), (2, 13, 12, 1, 1, 2), (2, 13, 13, 1, 1, 2), (2, 13, 14, 1, 1, 2), (2, 13, 15, 1, 1, 2),
(2, 13, 11, 2, 1, 2), (2, 13, 12, 2, 1, 2), (2, 13, 13, 2, 1, 2), (2, 13, 14, 2, 1, 2), (2, 13, 15, 2, 1, 2),
(2, 13, 11, 3, 1, 2), (2, 13, 12, 3, 1, 2), (2, 13, 13, 3, 1, 2), (2, 13, 14, 3, 1, 2), (2, 13, 15, 3, 1, 2),
-- -- -- Aluno 4
(2, 14, 11, 1, 1, 2), (2, 14, 12, 1, 1, 2), (2, 14, 13, 1, 1, 2), (2, 14, 14, 1, 1, 2), (2, 14, 15, 1, 1, 2),
(2, 14, 11, 2, 1, 2), (2, 14, 12, 2, 1, 2), (2, 14, 13, 2, 1, 2), (2, 14, 14, 2, 1, 2), (2, 14, 15, 2, 1, 2),
(2, 14, 11, 3, 1, 2), (2, 14, 12, 3, 1, 2), (2, 14, 13, 3, 1, 2), (2, 14, 14, 3, 1, 2), (2, 14, 15, 3, 1, 2),
-- -- -- Aluno 5
(2, 15, 11, 1, 1, 2), (2, 15, 12, 1, 1, 2), (2, 15, 13, 1, 1, 2), (2, 15, 14, 1, 1, 2), (2, 15, 15, 1, 1, 2),
(2, 15, 11, 2, 1, 2), (2, 15, 12, 2, 1, 2), (2, 15, 13, 2, 1, 2), (2, 15, 14, 2, 1, 2), (2, 15, 15, 2, 1, 2),
(2, 15, 11, 3, 1, 2), (2, 15, 12, 3, 1, 2), (2, 15, 13, 3, 1, 2), (2, 15, 14, 3, 1, 2), (2, 15, 15, 3, 1, 2),
-- Sprint 3
-- -- Equipe 1
-- -- -- Aluno 1
(3, 2, 2, 1, 1, 3), (3, 2, 3, 1, 1, 3), (3, 2, 4, 1, 1, 3), (3, 2, 5, 1, 1, 3), (3, 2, 6, 1, 1, 3), (3, 2, 7, 1, 1, 3), (3, 2, 8, 1, 1, 3), (3, 2, 9, 1, 1, 3), (3, 2, 10, 1, 1, 3),
(3, 2, 2, 2, 1, 3), (3, 2, 3, 2, 1, 3), (3, 2, 4, 2, 1, 3), (3, 2, 5, 2, 1, 3), (3, 2, 6, 2, 1, 3), (3, 2, 7, 2, 1, 3), (3, 2, 8, 2, 1, 3), (3, 2, 9, 2, 1, 3), (3, 2, 10, 2, 1, 3),
(3, 2, 2, 3, 1, 3), (3, 2, 3, 3, 1, 3), (3, 2, 4, 3, 1, 3), (3, 2, 5, 3, 1, 3), (3, 2, 6, 3, 1, 3), (3, 2, 7, 3, 1, 3), (3, 2, 8, 3, 1, 3), (3, 2, 9, 3, 1, 3), (3, 2, 10, 3, 1, 3),
-- -- Aluno 2
(3, 3, 2, 1, 1, 3), (3, 3, 3, 1, 1, 3), (3, 3, 4, 1, 1, 3), (3, 3, 5, 1, 1, 3), (3, 3, 6, 1, 1, 3), (3, 3, 7, 1, 1, 3), (3, 3, 8, 1, 1, 3), (3, 3, 9, 1, 1, 3), (3, 3, 10, 1, 1, 3),
(3, 3, 2, 2, 1, 3), (3, 3, 3, 2, 1, 3), (3, 3, 4, 2, 1, 3), (3, 3, 5, 2, 1, 3), (3, 3, 6, 2, 1, 3), (3, 3, 7, 2, 1, 3), (3, 3, 8, 2, 1, 3), (3, 3, 9, 2, 1, 3), (3, 3, 10, 2, 1, 3),
(3, 3, 2, 3, 1, 3), (3, 3, 3, 3, 1, 3), (3, 3, 4, 3, 1, 3), (3, 3, 5, 3, 1, 3), (3, 3, 6, 3, 1, 3), (3, 3, 7, 3, 1, 3), (3, 3, 8, 3, 1, 3), (3, 3, 9, 3, 1, 3), (3, 3, 10, 3, 1, 3),
-- -- -- Aluno 3
(3, 4, 2, 1, 1, 3), (3, 4, 3, 1, 1, 3), (3, 4, 4, 1, 1, 3), (3, 4, 5, 1, 1, 3), (3, 4, 6, 1, 1, 3), (3, 4, 7, 1, 1, 3), (3, 4, 8, 1, 1, 3), (3, 4, 9, 1, 1, 3), (3, 4, 10, 1, 1, 3),
(3, 4, 2, 2, 1, 3), (3, 4, 3, 2, 1, 3), (3, 4, 4, 2, 1, 3), (3, 4, 5, 2, 1, 3), (3, 4, 6, 2, 1, 3), (3, 4, 7, 2, 1, 3), (3, 4, 8, 2, 1, 3), (3, 4, 9, 2, 1, 3), (3, 4, 10, 2, 1, 3),
(3, 4, 2, 3, 1, 3), (3, 4, 3, 3, 1, 3), (3, 4, 4, 3, 1, 3), (3, 4, 5, 3, 1, 3), (3, 4, 6, 3, 1, 3), (3, 4, 7, 3, 1, 3), (3, 4, 8, 3, 1, 3), (3, 4, 9, 3, 1, 3), (3, 4, 10, 3, 1, 3),
-- -- -- Aluno 4
(3, 5, 2, 1, 1, 3), (3, 5, 3, 1, 1, 3), (3, 5, 4, 1, 1, 3), (3, 5, 5, 1, 1, 3), (3, 5, 6, 1, 1, 3), (3, 5, 7, 1, 1, 3), (3, 5, 8, 1, 1, 3), (3, 5, 9, 1, 1, 3), (3, 5, 10, 1, 1, 3),
(3, 5, 2, 2, 1, 3), (3, 5, 3, 2, 1, 3), (3, 5, 4, 2, 1, 3), (3, 5, 5, 2, 1, 3), (3, 5, 6, 2, 1, 3), (3, 5, 7, 2, 1, 3), (3, 5, 8, 2, 1, 3), (3, 5, 9, 2, 1, 3), (3, 5, 10, 2, 1, 3),
(3, 5, 2, 3, 1, 3), (3, 5, 3, 3, 1, 3), (3, 5, 4, 3, 1, 3), (3, 5, 5, 3, 1, 3), (3, 5, 6, 3, 1, 3), (3, 5, 7, 3, 1, 3), (3, 5, 8, 3, 1, 3), (3, 5, 9, 3, 1, 3), (3, 5, 10, 3, 1, 3),
-- -- -- Aluno 5
(3, 6, 2, 1, 1, 3), (3, 6, 3, 1, 1, 3), (3, 6, 4, 1, 1, 3), (3, 6, 5, 1, 1, 3), (3, 6, 6, 1, 1, 3), (3, 6, 7, 1, 1, 3), (3, 6, 8, 1, 1, 3), (3, 6, 9, 1, 1, 3), (3, 6, 10, 1, 1, 3),
(3, 6, 2, 2, 1, 3), (3, 6, 3, 2, 1, 3), (3, 6, 4, 2, 1, 3), (3, 6, 5, 2, 1, 3), (3, 6, 6, 2, 1, 3), (3, 6, 7, 2, 1, 3), (3, 6, 8, 2, 1, 3), (3, 6, 9, 2, 1, 3), (3, 6, 10, 2, 1, 3),
(3, 6, 2, 3, 1, 3), (3, 6, 3, 3, 1, 3), (3, 6, 4, 3, 1, 3), (3, 6, 5, 3, 1, 3), (3, 6, 6, 3, 1, 3), (3, 6, 7, 3, 1, 3), (3, 6, 8, 3, 1, 3), (3, 6, 9, 3, 1, 3), (3, 6, 10, 3, 1, 3),
-- -- -- Aluno 6
(3, 7, 2, 1, 1, 3), (3, 7, 3, 1, 1, 3), (3, 7, 4, 1, 1, 3), (3, 7, 5, 1, 1, 3), (3, 7, 6, 1, 1, 3), (3, 7, 7, 1, 1, 3), (3, 7, 8, 1, 1, 3), (3, 7, 9, 1, 1, 3), (3, 7, 10, 1, 1, 3),
(3, 7, 2, 2, 1, 3), (3, 7, 3, 2, 1, 3), (3, 7, 4, 2, 1, 3), (3, 7, 5, 2, 1, 3), (3, 7, 6, 2, 1, 3), (3, 7, 7, 2, 1, 3), (3, 7, 8, 2, 1, 3), (3, 7, 9, 2, 1, 3), (3, 7, 10, 2, 1, 3),
(3, 7, 2, 3, 1, 3), (3, 7, 3, 3, 1, 3), (3, 7, 4, 3, 1, 3), (3, 7, 5, 3, 1, 3), (3, 7, 6, 3, 1, 3), (3, 7, 7, 3, 1, 3), (3, 7, 8, 3, 1, 3), (3, 7, 9, 3, 1, 3), (3, 7, 10, 3, 1, 3),
-- -- -- Aluno 7
(3, 8, 2, 1, 1, 3), (3, 8, 3, 1, 1, 3), (3, 8, 4, 1, 1, 3), (3, 8, 5, 1, 1, 3), (3, 8, 6, 1, 1, 3), (3, 8, 7, 1, 1, 3), (3, 8, 8, 1, 1, 3), (3, 8, 9, 1, 1, 3), (3, 8, 10, 1, 1, 3),
(3, 8, 2, 2, 1, 3), (3, 8, 3, 2, 1, 3), (3, 8, 4, 2, 1, 3), (3, 8, 5, 2, 1, 3), (3, 8, 6, 2, 1, 3), (3, 8, 7, 2, 1, 3), (3, 8, 8, 2, 1, 3), (3, 8, 9, 2, 1, 3), (3, 8, 10, 2, 1, 3),
(3, 8, 2, 3, 1, 3), (3, 8, 3, 3, 1, 3), (3, 8, 4, 3, 1, 3), (3, 8, 5, 3, 1, 3), (3, 8, 6, 3, 1, 3), (3, 8, 7, 3, 1, 3), (3, 8, 8, 3, 1, 3), (3, 8, 9, 3, 1, 3), (3, 8, 10, 3, 1, 3),
-- -- -- Aluno 8
(3, 9, 2, 1, 1, 3), (3, 9, 3, 1, 1, 3), (3, 9, 4, 1, 1, 3), (3, 9, 5, 1, 1, 3), (3, 9, 6, 1, 1, 3), (3, 9, 7, 1, 1, 3), (3, 9, 8, 1, 1, 3), (3, 9, 9, 1, 1, 3), (3, 9, 10, 1, 1, 3),
(3, 9, 2, 2, 1, 3), (3, 9, 3, 2, 1, 3), (3, 9, 4, 2, 1, 3), (3, 9, 5, 2, 1, 3), (3, 9, 6, 2, 1, 3), (3, 9, 7, 2, 1, 3), (3, 9, 8, 2, 1, 3), (3, 9, 9, 2, 1, 3), (3, 9, 10, 2, 1, 3),
(3, 9, 2, 3, 1, 3), (3, 9, 3, 3, 1, 3), (3, 9, 4, 3, 1, 3), (3, 9, 5, 3, 1, 3), (3, 9, 6, 3, 1, 3), (3, 9, 7, 3, 1, 3), (3, 9, 8, 3, 1, 3), (3, 9, 9, 3, 1, 3), (3, 9, 10, 3, 1, 3),
-- -- -- Aluno 9
(3, 10, 2, 1, 1, 3), (3, 10, 3, 1, 1, 3), (3, 10, 4, 1, 1, 3), (3, 10, 5, 1, 1, 3), (3, 10, 6, 1, 1, 3), (3, 10, 7, 1, 1, 3), (3, 10, 8, 1, 1, 3), (3, 10, 9, 1, 1, 3), (3, 10, 10, 1, 1, 3),
(3, 10, 2, 2, 1, 3), (3, 10, 3, 2, 1, 3), (3, 10, 4, 2, 1, 3), (3, 10, 5, 2, 1, 3), (3, 10, 6, 2, 1, 3), (3, 10, 7, 2, 1, 3), (3, 10, 8, 2, 1, 3), (3, 10, 9, 2, 1, 3), (3, 10, 10, 2, 1, 3),
(3, 10, 2, 3, 1, 3), (3, 10, 3, 3, 1, 3), (3, 10, 4, 3, 1, 3), (3, 10, 5, 3, 1, 3), (3, 10, 6, 3, 1, 3), (3, 10, 7, 3, 1, 3), (3, 10, 8, 3, 1, 3), (3, 10, 9, 3, 1, 3), (3, 10, 10, 3, 1, 3),
-- -- Equipe 2
-- -- -- Aluno 1
(1, 11, 11, 1, 1, 3), (1, 11, 12, 1, 1, 3), (1, 11, 13, 1, 1, 3), (1, 11, 14, 1, 1, 3), (1, 11, 15, 1, 1, 3),
(1, 11, 11, 2, 1, 3), (1, 11, 12, 2, 1, 3), (1, 11, 13, 2, 1, 3), (1, 11, 14, 2, 1, 3), (1, 11, 15, 2, 1, 3),
(1, 11, 11, 3, 1, 3), (1, 11, 12, 3, 1, 3), (1, 11, 13, 3, 1, 3), (1, 11, 14, 3, 1, 3), (1, 11, 15, 3, 1, 3),
-- -- -- Aluno 2
(1, 12, 11, 1, 1, 3), (1, 12, 12, 1, 1, 3), (1, 12, 13, 1, 1, 3), (1, 12, 14, 1, 1, 3), (1, 12, 15, 1, 1, 3),
(1, 12, 11, 2, 1, 3), (1, 12, 12, 2, 1, 3), (1, 12, 13, 2, 1, 3), (1, 12, 14, 2, 1, 3), (1, 12, 15, 2, 1, 3),
(1, 12, 11, 3, 1, 3), (1, 12, 12, 3, 1, 3), (1, 12, 13, 3, 1, 3), (1, 12, 14, 3, 1, 3), (1, 12, 15, 3, 1, 3),
-- -- -- Aluno 3
(1, 13, 11, 1, 1, 3), (1, 13, 12, 1, 1, 3), (1, 13, 13, 1, 1, 3), (1, 13, 14, 1, 1, 3), (1, 13, 15, 1, 1, 3),
(1, 13, 11, 2, 1, 3), (1, 13, 12, 2, 1, 3), (1, 13, 13, 2, 1, 3), (1, 13, 14, 2, 1, 3), (1, 13, 15, 2, 1, 3),
(1, 13, 11, 3, 1, 3), (1, 13, 12, 3, 1, 3), (1, 13, 13, 3, 1, 3), (1, 13, 14, 3, 1, 3), (1, 13, 15, 3, 1, 3),
-- -- -- Aluno 4
(1, 14, 11, 1, 1, 3), (1, 14, 12, 1, 1, 3), (1, 14, 13, 1, 1, 3), (1, 14, 14, 1, 1, 3), (1, 14, 15, 1, 1, 3),
(1, 14, 11, 2, 1, 3), (1, 14, 12, 2, 1, 3), (1, 14, 13, 2, 1, 3), (1, 14, 14, 2, 1, 3), (1, 14, 15, 2, 1, 3),
(1, 14, 11, 3, 1, 3), (1, 14, 12, 3, 1, 3), (1, 14, 13, 3, 1, 3), (1, 14, 14, 3, 1, 3), (1, 14, 15, 3, 1, 3),
-- -- -- Aluno 5
(1, 15, 11, 1, 1, 3), (1, 15, 12, 1, 1, 3), (1, 15, 13, 1, 1, 3), (1, 15, 14, 1, 1, 3), (2, 15, 15, 1, 1, 3),
(1, 15, 11, 2, 1, 3), (1, 15, 12, 2, 1, 3), (1, 15, 13, 2, 1, 3), (1, 15, 14, 2, 1, 3), (1, 15, 15, 2, 1, 3),
(1, 15, 11, 3, 1, 3), (1, 15, 12, 3, 1, 3), (1, 15, 13, 3, 1, 3), (1, 15, 14, 3, 1, 3), (1, 15, 15, 3, 1, 3);
