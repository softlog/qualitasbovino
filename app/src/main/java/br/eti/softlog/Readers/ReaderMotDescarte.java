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

public class ReaderMotDescarte {

    AppMain app;
    Manager manager;
    BufferedReader fileReader = null;

    public ReaderMotDescarte(AppMain appMain){

        app = appMain;
        manager = new Manager(app);

    }

    public void read(String fileName) {


        try {
            fileReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), "ISO-8859-1"));

            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                    .withHeader("motivo","descricao","descricaocurta")
                    .withIgnoreHeaderCase()
                    .withSkipHeaderRecord()
                    .withTrim());

            for (CSVRecord csvRecord : csvParser){
                //Log.d("Descricao Criador",csvRecord.get("descricao"));

                String cMotivo = csvRecord.get("motivo");
                String descricao = csvRecord.get("descricao");
                String descricaoCurta = csvRecord.get("descricaoCurta");

                Log.d("Gravando", csvRecord.get("descricao"));
                manager.addMotivoDescarte(Long.valueOf(cMotivo), descricao, descricaoCurta);
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
