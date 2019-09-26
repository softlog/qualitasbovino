package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "criador")
public class Criador {

    //Montar o id convertendo em números o código alfanumerico do criador.
    //Cada Letra de A a Z recebe um valor numerico començando por 11.
    //assim CA:  C = 13 e A = 11, codigo fica 1311
    @Id(autoincrement = false)
    Long Id;

    @Property(nameInDb = "codigo")
    String codigo;

    @Property(nameInDb = "descricao")
    String descricao;

    @Property(nameInDb = "fazenda")
    String fazenda;

    @Property(nameInDb = "municipio")
    String muncipio;

    @Property(nameInDb = "uf")
    String uf;

    @Property(nameInDb = "pais_sigla")
    String paisSigla;

    @ToMany(referencedJoinProperty = "criadorId")
    List<MTFDados> animais;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 34861300)
    private transient CriadorDao myDao;

    @Generated(hash = 2090216580)
    public Criador(Long Id, String codigo, String descricao, String fazenda,
            String muncipio, String uf, String paisSigla) {
        this.Id = Id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.fazenda = fazenda;
        this.muncipio = muncipio;
        this.uf = uf;
        this.paisSigla = paisSigla;
    }

    @Generated(hash = 701028023)
    public Criador() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFazenda() {
        return this.fazenda;
    }

    public void setFazenda(String fazenda) {
        this.fazenda = fazenda;
    }

    public String getMuncipio() {
        return this.muncipio;
    }

    public void setMuncipio(String muncipio) {
        this.muncipio = muncipio;
    }

    public String getUf() {
        return this.uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getPaisSigla() {
        return this.paisSigla;
    }

    public void setPaisSigla(String paisSigla) {
        this.paisSigla = paisSigla;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 138119763)
    public List<MTFDados> getAnimais() {
        if (animais == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MTFDadosDao targetDao = daoSession.getMTFDadosDao();
            List<MTFDados> animaisNew = targetDao._queryCriador_Animais(Id);
            synchronized (this) {
                if (animais == null) {
                    animais = animaisNew;
                }
            }
        }
        return animais;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1608893488)
    public synchronized void resetAnimais() {
        animais = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1491162952)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCriadorDao() : null;
    }


}
