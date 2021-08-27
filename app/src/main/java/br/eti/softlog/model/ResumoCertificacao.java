package br.eti.softlog.model;

import android.content.Context;
import android.database.Cursor;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import br.eti.softlog.qualitasbovino.AppMain;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ResumoCertificacao {

    private int qtAnimais;
    private int qtAnimaisMacho;
    private int qtAnimaisFemea;

    private int qtAnimaisSuplente;
    private int qtAnimaisMachoSuplente;
    private int qtAnimaisFemeaSuplente;

    private int qtAnimaisCeip;
    private int qtAnimaisMachoCeip;
    private int qtAnimaisFemeaCeip;

    private int qtAnimaisAvaliado;
    private int qtAnimaisMachoAvaliado;
    private int qtAnimaisFemeaAvaliado;

    private int qtAnimaisCeipAvaliado;
    private int qtAnimaisMachoCeipAvaliado;
    private int qtAnimaisFemeaCeipAvaliado;

    private int qtAnimaisSuplenteAvaliado;
    private int qtAnimaisMachoSuplenteAvaliado;
    private int qtAnimaisFemeaSuplenteAvaliado;

    private int qtAnimaisCertificado;
    private int qtAnimaisMachoCertificado;
    private int qtAnimaisFemeaCertificado;

    private int qtAnimaisCertificadoCeip;
    private int qtAnimaisMachoCertificadoCeip;
    private int qtAnimaisFemeaCertificadoCeip;

    private int qtAnimaisCertificadoSuplente;
    private int qtAnimaisMachoCertificadoSuplente;
    private int qtAnimaisFemeaCertificadoSuplente;

    private int qtAnimaisDescarte;
    private int qtAnimaisMachoDescarte;
    private int qtAnimaisFemeaDescarte;

    private int qtAnimaisCeipDescarte;
    private int qtAnimaisMachoCeipDescarte;
    private int qtAnimaisFemeaCeipDescarte;

    private int qtAnimaisSuplenteDescarte;
    private int qtAnimaisMachoSuplenteDescarte;
    private int qtAnimaisFemeaSuplenteDescarte;


    private double mPesoCertMacho;
    private double mPesoCertFemea;

    private double mCeCertMacho;
    private double mCeCertFemea;

    private double mCertClassMacho;
    private double mCertClassFemea;

    private double mCertIQMacho;
    private double mCertIQFemea;

    private double mPesoCertMachoP;
    private double mPesoCertMachoF;

    private double mCeCertMachoP;
    private double mCeCertMachoF;

    private double mCertClassMachoP;
    private double mCertClassMachoF;

    private double mCertIQMachoP;
    private double mCertIQMachoF;

    private int mCertTotalP;
    private int mCertTotalF;


    public ResumoCertificacao(Context context){

        List<MTFDados> animais;
        AppMain app;

        app = (AppMain)  context.getApplicationContext();

        qtAnimais = 0;
        qtAnimaisMacho = 0;
        qtAnimaisFemea = 0;

        qtAnimaisSuplente = 0;
        qtAnimaisMachoSuplente = 0;
        qtAnimaisFemeaSuplente = 0;

        qtAnimaisCeip = 0;
        qtAnimaisMachoCeip = 0;
        qtAnimaisFemeaCeip = 0;

        qtAnimaisAvaliado = 0;
        qtAnimaisMachoAvaliado = 0;
        qtAnimaisFemeaAvaliado = 0;

        qtAnimaisCeipAvaliado = 0;
        qtAnimaisMachoCeipAvaliado = 0;
        qtAnimaisFemeaCeipAvaliado = 0;

        qtAnimaisSuplenteAvaliado = 0;
        qtAnimaisMachoSuplenteAvaliado = 0;
        qtAnimaisFemeaSuplenteAvaliado = 0;

        qtAnimaisCertificado = 0;
        qtAnimaisMachoCertificado = 0;
        qtAnimaisFemeaCertificado = 0;

        qtAnimaisCertificadoCeip = 0;
        qtAnimaisMachoCertificadoCeip = 0;
        qtAnimaisFemeaCertificadoCeip = 0;

        qtAnimaisCertificadoSuplente = 0;
        qtAnimaisMachoCertificadoSuplente = 0;
        qtAnimaisFemeaCertificadoSuplente = 0;

        qtAnimaisDescarte = 0;
        qtAnimaisMachoDescarte = 0;
        qtAnimaisFemeaDescarte = 0;

        qtAnimaisCeipDescarte = 0;
        qtAnimaisMachoCeipDescarte = 0;
        qtAnimaisFemeaCeipDescarte = 0;

        qtAnimaisSuplenteDescarte = 0;
        qtAnimaisMachoSuplenteDescarte = 0;
        qtAnimaisFemeaSuplenteDescarte = 0;


        mPesoCertMacho = 0;
        mPesoCertFemea = 0;

        mCeCertMacho = 0;
        mCeCertFemea = 0;

        mCertClassMacho = 0;
        mCertClassFemea = 0;

        mCertIQMacho = 0;
        mCertIQFemea = 0;

        mPesoCertMachoP = 0;
        mPesoCertMachoF = 0;

        mCeCertMachoP = 0;
        mCeCertMachoF = 0;

        mCertClassMachoP = 0;
        mCertClassMachoF = 0;

        mCertIQMachoP = 0;
        mCertIQMachoF = 0;

        mCertTotalP = 0;
        mCertTotalF = 0;

        //Total Animal Macho
        QueryBuilder qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"));
        animais = qryAnimal.list();

        qtAnimaisMacho = animais.size();

        //Total Animal Femea
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"));

        animais = qryAnimal.list();

        qtAnimaisFemea = animais.size();
        qtAnimais = qtAnimaisMacho + qtAnimaisFemea;

        //Candidatos
        String qryCandidatos = "SELECT \n" +
                "    count(*) as qt,    \n" +
                "    sexo,\n" +
                "    'C' as tipo\n" +
                "FROM \n" +
                "    mtf_dados\n" +
                "WHERE \n" +
                "    ceip IN ('C','P')\n" +
                "GROUP BY   \n" +
                "    sexo \n" +
                "UNION \n" +
                "SELECT \n" +
                "    count(*) as qt,\n" +
                "    sexo,\n" +
                "    'S' as tipo\n" +
                "FROM \n" +
                "    mtf_dados\n" +
                "WHERE \n" +
                "    ceip IN ('S')\n" +
                "GROUP BY   \n" +
                "    sexo \n";


        Cursor cursorCanditados = app.getDb().rawQuery(qryCandidatos,null);

        while (cursorCanditados.moveToNext()){
            int qt = cursorCanditados.getInt(0);
            String sexo = cursorCanditados.getString(1);
            String tipo = cursorCanditados.getString(2);

            if (tipo.equals("C")) {
                if (sexo.equals("M"))
                    qtAnimaisMachoCeip = qt;
                else
                    qtAnimaisFemeaCeip = qt;

                qtAnimaisCeip = qtAnimaisFemeaCeip + qtAnimaisMachoCeip;
            } else {
                //String media = String.valueOf(curMediaM.getDouble(4)).replace(".",",");
                if (sexo.equals("M"))
                    qtAnimaisMachoSuplente = qt;
                else
                    qtAnimaisFemeaSuplente = qt;

                qtAnimaisSuplente = qtAnimaisFemeaSuplente + qtAnimaisMachoSuplente;

            }

        }

        //Certificados
        String qryCertificados = "SELECT \n" +
                "    count(*) as qt,\n" +
                "    sexo,\n" +
                "    'C' as tipo\n" +
                "FROM \n" +
                "    mtf_dados\n" +
                "WHERE \n" +
                "    marcacao = 1\n" +
                "    AND ceip IN ('C','P')\n" +
                "GROUP BY   \n" +
                "    sexo \n" +
                "UNION \n" +
                "SELECT \n" +
                "    count(*) as qt,\n" +
                "    sexo,\n" +
                "    'S' as tipo\n" +
                "FROM \n" +
                "    mtf_dados\n" +
                "WHERE \n" +
                "    marcacao = 1\n" +
                "    AND ceip IN ('S')\n" +
                "GROUP BY   \n" +
                "    sexo \n";

        Cursor cursorCertificados = app.getDb().rawQuery(qryCertificados,null);

        while (cursorCertificados.moveToNext()){
            int qt = cursorCertificados.getInt(0);
            String sexo = cursorCertificados.getString(1);
            String tipo = cursorCertificados.getString(2);

            if (tipo.equals("C")) {
                if (sexo.equals("M"))
                    qtAnimaisMachoCertificadoCeip = qt;
                else
                    qtAnimaisFemeaCertificadoCeip = qt;

                qtAnimaisCertificadoCeip = qtAnimaisFemeaCertificadoCeip + qtAnimaisMachoCertificadoCeip;

            } else {
                //String media = String.valueOf(curMediaM.getDouble(4)).replace(".",",");
                if (sexo.equals("M"))
                    qtAnimaisMachoCertificadoSuplente = qt;
                else
                    qtAnimaisFemeaCertificadoSuplente = qt;

                qtAnimaisCertificadoSuplente = qtAnimaisFemeaCertificadoSuplente + qtAnimaisMachoCertificadoSuplente;

            }

            qtAnimaisMachoCertificado = qtAnimaisMachoCertificadoCeip + qtAnimaisMachoCertificadoSuplente;
            qtAnimaisFemeaCertificado = qtAnimaisFemeaCertificadoCeip + qtAnimaisFemeaCertificadoSuplente;

        }

        //Desclassificados
        String qryDesc =  "SELECT \n" +
                "    count(*) as qt,\n" +
                "    sexo,\n" +
                "    'C' as tipo\n" +
                "FROM \n" +
                "    mtf_dados\n" +
                "WHERE \n" +
                "    marcacao = 0\n" +
                "    AND ceip IN ('C','P')\n" +
                "GROUP BY   \n" +
                "    sexo \n" +
                "UNION \n" +
                "SELECT \n" +
                "    count(*) as qt,\n" +
                "    sexo,\n" +
                "    'S' as tipo\n" +
                "FROM \n" +
                "    mtf_dados\n" +
                "WHERE \n" +
                "    marcacao = 0\n" +
                "    AND ceip IN ('S')\n" +
                "GROUP BY   \n" +
                "    sexo \n";

        Cursor cursorDesc = app.getDb().rawQuery(qryDesc,null);

        while (cursorDesc.moveToNext()){
            int qt = cursorDesc.getInt(0);
            String sexo = cursorDesc.getString(1);
            String tipo = cursorDesc.getString(2);

            if (tipo.equals("C")) {
                if (sexo.equals("M"))
                    qtAnimaisMachoCeipDescarte = qt;
                else
                    qtAnimaisFemeaCeipDescarte = qt;
                qtAnimaisCeipDescarte = qtAnimaisFemeaCeipDescarte + qtAnimaisMachoCeipDescarte;
            } else {
                //String media = String.valueOf(curMediaM.getDouble(4)).replace(".",",");
                if (sexo.equals("M"))
                    qtAnimaisMachoSuplenteDescarte = qt;
                else
                    qtAnimaisFemeaSuplenteDescarte = qt;
                qtAnimaisSuplenteDescarte = qtAnimaisFemeaSuplenteDescarte + qtAnimaisMachoSuplenteDescarte;
            }

            qtAnimaisMachoDescarte = qtAnimaisMachoCeipDescarte + qtAnimaisMachoSuplenteDescarte;
            qtAnimaisFemeaDescarte = qtAnimaisFemeaCeipDescarte + qtAnimaisFemeaSuplenteDescarte;

        }

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Media Certificados

        String qryMPeso = "SELECT \n" +
                "    sexo, \n" +
                "    COUNT(*) as qt, \n" +
                "    ROUND(AVG(p_marcacao),1) as media_peso\n" +
                "FROM\n" +
                "    mtf_dados\n" +
                "WHERE\n" +
                "    marcacao = 1\n" +
                "    AND p_marcacao IS NOT NULL\n" +
                "GROUP BY\n" +
                "    sexo;";

        Cursor cursorTemp = app.getDb().rawQuery(qryMPeso,null);

        while (cursorTemp.moveToNext()) {

            String sexo = cursorTemp.getString(0) == null ? "" : cursorTemp.getString(0);
            double media = cursorTemp.getInt(2);

            if (sexo.equals("M"))
                mPesoCertMacho = media;
            if (sexo.equals("F"))
                mPesoCertFemea = media;

        }

        String qryMCe = "SELECT \n" +
                "    sexo, \n" +
                "    COUNT(*) as qt, \n" +
                "    ROUND(AVG(ce_marcacao),1) as media\n" +
                "FROM\n" +
                "    mtf_dados\n" +
                "WHERE\n" +
                "    marcacao = 1\n" +
                "    AND sexo = 'M'\n" +
                "    AND ce_marcacao IS NOT NULL\n";

        cursorTemp = app.getDb().rawQuery(qryMCe,null);

        while (cursorTemp.moveToNext()) {

            String sexo =  cursorTemp.getString(0) == null ? "" : cursorTemp.getString(0);
            double media = cursorTemp.getDouble(2);

            if (sexo.equals("M"))
                mCeCertMacho = media;
        }

        String qryMClass = "SELECT \n" +
                "    sexo, \n" +
                "    COUNT(*) as qt, \n" +
                "    ROUND(AVG(classificacao),1) as media\n" +
                "FROM\n" +
                "    mtf_dados\n" +
                "WHERE\n" +
                "    marcacao = 1\n" +
                "    AND classificacao IS NOT NULL\n" +
                "GROUP BY sexo";

        cursorTemp = app.getDb().rawQuery(qryMClass,null);

        while (cursorTemp.moveToNext()) {

            String sexo = cursorTemp.getString(0) == null ? "" : cursorTemp.getString(0);
            double media = cursorTemp.getDouble(2);

            if (sexo.equals("M"))
                mCertClassMacho = media;

            if (sexo.equals("F"))
                mCertClassFemea = media;
        }

        String qryMIQ = "SELECT \n" +
                "    sexo, \n" +
                "    COUNT(*) as qt, \n" +
                "    ROUND(AVG(ind_qlt),1) as media\n" +
                "FROM\n" +
                "    mtf_dados\n" +
                "WHERE\n" +
                "    marcacao = 1\n" +
                "    AND ind_qlt IS NOT NULL\n" +
                "GROUP BY sexo";;

        cursorTemp = app.getDb().rawQuery(qryMIQ,null);

        while (cursorTemp.moveToNext()) {

            String sexo = cursorTemp.getString(0)== null ? "" : cursorTemp.getString(0);;
            double media = cursorTemp.getDouble(2);

            if (sexo.equals("M"))
                mCertIQMacho = media;
            if (sexo.equals("F"))
                mCertIQFemea = media;
        }

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Media Certificados P e F
        String qryMPeso2 = "SELECT \n" +
                "    classificacao_fp, \n" +
                "    COUNT(*) as qt, \n" +
                "    ROUND(AVG(p_marcacao),1) as media_peso\n" +
                "FROM\n" +
                "    mtf_dados\n" +
                "WHERE\n" +
                "    marcacao = 1\n" +
                "    AND classificacao_fp IS NOT NULL\n" +
                "    AND sexo = 'M'\n" +
                "    AND p_marcacao IS NOT NULL\n" +
                "GROUP BY\n" +
                "    classificacao_fp";

        cursorTemp = app.getDb().rawQuery(qryMPeso2,null);

        while (cursorTemp.moveToNext()) {

            String tipo = cursorTemp.getString(0) == null ? "" : cursorTemp.getString(0);
            double media = cursorTemp.getDouble(2);
            int total = cursorTemp.getInt(1);

            if (tipo.equals("P"))
                mPesoCertMachoP = media;
            if (tipo.equals("F"))
                mPesoCertMachoF = media;

        }

        String qryMCe2 = "SELECT \n" +
                "    classificacao_fp, \n" +
                "    COUNT(*) as qt, \n" +
                "    ROUND(AVG(ce_marcacao),1) as media\n" +
                "FROM\n" +
                "    mtf_dados\n" +
                "WHERE\n" +
                "    classificacao_fp IS NOT NULL\n" +
                "    AND sexo = 'M'\n" +
                "    AND ce_marcacao IS NOT NULL\n" +
                "GROUP BY\n" +
                "    classificacao_fp";


        cursorTemp = app.getDb().rawQuery(qryMCe2,null);

        while (cursorTemp.moveToNext()) {

            String tipo =  cursorTemp.getString(0) == null ? "" : cursorTemp.getString(0);
            double media = cursorTemp.getDouble(2);
            int total = cursorTemp.getInt(1);
            if (tipo.equals("P"))
                mCeCertMachoP = media;
            if (tipo.equals("F"))
                mCeCertMachoF = media;

        }

        String qryMClass2 = "SELECT \n" +
                "    classificacao_fp, \n" +
                "    COUNT(*) as qt, \n" +
                "    ROUND(AVG(classificacao),1) as media\n" +
                "FROM\n" +
                "    mtf_dados\n" +
                "WHERE\n" +
                "    classificacao_fp IS NOT NULL\n" +
                "    AND sexo = 'M'\n" +
                "    AND classificacao IS NOT NULL\n" +
                "GROUP BY\n" +
                "    classificacao_fp";

        cursorTemp = app.getDb().rawQuery(qryMClass2,null);

        while (cursorTemp.moveToNext()) {

            String tipo = cursorTemp.getString(0) == null ? "" : cursorTemp.getString(0);
            double media = cursorTemp.getDouble(2);


            if (tipo.equals("P"))
                mCertClassMachoP = media;

            if (tipo.equals("F"))
                mCertClassMachoF = media;
        }

        String qryMIQ2 = "SELECT \n" +
                "    classificacao_fp, \n" +
                "    COUNT(*) as qt, \n" +
                "    ROUND(AVG(ind_qlt),1) as media\n" +
                "FROM\n" +
                "    mtf_dados\n" +
                "WHERE\n" +
                "    classificacao_fp IS NOT NULL\n" +
                "    AND sexo = 'M'\n" +
                "    AND classificacao IS NOT NULL\n" +
                "GROUP BY\n" +
                "    classificacao_fp";

        cursorTemp = app.getDb().rawQuery(qryMIQ2,null);

        while (cursorTemp.moveToNext()) {


            String tipo = cursorTemp.getString(0)== null ? "" : cursorTemp.getString(0);;
            double media = cursorTemp.getDouble(2);
            int total = cursorTemp.getInt(1);

            if (tipo.equals("P")){
                mCertIQMachoP = media;
                mCertTotalP = total;
            }


            if (tipo.equals("F")){
                mCertIQMachoF = media;
                mCertTotalF = total;
            }

        }


        ///////////////////////////////////////////////////////////////////////////////////////////
        // Avaliados
        String qryAval =  "SELECT \n" +
                "    count(*) as qt,\n" +
                "    sexo,\n" +
                "    'C' as tipo\n" +
                "FROM \n" +
                "    mtf_dados\n" +
                "WHERE \n" +
                "    marcacao IS NOT NULL\n" +
                "    AND ceip IN ('C','P')\n" +
                "GROUP BY   \n" +
                "    sexo \n" +
                "UNION \n" +
                "SELECT \n" +
                "    count(*) as qt,\n" +
                "    sexo,\n" +
                "    'S' as tipo\n" +
                "FROM \n" +
                "    mtf_dados\n" +
                "WHERE \n" +
                "    marcacao IS NOT NULL\n" +
                "    AND ceip IN ('S')\n" +
                "GROUP BY   \n" +
                "    sexo \n";;

        Cursor cursorAval = app.getDb().rawQuery(qryAval,null);

        while (cursorAval.moveToNext()){
            int qt = cursorAval.getInt(0);
            String sexo = cursorAval.getString(1);
            String tipo = cursorAval.getString(2);

            if (tipo.equals("C")) {
                if (sexo.equals("M"))
                    qtAnimaisMachoCeipAvaliado = qt;
                else
                    qtAnimaisFemeaCeipAvaliado = qt;
                qtAnimaisCeipAvaliado = qtAnimaisFemeaCeipAvaliado + qtAnimaisMachoCeipAvaliado;
            } else {
                //String media = String.valueOf(curMediaM.getDouble(4)).replace(".",",");
                if (sexo.equals("M"))
                    qtAnimaisMachoSuplenteAvaliado = qt;
                else
                    qtAnimaisFemeaSuplenteAvaliado = qt;
                qtAnimaisSuplenteAvaliado = qtAnimaisFemeaSuplenteAvaliado + qtAnimaisMachoSuplenteAvaliado;
            }

            qtAnimaisMachoAvaliado = qtAnimaisMachoCeipAvaliado + qtAnimaisMachoSuplenteAvaliado;
            qtAnimaisFemeaAvaliado = qtAnimaisFemeaCeipAvaliado + qtAnimaisFemeaSuplenteAvaliado;
        }


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

    public int getQtAnimaisSuplente() {
        return qtAnimaisSuplente;
    }

    public void setQtAnimaisSuplente(int qtAnimaisSuplente) {
        this.qtAnimaisSuplente = qtAnimaisSuplente;
    }

    public int getQtAnimaisMachoSuplente() {
        return qtAnimaisMachoSuplente;
    }

    public void setQtAnimaisMachoSuplente(int qtAnimaisMachoSuplente) {
        this.qtAnimaisMachoSuplente = qtAnimaisMachoSuplente;
    }

    public int getQtAnimaisFemeaSuplente() {
        return qtAnimaisFemeaSuplente;
    }

    public void setQtAnimaisFemeaSuplente(int qtAnimaisFemeaSuplente) {
        this.qtAnimaisFemeaSuplente = qtAnimaisFemeaSuplente;
    }

    public int getQtAnimaisCeip() {
        return qtAnimaisCeip;
    }

    public void setQtAnimaisCeip(int qtAnimaisCeip) {
        this.qtAnimaisCeip = qtAnimaisCeip;
    }

    public int getQtAnimaisMachoCeip() {
        return qtAnimaisMachoCeip;
    }

    public void setQtAnimaisMachoCeip(int qtAnimaisMachoCeip) {
        this.qtAnimaisMachoCeip = qtAnimaisMachoCeip;
    }

    public int getQtAnimaisFemeaCeip() {
        return qtAnimaisFemeaCeip;
    }

    public void setQtAnimaisFemeaCeip(int qtAnimaisFemeaCeip) {
        this.qtAnimaisFemeaCeip = qtAnimaisFemeaCeip;
    }

    public int getQtAnimaisAvaliado() {
        return qtAnimaisAvaliado;
    }

    public void setQtAnimaisAvaliado(int qtAnimaisAvaliado) {
        this.qtAnimaisAvaliado = qtAnimaisAvaliado;
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

    public int getQtAnimaisCeipAvaliado() {
        return qtAnimaisCeipAvaliado;
    }

    public void setQtAnimaisCeipAvaliado(int qtAnimaisCeipAvaliado) {
        this.qtAnimaisCeipAvaliado = qtAnimaisCeipAvaliado;
    }

    public int getQtAnimaisMachoCeipAvaliado() {
        return qtAnimaisMachoCeipAvaliado;
    }

    public void setQtAnimaisMachoCeipAvaliado(int qtAnimaisMachoCeipAvaliado) {
        this.qtAnimaisMachoCeipAvaliado = qtAnimaisMachoCeipAvaliado;
    }

    public int getQtAnimaisFemeaCeipAvaliado() {
        return qtAnimaisFemeaCeipAvaliado;
    }

    public void setQtAnimaisFemeaCeipAvaliado(int qtAnimaisFemeaCeipAvaliado) {
        this.qtAnimaisFemeaCeipAvaliado = qtAnimaisFemeaCeipAvaliado;
    }

    public int getQtAnimaisSuplenteAvaliado() {
        return qtAnimaisSuplenteAvaliado;
    }

    public void setQtAnimaisSuplenteAvaliado(int qtAnimaisSuplenteAvaliado) {
        this.qtAnimaisSuplenteAvaliado = qtAnimaisSuplenteAvaliado;
    }

    public int getQtAnimaisMachoSuplenteAvaliado() {
        return qtAnimaisMachoSuplenteAvaliado;
    }

    public void setQtAnimaisMachoSuplenteAvaliado(int qtAnimaisMachoSuplenteAvaliado) {
        this.qtAnimaisMachoSuplenteAvaliado = qtAnimaisMachoSuplenteAvaliado;
    }

    public int getQtAnimaisFemeaSuplenteAvaliado() {
        return qtAnimaisFemeaSuplenteAvaliado;
    }

    public void setQtAnimaisFemeaSuplenteAvaliado(int qtAnimaisFemeaSuplenteAvaliado) {
        this.qtAnimaisFemeaSuplenteAvaliado = qtAnimaisFemeaSuplenteAvaliado;
    }

    public int getQtAnimaisCertificado() {
        return qtAnimaisCertificado;
    }

    public void setQtAnimaisCertificado(int qtAnimaisCertificado) {
        this.qtAnimaisCertificado = qtAnimaisCertificado;
    }

    public int getQtAnimaisMachoCertificado() {
        return qtAnimaisMachoCertificado;
    }

    public void setQtAnimaisMachoCertificado(int qtAnimaisMachoCertificado) {
        this.qtAnimaisMachoCertificado = qtAnimaisMachoCertificado;
    }

    public int getQtAnimaisFemeaCertificado() {
        return qtAnimaisFemeaCertificado;
    }

    public void setQtAnimaisFemeaCertificado(int qtAnimaisFemeaCertificado) {
        this.qtAnimaisFemeaCertificado = qtAnimaisFemeaCertificado;
    }

    public int getQtAnimaisCertificadoCeip() {
        return qtAnimaisCertificadoCeip;
    }

    public void setQtAnimaisCertificadoCeip(int qtAnimaisCertificadoCeip) {
        this.qtAnimaisCertificadoCeip = qtAnimaisCertificadoCeip;
    }

    public int getQtAnimaisMachoCertificadoCeip() {
        return qtAnimaisMachoCertificadoCeip;
    }

    public void setQtAnimaisMachoCertificadoCeip(int qtAnimaisMachoCertificadoCeip) {
        this.qtAnimaisMachoCertificadoCeip = qtAnimaisMachoCertificadoCeip;
    }

    public int getQtAnimaisFemeaCertificadoCeip() {
        return qtAnimaisFemeaCertificadoCeip;
    }

    public void setQtAnimaisFemeaCertificadoCeip(int qtAnimaisFemeaCertificadoCeip) {
        this.qtAnimaisFemeaCertificadoCeip = qtAnimaisFemeaCertificadoCeip;
    }

    public int getQtAnimaisCertificadoSuplente() {
        return qtAnimaisCertificadoSuplente;
    }

    public void setQtAnimaisCertificadoSuplente(int qtAnimaisCertificadoSuplente) {
        this.qtAnimaisCertificadoSuplente = qtAnimaisCertificadoSuplente;
    }

    public int getQtAnimaisMachoCertificadoSuplente() {
        return qtAnimaisMachoCertificadoSuplente;
    }

    public void setQtAnimaisMachoCertificadoSuplente(int qtAnimaisMachoCertificadoSuplente) {
        this.qtAnimaisMachoCertificadoSuplente = qtAnimaisMachoCertificadoSuplente;
    }

    public int getQtAnimaisFemeaCertificadoSuplente() {
        return qtAnimaisFemeaCertificadoSuplente;
    }

    public void setQtAnimaisFemeaCertificadoSuplente(int qtAnimaisFemeaCertificadoSuplente) {
        this.qtAnimaisFemeaCertificadoSuplente = qtAnimaisFemeaCertificadoSuplente;
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

    public int getQtAnimaisCeipDescarte() {
        return qtAnimaisCeipDescarte;
    }

    public void setQtAnimaisCeipDescarte(int qtAnimaisCeipDescarte) {
        this.qtAnimaisCeipDescarte = qtAnimaisCeipDescarte;
    }

    public int getQtAnimaisMachoCeipDescarte() {
        return qtAnimaisMachoCeipDescarte;
    }

    public void setQtAnimaisMachoCeipDescarte(int qtAnimaisMachoCeipDescarte) {
        this.qtAnimaisMachoCeipDescarte = qtAnimaisMachoCeipDescarte;
    }

    public int getQtAnimaisFemeaCeipDescarte() {
        return qtAnimaisFemeaCeipDescarte;
    }

    public void setQtAnimaisFemeaCeipDescarte(int qtAnimaisFemeaCeipDescarte) {
        this.qtAnimaisFemeaCeipDescarte = qtAnimaisFemeaCeipDescarte;
    }

    public int getQtAnimaisSuplenteDescarte() {
        return qtAnimaisSuplenteDescarte;
    }

    public void setQtAnimaisSuplenteDescarte(int qtAnimaisSuplenteDescarte) {
        this.qtAnimaisSuplenteDescarte = qtAnimaisSuplenteDescarte;
    }

    public int getQtAnimaisMachoSuplenteDescarte() {
        return qtAnimaisMachoSuplenteDescarte;
    }

    public void setQtAnimaisMachoSuplenteDescarte(int qtAnimaisMachoSuplenteDescarte) {
        this.qtAnimaisMachoSuplenteDescarte = qtAnimaisMachoSuplenteDescarte;
    }

    public int getQtAnimaisFemeaSuplenteDescarte() {
        return qtAnimaisFemeaSuplenteDescarte;
    }

    public void setQtAnimaisFemeaSuplenteDescarte(int qtAnimaisFemeaSuplenteDescarte) {
        this.qtAnimaisFemeaSuplenteDescarte = qtAnimaisFemeaSuplenteDescarte;
    }

    public double getmPesoCertMacho() {
        return mPesoCertMacho;
    }

    public void setmPesoCertMacho(double mPesoCertMacho) {
        this.mPesoCertMacho = mPesoCertMacho;
    }

    public double getmPesoCertFemea() {
        return mPesoCertFemea;
    }

    public void setmPesoCertFemea(double mPesoCertFemea) {
        this.mPesoCertFemea = mPesoCertFemea;
    }

    public double getmCeCertMacho() {
        return mCeCertMacho;
    }

    public void setmCeCertMacho(double mCeCertMacho) {
        this.mCeCertMacho = mCeCertMacho;
    }

    public double getmCeCertFemea() {
        return mCeCertFemea;
    }

    public void setmCeCertFemea(double mCeCertFemea) {
        this.mCeCertFemea = mCeCertFemea;
    }

    public double getmCertClassMacho() {
        return mCertClassMacho;
    }

    public void setmCertClassMacho(double mCertClassMacho) {
        this.mCertClassMacho = mCertClassMacho;
    }

    public double getmCertClassFemea() {
        return mCertClassFemea;
    }

    public void setmCertClassFemea(double mCertClassFemea) {
        this.mCertClassFemea = mCertClassFemea;
    }

    public double getmCertIQMacho() {
        return mCertIQMacho;
    }

    public void setmCertIQMacho(double mCertIQMacho) {
        this.mCertIQMacho = mCertIQMacho;
    }

    public double getmCertIQFemea() {
        return mCertIQFemea;
    }

    public void setmCertIQFemea(double mCertIQFemea) {
        this.mCertIQFemea = mCertIQFemea;
    }

    public double getmPesoCertMachoP() {
        return mPesoCertMachoP;
    }

    public void setmPesoCertMachoP(double mPesoCertMachoP) {
        this.mPesoCertMachoP = mPesoCertMachoP;
    }

    public double getmPesoCertMachoF() {
        return mPesoCertMachoF;
    }

    public void setmPesoCertMachoF(double mPesoCertMachoF) {
        this.mPesoCertMachoF = mPesoCertMachoF;
    }

    public double getmCeCertMachoP() {
        return mCeCertMachoP;
    }

    public void setmCeCertMachoP(double mCeCertMachoP) {
        this.mCeCertMachoP = mCeCertMachoP;
    }

    public double getmCeCertMachoF() {
        return mCeCertMachoF;
    }

    public void setmCeCertMachoF(double mCeCertMachoF) {
        this.mCeCertMachoF = mCeCertMachoF;
    }

    public double getmCertClassMachoP() {
        return mCertClassMachoP;
    }

    public void setmCertClassMachoP(double mCertClassMachoP) {
        this.mCertClassMachoP = mCertClassMachoP;
    }

    public double getmCertClassMachoF() {
        return mCertClassMachoF;
    }

    public void setmCertClassMachoF(double mCertClassMachoF) {
        this.mCertClassMachoF = mCertClassMachoF;
    }

    public double getmCertIQMachoP() {
        return mCertIQMachoP;
    }

    public void setmCertIQMachoP(double mCertIQMachoP) {
        this.mCertIQMachoP = mCertIQMachoP;
    }

    public double getmCertIQMachoF() {
        return mCertIQMachoF;
    }

    public void setmCertIQMachoF(double mCertIQMachoF) {
        this.mCertIQMachoF = mCertIQMachoF;
    }

    public int getmCertTotalP() {
        return mCertTotalP;
    }

    public void setmCertTotalP(int mCertTotalP) {
        this.mCertTotalP = mCertTotalP;
    }

    public int getmCertTotalF() {
        return mCertTotalF;
    }

    public void setmCertTotalF(int mCertTotalF) {
        this.mCertTotalF = mCertTotalF;
    }
}
