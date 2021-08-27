package br.eti.softlog.model;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import br.eti.softlog.qualitasbovino.AnimalMainActivity;

@Entity(nameInDb = "mtf_dados",
        indexes = {
                @Index(value = "animal", unique = true),
                @Index(value = "pai", unique=false),
                @Index(value = "mae")
        })
public class MTFDados {

    @Id(autoincrement = true)
    Long id;

    @Property(nameInDb = "criador_id")
    Long criadorId;

    @ToOne(joinProperty = "criadorId")
    Criador criador;

    @Property(nameInDb = "proprietario_id")
    Long proprietarioId;

    @ToOne(joinProperty = "proprietarioId")
    Criador proprietario;

    @Property(nameInDb = "animal")
    Long animal;

    @ToOne(joinProperty = "animal")
    Pedigree animalPrincipal;

    @Property(nameInDb = "pai")
    Long pai;

    @ToOne(joinProperty = "pai")
    Pedigree animalPai;

    @Property(nameInDb = "mae")
    Long mae;

    @ToOne(joinProperty = "mae")
    Pedigree animalMae;

    @Property(nameInDb = "data_nasc")
    String dataNasc;

    @Property(nameInDb = "sexo")
    String sexo;

    @Property(nameInDb = "situ_repro")
    String situRepro;

    @Property(nameInDb = "livro")
    String livro;

    @Property(nameInDb = "ra_nasc")
    String raNasc;

    @Property(nameInDb = "r_p_nasc")
    Double rPNasc;

    //Desmame
    @Property(nameInDb = "ra_desm")
    String raDesm;

    @Property(nameInDb = "i_p_desm")
    Double iPDesm;

    @Property(nameInDb = "d_p_desm")
    String dPDesm;

    @Property(nameInDb = "r_p_desm")
    Double rPDesm;

    //365 Dias
    @Property(nameInDb = "ra_365")
    String ra365;

    @Property(nameInDb = "i_p_365")
    Double iP365;

    @Property(nameInDb = "d_p_365")
    String dP365;

    @Property(nameInDb = "r_p_365")
    Double rP365;

    //450 Dias
    @Property(nameInDb = "ra_450")
    String ra450;

    @Property(nameInDb = "i_p_450")
    Double iP450;

    @Property(nameInDb = "d_p_450")
    String dP450;

    @Property(nameInDb = "r_p_450")
    Double rP450;

    //550 Dias
    @Property(nameInDb = "ra_550")
    String ra550;

    @Property(nameInDb = "i_p_550")
    Double iP550;

    @Property(nameInDb = "d_p_550")
    String dP550;

    @Property(nameInDb = "r_p_550")
    Double rP550;

    //Outras Medidas
    @Property(nameInDb = "i_ce")
    Double iCe;

    @Property(nameInDb = "r_ce")
    Double rCe;

    @Property(nameInDb = "a_ce")
    Double aCe;

    @Property(nameInDb = "i_mus")
    Double iMus;

    @Property(nameInDb = "r_mus")
    Double rMus;

    @Property(nameInDb = "avaliado")
    Boolean avaliado;

    @Property(nameInDb = "data_avaliacao")
    String dataAvaliacao;

    @Property(nameInDb = "importado")
    int importado;

    @Property(nameInDb = "data_importacao")
    String dataImportacao;

    @Property(nameInDb = "alterado")
    int alterado;

    @Property(nameInDb = "idf")
    String idf;

    @Property(nameInDb = "observacao")
    String observacao;

    @ToMany(referencedJoinProperty = "animal")
    List<MedicoesAnimal> medicoesAnimals;

    @Property(nameInDb = "idf2")
    String idf2;

    @Property(nameInDb = "pref_idf")
    String prefixIdf;

    @Property(nameInDb = "codigo_idf")
    Long codigoIdf;

    //Used in operation Certificacao
    @Property(nameInDb = "dep_nasc")
    Double depNasc;

    @Property(nameInDb = "perc_nasc")
    String percNasc;

    @Property(nameInDb = "dep_desm")
    Double depDesm;

    @Property(nameInDb = "perc_desm")
    Double percDesm;

    @Property(nameInDb = "dep_gpd")
    Double depGPD;

    @Property(nameInDb = "perc_gpd")
    Double percGPD;

    @Property(nameInDb = "p_gpd")
    Double pGPD;


    @Property(nameInDb = "dep_sob")
    Double depSob;

    @Property(nameInDb = "perc_sob")
    Double percSob;

    @Property(nameInDb = "p_sob")
    Double pSob;

    @Property(nameInDb = "dep_ce")
    Double depCE;

    @Property(nameInDb = "perc_ce")
    Double percCE;

    @Property(nameInDb = "rc_ce")
    Double rcCE;

    @Property(nameInDb = "ce")
    Double ce;

    @Property(nameInDb = "dep_musc")
    Double dep_musc;

    @Property(nameInDb = "perc_musc")
    Double perc_musc;

    @Property(nameInDb = "musc")
    Double musc;

    @Property(nameInDb = "ind_qlt")
    Double indQlt;

    @Property(nameInDb = "perc_qlt")
    Double percQlt;

    @Property(nameInDb = "rank_qlt")
    Double rank_qlt;

    @Property(nameInDb = "marcacao")
    Long marcacao;

    @Property(nameInDb = "classificacao")
    Long classificacao;

    @Property(nameInDb = "classificacao_fp")
    String classificacaoFP;

    @Property(nameInDb = "mocho")
    Long mocho;

    @Property(nameInDb = "p_marcacao")
    Double p_marcacao;

    @Property(nameInDb = "ce_marcacao")
    Double ce_marcacao;

    @Property(nameInDb = "mot_desc_id")
    Long motDescId;

    @ToOne(joinProperty = "motDescId")
    MotivoDescarte motivoDescarte;

    @Property(nameInDb = "ceip")
    String ceip;

    @Property(nameInDb = "lote")
    String lote;

    @Property(nameInDb = "idf_alterado")
    int idfAlterado;

    @Property(nameInDb = "ordem_avaliacao")
    int ordem_avaliacao;

    @ToMany(referencedJoinProperty = "animalId")
    List<MotivoDescarteAnimais> motivoDescarteAnimais;

    @Property(nameInDb = "id_mae")
    int idMae;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1637768460)
    private transient MTFDadosDao myDao;

    @Generated(hash = 625428377)
    public MTFDados(Long id, Long criadorId, Long proprietarioId, Long animal, Long pai, Long mae,
            String dataNasc, String sexo, String situRepro, String livro, String raNasc, Double rPNasc,
            String raDesm, Double iPDesm, String dPDesm, Double rPDesm, String ra365, Double iP365, String dP365,
            Double rP365, String ra450, Double iP450, String dP450, Double rP450, String ra550, Double iP550,
            String dP550, Double rP550, Double iCe, Double rCe, Double aCe, Double iMus, Double rMus,
            Boolean avaliado, String dataAvaliacao, int importado, String dataImportacao, int alterado,
            String idf, String observacao, String idf2, String prefixIdf, Long codigoIdf, Double depNasc,
            String percNasc, Double depDesm, Double percDesm, Double depGPD, Double percGPD, Double pGPD,
            Double depSob, Double percSob, Double pSob, Double depCE, Double percCE, Double rcCE, Double ce,
            Double dep_musc, Double perc_musc, Double musc, Double indQlt, Double percQlt, Double rank_qlt,
            Long marcacao, Long classificacao, String classificacaoFP, Long mocho, Double p_marcacao,
            Double ce_marcacao, Long motDescId, String ceip, String lote, int idfAlterado, int ordem_avaliacao,
            int idMae) {
        this.id = id;
        this.criadorId = criadorId;
        this.proprietarioId = proprietarioId;
        this.animal = animal;
        this.pai = pai;
        this.mae = mae;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
        this.situRepro = situRepro;
        this.livro = livro;
        this.raNasc = raNasc;
        this.rPNasc = rPNasc;
        this.raDesm = raDesm;
        this.iPDesm = iPDesm;
        this.dPDesm = dPDesm;
        this.rPDesm = rPDesm;
        this.ra365 = ra365;
        this.iP365 = iP365;
        this.dP365 = dP365;
        this.rP365 = rP365;
        this.ra450 = ra450;
        this.iP450 = iP450;
        this.dP450 = dP450;
        this.rP450 = rP450;
        this.ra550 = ra550;
        this.iP550 = iP550;
        this.dP550 = dP550;
        this.rP550 = rP550;
        this.iCe = iCe;
        this.rCe = rCe;
        this.aCe = aCe;
        this.iMus = iMus;
        this.rMus = rMus;
        this.avaliado = avaliado;
        this.dataAvaliacao = dataAvaliacao;
        this.importado = importado;
        this.dataImportacao = dataImportacao;
        this.alterado = alterado;
        this.idf = idf;
        this.observacao = observacao;
        this.idf2 = idf2;
        this.prefixIdf = prefixIdf;
        this.codigoIdf = codigoIdf;
        this.depNasc = depNasc;
        this.percNasc = percNasc;
        this.depDesm = depDesm;
        this.percDesm = percDesm;
        this.depGPD = depGPD;
        this.percGPD = percGPD;
        this.pGPD = pGPD;
        this.depSob = depSob;
        this.percSob = percSob;
        this.pSob = pSob;
        this.depCE = depCE;
        this.percCE = percCE;
        this.rcCE = rcCE;
        this.ce = ce;
        this.dep_musc = dep_musc;
        this.perc_musc = perc_musc;
        this.musc = musc;
        this.indQlt = indQlt;
        this.percQlt = percQlt;
        this.rank_qlt = rank_qlt;
        this.marcacao = marcacao;
        this.classificacao = classificacao;
        this.classificacaoFP = classificacaoFP;
        this.mocho = mocho;
        this.p_marcacao = p_marcacao;
        this.ce_marcacao = ce_marcacao;
        this.motDescId = motDescId;
        this.ceip = ceip;
        this.lote = lote;
        this.idfAlterado = idfAlterado;
        this.ordem_avaliacao = ordem_avaliacao;
        this.idMae = idMae;
    }

    @Generated(hash = 387116617)
    public MTFDados() {
    }



    @Generated(hash = 783169377)
    private transient Long criador__resolvedKey;

    @Generated(hash = 1507146304)
    private transient Long proprietario__resolvedKey;

    @Generated(hash = 1028869509)
    private transient Long animalPrincipal__resolvedKey;

    @Generated(hash = 1109399213)
    private transient Long animalPai__resolvedKey;

    @Generated(hash = 159581047)
    private transient Long animalMae__resolvedKey;

    @Generated(hash = 359437555)
    private transient Long motivoDescarte__resolvedKey;

    public Double getIdade(){

        Double idade;
        try {
            java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");
            Date dataNascimento = formato.parse(getDataNasc());
            Date dataAtual = new Date();

            Long diff = dataAtual.getTime() - dataNascimento.getTime();

            idade = (Double.valueOf(diff) / 1000 / 60 / 60 / 24)/30.4;

        } catch (ParseException e) {
           idade = 0.0;
        }

        return idade;
    }

    public String getAnimalTitle(){
        String title;
        title = String.valueOf(this.animal) ;
        if (this.animalPrincipal.getNome() != null && (!this.animalPrincipal.getNome().trim().equals(""))){
            title = title +  " - " + this.animalPrincipal.getNome();
        }
        return title;
    }


    public String getSexoDesc(){
        if (this.getSexo().equals("M")){
            return "MACHO";
        } else {
            return "FÊMEA";
        }
    }

    public String getAvaliadoDesc(){
        if (this.getAvaliado())
            return "SIM";
        else
            return "NÃO";

    }



    public boolean validDescarte(){

        boolean descarte;
        descarte = false;

        for (int i=0;i<this.getMedicoesAnimals().size()-1;i++){
            if (this.getMedicoesAnimals().get(i).getMedicaoId() == 141529)
                continue;

            if (this.getMedicoesAnimals().get(i).getDescarte()) {
                descarte = true;
                break;
            }
        }

        for (int i=0;i<this.getMedicoesAnimals().size();i++){
            if (this.getMedicoesAnimals().get(i).getMedicaoId() == 141529) {
                if (descarte){
                    this.getMedicoesAnimals().get(i).setValor(Long.valueOf(1));
                    Date date = new Date();
                } else {
                    this.getMedicoesAnimals().get(i).setValor(Long.valueOf(0));
                }
            }
        }

        return descarte;
    }


    public boolean isDescarte(){

        boolean descarte;
        descarte = false;

        for (int i=0; i<this.getMedicoesAnimals().size()-1; i++){
            if (this.getMedicoesAnimals().get(i).getDescarte()){
                descarte = true;
                break;
            }
        }
        return descarte;
    }

    public boolean isVenda(){

        boolean venda;
        venda = false;

        for (int i=this.getMedicoesAnimals().size()-1;i>=0; i--){
            if (this.getMedicoesAnimals().get(i).getMedicaoId() == 321524){
                if (this.getMedicoesAnimals().get(i).getValor() == Long.valueOf(1)){
                    venda = true;
                }
                else {
                    venda = false;
                }
                break;
            }
        }
        return venda;
    }


    public boolean isOnlyVenda(){

        boolean soVenda;
        boolean venda;
        int qtAvaliado;

        venda = false;
        qtAvaliado = 0;

        for (int i = 0; i < this.getMedicoesAnimals().size(); i++) {
            if (!(this.getMedicoesAnimals().get(i).getValor()== null)){
                qtAvaliado++;
                if (this.getMedicoesAnimals().get(i).getMedicaoId() == 321524)
                    venda = true;
            }
        }

        if (venda && qtAvaliado == 2){
            return true;
        } else {
            return false;
        }
    }




    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDataNasc() {
        return this.dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSituRepro() {
        return this.situRepro;
    }

    public void setSituRepro(String situRepro) {
        this.situRepro = situRepro;
    }

    public String getLivro() {
        return this.livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getRaNasc() {
        return this.raNasc;
    }

    public void setRaNasc(String raNasc) {
        this.raNasc = raNasc;
    }

    public Double getRPNasc() {
        return this.rPNasc;
    }

    public void setRPNasc(Double rPNasc) {
        this.rPNasc = rPNasc;
    }

    public String getRaDesm() {
        return this.raDesm;
    }

    public void setRaDesm(String raDesm) {
        this.raDesm = raDesm;
    }

    public Double getIPDesm() {
        return this.iPDesm;
    }

    public void setIPDesm(Double iPDesm) {
        this.iPDesm = iPDesm;
    }

    public String getDPDesm() {
        return this.dPDesm;
    }

    public void setDPDesm(String dPDesm) {
        this.dPDesm = dPDesm;
    }

    public Double getRPDesm() {
        return this.rPDesm;
    }

    public void setRPDesm(Double rPDesm) {
        this.rPDesm = rPDesm;
    }

    public String getRa365() {
        return this.ra365;
    }

    public void setRa365(String ra365) {
        this.ra365 = ra365;
    }

    public Double getIP365() {
        return this.iP365;
    }

    public void setIP365(Double iP365) {
        this.iP365 = iP365;
    }

    public String getDP365() {
        return this.dP365;
    }

    public void setDP365(String dP365) {
        this.dP365 = dP365;
    }

    public Double getRP365() {
        return this.rP365;
    }

    public void setRP365(Double rP365) {
        this.rP365 = rP365;
    }

    public String getRa450() {
        return this.ra450;
    }

    public void setRa450(String ra450) {
        this.ra450 = ra450;
    }

    public Double getIP450() {
        return this.iP450;
    }

    public void setIP450(Double iP450) {
        this.iP450 = iP450;
    }

    public String getDP450() {
        return this.dP450;
    }

    public void setDP450(String dP450) {
        this.dP450 = dP450;
    }

    public Double getRP450() {
        return this.rP450;
    }

    public void setRP450(Double rP450) {
        this.rP450 = rP450;
    }

    public String getRa550() {
        return this.ra550;
    }

    public void setRa550(String ra550) {
        this.ra550 = ra550;
    }

    public Double getIP550() {
        return this.iP550;
    }

    public void setIP550(Double iP550) {
        this.iP550 = iP550;
    }

    public String getDP550() {
        return this.dP550;
    }

    public void setDP550(String dP550) {
        this.dP550 = dP550;
    }

    public Double getRP550() {
        return this.rP550;
    }

    public void setRP550(Double rP550) {
        this.rP550 = rP550;
    }

    public Double getICe() {
        return this.iCe;
    }

    public void setICe(Double iCe) {
        this.iCe = iCe;
    }

    public Double getRCe() {
        return this.rCe;
    }

    public void setRCe(Double rCe) {
        this.rCe = rCe;
    }

    public Double getACe() {
        return this.aCe;
    }

    public void setACe(Double aCe) {
        this.aCe = aCe;
    }

    public Double getIMus() {
        return this.iMus;
    }

    public void setIMus(Double iMus) {
        this.iMus = iMus;
    }

    public Double getRMus() {
        return this.rMus;
    }

    public void setRMus(Double rMus) {
        this.rMus = rMus;
    }

    public Boolean getAvaliado() {
        return this.avaliado;
    }

    public void setAvaliado(Boolean avaliado) {
        this.avaliado = avaliado;
    }

    public String getDataAvaliacao() {
        return this.dataAvaliacao;
    }

    public void setDataAvaliacao(String dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public int getImportado() {
        return this.importado;
    }

    public void setImportado(int importado) {
        this.importado = importado;
    }

    public String getDataImportacao() {
        return this.dataImportacao;
    }

    public void setDataImportacao(String dataImportacao) {
        this.dataImportacao = dataImportacao;
    }

    public int getAlterado() {
        return this.alterado;
    }

    public void setAlterado(int alterado) {
        this.alterado = alterado;
    }

    public String getIdf() {
        return this.idf;
    }

    public void setIdf(String idf) {
        this.idf = idf;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getIdf2() {
        return this.idf2;
    }

    public void setIdf2(String idf2) {
        this.idf2 = idf2;
    }

    public String getPrefixIdf() {
        return this.prefixIdf;
    }

    public void setPrefixIdf(String prefixIdf) {
        this.prefixIdf = prefixIdf;
    }

    public Long getCodigoIdf() {
        return this.codigoIdf;
    }

    public void setCodigoIdf(Long codigoIdf) {
        this.codigoIdf = codigoIdf;
    }

    public Double getDepNasc() {
        return this.depNasc;
    }

    public void setDepNasc(Double depNasc) {
        this.depNasc = depNasc;
    }

    public String getPercNasc() {
        return this.percNasc;
    }

    public void setPercNasc(String percNasc) {
        this.percNasc = percNasc;
    }

    public Double getDepDesm() {
        return this.depDesm;
    }

    public void setDepDesm(Double depDesm) {
        this.depDesm = depDesm;
    }

    public Double getPercDesm() {
        return this.percDesm;
    }

    public void setPercDesm(Double percDesm) {
        this.percDesm = percDesm;
    }

    public Double getDepGPD() {
        return this.depGPD;
    }

    public void setDepGPD(Double depGPD) {
        this.depGPD = depGPD;
    }

    public Double getPercGPD() {
        return this.percGPD;
    }

    public void setPercGPD(Double percGPD) {
        this.percGPD = percGPD;
    }

    public Double getPGPD() {
        return this.pGPD;
    }

    public void setPGPD(Double pGPD) {
        this.pGPD = pGPD;
    }

    public Double getDepSob() {
        return this.depSob;
    }

    public void setDepSob(Double depSob) {
        this.depSob = depSob;
    }

    public Double getPercSob() {
        return this.percSob;
    }

    public void setPercSob(Double percSob) {
        this.percSob = percSob;
    }

    public Double getPSob() {
        return this.pSob;
    }

    public void setPSob(Double pSob) {
        this.pSob = pSob;
    }

    public Double getDepCE() {
        return this.depCE;
    }

    public void setDepCE(Double depCE) {
        this.depCE = depCE;
    }

    public Double getPercCE() {
        return this.percCE;
    }

    public void setPercCE(Double percCE) {
        this.percCE = percCE;
    }

    public Double getRcCE() {
        return this.rcCE;
    }

    public void setRcCE(Double rcCE) {
        this.rcCE = rcCE;
    }

    public Double getCe() {
        return this.ce;
    }

    public void setCe(Double ce) {
        this.ce = ce;
    }

    public Double getDep_musc() {
        return this.dep_musc;
    }

    public void setDep_musc(Double dep_musc) {
        this.dep_musc = dep_musc;
    }

    public Double getPerc_musc() {
        return this.perc_musc;
    }

    public void setPerc_musc(Double perc_musc) {
        this.perc_musc = perc_musc;
    }

    public Double getMusc() {
        return this.musc;
    }

    public void setMusc(Double musc) {
        this.musc = musc;
    }

    public Double getIndQlt() {
        return this.indQlt;
    }

    public void setIndQlt(Double indQlt) {
        this.indQlt = indQlt;
    }

    public Double getPercQlt() {
        return this.percQlt;
    }

    public void setPercQlt(Double percQlt) {
        this.percQlt = percQlt;
    }

    public Double getRank_qlt() {
        return this.rank_qlt;
    }

    public void setRank_qlt(Double rank_qlt) {
        this.rank_qlt = rank_qlt;
    }

    public Long getMarcacao() {
        return this.marcacao;
    }

    public void setMarcacao(Long marcacao) {
        this.marcacao = marcacao;
    }

    public Long getClassificacao() {
        return this.classificacao;
    }

    public void setClassificacao(Long classificacao) {
        this.classificacao = classificacao;
    }

    public Long getMocho() {
        return this.mocho;
    }

    public void setMocho(Long mocho) {
        this.mocho = mocho;
    }

    public Double getP_marcacao() {
        return this.p_marcacao;
    }

    public void setP_marcacao(Double p_marcacao) {
        this.p_marcacao = p_marcacao;
    }

    public Double getCe_marcacao() {
        return this.ce_marcacao;
    }

    public void setCe_marcacao(Double ce_marcacao) {
        this.ce_marcacao = ce_marcacao;
    }

    public Long getMotDescId() {
        return this.motDescId;
    }

    public void setMotDescId(Long motDescId) {
        this.motDescId = motDescId;
    }

    public String getCeip() {
        return this.ceip;
    }

    public void setCeip(String ceip) {
        this.ceip = ceip;
    }

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

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1677669022)
    public Criador getProprietario() {
        Long __key = this.proprietarioId;
        if (proprietario__resolvedKey == null || !proprietario__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CriadorDao targetDao = daoSession.getCriadorDao();
            Criador proprietarioNew = targetDao.load(__key);
            synchronized (this) {
                proprietario = proprietarioNew;
                proprietario__resolvedKey = __key;
            }
        }
        return proprietario;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 359757895)
    public void setProprietario(Criador proprietario) {
        synchronized (this) {
            this.proprietario = proprietario;
            proprietarioId = proprietario == null ? null : proprietario.getId();
            proprietario__resolvedKey = proprietarioId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1711653041)
    public Pedigree getAnimalPrincipal() {
        Long __key = this.animal;
        if (animalPrincipal__resolvedKey == null || !animalPrincipal__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PedigreeDao targetDao = daoSession.getPedigreeDao();
            Pedigree animalPrincipalNew = targetDao.load(__key);
            synchronized (this) {
                animalPrincipal = animalPrincipalNew;
                animalPrincipal__resolvedKey = __key;
            }
        }
        return animalPrincipal;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 136329033)
    public void setAnimalPrincipal(Pedigree animalPrincipal) {
        synchronized (this) {
            this.animalPrincipal = animalPrincipal;
            animal = animalPrincipal == null ? null : animalPrincipal.getId();
            animalPrincipal__resolvedKey = animal;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 848672825)
    public Pedigree getAnimalPai() {
        Long __key = this.pai;
        if (animalPai__resolvedKey == null || !animalPai__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PedigreeDao targetDao = daoSession.getPedigreeDao();
            Pedigree animalPaiNew = targetDao.load(__key);
            synchronized (this) {
                animalPai = animalPaiNew;
                animalPai__resolvedKey = __key;
            }
        }
        return animalPai;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 183746985)
    public void setAnimalPai(Pedigree animalPai) {
        synchronized (this) {
            this.animalPai = animalPai;
            pai = animalPai == null ? null : animalPai.getId();
            animalPai__resolvedKey = pai;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1104034931)
    public Pedigree getAnimalMae() {
        Long __key = this.mae;
        if (animalMae__resolvedKey == null || !animalMae__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PedigreeDao targetDao = daoSession.getPedigreeDao();
            Pedigree animalMaeNew = targetDao.load(__key);
            synchronized (this) {
                animalMae = animalMaeNew;
                animalMae__resolvedKey = __key;
            }
        }
        return animalMae;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1256541892)
    public void setAnimalMae(Pedigree animalMae) {
        synchronized (this) {
            this.animalMae = animalMae;
            mae = animalMae == null ? null : animalMae.getId();
            animalMae__resolvedKey = mae;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1282489802)
    public List<MedicoesAnimal> getMedicoesAnimals() {
        if (medicoesAnimals == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MedicoesAnimalDao targetDao = daoSession.getMedicoesAnimalDao();
            List<MedicoesAnimal> medicoesAnimalsNew = targetDao._queryMTFDados_MedicoesAnimals(id);
            synchronized (this) {
                if (medicoesAnimals == null) {
                    medicoesAnimals = medicoesAnimalsNew;
                }
            }
        }
        return medicoesAnimals;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2023901699)
    public synchronized void resetMedicoesAnimals() {
        medicoesAnimals = null;
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

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 858844258)
    public MotivoDescarte getMotivoDescarte() {
        Long __key = this.motDescId;
        if (motivoDescarte__resolvedKey == null || !motivoDescarte__resolvedKey.equals(__key)) {
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
    @Generated(hash = 129930372)
    public void setMotivoDescarte(MotivoDescarte motivoDescarte) {
        synchronized (this) {
            this.motivoDescarte = motivoDescarte;
            motDescId = motivoDescarte == null ? null : motivoDescarte.getId();
            motivoDescarte__resolvedKey = motDescId;
        }
    }

    public String getClassificacaoFP() {
        return this.classificacaoFP;
    }

    public void setClassificacaoFP(String classificacaoFP) {
        this.classificacaoFP = classificacaoFP;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1519770570)
    public List<MotivoDescarteAnimais> getMotivoDescarteAnimais() {
        if (motivoDescarteAnimais == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MotivoDescarteAnimaisDao targetDao = daoSession.getMotivoDescarteAnimaisDao();
            List<MotivoDescarteAnimais> motivoDescarteAnimaisNew = targetDao
                    ._queryMTFDados_MotivoDescarteAnimais(id);
            synchronized (this) {
                if (motivoDescarteAnimais == null) {
                    motivoDescarteAnimais = motivoDescarteAnimaisNew;
                }
            }
        }
        return motivoDescarteAnimais;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1466207610)
    public synchronized void resetMotivoDescarteAnimais() {
        motivoDescarteAnimais = null;
    }

    public String getLote() {
        return this.lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getIdfAlterado() {
        return this.idfAlterado;
    }

    public void setIdfAlterado(int idfAlterado) {
        this.idfAlterado = idfAlterado;
    }

    public int getOrdem_avaliacao() {
        return this.ordem_avaliacao;
    }

    public void setOrdem_avaliacao(int ordem_avaliacao) {
        this.ordem_avaliacao = ordem_avaliacao;
    }

    public int getIdMae() {
        return this.idMae;
    }

    public void setIdMae(int idMae) {
        this.idMae = idMae;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 511985764)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMTFDadosDao() : null;
    }

}
