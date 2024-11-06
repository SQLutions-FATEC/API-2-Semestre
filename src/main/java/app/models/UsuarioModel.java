package app.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UsuarioModel {

    private SimpleIntegerProperty ra;
    private SimpleStringProperty nome;
    private SimpleStringProperty email;
    private SimpleStringProperty senha;
    private SimpleIntegerProperty id_equipe;

    public UsuarioModel(int ra, String nome, String email, String senha, int id_equipe) {
        this.ra = new SimpleIntegerProperty(ra);
        this.nome = new SimpleStringProperty(nome);
        this.email = new SimpleStringProperty(email);
        this.senha = new SimpleStringProperty(senha);
        this.id_equipe = new SimpleIntegerProperty(id_equipe);
    }

    public Integer getRa() {
        return ra.get();
    }

    public void setRa(Integer ra) {
        this.ra = new SimpleIntegerProperty(ra);
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

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public String getSenha() {
        return senha.get();
    }

    public void setSenha(String senha) {
        this.senha = new SimpleStringProperty(senha);
    }

    public Integer getId_equipe() {
        return id_equipe.get();
    }

    public void setId_equipe(Integer id_equipe) {
        this.id_equipe = new SimpleIntegerProperty(id_equipe);
    }

}
