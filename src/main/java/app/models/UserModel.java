package app.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserModel {
    private SimpleIntegerProperty ra;
    private SimpleStringProperty nome;
    private SimpleStringProperty email;
    private SimpleStringProperty senha;
    private SimpleIntegerProperty equipeId;

    public UserModel(int ra, String nome, String email, String senha, int equipeId) {
        this.ra = new SimpleIntegerProperty(ra);
        this.nome = new SimpleStringProperty(nome);
        this.email = new SimpleStringProperty(email);
        this.senha = new SimpleStringProperty(senha);
        this.equipeId = new SimpleIntegerProperty(equipeId);
    }

    public Integer getRa() {
        return ra.get();
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome = new SimpleStringProperty(nome);
    }

    public String getEmail() {
        return email.get();
    }

    public String getSenha() {
        return senha.get();
    }

    public int getEquipeId() {
        return equipeId.get();
    }
}
