package br.eti.softlog.qualitasbovino;

import android.app.AlertDialog;
import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.AnimalNovo;
import br.eti.softlog.model.AnimalNovoDao;
import br.eti.softlog.model.Criador;
import br.eti.softlog.model.CriadorDao;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MTFDadosDao;
import br.eti.softlog.model.Medicao;
import br.eti.softlog.model.MedicaoDao;
import br.eti.softlog.model.MedicoesAnimal;
import br.eti.softlog.model.MedicoesAnimalDao;
import br.eti.softlog.model.MotivoDescarte;
import br.eti.softlog.model.MotivoDescarteDao;
import br.eti.softlog.model.Pedigree;
import br.eti.softlog.model.PedigreeDao;

public class Manager {

    private AppMain app ;

    Map<String,String> letras = new HashMap<String,String>();
    Map<String,String> numeros = new HashMap<String,String>();
    Util util;

    public Manager(AppMain myapp) {
        app = myapp;

        util = new Util();

        letras.put("A","11");
        letras.put("B","12");
        letras.put("C","13");
        letras.put("D","14");
        letras.put("E","15");
        letras.put("F","16");
        letras.put("G","17");
        letras.put("H","18");
        letras.put("I","19");
        letras.put("J","20");
        letras.put("K","21");
        letras.put("L","22");
        letras.put("M","23");
        letras.put("N","24");
        letras.put("O","25");
        letras.put("P","26");
        letras.put("Q","27");
        letras.put("R","28");
        letras.put("S","29");
        letras.put("T","30");
        letras.put("U","31");
        letras.put("V","32");
        letras.put("W","33");
        letras.put("X","34");
        letras.put("Y","35");
        letras.put("Z","36");
        letras.put("0","37");
        letras.put("1","38");
        letras.put("2","39");
        letras.put("3","40");
        letras.put("4","41");
        letras.put("5","42");
        letras.put("6","43");
        letras.put("7","44");
        letras.put("8","45");
        letras.put("9","46");


        numeros.put("11","A");
        numeros.put("12","B");
        numeros.put("13","C");
        numeros.put("14","D");
        numeros.put("15","E");
        numeros.put("16","F");
        numeros.put("17","G");
        numeros.put("18","H");
        numeros.put("19","I");
        numeros.put("20","J");
        numeros.put("21","K");
        numeros.put("22","L");
        numeros.put("23","M");
        numeros.put("24","N");
        numeros.put("25","O");
        numeros.put("26","P");
        numeros.put("27","Q");
        numeros.put("28","R");
        numeros.put("29","S");
        numeros.put("30","T");
        numeros.put("31","U");
        numeros.put("32","V");
        numeros.put("33","W");
        numeros.put("34","X");
        numeros.put("35","Y");
        numeros.put("36","Z");
        numeros.put("37","0");
        numeros.put("38","1");
        numeros.put("39","2");
        numeros.put("40","3");
        numeros.put("41","4");
        numeros.put("42","5");
        numeros.put("43","6");
        numeros.put("44","7");
        numeros.put("45","8");
        numeros.put("46","9");

    }

    public Criador findCriadorByCodigo(String codigo){

        return app.getDaoSession().getCriadorDao().queryBuilder().
                where(CriadorDao.Properties.Codigo.eq(codigo)).unique();
    }

    public Criador addCriador(String codigo, String descricao, String fazenda,
                              String municipio, String uf, String paisSigla) {

        Criador criador = findCriadorByCodigo(codigo);

        if (criador==null) {

            criador = new Criador();
            Long id = codigoToId(codigo);

            criador.setId(id);
            criador.setCodigo(codigo);
            criador.setDescricao(descricao);
            criador.setFazenda(fazenda);
            criador.setMuncipio(municipio);
            criador.setUf(uf);
            criador.setPaisSigla(paisSigla);

            app.getDaoSession().insert(criador);
        }

        return criador;
    }

    public MotivoDescarte findMotivoById(Long idMotivo){

        return app.getDaoSession().getMotivoDescarteDao().queryBuilder().
                where(MotivoDescarteDao.Properties.Id.eq(idMotivo)).unique();
    }

    public MotivoDescarte addMotivoDescarte(Long codigo, String descricao, String descricaoCurta) {

        MotivoDescarte motivoDescarte = findMotivoById(codigo);

        if (motivoDescarte==null) {

            motivoDescarte = new MotivoDescarte();


            motivoDescarte.setId(codigo);
            motivoDescarte.setDescricao(descricao);
            motivoDescarte.setAbrev(descricaoCurta);

            app.getDaoSession().insert(motivoDescarte);
        }

        return motivoDescarte;
    }

    public Pedigree findPedigreeByAnimal(Long id){
        return app.getDaoSession().getPedigreeDao().queryBuilder().
                where(PedigreeDao.Properties.Animal.eq(id)).unique();
    }

    public Pedigree addPedigree(Long id, Long animal, Long pai, Long mae, String nome,
                                String idf, String codigo, int safra){
        Pedigree pedigree = findPedigreeByAnimal(id);

        boolean alterar;
        alterar = false;

        if (!(pedigree==null) && id < 0)
            alterar = true;

        //Se pedigree não existe, ou é um animal novo alterado
        if (pedigree==null || alterar) {

            if (!alterar)
                pedigree = new Pedigree();

            pedigree.setId(id);
            pedigree.setAnimal(animal);
            pedigree.setPai(pai);
            pedigree.setMae(mae);
            pedigree.setNome(nome);
            pedigree.setIdf(idf);
            pedigree.setCriadorId(codigoToId(codigo));
            pedigree.setSafra(safra);

            if (alterar)
                app.getDaoSession().update(pedigree);
            else
                app.getDaoSession().insert(pedigree);


        }

        return pedigree;
    }

    public MTFDados findMTFDadosByAnimal(Long animal) {

        return app.getDaoSession().getMTFDadosDao().queryBuilder().
                where(MTFDadosDao.Properties.Animal.eq(animal)).unique();
    }

    public MTFDados findMTFDadosByIdfCriador(String idf, Long criador) {

        return app.getDaoSession().getMTFDadosDao().queryBuilder()
                .where(MTFDadosDao.Properties.Idf.eq(idf))
                .where(MTFDadosDao.Properties.CriadorId.eq(criador))
                .unique();
    }

    public MTFDados addMTFDados(String criador, String proprietario, Long animal,
                                Long pai, Long mae, String dataNasc, String sexo, String situRepro,
                                String livro, String raNasc, Double rPNasc, String raDesm,
                                Double iPDesm, String dPDesm, Double rPDesm, String ra365, Double iP365,
                                String dP365, Double rP365, String ra450, Double iP450, String dP450,
                                Double rP450, String ra550, Double iP550, String dP550, Double rP550,
                                Double iCe, Double rCe, Double aCe, Double iMus, Double rMus) {



        Pedigree pedigree = findPedigreeByAnimal(animal);

        MTFDados dados;

        boolean alterar;
        alterar = false;


        if (animal > 0)
            dados = findMTFDadosByIdfCriador(pedigree.getIdf(),codigoToId(criador));
        else
            dados = findMTFDadosByAnimal(animal);

        if (!(dados==null) && animal < 0)
            alterar =  true;

        //Log.d("Importando",pedigree.getIdf());

        if (dados==null || alterar) {

            if (!alterar)
                dados = new MTFDados();

            dados.setId(animal);
            dados.setCriadorId(codigoToId(criador));
            dados.setProprietarioId(codigoToId(proprietario));
            dados.setAnimal(animal);
            dados.setPai(pai);
            dados.setMae(mae);
            dados.setDataNasc(dataNasc);
            dados.setSexo(sexo);
            dados.setSituRepro(situRepro);
            dados.setLivro(livro);
            dados.setRaNasc(raNasc);
            dados.setRPNasc(rPNasc);
            dados.setRaDesm(raDesm);
            dados.setIPDesm(iPDesm);
            dados.setDPDesm(dPDesm);
            dados.setRPDesm(rPDesm);
            dados.setRa365(ra365);
            dados.setIP365(iP365);
            dados.setDP365(dP365);
            dados.setRP365(rP365);
            dados.setRa450(ra450);
            dados.setIP450(iP450);
            dados.setDP450(dP450);
            dados.setRP450(rP450);
            dados.setRa550(ra550);
            dados.setIP550(iP550);
            dados.setDP550(dP550);
            dados.setRP550(rP550);
            dados.setICe(iCe);
            dados.setRCe(rCe);
            dados.setACe(aCe);
            dados.setIMus(iMus);
            dados.setRMus(rMus);
            dados.setImportado(1);
            dados.setAvaliado(false);
            dados.setIdf(pedigree.getIdf());
            dados.setIdf2(util.trataIdf(pedigree.getIdf()));

            dados.setPrefixIdf(util.getPrefixoIdf(pedigree.getIdf()));
            dados.setCodigoIdf(util.getCodigoIdf(pedigree.getIdf()));
//            String filename = pedigree.getIdf();
//            String[] partes = filename.split(" ");
//
//            if (partes.length == 2){
//                dados.setPrefixIdf(partes[0]);
//                dados.setCodigoIdf(Long.valueOf(partes[1]));
//            } else {
//                dados.setCodigoIdf(Long.valueOf(partes[0]));
//            }

            if (alterar)
                app.getDaoSession().update(dados);
            else
                app.getDaoSession().insert(dados);

        } else {
            dados.setIdf2(util.trataIdf(pedigree.getIdf()));
            app.getDaoSession().update(dados);
        }
        return dados;
    }

    public MTFDados addMTFDadosCert(String criador, String proprietario, Long animal, Double pNasc,
                                    String dataNasc, String sexo, String percNasc, Double depNasc,
                                    Double depDesm, Double percDesm, Double pDesm, Double depGPD,
                                    Double percGPD,Double pGPD, Double depSob, Double percSob,
                                    Double pSob,Double depCE, Double percCE,  Double ce, Double rcCE,
                                    Double depMusc, Double percMusc, Double musc, Double indQlt,
                                    Double percQlt, Double rankQlt, String ceip) {

        Pedigree pedigree = findPedigreeByAnimal(animal);
        MTFDados dados = findMTFDadosByIdfCriador(pedigree.getIdf(),codigoToId(criador));

        Log.d("Importando",pedigree.getIdf());

        if (dados==null) {
            dados = new MTFDados();
            dados.setId(animal);
            dados.setCriadorId(codigoToId(criador));
            dados.setProprietarioId(codigoToId(proprietario));
            dados.setAnimal(animal);
            dados.setPai(pedigree.getPai());
            dados.setMae(pedigree.getMae());
            dados.setDataNasc("20" + dataNasc.substring(6,8) + "-" + dataNasc.substring(3,5) + "-" + dataNasc.substring(0,2));
            dados.setSexo(sexo);
            dados.setRPNasc(pNasc);
            dados.setPercNasc(percNasc);
            dados.setDepNasc(depNasc);
            dados.setDepDesm(depDesm);
            dados.setPercDesm(percDesm);
            dados.setRPDesm(pDesm);
            dados.setDepGPD(depGPD);
            dados.setPercGPD(percGPD);
            dados.setPGPD(pGPD);
            dados.setDepSob(depSob);
            dados.setPercSob(percSob);
            dados.setPSob(pSob);
            dados.setDepCE(depCE);
            dados.setPercCE(percCE);
            dados.setRcCE(rcCE);
            dados.setCe(ce);
            dados.setICe(rcCE);
            dados.setDep_musc(depMusc);
            dados.setMusc(musc);
            dados.setPerc_musc(percMusc);
            dados.setIndQlt(indQlt);
            dados.setPercQlt(percQlt);
            dados.setRank_qlt(rankQlt);
            dados.setIdf(pedigree.getIdf());
            dados.setIdf2(util.trataIdf(pedigree.getIdf()));
            dados.setPrefixIdf(util.getPrefixoIdf(pedigree.getIdf()));
            dados.setCodigoIdf(util.getCodigoIdf(pedigree.getIdf()));
            dados.setCeip(ceip);
            dados.setAvaliado(false);
            dados.setP_marcacao(null);
            dados.setCe_marcacao(null);
            dados.setMarcacao(null);
            dados.setMotDescId(null);
            dados.setMocho(null);
            dados.setClassificacao(null);


            app.getDaoSession().insert(dados);
        }
        return dados;
    }
    public Long codigoToId(String codigo){

        //Log.d("Codigo",codigo);
        String letra1 = codigo.substring(0,1);
        String letra2 = codigo.substring(1,2);
        String digito1 = letras.get(letra1.toUpperCase());
        String digito2 = letras.get(letra2.toUpperCase());

        return Long.valueOf(digito1 + digito2);

    }

    public String IdToCodigo(Long id){

        String idStr = String.valueOf(id);

        String numero1 = idStr.substring(0,2);
        String numero2 = idStr.substring(2,4);

        String digito1 = numeros.get(numero1);
        String digito2 = numeros.get(numero2);

        return digito1 + digito2;

    }

    public Long codigoToId3(String codigo){

        //Log.d("Codigo",codigo);
        String letra1 = codigo.substring(0,1);
        String letra2 = codigo.substring(1,2);
        String letra3 = codigo.substring(2,3);

        String digito1 = letras.get(letra1.toUpperCase());
        String digito2 = letras.get(letra2.toUpperCase());
        String digito3 = letras.get(letra3.toUpperCase());

        return Long.valueOf(digito1 + digito2 + digito3);

    }

    public String IdToCodigo3(Long id){

        String idStr = String.valueOf(id);

        String numero1 = idStr.substring(0,2);
        String numero2 = idStr.substring(2,4);
        String numero3 = idStr.substring(4,6);

        String digito1 = numeros.get(numero1);
        String digito2 = numeros.get(numero2);
        String digito3 = numeros.get(numero3);

        return digito1 + digito2 + digito3;

    }

    public Medicao findMedicaoById(Long id){
        return app.getDaoSession().getMedicaoDao().queryBuilder().
                where(MedicaoDao.Properties.Id.eq(id)).unique();

    }

    public Medicao addMedicao(int ordem, String descricao, String abrev,
                              int menorValor, int maiorValor, String sexo, int restricao,
                              int descarte1,int descarte2, int descarte3, int descarte4,
                              boolean obrigatorio){

        Long id = codigoToId3(abrev);

        Medicao medicao = findMedicaoById(id);
        //Log.d("Caracteristica",descricao);
        if (medicao == null) {

            medicao = new Medicao();
            medicao.setId(id);
            medicao.setOrdem(ordem);
            medicao.setAbrev(abrev);
            medicao.setDescricao(descricao);
            medicao.setMenorValor(menorValor);
            medicao.setMaiorValor(maiorValor);
            medicao.setSexo(sexo);
            medicao.setRestricao(restricao);
            medicao.setDescarte1(descarte1);
            medicao.setDescarte2(descarte2);
            medicao.setDescarte3(descarte3);
            medicao.setDescarte4(descarte4);
            medicao.setObrigatorio(obrigatorio);
            app.getDaoSession().insert(medicao);
        } else {

            medicao.setRestricao(restricao);
            medicao.setDescarte1(descarte1);
            medicao.setDescarte2(descarte2);
            medicao.setDescarte3(descarte3);
            medicao.setDescarte4(descarte4);
            medicao.setObrigatorio(obrigatorio);
            app.getDaoSession().update(medicao);

        }


        return medicao;

    }

    public void addAllMedicoes(){

        addMedicao(1,"REPRODUÇÃO","REP",1,6,"A",
                9,1,2,3,9, true);

        addMedicao(2,"UBERE","UBE",1,4,"F",
                0,0,0,0,0,true);

        addMedicao(3,"MUSCULOSIDADE","MUS",1,6,"A",
                0,1,2,3,0,true);

        addMedicao(4,"FRAME","FRA",1,5,"A",
                0,1,5,0,0,true);

        addMedicao(5,"APRUMOS","APR",1,5,"A",
                9,1,5,9,0,true);

        addMedicao(6,"CASCO","CAS",0,1,"A",
                0,1,0,0,0, false);

        addMedicao(7,"OSSATURA","OSS",1,4,"A",
                0,0,0,0,0, true);

        addMedicao(8,"PROFUNDIDADE","PRO",1,5,"A",
                0,1,2,0,0, true);

        addMedicao(9,"LINHA DORSO","DOR",0,1,"A",
                0,1,0,0,0, false);

        addMedicao(10,"GARUPA","GAR",1,4,"A",
                9,1,9,0,0, true);

        addMedicao(11,"UMBIGO","UMB",1,5,"A",
                    9,1,5,9,0, true);

        addMedicao(12,"BOCA","BOC",1,5,"A",
                9,1,9,0,0, true);

        addMedicao(13,"INSERÇÃO CAUDA","CAU",1,4,"A",
                0,1,0,0,0, true);

        addMedicao(14,"PELAGEM","PLA",1,4,"A",
                9,1,9,0,0, true);

        addMedicao(15,"TEMPERAMENTO","TEM",1,5,"A",
                0,1,0,0,0, true);

        addMedicao(16,"CHANFRO","CHA",0,1,"A",
                0,1,0,0,0, false);

        addMedicao(17, "TORÇÃO TESTICULAR","TTE",1,3,"M",
                0,1,0,0,0, true);

        addMedicao(18,"RACIAL","RAC",1,4,"A",
                9,1,9,0,0, true);

        addMedicao(19,"DESCARTE","DES",0,1,"A",
                0,1,0,0,0, false);

        addMedicao(19,"VENDA","VEN",0,1,"A",
                0,0,0,0,0, false);

        addMedicao(20,"LOTE","LOT",0,1,"A",
                0,0,0,0,0, false);
    }

    public MedicoesAnimal findMedicoesAnimalByAnimalIdMedida(Long animal, Long medicaoId) {

        return app.getDaoSession().getMedicoesAnimalDao().queryBuilder().where(
                MedicoesAnimalDao.Properties.Animal.eq(animal),
                MedicoesAnimalDao.Properties.MedicaoId.eq(medicaoId)
        ).unique();

    }


    public void addAllMedicaoAnimal(Long idAnimal) {

        List<Medicao> medicoes;

        medicoes = app.getDaoSession().getMedicaoDao().queryBuilder().
                orderAsc(MedicaoDao.Properties.Ordem).list();

        MedicoesAnimal medicoesAnimal;
        MTFDados animal = findMTFDadosByAnimal(idAnimal);

        for (int i=0;i<medicoes.size();i++){

            Medicao medicao = medicoes.get(i);
            medicoesAnimal = findMedicoesAnimalByAnimalIdMedida(idAnimal,medicao.getId());

            if (animal.getSexo().equals("M") && medicao.getSexo().equals("F")) {
                continue;
            }

            if (animal.getSexo().equals("F") && medicao.getSexo().equals("M")) {
                continue;
            }


            if (medicoesAnimal==null){
                medicoesAnimal = new MedicoesAnimal();
                medicoesAnimal.setAnimal(idAnimal);
                medicoesAnimal.setMedicaoId(medicoes.get(i).getId());
                medicoesAnimal.setDescarte(false);


                app.getDaoSession().insert(medicoesAnimal);
            }
        }


        return;
    }

    public MTFDados addAnimalNovo(String idf, String nome, String sexo, String dataNascimento,
                                    String dataRegistro, Long proprietarioId, Long criadorId){

        AnimalNovo animalNovo = new AnimalNovo();

        animalNovo.setIdf(idf);
        animalNovo.setNome(nome);
        animalNovo.setSexo(sexo);
        animalNovo.setDataNascimento(dataNascimento);
        animalNovo.setDataRegistro(dataRegistro);
        animalNovo.setProprietarioId(proprietarioId);
        animalNovo.setCriadorId(criadorId);

        app.getDaoSession().insert(animalNovo);


        //Inserir na tabela de pedigree
        int safra = Prefs.getInt("safra",Integer.valueOf(0));
        Long id;
        id = animalNovo.getId() * -1;

        addPedigree(id,id,Long.valueOf(0),Long.valueOf(0),nome,idf,IdToCodigo(criadorId), safra);

        //Inserir na tabela de dados
        MTFDados animal = addMTFDados(IdToCodigo(criadorId),IdToCodigo(criadorId),id,Long.valueOf(0),Long.valueOf(0),
                dataNascimento,sexo,"","","",0.0,"",0.0,
                "",0.0,"",0.0,"",0.0,"",
                0.0,"",0.0,"",0.0,"",0.0,0.0,
                0.0,0.0,0.0,0.0);

        animal.setImportado(0);
        app.getDaoSession().update(animal);

        return animal;
    };

    public MTFDados editAnimalNovo(Long id, String idf, String nome, String sexo, String dataNascimento,
                                  String dataRegistro, Long proprietarioId, Long criadorId){

        AnimalNovo animalNovo = app.getDaoSession().getAnimalNovoDao().queryBuilder()
                                    .where(AnimalNovoDao.Properties.Id.eq(id * -1)).unique();

        animalNovo.setIdf(idf);
        animalNovo.setNome(nome);
        animalNovo.setSexo(sexo);
        animalNovo.setDataNascimento(dataNascimento);
        animalNovo.setDataRegistro(dataRegistro);
        animalNovo.setProprietarioId(proprietarioId);
        animalNovo.setCriadorId(criadorId);

        app.getDaoSession().update(animalNovo);

        //Inserir na tabela de pedigree
        int safra = Prefs.getInt("safra",Integer.valueOf(0));


        addPedigree(id,id,Long.valueOf(0),Long.valueOf(0),nome,idf,IdToCodigo(criadorId), safra);

        //Inserir na tabela de dados
        MTFDados animal = addMTFDados(IdToCodigo(criadorId),IdToCodigo(criadorId),id,Long.valueOf(0),Long.valueOf(0),
                dataNascimento,sexo,"","","",0.0,"",0.0,
                "",0.0,"",0.0,"",0.0,"",
                0.0,"",0.0,"",0.0,"",0.0,0.0,
                0.0,0.0,0.0,0.0);

        animal.setImportado(0);
        app.getDaoSession().update(animal);

        return animal;
    };

}
