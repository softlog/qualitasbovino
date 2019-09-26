package br.eti.softlog.Readers;

import android.util.Log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


import br.eti.softlog.qualitasbovino.AppMain;
import br.eti.softlog.qualitasbovino.Manager;

public class ReaderMTFDados {
    AppMain app;
    Manager manager;
    BufferedReader fileReader = null;


    public ReaderMTFDados(AppMain appMain){
        app = appMain;
        manager = new Manager(app);
    }

    public void read(String fileName) {


        try {
            fileReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), "ISO-8859-1"));

            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                    .withHeader("criador","proprietario","animal","pai","mae","datanasc"
                            ,"sexo","situ_reprod","livro","ra_nasc","r_pnasc","ra_desm",
                            "i_pdesm","d_pdesm","r_pdesm","ra_365","i_p365","d_p365","r_p365",
                            "ra_450","i_p450","d_p450","r_p450","ra_550","i_p550","d_p550",
                            "r_p550","i_ce","r_ce","a_ce","i_mus","r_mus")
                    .withIgnoreHeaderCase()
                    .withSkipHeaderRecord()
                    .withTrim());

            for (CSVRecord r : csvParser){
                //Log.d("Animal",r.get("animal"));

                Long pai;
                Long mae;


                String criador = r.get("criador");
                String proprietario = r.get("proprietario");


                Long animal = Long.valueOf(r.get("animal"));

                try {
                    pai = Long.valueOf(r.get("pai"));
                } catch (Error e){
                    pai = null;
                }

                try {
                    mae = Long.valueOf(r.get("mae"));
                } catch (Error e){
                    mae = null;
                }

                String datanasc = r.get("datanasc");
                String sexo = r.get("sexo");
                String situRepro = r.get("situ_reprod");
                String livro = r.get("livro");
                String raNasc = r.get("ra_nasc");

                Double rPNasc = Double.valueOf(r.get("r_pnasc"));

                String raDesm = r.get("ra_desm");

                Double iPDesm;
                try {
                    iPDesm = Double.valueOf(r.get("i_pdesm"));
                } catch (Error e){
                    iPDesm = 0.0;
                }
                String dPDesm = r.get("d_pdesm");
                Double rPDesm = Double.valueOf(r.get("r_pdesm"));

                String ra365 = r.get("ra_365");
                Double iP365 = Double.valueOf(r.get("i_p365"));
                String dP365 = r.get("d_p365");
                Double rP365 = Double.valueOf(r.get("r_p365"));

                String ra450 = r.get("ra_450");
                Double iP450 = Double.valueOf(r.get("i_p450"));
                String dP450 = r.get("d_p450");
                Double rP450 = Double.valueOf(r.get("r_p450"));

                String ra550 = r.get("ra_550");
                Double iP550 = Double.valueOf(r.get("i_p550"));
                String dP550 = r.get("d_p550");
                Double rP550 = Double.valueOf(r.get("r_p550"));

                Double iCe = Double.valueOf(r.get("i_ce"));
                Double rCe = Double.valueOf(r.get("r_ce"));
                Double aCe = Double.valueOf(r.get("a_ce"));
                Double iMus = Double.valueOf(r.get("i_mus"));
                Double rMus = Double.valueOf(r.get("r_mus"));


                manager.addMTFDados(criador,proprietario,animal,pai,mae,
                        datanasc,sexo,situRepro,livro,raNasc,rPNasc,
                        raDesm,iPDesm,dPDesm,rPDesm,
                        ra365,iP365,dP365,rP365,
                        ra450,iP450,dP450,rP450,
                        ra550,iP550,dP550,rP550,
                        iCe,rCe,aCe,iMus,rMus
                        );

                //manager.addAllMedicaoAnimal(animal);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Error e1){
            e1.printStackTrace();
            //Log.d("Erro", e1.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
