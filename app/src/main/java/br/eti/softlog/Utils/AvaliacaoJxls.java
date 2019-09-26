package br.eti.softlog.Utils;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MedicoesAnimal;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Orientation;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



public class AvaliacaoJxls {


    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private String inputArquivo;
    private List<MTFDados> animais;


    public AvaliacaoJxls(List<MTFDados> animais, String inputArquivo){
        this.inputArquivo = inputArquivo;
        this.animais = animais;
    }

    public AvaliacaoJxls(){
        this.inputArquivo = "/mnt/sdcard/qualitas_bovino/exportacao/teste.xls";
    }


    // Exemplo de Como criar uma planilha com JXL no Java
    public void setOutputFile(String inputArquivo) {
        this.inputArquivo = inputArquivo;
    }

    // Método responsável por fazer a escrita, a inserção dos dados na planilha
    public void exportaAvaliacaoFuncional() throws IOException, WriteException {
        // Cria um novo arquivo
        File arquivo = new File(inputArquivo);
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("pt", "BR"));

        WritableWorkbook workbook = Workbook.createWorkbook(arquivo, wbSettings);
        // Define um nome para a planilha
        workbook.createSheet("A_Funcional", 0);
        WritableSheet excelSheet = workbook.getSheet(0);

        criaLabel(excelSheet);
        defineConteudo(excelSheet);

        workbook.write();
        workbook.close();

    }

    // Método responsável pela definição das labels
    private void criaLabel(WritableSheet sheet)
            throws WriteException {


        //Seta a altura da primeira linha
        sheet.setRowView(0,1600);

        // Cria o tipo de fonte como TIMES e tamanho
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);

        // Define o formato da célula
        times = new WritableCellFormat(times10pt);

        // Efetua a quebra automática das células
        times.setWrap(true);

        // Cria a fonte em negrito com underlines
        WritableFont times10ptBoldUnderline = new WritableFont(
                WritableFont.ARIAL, 10, WritableFont.BOLD, false);
        //UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);

        // Efetua a quebra automática das células
        timesBoldUnderline.setWrap(true);


        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setAutosize(true);


        // Escreve os cabeçalhos
        addCaption(sheet, 0, 0, "Animal");
        addCaption(sheet, 1, 0, "Reprodução");
        addCaption(sheet, 2, 0, "Ubere");
        addCaption(sheet, 3, 0, "Musculosidade");
        addCaption(sheet, 4, 0, "Frame");
        addCaption(sheet, 5, 0, "Aprumo");
        addCaption(sheet, 6, 0, "Casco");
        addCaption(sheet, 7, 0, "Ossatura");
        addCaption(sheet, 8, 0, "Profundidade");
        addCaption(sheet, 9, 0, "Dorso");
        addCaption(sheet, 10, 0, "Garupa");
        addCaption(sheet, 11, 0, "Umbigo");
        addCaption(sheet, 12, 0, "Boca");
        addCaption(sheet, 13, 0, "Ins.Cauda");
        addCaption(sheet, 14, 0, "Pelagem");
        addCaption(sheet, 15, 0, "Temperamento");
        addCaption(sheet, 16, 0, "Chanfro");
        addCaption(sheet, 17, 0, "Testículos");
        addCaption(sheet, 18, 0, "Racial");
        addCaption(sheet, 19, 0, "Descarte");

    }

    private void defineConteudo(WritableSheet sheet) throws WriteException,
            RowsExceededException {
        // Escreve alguns números

        int linha;
        linha = 0;
        for (int i = 0; i<this.animais.size();i++){
            List<MedicoesAnimal> medicoes = this.animais.get(i).getMedicoesAnimals();
            linha++;
            addLabel(sheet,0,linha,animais.get(i).getIdf());
            for (int j=0; j < medicoes.size(); j++){
                MedicoesAnimal m = medicoes.get(j);


                switch (m.getMedicoes().getAbrev()){

                    case "REP":{
                        addNumero(sheet,1,linha, m.getValor());
                        break;
                    }
                    case "UBE":{
                        addNumero(sheet,2,linha, m.getValor());
                        break;
                    }
                    case "MUS":{
                        addNumero(sheet,3,linha, m.getValor());
                        break;
                    }
                    case "FRA":{
                        addNumero(sheet,4,linha, m.getValor());
                        break;
                    }
                    case "APR":{
                        addNumero(sheet,5,linha, m.getValor());
                        break;
                    }
                    case "CAS":{
                        addNumero(sheet,6,linha, m.getValor());
                        break;
                    }
                    case "OSS":{
                        addNumero(sheet,7,linha, m.getValor());
                        break;
                    }
                    case "PRO":{
                        addNumero(sheet,8,linha, m.getValor());
                        break;
                    }
                    case "DOR":{
                        addNumero(sheet,9,linha, m.getValor());
                        break;
                    }
                    case "GAR":{
                        addNumero(sheet,10,linha, m.getValor());
                        break;
                    }
                    case "UMB":{
                        addNumero(sheet,11,linha, m.getValor());
                        break;
                    }
                    case "BOC":{
                        addNumero(sheet,12,linha, m.getValor());
                        break;
                    }
                    case "CAU":{
                        addNumero(sheet,13,linha, m.getValor());
                        break;
                    }
                    case "PLA":{
                        addNumero(sheet,14,linha, m.getValor());
                        break;
                    }
                    case "TEM":{
                        addNumero(sheet,15,linha, m.getValor());
                        break;
                    }
                    case "CHA":{
                        addNumero(sheet,16,linha, m.getValor());
                        break;
                    }
                    case "TTE":{
                        addNumero(sheet,17,linha, m.getValor());
                        break;
                    }
                    case "RAC":{
                        addNumero(sheet,18,linha, m.getValor());
                        break;
                    }
                    case "DES":{
                        addNumero(sheet,19,linha, m.getValor());
                        break;
                    }
                }
            }

        }

    }

    // Adiciona cabecalho
    private void addCaption(WritableSheet planilha, int coluna, int linha, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(coluna, linha, s, timesBoldUnderline);

        WritableCellFormat format = new WritableCellFormat(label.getCellFormat());


        if (coluna>0){
            format.setOrientation(Orientation.PLUS_90);
            planilha.setColumnView(coluna,6);
        } else {
            planilha.setColumnView(coluna,13);
        }

        format.setAlignment(Alignment.CENTRE);
        label.setCellFormat(format);


        planilha.addCell(label);
    }

    private void addNumero(WritableSheet planilha, int coluna, int linha,
                           Long valor) throws WriteException, RowsExceededException {

        Number numero;
        if (!(valor == null)){
            numero = new Number(coluna, linha, valor, times);
            planilha.addCell(numero);
        }

    }

    private void addLabel(WritableSheet planilha, int coluna, int linha, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(coluna, linha, s, times);
        planilha.addCell(label);
    }

}
