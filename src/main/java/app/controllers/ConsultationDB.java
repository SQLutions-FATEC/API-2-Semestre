package app.controllers;

import java.sql.*;
import java.util.*;

public class ConsultationDB {
    private final Connection conn;

    public ConsultationDB(Connection conn) {
        this.conn = conn;
    }

    public Map<String, Map<String, Double>> obterMediaNotasPorEquipe(String semestreId, String sprintId, String equipeId) throws SQLException {
        String sql = """
    SELECT 
        e.nome AS equipe_nome,
        u.nome AS aluno_nome,
        c.descricao AS criterio_descricao,
        AVG(n.valor) AS media_nota
    FROM 
        nota n
    JOIN usuario u ON n.avaliado = u.ra
    JOIN criterio c ON n.criterio = c.id
    JOIN equipe e ON u.equipe = e.id
    JOIN periodo s ON s.id = ?  -- Tabela onde o semestre é armazenado
    JOIN sprint sp ON sp.id = ? AND n.sprint = sp.id
    WHERE 
        e.id = ?
    GROUP BY 
        e.nome, u.nome, c.descricao
    """;

        Map<String, Map<String, Double>> medias = new HashMap<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, semestreId);
            pstmt.setString(2, sprintId);
            pstmt.setString(3, equipeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String alunoNome = rs.getString("aluno_nome");
                String criterioDescricao = rs.getString("criterio_descricao");
                double mediaNota = rs.getDouble("media_nota");

                medias.computeIfAbsent(alunoNome, k -> new HashMap<>()).put(criterioDescricao, mediaNota);
            }
        }

        return medias;
    }
}
