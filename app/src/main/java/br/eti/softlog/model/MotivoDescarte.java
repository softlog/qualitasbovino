package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "motivo_descarte")
public class MotivoDescarte {

    @Id(autoincrement = false)
    Long id;

    @Property(nameInDb = "descricao")
    String descricao;

    @Property(nameInDb = "abrev")
    String abrev;

    @Generated(hash = 1498596886)
    public MotivoDescarte(Long id, String descricao, String abrev) {
        this.id = id;
        this.descricao = descricao;
        this.abrev = abrev;
    }

    @Generated(hash = 1809213750)
    public MotivoDescarte() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
