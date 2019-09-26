package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "medicoes_animal",
        indexes = {
                @Index(value = "animal")
        })
public class MedicoesAnimal {

    @Id(autoincrement = true)
    Long id;

    @Property(nameInDb = "animal")
    Long animal;

    @Property(nameInDb = "medicao_id")
    Long medicaoId;

    @Property(nameInDb = "data_medicao")
    String dataMedicao;

    @Property(nameInDb = "valor")
    Long valor;

    @Property(nameInDb = "descarte")
    boolean descarte;

    @ToOne(joinProperty = "medicaoId")
    Medicao medicoes;

    @ToOne(joinProperty = "animal")
    MTFDados AnimalPrincipal;


/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 810622311)
private transient MedicoesAnimalDao myDao;

@Generated(hash = 591582780)
public MedicoesAnimal(Long id, Long animal, Long medicaoId, String dataMedicao,
        Long valor, boolean descarte) {
    this.id = id;
    this.animal = animal;
    this.medicaoId = medicaoId;
    this.dataMedicao = dataMedicao;
    this.valor = valor;
    this.descarte = descarte;
}

@Generated(hash = 924401202)
public MedicoesAnimal() {
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

public Long getMedicaoId() {
    return this.medicaoId;
}

public void setMedicaoId(Long medicaoId) {
    this.medicaoId = medicaoId;
}

public String getDataMedicao() {
    return this.dataMedicao;
}

public void setDataMedicao(String dataMedicao) {
    this.dataMedicao = dataMedicao;
}

public Long getValor() {
    return this.valor;
}

public void setValor(Long valor) {
    this.valor = valor;

    if (valor == null){
        this.setDescarte(false);
        return ;
    }

    if (this.valor == this.getMedicoes().getDescarte1() && this.getMedicoes().getDescarte1() > 0){
        this.setDescarte(true);
    } else if (this.valor == this.getMedicoes().getDescarte2() && this.getMedicoes().getDescarte2() > 0) {
        this.setDescarte(true);
    } else if (this.valor == this.getMedicoes().getDescarte3() && this.getMedicoes().getDescarte3() > 0) {
        this.setDescarte(true);
    } else if (this.valor == this.getMedicoes().getDescarte4() && this.getMedicoes().getDescarte4() > 0) {
        this.setDescarte(true);
    } else {
        this.setDescarte(false);
    }

}

@Generated(hash = 524413025)
private transient Long medicoes__resolvedKey;

@Generated(hash = 315716074)
private transient Long AnimalPrincipal__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 648576216)
public Medicao getMedicoes() {
    Long __key = this.medicaoId;
    if (medicoes__resolvedKey == null || !medicoes__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        MedicaoDao targetDao = daoSession.getMedicaoDao();
        Medicao medicoesNew = targetDao.load(__key);
        synchronized (this) {
            medicoes = medicoesNew;
            medicoes__resolvedKey = __key;
        }
    }
    return medicoes;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 787335230)
public void setMedicoes(Medicao medicoes) {
    synchronized (this) {
        this.medicoes = medicoes;
        medicaoId = medicoes == null ? null : medicoes.getId();
        medicoes__resolvedKey = medicaoId;
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

public boolean getDescarte() {
    return this.descarte;
}

public void setDescarte(boolean descarte) {
    this.descarte = descarte;
}

/** To-one relationship, resolved on first access. */
@Generated(hash = 1667456754)
public MTFDados getAnimalPrincipal() {
    Long __key = this.animal;
    if (AnimalPrincipal__resolvedKey == null || !AnimalPrincipal__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        MTFDadosDao targetDao = daoSession.getMTFDadosDao();
        MTFDados AnimalPrincipalNew = targetDao.load(__key);
        synchronized (this) {
            AnimalPrincipal = AnimalPrincipalNew;
            AnimalPrincipal__resolvedKey = __key;
        }
    }
    return AnimalPrincipal;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1073253625)
public void setAnimalPrincipal(MTFDados AnimalPrincipal) {
    synchronized (this) {
        this.AnimalPrincipal = AnimalPrincipal;
        animal = AnimalPrincipal == null ? null : AnimalPrincipal.getId();
        AnimalPrincipal__resolvedKey = animal;
    }
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 342577777)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getMedicoesAnimalDao() : null;
}


}
