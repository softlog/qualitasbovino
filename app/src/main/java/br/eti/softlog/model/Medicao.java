package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "medicao")
public class Medicao {

    @Id(autoincrement = false)
    Long id;

    @Property(nameInDb = "ordem")
    int ordem;

    @Property(nameInDb = "descricao")
    String descricao;

    @Property(nameInDb = "abrev")
    String abrev;

    @Property(nameInDb = "menor_valor")
    int menorValor;

    @Property(nameInDb = "maior_valor")
    int maiorValor;

    @Property(nameInDb = "sexo")
    String sexo;

    @Property(nameInDb = "restricao")
    int restricao;

    @Property(nameInDb = "descarte1")
    int descarte1;

    @Property(nameInDb = "descarte2")
    int descarte2;

    @Property(nameInDb = "descarte3")
    int descarte3;

    @Property(nameInDb = "descarte4")
    int descarte4;

    @Property(nameInDb = "obrigatorio")
    boolean obrigatorio;

    @Override
    public String toString(){
        return String.format("%1$" + 2 + "s", String.valueOf(getOrdem())).replace(' ', '0')
                + " - " + getDescricao();
    }

    @Generated(hash = 1415096427)
    public Medicao(Long id, int ordem, String descricao, String abrev, int menorValor,
            int maiorValor, String sexo, int restricao, int descarte1, int descarte2,
            int descarte3, int descarte4, boolean obrigatorio) {
        this.id = id;
        this.ordem = ordem;
        this.descricao = descricao;
        this.abrev = abrev;
        this.menorValor = menorValor;
        this.maiorValor = maiorValor;
        this.sexo = sexo;
        this.restricao = restricao;
        this.descarte1 = descarte1;
        this.descarte2 = descarte2;
        this.descarte3 = descarte3;
        this.descarte4 = descarte4;
        this.obrigatorio = obrigatorio;
    }

    @Generated(hash = 90890620)
    public Medicao() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrdem() {
        return this.ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAbrev() {
        return this.abrev;
    }

    public void setAbrev(String abrev) {
        this.abrev = abrev;
    }

    public int getMenorValor() {
        return this.menorValor;
    }

    public void setMenorValor(int menorValor) {
        this.menorValor = menorValor;
    }

    public int getMaiorValor() {
        return this.maiorValor;
    }

    public void setMaiorValor(int maiorValor) {
        this.maiorValor = maiorValor;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getRestricao() {
        return this.restricao;
    }

    public void setRestricao(int restricao) {
        this.restricao = restricao;
    }

    public int getDescarte1() {
        return this.descarte1;
    }

    public void setDescarte1(int descarte1) {
        this.descarte1 = descarte1;
    }

    public int getDescarte2() {
        return this.descarte2;
    }

    public void setDescarte2(int descarte2) {
        this.descarte2 = descarte2;
    }

    public int getDescarte3() {
        return this.descarte3;
    }

    public void setDescarte3(int descarte3) {
        this.descarte3 = descarte3;
    }

    public int getDescarte4() {
        return this.descarte4;
    }

    public void setDescarte4(int descarte4) {
        this.descarte4 = descarte4;
    }

    public boolean getObrigatorio() {
        return this.obrigatorio;
    }

    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }



}
