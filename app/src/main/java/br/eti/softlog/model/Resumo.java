package br.eti.softlog.model;

import android.content.Context;
import android.database.Cursor;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import br.eti.softlog.qualitasbovino.AppMain;

public class Resumo {

    private int qtAnimais;
    private int qtAnimaisMacho;
    private int qtAnimaisFemea;

    private int qtAnimaisAvaliados;
    private int qtAnimaisMachoAvaliado;
    private int qtAnimaisFemeaAvaliado;

    private int qtAnimaisAprovados;
    private int qtAnimaisMachoAprovado;
    private int qtAnimaisFemeaAprovado;

    private int qtAnimaisDescarte;
    private int qtAnimaisMachoDescarte;
    private int qtAnimaisFemeaDescarte;

    private int qtAnimaisVenda;
    private int qtAnimaisMachoVenda;
    private int qtAnimaisFemeaVenda;

    private int qtAnimaisOutros;
    private int qtAnimaisMachoOutros;
    private int qtAnimaisFemeaOutros;

    private int qtAnimaisComercial;
    private int qtAnimaisMachoComercial;
    private int qtAnimaisFemeaComercial;

    private int qtAnimaisSoVenda;
    private int qtAnimaisMachoSoVenda;
    private int qtAnimaisFemeaSoVenda;

    private int qtAnimaisDescarteVenda;
    private int qtAnimaisMachoDescarteVenda;
    private int qtAnimalFemeaDescarteVenda;

    private int qtNasc;
    private int qtNascM;
    private int qtNascF;

    private int qtDesm;
    private int qtDesmM;
    private int qtDesmF;

    private int qt365;
    private int qt365M;
    private int qt365F;

    private int qt450;
    private int qt450M;
    private int qt450F;


    private int qt550;
    private int qt550M;
    private int qt550F;

    private int qtCe;
    private int qtCeM;
    private int qtCeF;

    private Double mNasc;
    private Double mNascM;
    private Double mNascF;

    private Double mDesm;
    private Double mDesmM;
    private Double mDesmF;

    private Double m365;
    private Double m365M;
    private Double m365F;

    private Double m450;
    private Double m450M;
    private Double m450F;


    private Double m550;
    private Double m550M;
    private Double m550F;

    private Double mCe;
    private Double mCeM;
    private Double mCeF;

    private Double vNascM;
    private Double vDesmM;
    private Double v365M;
    private Double v450M;
    private Double v550M;
    private Double vCeM;

    private Double vNascF;
    private Double vDesmF;
    private Double v365F;
    private Double v450F;
    private Double v550F;
    private Double vCeF;



    private String mRepM;
    private String mRepF;

    private String mUbeM;
    private String mUbeF;

    private String mMuscM;
    private String mMuscF;

    private String mFraM;
    private String mFraF;

    private String mAprM;
    private String mAprF;

    private String mOssM;
    private String mOssF;

    private String mProM;
    private String mProF;

    private String mGarM;
    private String mGarF;

    private String mUmbM;
    private String mUmbF;

    private String mBocM;
    private String mBocF;

    private String mCauM;
    private String mCauF;

    private String mPlaM;
    private String mPlaF;

    private String mTemM;
    private String mTemF;

    private String mTteM;
    private String mTteF;

    private String mRacM;
    private String mRacF;

    public Resumo(Context context, Long idCriador){

        List<MTFDados> animais;
        AppMain app;

        app = (AppMain)  context.getApplicationContext();

        qtAnimais=0;
        qtAnimaisMacho=0;
        qtAnimaisFemea=0;

        qtAnimaisAvaliados=0;
        qtAnimaisMachoAvaliado=0;
        qtAnimaisFemeaAvaliado=0;

        qtAnimaisAprovados = 0;
        qtAnimaisMachoAprovado = 0;
        qtAnimaisFemeaAprovado = 0;

        qtAnimaisDescarte=0;
        qtAnimaisMachoDescarte=0;
        qtAnimaisFemeaDescarte=0;

        qtAnimaisVenda = 0;
        qtAnimaisMachoVenda = 0;
        qtAnimaisFemeaVenda = 0;


        qtAnimaisOutros = 0;
        qtAnimaisMachoOutros = 0;
        qtAnimaisFemeaOutros = 0;

        qtAnimaisComercial = 0;
        qtAnimaisMachoComercial = 0;
        qtAnimaisFemeaComercial = 0;


        qtAnimaisDescarteVenda = 0;
        qtAnimaisMachoDescarteVenda = 0;
        qtAnimalFemeaDescarteVenda = 0;

        qtAnimaisDescarteVenda = 0;
        qtAnimaisMachoDescarteVenda = 0;
        qtAnimalFemeaDescarteVenda = 0;

        QueryBuilder qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"));
        animais = qryAnimal.list();
        qtAnimaisMacho = animais.size();

        //Total Animal Femea
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"));

        animais = qryAnimal.list();

        qtAnimaisFemea = animais.size();

        qtAnimais = qtAnimaisMacho + qtAnimaisFemea;

        //Animais Avaliados Macho
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"))
                .where(MTFDadosDao.Properties.Avaliado.eq(true));

        animais = qryAnimal.list();

        qtAnimaisMachoAvaliado = animais.size();

        //Animais Avaliados Femea
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"))
                .where(MTFDadosDao.Properties.Avaliado.eq(true));

        animais = qryAnimal.list();

        qtAnimaisFemeaAvaliado = animais.size();

        qtAnimaisAvaliados = qtAnimaisMachoAvaliado + qtAnimaisFemeaAvaliado;

        //Animais Venda Macho Descarte
        WhereCondition.StringCondition condition = new WhereCondition.StringCondition("" +
                "animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id = 281526 AND valor IS NOT NULL)" +
                " AND animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id = 141529 AND valor = 0)");

        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }


        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimaisMachoAprovado = animais.size();

        //Animais Venda Femea Descarte
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimaisFemeaAprovado = animais.size();

        qtAnimaisAprovados = qtAnimaisMachoAprovado + qtAnimaisFemeaAprovado;

        //Animais Venda Macho Aprovado
        condition = new WhereCondition.StringCondition("" +
                "animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id = 281526 AND valor IS NOT NULL)" +
                " AND animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id = 141529 AND valor = 1)");

        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }


        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimaisMachoDescarte = animais.size();

        //Animais Venda Femea Aprovado
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimaisFemeaDescarte = animais.size();

        qtAnimaisDescarte = qtAnimaisMachoDescarte + qtAnimaisFemeaDescarte;


        //Animais Venda Macho Descartado
        condition = new WhereCondition.StringCondition("" +
                "animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id = 321524 AND valor = 1)" +
                " AND animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id = 141529 AND valor = 1)");

        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }


        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimaisMachoDescarteVenda = animais.size();

        //Animais Venda Femea Descartado
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimalFemeaDescarteVenda = animais.size();

        qtAnimaisDescarteVenda = qtAnimaisMachoDescarteVenda + qtAnimalFemeaDescarteVenda;

        //Animais para Venda
        condition = new WhereCondition.StringCondition("" +
                " animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id IN (321524) AND valor = 1)");


        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }


        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimaisMachoVenda = animais.size();

        //Animais Venda Femea Descartado
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimaisFemeaVenda = animais.size();

        qtAnimaisVenda = qtAnimaisMachoVenda + qtAnimaisFemeaVenda;




        //Animais Comercial
        condition = new WhereCondition.StringCondition("" +
                " animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id IN (132523) AND valor = 1)");

        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }


        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"))
                .where(condition);

        animais = qryAnimal.list();

        qtAnimaisMachoComercial = animais.size();

        //Animais Comercial Fêmea
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"))
                .where(condition);

        animais = qryAnimal.list();

        qtAnimaisFemeaComercial = animais.size();

        qtAnimaisComercial = qtAnimaisMachoComercial + qtAnimaisFemeaComercial;

        qtAnimaisSoVenda = qtAnimaisVenda - qtAnimaisDescarteVenda;
        qtAnimaisMachoSoVenda = qtAnimaisMachoVenda - qtAnimaisMachoDescarteVenda;
        qtAnimaisFemeaSoVenda = qtAnimaisFemeaVenda - qtAnimalFemeaDescarteVenda;

        qtAnimaisOutros = qtAnimaisVenda + qtAnimaisComercial;
        qtAnimaisMachoOutros = qtAnimaisMachoVenda + qtAnimaisMachoComercial;
        qtAnimaisFemeaOutros = qtAnimaisFemeaVenda + qtAnimaisFemeaComercial;

        /*
        qtAnimaisAprovados = qtAnimaisAvaliados - qtAnimaisVenda - qtAnimaisDescarte;
        qtAnimaisMachoAprovado = qtAnimaisMachoAvaliado - qtAnimaisMachoVenda - qtAnimaisMachoDescarte;
        qtAnimaisFemeaAprovado = qtAnimaisFemeaAvaliado - qtAnimaisFemeaVenda - qtAnimaisFemeaDescarte;
         */



        String qryMediaM;
        if (idCriador>0){
            qryMediaM = "SELECT m.medicao_id, me.abrev, a.sexo, COUNT(*) as qt, ROUND(AVG(m.valor),1) as media \n" +
                    "FROM medicoes_animal m\n" +
                    "LEFT JOIN mtf_dados a ON a._id = m.animal \n" +
                    "LEFT JOIN medicao me ON me._id = m.medicao_id\n" +
                    "WHERE a.sexo = 'M' AND m.valor IS NOT null AND m.valor <> 9 AND m.medicao_id not in (131129,131811,141529,142528,222530,321524,132523) \n" +
                    " AND a.criador_id = " + String.valueOf(idCriador) + " \n" +
                    " GROUP BY m.medicao_id, me.abrev, a.sexo \n" +
                    "ORDER BY me.ordem, m.medicao_id";
        } else {
            qryMediaM = "SELECT m.medicao_id, me.abrev, a.sexo, COUNT(*) as qt, ROUND(AVG(m.valor),1) as media \n" +
                    "FROM medicoes_animal m\n" +
                    "LEFT JOIN mtf_dados a ON a._id = m.animal \n" +
                    "LEFT JOIN medicao me ON me._id = m.medicao_id\n" +
                    "WHERE a.sexo = 'M' AND m.valor IS NOT null AND m.valor <> 9 AND m.medicao_id not in (131129,131811,141529,142528,222530,321524,132523) GROUP BY m.medicao_id, me.abrev, a.sexo \n" +
                    "ORDER BY me.ordem, m.medicao_id";
        }


        Cursor curMediaM = app.getDb().rawQuery(qryMediaM,null);

        while (curMediaM.moveToNext()){
            String abrev = curMediaM.getString(1);
            String media = String.valueOf(curMediaM.getDouble(4)).replace(".",",");

            switch (abrev){
                case "REP":
                    mRepM = media;
                    break;
                case "UBE":
                    mUbeM = media;
                    break;
                case "MUS":
                    mMuscM = media;
                    break;
                case "FRA":
                    mFraM = media;
                    break;
                case "APR":
                    mAprM = media;
                    break;
                case "OSS":
                    mOssM = media;
                    break;
                case "PRO":
                    mProM = media;
                    break;
                case "GAR":
                    mGarM = media;
                    break;
                case "UMB":
                    mUmbM = media;
                    break;
                case "BOC":
                    mBocM = media;
                    break;
                case "CAU":
                    mCauM = media;
                    break;
                case "PLA":
                    mPlaM = media;
                    break;
                case "TEM":
                    mTemM = media;
                    break;
                case "TTE":
                    mTteM = media;
                    break;
                case "RAC":
                    mRacM = media;
                    break;

            }

        }


        //Media das medidas Femea
        String qryMediaF;
        if (idCriador>0){
            qryMediaF = "SELECT m.medicao_id, me.abrev, a.sexo, COUNT(*) as qt, ROUND(AVG(m.valor),1) as media \n" +
                    "FROM medicoes_animal m\n" +
                    "LEFT JOIN mtf_dados a ON a._id = m.animal \n" +
                    "LEFT JOIN medicao me ON me._id = m.medicao_id\n" +
                    "WHERE a.sexo = 'F' AND m.valor IS NOT null AND m.valor <> 9 AND m.medicao_id not in (131129,131811,141529,142528,222530,321524,132523) \n" +
                    " AND a.criador_id = " + String.valueOf(idCriador) + "\n" +
                    "GROUP BY m.medicao_id, me.abrev, a.sexo \n" +

                    "ORDER BY me.ordem, m.medicao_id";
        } else {
            qryMediaF = "SELECT m.medicao_id, me.abrev, a.sexo, COUNT(*) as qt, ROUND(AVG(m.valor),1) as media \n" +
                    "FROM medicoes_animal m\n" +
                    "LEFT JOIN mtf_dados a ON a._id = m.animal \n" +
                    "LEFT JOIN medicao me ON me._id = m.medicao_id\n" +
                    "WHERE a.sexo = 'F' AND m.valor IS NOT null AND m.valor <> 9 AND m.medicao_id not in (131129,131811,141529,142528,222530,321524,132523) GROUP BY m.medicao_id, me.abrev, a.sexo \n" +
                    "ORDER BY me.ordem, m.medicao_id";
        }


        Cursor curMediaF = app.getDb().rawQuery(qryMediaF,null);

        while (curMediaF.moveToNext()){
            String abrev = curMediaF.getString(1);
            String media = String.valueOf(curMediaF.getDouble(4)).replace(".",",");

            switch (abrev){
                case "REP":
                    mRepF = media;
                    break;
                case "UBE":
                    mUbeF = media;
                    break;
                case "MUS":
                    mMuscF = media;
                    break;
                case "FRA":
                    mFraF = media;
                    break;
                case "APR":
                    mAprF = media;
                    break;
                case "OSS":
                    mOssF = media;
                    break;
                case "PRO":
                    mProF = media;
                    break;
                case "GAR":
                    mGarF = media;
                    break;
                case "UMB":
                    mUmbF = media;
                    break;
                case "BOC":
                    mBocF = media;
                    break;
                case "CAU":
                    mCauF = media;
                    break;
                case "PLA":
                    mPlaF = media;
                    break;
                case "TEM":
                    mTemF = media;
                    break;
                case "TTE":
                    mTteF = media;
                    break;
                case "RAC":
                    mRacF = media;
                    break;
            }
        }


        qtNascM = 0;
        qtNascF = 0;
        qtDesmM = 0;
        qtDesmF = 0;
        qt365M = 0;
        qt365M = 0;
        qt450M = 0;
        qt450F = 0;
        qt550M = 0;
        qt550F = 0;
        qtCeM = 0;
        qtCeF = 0;

        mNasc = 0.00;
        mNascM = 0.00;
        mNascF = 0.00;

        mDesm = 0.00;
        mDesmM = 0.00;
        mDesmF = 0.00;

        m365 = 0.00;
        m365M = 0.00;
        m365F = 0.00;

        m450 = 0.00;
        m450M = 0.00;
        m450F = 0.00;

        m550 = 0.00;
        m550M = 0.00;
        m550F = 0.00;

        mCe = 0.00;
        mCeM = 0.00;
        mCeF = 0.00;

        vNascM = 0.00;
        vDesmM = 0.00;
        v365M = 0.00;
        v450M = 0.00;
        v550M = 0.00;
        vCeM = 0.00;

        vNascF = 0.00;
        vDesmF = 0.00;
        v365F = 0.00;
        v450F = 0.00;
        v550F = 0.00;
        vCeF = 0.00;


        if (idCriador>0)
            animais = app.getDaoSession().getMTFDadosDao().queryBuilder()
                    .where(MTFDadosDao.Properties.CriadorId.eq(idCriador)).list();
        else
            animais = app.getDaoSession().getMTFDadosDao().queryBuilder().list();


        for (int i = 0; i<animais.size();i++){
            MTFDados animal = animais.get(i);

            if (animal.getSexo().equals("M"))
            {
                if (animal.getRPNasc()>0){
                    qtNascM++;
                    vNascM = vNascM + animal.getRPNasc();
                }


                if (animal.getRPDesm()>0){
                    qtDesmM++;
                    vDesmM = vDesmM + animal.getRPDesm();
                }


                if (animal.getRP365()>0){
                    qt365M++;
                    v365M = v365M + animal.getRP365();
                }


                if (animal.getRP450()>0){
                    qt450M++;
                    v450M = v450M + animal.getRP450();
                }


                if (animal.getRP550()>0){
                    qt550M++;
                    v550M = v550M + animal.getRP550();
                }


                if (animal.getRCe()>0){
                    qtCeM++;
                    vCeM = vCeM + animal.getRCe();
                }

            } else {
                if (animal.getRPNasc()>0){
                    qtNascF++;
                    vNascF = vNascF + animal.getRPNasc();
                }


                if (animal.getRPDesm()>0){
                    qtDesmF++;
                    vDesmF = vDesmF + animal.getRPDesm();
                }


                if (animal.getRP365()>0){
                    qt365F++;
                    v365F = v365F + animal.getRP365();
                }


                if (animal.getRP450()>0){
                    qt450F++;
                    v450F = v450F + animal.getRP450();
                }


                if (animal.getRP550()>0){
                    qt550F++;
                    v550F = v550F + animal.getRP550();
                }


                if (animal.getRCe()>0){
                    qtCeF++;
                    vCeF = vCeF + animal.getRCe();
                }
            }

        }

        mNascM = vNascM/qtNascM;
        mDesmM = vDesmM/qtDesmM;
        m365M = v365M/qt365M;
        m450M = v450M/qt450M;
        m550M = v550M/qt550M;
        mCeM = vCeM/qtCeM;

        mNascF = vNascF/qtNascF;
        mDesmF = vDesmF/qtDesmF;
        m365F = v365F/qt365F;
        m450F = v450F/qt450F;
        m550F = v550F/qt550F;
        mCeF = vCeF/qtCeF;

        if (mNascM.isNaN() || mNascM.isInfinite())
            mNascM = 0.0;
        if (mDesmM.isNaN() || mDesmM.isInfinite())
            mDesmM = 0.00;
        if (m365M.isNaN() || m365M.isInfinite())
            m365M = 0.00;
        if (m450M.isNaN() || m450M.isInfinite())
            m450M = 0.00;
        if (m550M.isNaN() || m550M.isInfinite())
            m550M = 0.00;
        if (mCeF.isNaN() || mCeF.isInfinite())
            mCeF = 0.00;

        if (mNascF.isNaN() || mNascF.isInfinite())
            mNascF = 0.0;
        if (mDesmF.isNaN() || mDesmF.isInfinite())
            mDesmF = 0.00;
        if (m365F.isNaN() || m365F.isInfinite())
            m365F = 0.00;
        if (m450F.isNaN() || m450F.isInfinite())
            m450F = 0.00;
        if (m550F.isNaN() || m550F.isInfinite())
            m550F = 0.00;

    }


    public int getQtAnimais() {
        return qtAnimais;
    }

    public void setQtAnimais(int qtAnimais) {
        this.qtAnimais = qtAnimais;
    }

    public int getQtAnimaisMacho() {
        return qtAnimaisMacho;
    }

    public void setQtAnimaisMacho(int qtAnimaisMacho) {
        this.qtAnimaisMacho = qtAnimaisMacho;
    }

    public int getQtAnimaisFemea() {
        return qtAnimaisFemea;
    }

    public void setQtAnimaisFemea(int qtAnimaisFemea) {
        this.qtAnimaisFemea = qtAnimaisFemea;
    }

    public int getQtAnimaisAvaliados() {
        return qtAnimaisAvaliados;
    }

    public void setQtAnimaisAvaliados(int qtAnimaisAvaliados) {
        this.qtAnimaisAvaliados = qtAnimaisAvaliados;
    }

    public int getQtAnimaisMachoAvaliado() {
        return qtAnimaisMachoAvaliado;
    }

    public void setQtAnimaisMachoAvaliado(int qtAnimaisMachoAvaliado) {
        this.qtAnimaisMachoAvaliado = qtAnimaisMachoAvaliado;
    }

    public int getQtAnimaisFemeaAvaliado() {
        return qtAnimaisFemeaAvaliado;
    }

    public void setQtAnimaisFemeaAvaliado(int qtAnimaisFemeaAvaliado) {
        this.qtAnimaisFemeaAvaliado = qtAnimaisFemeaAvaliado;
    }

    public int getQtAnimaisAprovados() {
        return qtAnimaisAprovados;
    }

    public void setQtAnimaisAprovados(int qtAnimaisAprovados) {
        this.qtAnimaisAprovados = qtAnimaisAprovados;
    }

    public int getQtAnimaisMachoAprovado() {
        return qtAnimaisMachoAprovado;
    }

    public void setQtAnimaisMachoAprovado(int qtAnimaisMachoAprovado) {
        this.qtAnimaisMachoAprovado = qtAnimaisMachoAprovado;
    }

    public int getQtAnimaisFemeaAprovado() {
        return qtAnimaisFemeaAprovado;
    }

    public void setQtAnimaisFemeaAprovado(int qtAnimaisFemeaAprovado) {
        this.qtAnimaisFemeaAprovado = qtAnimaisFemeaAprovado;
    }

    public int getQtAnimaisDescarte() {
        return qtAnimaisDescarte;
    }

    public void setQtAnimaisDescarte(int qtAnimaisDescarte) {
        this.qtAnimaisDescarte = qtAnimaisDescarte;
    }

    public int getQtAnimaisMachoDescarte() {
        return qtAnimaisMachoDescarte;
    }

    public void setQtAnimaisMachoDescarte(int qtAnimaisMachoDescarte) {
        this.qtAnimaisMachoDescarte = qtAnimaisMachoDescarte;
    }

    public int getQtAnimaisFemeaDescarte() {
        return qtAnimaisFemeaDescarte;
    }

    public void setQtAnimaisFemeaDescarte(int qtAnimaisFemeaDescarte) {
        this.qtAnimaisFemeaDescarte = qtAnimaisFemeaDescarte;
    }

    public int getQtAnimaisVenda() {
        return qtAnimaisVenda;
    }

    public void setQtAnimaisVenda(int qtAnimaisVenda) {
        this.qtAnimaisVenda = qtAnimaisVenda;
    }

    public int getQtAnimaisMachoVenda() {
        return qtAnimaisMachoVenda;
    }

    public void setQtAnimaisMachoVenda(int qtAnimaisMachoVenda) {
        this.qtAnimaisMachoVenda = qtAnimaisMachoVenda;
    }

    public int getQtAnimaisFemeaVenda() {
        return qtAnimaisFemeaVenda;
    }

    public void setQtAnimaisFemeaVenda(int qtAnimaisFemeaVenda) {
        this.qtAnimaisFemeaVenda = qtAnimaisFemeaVenda;
    }

    public int getQtAnimaisSoVenda() {
        return qtAnimaisSoVenda;
    }

    public void setQtAnimaisSoVenda(int qtAnimaisSoVenda) {
        this.qtAnimaisSoVenda = qtAnimaisSoVenda;
    }

    public int getQtAnimaisMachoSoVenda() {
        return qtAnimaisMachoSoVenda;
    }

    public void setQtAnimaisMachoSoVenda(int qtAnimaisMachoSoVenda) {
        this.qtAnimaisMachoSoVenda = qtAnimaisMachoSoVenda;
    }

    public int getQtAnimaisFemeaSoVenda() {
        return qtAnimaisFemeaSoVenda;
    }

    public void setQtAnimaisFemeaSoVenda(int qtAnimaisFemeaSoVenda) {
        this.qtAnimaisFemeaSoVenda = qtAnimaisFemeaSoVenda;
    }

    public int getQtAnimaisDescarteVenda() {
        return qtAnimaisDescarteVenda;
    }

    public void setQtAnimaisDescarteVenda(int qtAnimaisDescarteVenda) {
        this.qtAnimaisDescarteVenda = qtAnimaisDescarteVenda;
    }

    public int getQtAnimaisMachoDescarteVenda() {
        return qtAnimaisMachoDescarteVenda;
    }

    public void setQtAnimaisMachoDescarteVenda(int qtAnimaisMachoDescarteVenda) {
        this.qtAnimaisMachoDescarteVenda = qtAnimaisMachoDescarteVenda;
    }

    public int getQtAnimalFemeaDescarteVenda() {
        return qtAnimalFemeaDescarteVenda;
    }

    public void setQtAnimalFemeaDescarteVenda(int qtAnimalFemeaDescarteVenda) {
        this.qtAnimalFemeaDescarteVenda = qtAnimalFemeaDescarteVenda;
    }

    public int getQtNasc() {
        return qtNasc;
    }

    public void setQtNasc(int qtNasc) {
        this.qtNasc = qtNasc;
    }

    public int getQtNascM() {
        return qtNascM;
    }

    public void setQtNascM(int qtNascM) {
        this.qtNascM = qtNascM;
    }

    public int getQtNascF() {
        return qtNascF;
    }

    public void setQtNascF(int qtNascF) {
        this.qtNascF = qtNascF;
    }

    public int getQtDesm() {
        return qtDesm;
    }

    public void setQtDesm(int qtDesm) {
        this.qtDesm = qtDesm;
    }

    public int getQtDesmM() {
        return qtDesmM;
    }

    public void setQtDesmM(int qtDesmM) {
        this.qtDesmM = qtDesmM;
    }

    public int getQtDesmF() {
        return qtDesmF;
    }

    public void setQtDesmF(int qtDesmF) {
        this.qtDesmF = qtDesmF;
    }

    public int getQt365() {
        return qt365;
    }

    public void setQt365(int qt365) {
        this.qt365 = qt365;
    }

    public int getQt365M() {
        return qt365M;
    }

    public void setQt365M(int qt365M) {
        this.qt365M = qt365M;
    }

    public int getQt365F() {
        return qt365F;
    }

    public void setQt365F(int qt365F) {
        this.qt365F = qt365F;
    }

    public int getQt450() {
        return qt450;
    }

    public void setQt450(int qt450) {
        this.qt450 = qt450;
    }

    public int getQt450M() {
        return qt450M;
    }

    public void setQt450M(int qt450M) {
        this.qt450M = qt450M;
    }

    public int getQt450F() {
        return qt450F;
    }

    public void setQt450F(int qt450F) {
        this.qt450F = qt450F;
    }

    public int getQt550() {
        return qt550;
    }

    public void setQt550(int qt550) {
        this.qt550 = qt550;
    }

    public int getQt550M() {
        return qt550M;
    }

    public void setQt550M(int qt550M) {
        this.qt550M = qt550M;
    }

    public int getQt550F() {
        return qt550F;
    }

    public void setQt550F(int qt550F) {
        this.qt550F = qt550F;
    }

    public int getQtCe() {
        return qtCe;
    }

    public void setQtCe(int qtCe) {
        this.qtCe = qtCe;
    }

    public int getQtCeM() {
        return qtCeM;
    }

    public void setQtCeM(int qtCeM) {
        this.qtCeM = qtCeM;
    }

    public int getQtCeF() {
        return qtCeF;
    }

    public void setQtCeF(int qtCeF) {
        this.qtCeF = qtCeF;
    }

    public Double getmNasc() {
        return mNasc;
    }

    public void setmNasc(Double mNasc) {
        this.mNasc = mNasc;
    }

    public Double getmNascM() {
        return mNascM;
    }

    public void setmNascM(Double mNascM) {
        this.mNascM = mNascM;
    }

    public Double getmNascF() {
        return mNascF;
    }

    public void setmNascF(Double mNascF) {
        this.mNascF = mNascF;
    }

    public Double getmDesm() {
        return mDesm;
    }

    public void setmDesm(Double mDesm) {
        this.mDesm = mDesm;
    }

    public Double getmDesmM() {
        return mDesmM;
    }

    public void setmDesmM(Double mDesmM) {
        this.mDesmM = mDesmM;
    }

    public Double getmDesmF() {
        return mDesmF;
    }

    public void setmDesmF(Double mDesmF) {
        this.mDesmF = mDesmF;
    }

    public Double getM365() {
        return m365;
    }

    public void setM365(Double m365) {
        this.m365 = m365;
    }

    public Double getM365M() {
        return m365M;
    }

    public void setM365M(Double m365M) {
        this.m365M = m365M;
    }

    public Double getM365F() {
        return m365F;
    }

    public void setM365F(Double m365F) {
        this.m365F = m365F;
    }

    public Double getM450() {
        return m450;
    }

    public void setM450(Double m450) {
        this.m450 = m450;
    }

    public Double getM450M() {
        return m450M;
    }

    public void setM450M(Double m450M) {
        this.m450M = m450M;
    }

    public Double getM450F() {
        return m450F;
    }

    public void setM450F(Double m450F) {
        this.m450F = m450F;
    }

    public Double getM550() {
        return m550;
    }

    public void setM550(Double m550) {
        this.m550 = m550;
    }

    public Double getM550M() {
        return m550M;
    }

    public void setM550M(Double m550M) {
        this.m550M = m550M;
    }

    public Double getM550F() {
        return m550F;
    }

    public void setM550F(Double m550F) {
        this.m550F = m550F;
    }

    public Double getmCe() {
        return mCe;
    }

    public void setmCe(Double mCe) {
        this.mCe = mCe;
    }

    public Double getmCeM() {
        return mCeM;
    }

    public void setmCeM(Double mCeM) {
        this.mCeM = mCeM;
    }

    public Double getmCeF() {
        return mCeF;
    }

    public void setmCeF(Double mCeF) {
        this.mCeF = mCeF;
    }

    public Double getvNascM() {
        return vNascM;
    }

    public void setvNascM(Double vNascM) {
        this.vNascM = vNascM;
    }

    public Double getvDesmM() {
        return vDesmM;
    }

    public void setvDesmM(Double vDesmM) {
        this.vDesmM = vDesmM;
    }

    public Double getV365M() {
        return v365M;
    }

    public void setV365M(Double v365M) {
        this.v365M = v365M;
    }

    public Double getV450M() {
        return v450M;
    }

    public void setV450M(Double v450M) {
        this.v450M = v450M;
    }

    public Double getV550M() {
        return v550M;
    }

    public void setV550M(Double v550M) {
        this.v550M = v550M;
    }

    public Double getvCeM() {
        return vCeM;
    }

    public void setvCeM(Double vCeM) {
        this.vCeM = vCeM;
    }

    public Double getvNascF() {
        return vNascF;
    }

    public void setvNascF(Double vNascF) {
        this.vNascF = vNascF;
    }

    public Double getvDesmF() {
        return vDesmF;
    }

    public void setvDesmF(Double vDesmF) {
        this.vDesmF = vDesmF;
    }

    public Double getV365F() {
        return v365F;
    }

    public void setV365F(Double v365F) {
        this.v365F = v365F;
    }

    public Double getV450F() {
        return v450F;
    }

    public void setV450F(Double v450F) {
        this.v450F = v450F;
    }

    public Double getV550F() {
        return v550F;
    }

    public void setV550F(Double v550F) {
        this.v550F = v550F;
    }

    public Double getvCeF() {
        return vCeF;
    }

    public void setvCeF(Double vCeF) {
        this.vCeF = vCeF;
    }

    public String getmRepM() {
        return mRepM;
    }

    public void setmRepM(String mRepM) {
        this.mRepM = mRepM;
    }

    public String getmRepF() {
        return mRepF;
    }

    public void setmRepF(String mRepF) {
        this.mRepF = mRepF;
    }

    public String getmUbeM() {
        return mUbeM;
    }

    public void setmUbeM(String mUbeM) {
        this.mUbeM = mUbeM;
    }

    public String getmUbeF() {
        return mUbeF;
    }

    public void setmUbeF(String mUbeF) {
        this.mUbeF = mUbeF;
    }

    public String getmMuscM() {
        return mMuscM;
    }

    public void setmMuscM(String mMuscM) {
        this.mMuscM = mMuscM;
    }

    public String getmMuscF() {
        return mMuscF;
    }

    public void setmMuscF(String mMuscF) {
        this.mMuscF = mMuscF;
    }

    public String getmFraM() {
        return mFraM;
    }

    public void setmFraM(String mFraM) {
        this.mFraM = mFraM;
    }

    public String getmFraF() {
        return mFraF;
    }

    public void setmFraF(String mFraF) {
        this.mFraF = mFraF;
    }

    public String getmAprM() {
        return mAprM;
    }

    public void setmAprM(String mAprM) {
        this.mAprM = mAprM;
    }

    public String getmAprF() {
        return mAprF;
    }

    public void setmAprF(String mAprF) {
        this.mAprF = mAprF;
    }

    public String getmOssM() {
        return mOssM;
    }

    public void setmOssM(String mOssM) {
        this.mOssM = mOssM;
    }

    public String getmOssF() {
        return mOssF;
    }

    public void setmOssF(String mOssF) {
        this.mOssF = mOssF;
    }

    public String getmProM() {
        return mProM;
    }

    public void setmProM(String mProM) {
        this.mProM = mProM;
    }

    public String getmProF() {
        return mProF;
    }

    public void setmProF(String mProF) {
        this.mProF = mProF;
    }

    public String getmGarM() {
        return mGarM;
    }

    public void setmGarM(String mGarM) {
        this.mGarM = mGarM;
    }

    public String getmGarF() {
        return mGarF;
    }

    public void setmGarF(String mGarF) {
        this.mGarF = mGarF;
    }

    public String getmUmbM() {
        return mUmbM;
    }

    public void setmUmbM(String mUmbM) {
        this.mUmbM = mUmbM;
    }

    public String getmUmbF() {
        return mUmbF;
    }

    public void setmUmbF(String mUmbF) {
        this.mUmbF = mUmbF;
    }

    public String getmBocM() {
        return mBocM;
    }

    public void setmBocM(String mBocM) {
        this.mBocM = mBocM;
    }

    public String getmBocF() {
        return mBocF;
    }

    public void setmBocF(String mBocF) {
        this.mBocF = mBocF;
    }

    public String getmCauM() {
        return mCauM;
    }

    public void setmCauM(String mCauM) {
        this.mCauM = mCauM;
    }

    public String getmCauF() {
        return mCauF;
    }

    public void setmCauF(String mCauF) {
        this.mCauF = mCauF;
    }

    public String getmPlaM() {
        return mPlaM;
    }

    public void setmPlaM(String mPlaM) {
        this.mPlaM = mPlaM;
    }

    public String getmPlaF() {
        return mPlaF;
    }

    public void setmPlaF(String mPlaF) {
        this.mPlaF = mPlaF;
    }

    public String getmTemM() {
        return mTemM;
    }

    public void setmTemM(String mTemM) {
        this.mTemM = mTemM;
    }

    public String getmTemF() {
        return mTemF;
    }

    public void setmTemF(String mTemF) {
        this.mTemF = mTemF;
    }

    public String getmTteM() {
        return mTteM;
    }

    public void setmTteM(String mTteM) {
        this.mTteM = mTteM;
    }

    public String getmTteF() {
        return mTteF;
    }

    public void setmTteF(String mTteF) {
        this.mTteF = mTteF;
    }

    public String getmRacM() {
        return mRacM;
    }

    public void setmRacM(String mRacM) {
        this.mRacM = mRacM;
    }

    public String getmRacF() {
        return mRacF;
    }

    public void setmRacF(String mRacF) {
        this.mRacF = mRacF;
    }


    public int getQtAnimaisComercial() {
        return qtAnimaisComercial;
    }

    public void setQtAnimaisComercial(int qtAnimaisComercial) {
        this.qtAnimaisComercial = qtAnimaisComercial;
    }

    public int getQtAnimaisMachoComercial() {
        return qtAnimaisMachoComercial;
    }

    public void setQtAnimaisMachoComercial(int qtAnimaisMachoComercial) {
        this.qtAnimaisMachoComercial = qtAnimaisMachoComercial;
    }

    public int getQtAnimaisFemeaComercial() {
        return qtAnimaisFemeaComercial;
    }

    public void setQtAnimaisFemeaComercial(int qtAnimaisFemeaComercial) {
        this.qtAnimaisFemeaComercial = qtAnimaisFemeaComercial;
    }

    public int getQtAnimaisOutros() {
        return qtAnimaisOutros;
    }

    public void setQtAnimaisOutros(int qtAnimaisOutros) {
        this.qtAnimaisOutros = qtAnimaisOutros;
    }

    public int getQtAnimaisMachoOutros() {
        return qtAnimaisMachoOutros;
    }

    public void setQtAnimaisMachoOutros(int qtAnimaisMachoOutros) {
        this.qtAnimaisMachoOutros = qtAnimaisMachoOutros;
    }

    public int getQtAnimaisFemeaOutros() {
        return qtAnimaisFemeaOutros;
    }

    public void setQtAnimaisFemeaOutros(int qtAnimaisFemeaOutros) {
        this.qtAnimaisFemeaOutros = qtAnimaisFemeaOutros;
    }


    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
