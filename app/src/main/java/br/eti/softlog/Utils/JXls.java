package br.eti.softlog.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class JXls {

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private String inputArquivo;

    // Exemplo de Como criar uma planilha com JXL no Java
    public void setOutputFile(String inputArquivo) {
        this.inputArquivo = inputArquivo;
    }

    // Método responsável por fazer a escrita, a inserção dos dados na planilha
    public void insere() throws IOException, WriteException {
        // Cria um novo arquivo
        File arquivo = new File(inputArquivo);
        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("pt", "BR"));

        WritableWorkbook workbook = Workbook.createWorkbook(arquivo, wbSettings);
        // Define um nome para a planilha
        workbook.createSheet("Jexcel", 0);
        WritableSheet excelSheet = workbook.getSheet(0);
        criaLabel(excelSheet);
        defineConteudo(excelSheet);

        workbook.write();
        workbook.close();
    }

    // Método responsável pela definição das labels
    private void criaLabel(WritableSheet sheet)
            throws WriteException {
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
        addCaption(sheet, 0, 0, "Coluna 1");
        addCaption(sheet, 1, 0, "Coluna 2");
        addCaption(sheet, 2, 0, "Coluna 3");
    }

    private void defineConteudo(WritableSheet sheet) throws WriteException,
            RowsExceededException {
        // Escreve alguns números
        for (int i = 1; i < 10; i++) {
            // Primeira coluna
            addNumero(sheet, 0, i, i + 10);
            // Segunda coluna
            addNumero(sheet, 1, i, i * i);
            // Terceira coluna
            addNumero(sheet, 2, i, 10 - i);
        }

        // Efetua a soma das colunas criadas anteriormente
        StringBuffer buf = new StringBuffer();
        buf.append("SUM(A2:A10)");
        Formula f = new Formula(0, 10, buf.toString());
        sheet.addCell(f);
        buf = new StringBuffer();
        buf.append("SUM(B2:B10)");
        f = new Formula(1, 10, buf.toString());
        sheet.addCell(f);
        buf = new StringBuffer();
        buf.append("SUM(C2:C10)");
        f = new Formula(2, 10, buf.toString());
        sheet.addCell(f);

        // Agora vamos inserir algum texto nas colunas
        for (int i = 12; i < 20; i++) {
            // Primeira coluna
            addLabel(sheet, 0, i, "JExcel " + i);
            // Segunda coluna
            addLabel(sheet, 1, i, "Tutorial");
            // Terceira coluna
            addLabel(sheet, 2, i, "Exemplo" + (10 - i));
        }
    }

    // Adiciona cabecalho
    private void addCaption(WritableSheet planilha, int coluna, int linha, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(coluna, linha, s, timesBoldUnderline);
        planilha.addCell(label);
    }

    private void addNumero(WritableSheet planilha, int coluna, int linha,
                           Integer integer) throws WriteException, RowsExceededException {
        Number numero;
        numero = new Number(coluna, linha, integer, times);
        planilha.addCell(numero);
    }

    private void addLabel(WritableSheet planilha, int coluna, int linha, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(coluna, linha, s, times);
        planilha.addCell(label);
    }

}