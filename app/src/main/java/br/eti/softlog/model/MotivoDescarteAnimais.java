package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "motivo_descarte_animais")
public class MotivoDescarteAnimais {

    @Id(autoincrement = true)
    Long Id;

    @Property(nameInDb = "animal_id")
    Long animalId;

    @Property(nameInDb = "motivo_id")
    Long motivoId;

    @ToOne(joinProperty = "motivoId")
    MotivoDescarte motivoDescarte;

    @ToOne(joinProperty = "animalId")
    MTFDados animais;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1079227474)
    private transient MotivoDescarteAnimaisDao myDao;

    @Generated(hash = 359437555)
    private transient Long motivoDescarte__resolvedKey;

    @Generated(hash = 1336101160)
    private transient Long animais__resolvedKey;

    @Generated(hash = 201236561)
    public MotivoDescarteAnimais(Long Id, Long animalId, Long motivoId) {
        this.Id = Id;
        this.animalId = animalId;
        this.motivoId = motivoId;
    }

    @Generated(hash = 427412850)
    public MotivoDescarteAnimais() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getAnimalId() {
        return this.animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public Long getMotivoId() {
        return this.motivoId;
    }

    public void setMotivoId(Long motivoId) {
        this.motivoId = motivoId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 697929880)
    public MotivoDescarte getMotivoDescarte() {
        Long __key = this.motivoId;
        if (motivoDescarte__resolvedKey == null
                || !motivoDescarte__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MotivoDescarteDao targetDao = daoSession.getMotivoDescarteDao();
            MotivoDescarte motivoDescarteNew = targetDao.load(__key);
            synchronized (this) {
                motivoDescarte = motivoDescarteNew;
                motivoDescarte__resolvedKey = __key;
            }
        }
        return motivoDescarte;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 252654213)
    public void setMotivoDescarte(MotivoDescarte motivoDescarte) {
        synchronized (this) {
            this.motivoDescarte = motivoDescarte;
            motivoId = motivoDescarte == null ? null : motivoDescarte.getId();
            motivoDescarte__resolvedKey = motivoId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 197871050)
    public MTFDados getAnimais() {
        Long __key = this.animalId;
        if (animais__resolvedKey == null || !animais__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MTFDadosDao targetDao = daoSession.getMTFDadosDao();
            MTFDados animaisNew = targetDao.load(__key);
            synchronized (this) {
                animais = animaisNew;
                animais__resolvedKey = __key;
            }
        }
        return animais;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1487378979)
    public void setAnimais(MTFDados animais) {
        synchronized (this) {
            this.animais = animais;
            animalId = animais == null ? null : animais.getId();
            animais__resolvedKey = animalId;
        }
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
    @Generated(hash = 1487692966)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMotivoDescarteAnimaisDao() : null;
    }


}
