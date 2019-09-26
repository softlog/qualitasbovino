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

public class ReaderPedigree
{

    AppMain app;
    Manager manager;
    BufferedReader fileReader = null;

    public ReaderPedigree(AppMain appMain){

        app = appMain;
        manager = new Manager(app);
    }

    public void read(String fileName) {


        try {
            fileReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), "ISO-8859-1"));

            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                    .withHeader("animal","pai","mae","nome","idf","criador","safra")
                    .withIgnoreHeaderCase()
                    .withSkipHeaderRecord()
                    .withTrim());

            for (CSVRecord csvRecord : csvParser){
                //Log.d("Animal",csvRecord.get("animal"));

                Long pai;
                Long mae;
                int safra;

                Long animal = Long.valueOf(csvRecord.get("animal"));
                try {
                    pai = Long.valueOf(csvRecord.get("pai"));
                } catch (Error e){
                    pai = null;
                }

                try {
                    mae = Long.valueOf(csvRecord.get("mae"));
                } catch (Error e){
                    mae = null;
                }

                String nome = csvRecord.get("nome");
                String idf = csvRecord.get("idf");
                String criador = csvRecord.get("criador");

                try {
                    safra = Integer.valueOf(csvRecord.get("safra"));
                } catch (Error e){
                    safra = Integer.parseInt(null);
                }

                manager.addPedigree(animal,animal,pai,mae,nome,idf,criador,safra);

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
