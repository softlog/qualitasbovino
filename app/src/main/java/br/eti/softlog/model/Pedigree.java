package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import br.eti.softlog.Utils.Util;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "pedigree",
        indexes = {
                @Index(value = "animal", unique = true),
                @Index(value = "nome", unique = false),
                @Index(value = "pai", unique=false),
                @Index(value = "mae"),
                @Index(value = "idf")
        })
public class Pedigree {


    @Id(autoincrement = false)
    Long id;

    @Property(nameInDb = "animal")
    Long animal;

    @Property(nameInDb = "pai")
    Long pai;

    @ToOne(joinProperty = "pai")
    Pedigree pedigreePai;

    @Property(nameInDb = "mae")
    Long mae;

    @ToOne(joinProperty = "mae")
    Pedigree pedigreeMae;

    @Property(nameInDb = "nome")
    String nome;

    @Property(nameInDb = "idf")
    String idf;

    @Property(nameInDb = "criador_id")
    Long criadorId;

    @ToOne(joinProperty = "criadorId")
    Criador criador;

    @Property(nameInDb = "safra")
    int safra;

    @ToMany(referencedJoinProperty = "animal")
    @OrderBy("id ASC")
    List<MTFDados> dadosAnimal;

    public String getAliasPedigree(){
        Util util = new Util();
        String alias;
        alias = "IDF " + util.trataIdf(this.getIdf()) + "\n";
        alias = alias + this.getNome();
        return alias;
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1397665833)
    private transient PedigreeDao myDao;

    @Generated(hash = 757874098)
    public Pedigree(Long id, Long animal, Long pai, Long mae, String nome,
            String idf, Long criadorId, int safra) {
        this.id = id;
        this.animal = animal;
        this.pai = pai;
        this.mae = mae;
        this.nome = nome;
        this.idf = idf;
        this.criadorId = criadorId;
        this.safra = safra;
    }

    @Generated(hash = 2067497411)
    public Pedigree() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnimal() {
        return this.animal;
    }

    public void setAnimal(Long animal) {
        this.animal = animal;
    }

    public Long getPai() {
        return this.pai;
    }

    public void setPai(Long pai) {
        this.pai = pai;
    }

    public Long getMae() {
        return this.mae;
    }

    public void setMae(Long mae) {
        this.mae = mae;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdf() {
        return this.idf;
    }

    public void setIdf(String idf) {
        this.idf = idf;
    }

    public Long getCriadorId() {
        return this.criadorId;
    }

    public void setCriadorId(Long criadorId) {
        this.criadorId = criadorId;
    }

    public int getSafra() {
        return this.safra;
    }

    public void setSafra(int safra) {
        this.safra = safra;
    }

    @Generated(hash = 374880495)
    private transient Long pedigreePai__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1039371749)
    public Pedigree getPedigreePai() {
        Long __key = this.pai;
        if (pedigreePai__resolvedKey == null
                || !pedigreePai__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PedigreeDao targetDao = daoSession.getPedigreeDao();
            Pedigree pedigreePaiNew = targetDao.load(__key);
            synchronized (this) {
                pedigreePai = pedigreePaiNew;
                pedigreePai__resolvedKey = __key;
            }
        }
        return pedigreePai;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 457252713)
    public void setPedigreePai(Pedigree pedigreePai) {
        synchronized (this) {
            this.pedigreePai = pedigreePai;
            pai = pedigreePai == null ? null : pedigreePai.getId();
            pedigreePai__resolvedKey = pai;
        }
    }

    @Generated(hash = 585609758)
    private transient Long pedigreeMae__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 175451870)
    public Pedigree getPedigreeMae() {
        Long __key = this.mae;
        if (pedigreeMae__resolvedKey == null
                || !pedigreeMae__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PedigreeDao targetDao = daoSession.getPedigreeDao();
            Pedigree pedigreeMaeNew = targetDao.load(__key);
            synchronized (this) {
                pedigreeMae = pedigreeMaeNew;
                pedigreeMae__resolvedKey = __key;
            }
        }
        return pedigreeMae;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2116563075)
    public void setPedigreeMae(Pedigree pedigreeMae) {
        synchronized (this) {
            this.pedigreeMae = pedigreeMae;
            mae = pedigreeMae == null ? null : pedigreeMae.getId();
            pedigreeMae__resolvedKey = mae;
        }
    }

    @Generated(hash = 783169377)
    private transient Long criador__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1090560941)
    public Criador getCriador() {
        Long __key = this.criadorId;
        if (criador__resolvedKey == null || !criador__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CriadorDao targetDao = daoSession.getCriadorDao();
            Criador criadorNew = targetDao.load(__key);
            synchronized (this) {
                criador = criadorNew;
                criador__resolvedKey = __key;
            }
        }
        return criador;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1902275320)
    public void setCriador(Criador criador) {
        synchronized (this) {
            this.criador = criador;
            criadorId = criador == null ? null : criador.getId();
            criador__resolvedKey = criadorId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 461025908)
    public List<MTFDados> getDadosAnimal() {
        if (dadosAnimal == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MTFDadosDao targetDao = daoSession.getMTFDadosDao();
            List<MTFDados> dadosAnimalNew = targetDao
                    ._queryPedigree_DadosAnimal(id);
            synchronized (this) {
                if (dadosAnimal == null) {
                    dadosAnimal = dadosAnimalNew;
                }
            }
        }
        return dadosAnimal;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 868424886)
    public synchronized void resetDadosAnimal() {
        dadosAnimal = null;
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
    @Generated(hash = 1972949172)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPedigreeDao() : null;
    }


}
