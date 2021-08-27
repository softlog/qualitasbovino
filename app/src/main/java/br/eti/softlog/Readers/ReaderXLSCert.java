package br.eti.softlog.Readers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import br.eti.softlog.model.MTFDados;
import br.eti.softlog.qualitasbovino.AppMain;
import br.eti.softlog.qualitasbovino.Manager;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReaderXLSCert {

    AppMain app;
    Manager manager;
    BufferedReader fileReader = null;
    Workbook workbook;

    public ReaderXLSCert(AppMain app){
        app = app;
        manager = new Manager(app);

    }

    public void read(String fileXLS){

        //Log.d("Arquivo",fileXLS);
        try {
            //File file = new File("/mnt/sdcard/qualitas_bovino/importacao_certificacao/QLT_JM_2017_Marcacao_20190402_1435.xls");
            File file = new File(fileXLS);
            workbook = Workbook.getWorkbook(file);

            Sheet sh1 = workbook.getSheet(0);
            Sheet sh2 = workbook.getSheet(1);
            Sheet sh3 = workbook.getSheet(2);
            Sheet sh4 = workbook.getSheet(3);

            readSheet(sh1);
            readSheet(sh2);
            readSheet(sh3);
            readSheet(sh4);
            workbook.close();
            Log.d("Pausa","Debug");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }


    }

    public void readSheet(Sheet sheet) {
        int i;
        i = 1;
        String sheetName = sheet.getName();
        String ceip;
        if (sheetName.contains("Suplentes")){
            ceip = "S";
        } else {
            ceip = "C";
        }

        while (true) {
            Cell[] row1 = sheet.getRow(i);
            Cell[] row2 = sheet.getRow(i + 1);


            Log.d("Lendo Linha",String.valueOf(i));
            String codigoCriador = row1[0].getContents();
            String sexo = row2[0].getContents();

            if (codigoCriador.isEmpty())
                break;
            String idf = row1[1].getContents();
            String dataNasc = row2[1].getContents();

            String depNascStr = row1[4].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double depNasc = Double.valueOf(depNascStr);

            String percNasc = row1[5].getContents();

            String pNascStr = row2[4].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double pNasc = Double.valueOf(pNascStr);

            String depDesmStr = row1[6].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double depDesm = Double.valueOf(depDesmStr);

            String percDesmStr = row1[7].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");

            Double percDesm = Double.valueOf(percDesmStr);

            String pDesmStr = row2[6].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double pDesm = Double.valueOf(pDesmStr);


            String depGPDStr = row1[8].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double depGPD = Double.valueOf(depGPDStr);

            String percGPDStr = row1[9].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");

            Double percGPD = Double.valueOf(percGPDStr);
            String pGPDStr = row2[8].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double pGPD = Double.valueOf(pGPDStr);

            String depSobStr = row1[10].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double depSob = Double.valueOf(depSobStr);

            String percSobStr = row1[11].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double percSob = Double.valueOf(percSobStr);

            String pSobStr = row2[10].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");

            Double pSob = Double.valueOf(pSobStr);

            String depCEStr = row1[12].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double depCE = Double.valueOf(depCEStr);

            String percCEStr = row1[13].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double percCE = Double.valueOf(percCEStr);

            String ceStr = row2[12].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double ce;

            if (sexo.equals("M"))
                ce = Double.valueOf(ceStr);
            else
                ce = null;

            String iCEStr = row2[13].getContents().replace(",",".")
                    .replace(">","")
                    .replace("<","");
            Double iCE;

            if (sexo.equals("M"))
                iCE = Double.valueOf(iCEStr);
            else
                iCE = null;

            String depMuscStr = row1[14].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double depMusc = Double.valueOf(depMuscStr);

            String percMuscStr = row1[15].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double percMusc = Double.valueOf(percMuscStr);

            String muscStr = row2[14].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double musc = Double.valueOf(muscStr);

            String indQltStr = row1[16].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double indQlt = Double.valueOf(indQltStr);

            String rankQltStr = row2[16].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double rankQlt = Double.valueOf(rankQltStr);

            String percQltStr = row1[17].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");
            Double percQlt = Double.valueOf(percQltStr);

            String cCeip = row2[17].getContents().replace(",",".")
                    .replace("%","")
                    .replace(">","")
                    .replace("<","");

            String cIdAnimalStr = row1[24].getContents();
            Long idAnimal = Long.valueOf(cIdAnimalStr);
            Log.d("Gravando Animal", idf);

            String cIdMaeStr = row1[25].getContents();
            int idMae = Integer.valueOf(cIdMaeStr);


            MTFDados animal = manager.addMTFDadosCert(codigoCriador,codigoCriador,idAnimal,pNasc,
                    dataNasc,sexo,percNasc,depNasc,depDesm,percDesm,pDesm, depGPD,percGPD,pGPD,depSob,
                    percSob,pSob,depCE,percCE,ce,iCE,depMusc,percMusc,musc,indQlt,percQlt,rankQlt,cCeip, idMae);

            i = i + 2;
        }

    }
}
