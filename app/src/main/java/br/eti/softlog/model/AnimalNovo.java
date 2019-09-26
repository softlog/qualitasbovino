package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "animal_generator")
public class AnimalNovo {

    @Id(autoincrement = true)
    Long id;

    @Property(nameInDb = "idf")
    String idf;

    @Property(nameInDb = "nome")
    String nome;

    @Property(nameInDb = "sexo")
    String sexo;

    @Property(nameInDb = "data_nascimento")
    String dataNascimento;

    @Property(nameInDb = "data_registro")
    String dataRegistro;

    @Property(nameInDb = "criadorId")
    Long criadorId;

    @Property(nameInDb = "proprietarioId")
    Long proprietarioId;

    @Generated(hash = 1746752376)
    public AnimalNovo(Long id, String idf, String nome, String sexo,
            String dataNascimento, String dataRegistro, Long criadorId,
            Long proprietarioId) {
        this.id = id;
        this.idf = idf;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.dataRegistro = dataRegistro;
        this.criadorId = criadorId;
        this.proprietarioId = proprietarioId;
    }

    @Generated(hash = 152699533)
    public AnimalNovo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdf() {
        return this.idf;
    }

    public void setIdf(String idf) {
        this.idf = idf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataRegistro() {
        return this.dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Long getCriadorId() {
        return this.criadorId;
    }

    public void setCriadorId(Long criadorId) {
        this.criadorId = criadorId;
    }

    public Long getProprietarioId() {
        return this.proprietarioId;
    }

    public void setProprietarioId(Long proprietarioId) {
        this.proprietarioId = proprietarioId;
    }


}
