package br.eti.softlog.certificacao;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.celites.androidexternalfilewriter.AppExternalFileWriter;
import com.github.angads25.filepicker.model.DialogProperties;
import com.omega_r.libs.OmegaCenterIconButton;
import com.pixplicity.easyprefs.library.Prefs;


import java.io.File;
import java.io.IOException;

import java.io.PrintWriter;

import br.eti.softlog.Utils.AvaliacaoJxls;

import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.Criador;
import br.eti.softlog.model.CriadorDao;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MTFDadosDao;

import br.eti.softlog.model.ResumoCertificacao;
import br.eti.softlog.qualitasbovino.AppMain;
import br.eti.softlog.qualitasbovino.Manager;
import br.eti.softlog.qualitasbovino.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import jxl.Cell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Blank;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import static jxl.biff.FormatRecord.logger;

public class ExportacaoCertificacaoActivity extends AppCompatActivity {
    private AppMain app;
    private Manager manager;
    private Util util;
    private String dirDefault;
    private String dirDefault2;
    private String fileOriginal;
    private String fileNovo;
    private String fileResumo;
    private String dirMain;
    private String dirRoot;
    private String nomeBD;
    private DialogProperties properties = new DialogProperties();
    private ProgressBar circularBar;


    AppExternalFileWriter writer;
    PrintWriter writerAvaliacao;
    AvaliacaoJxls avaliacaoJxls;

    @BindView(R.id.btn_exportacao)
    OmegaCenterIconButton btnImportacao;

    @BindView(R.id.txt_status)
    TextView txtStatus;

    @BindView(R.id.txt_mensagem)
    TextView txtMensagem;

    String arquivoAtual;


    String fileAvaliacao;


    Long idCriador;
    Criador criador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportacao_certificacao);


        ButterKnife.bind(this);

        nomeBD = Prefs.getString("bancoDadosCurrentCert", "base_teste");

        //Local de Importação, Cria se não existe
        dirDefault = "/mnt/sdcard/qualitas_bovino/exportacao_certificacao";

        File folderDefault = new File(dirDefault);
        if (!folderDefault.exists()) {
            folderDefault.mkdir();
        }


        dirDefault2 = "/mnt/sdcard/qualitas_bovino/importacao_certificacao";


        //Procura por arquivos
        File directory = new File(dirDefault2);
        File[] files = directory.listFiles();


        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getName();
            String[] partes = filename.split("_");

            if (partes.length != 4 && partes.length != 6)
                break;

            if (partes.length == 6 && partes[3].equals("Marcacao")) {
                fileOriginal = files[i].getPath();
                fileNovo = files[i].getName();
                fileResumo = files[i].getName().replace(".xls", "_resumo.xls");
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Exportação de Dados");

        app = (AppMain) getApplication();
        manager = new Manager(app);
        util = new Util();
        nomeBD = Prefs.getString("bancoDadosCurrent", "");

        idCriador = Prefs.getLong("filtro_fazenda", 0);
        criador = app.getDaoSession().getCriadorDao().queryBuilder()
                .where(CriadorDao.Properties.Id.eq(idCriador)).unique();

        circularBar = findViewById(R.id.circularBar);
        circularBar.setVisibility(View.GONE);
        txtStatus.setVisibility(View.GONE);

        btnImportacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ExportacaoCertificacaoActivity.AsyncCircular().execute();
            }
        });


    }

    public class AsyncCircular extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            circularBar.setVisibility(View.VISIBLE);
            txtStatus.setText("");
            txtStatus.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Backup e criação de novo banco de dados

            //Exportacao da Planilha
            File file = new File(fileOriginal);

            String dirCriador = dirDefault + "/" + criador.getCodigo();

            File folderCriador = new File(dirCriador);
            if (!folderCriador.exists()) {
                folderCriador.mkdir();
            }

            fileAvaliacao = dirCriador + "/" + fileNovo;


            try {

                WorkbookSettings XLSSettings = new WorkbookSettings();
                XLSSettings.setEncoding("Cp1252");
                XLSSettings.setInitialFileSize(4096);
                XLSSettings.setUseTemporaryFileDuringWrite(true);

                Workbook existingWorkbook = Workbook.getWorkbook(file, XLSSettings);
                WritableWorkbook workbookCopy = Workbook.createWorkbook(new File(fileAvaliacao), existingWorkbook);

                WritableSheet sheetToEdit = workbookCopy.getSheet(0);
                sheetToEdit.setName("Machos");
                sheetToEdit.getSettings().setRecalculateFormulasBeforeSave(true);


                WritableSheet sheetToEdit2 = workbookCopy.getSheet(1);
                sheetToEdit2.setName("Fêmeas");
                sheetToEdit2.getSettings().setRecalculateFormulasBeforeSave(true);


                WritableSheet sheetToEdit3 = workbookCopy.getSheet(2);
                sheetToEdit3.setName("Machos Suplentes");
                sheetToEdit3.getSettings().setRecalculateFormulasBeforeSave(true);


                WritableSheet sheetToEdit4 = workbookCopy.getSheet(3);
                sheetToEdit4.setName("Fêmeas Suplentes");
                sheetToEdit4.getSettings().setRecalculateFormulasBeforeSave(true);


                existingWorkbook.close();

                writeSheet(sheetToEdit);
                writeSheet(sheetToEdit2);
                writeSheet(sheetToEdit3);
                writeSheet(sheetToEdit4);

                workbookCopy.write();

                //Gravação dos Dados
                workbookCopy.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (BiffException | WriteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            ///////////////////////////////////////////////////////////////////////////////////////
            /// RESUMO
            ///////////////////////////////////////////////////////////////////////////////////////
            String fileAvaliacaoResumo = dirCriador + "/" + fileResumo;


            try {

                WorkbookSettings XLSSettings = new WorkbookSettings();
                XLSSettings.setEncoding("Cp1252");
                XLSSettings.setInitialFileSize(4096);
                XLSSettings.setUseTemporaryFileDuringWrite(true);

                WritableWorkbook workbookCopy = null;
                try {
                    workbookCopy = Workbook.createWorkbook(new File(fileAvaliacaoResumo), XLSSettings);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }


                WritableSheet sheet = workbookCopy.createSheet("Resumo",0);
                sheet.getSettings().setRecalculateFormulasBeforeSave(true);

                ResumoCertificacao r;
                r = new ResumoCertificacao(getApplicationContext());


                int col, row;
                col = 0;
                row = -2;


                ///////////////////////////////////////////////////////////////////////////////////
                //Candidatos
                Long percCeipMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoCeip())
                        / Double.valueOf(r.getQtAnimaisMacho()) * 100);
                Long percCeipFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaCeip())
                        / Double.valueOf(r.getQtAnimaisFemea()) * 100);

                Long percSuplenteMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoSuplente())
                        / Double.valueOf(r.getQtAnimaisMacho()) * 100);
                Long percSuplenteFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaSuplente())
                        / Double.valueOf(r.getQtAnimaisFemea()) * 100);

                row++;
                row++;
                sheet.addCell(getCell(0, row, "Candidatos"));
                sheet.addCell(getCell(1, row, "Total"));
                sheet.addCell(getCell(2, row, "%"));
                sheet.addCell(getCell(3, row, "Ceip"));
                sheet.addCell(getCell(4, row, "%"));
                sheet.addCell(getCell(5, row, "Suplentes"));
                sheet.addCell(getCell(6, row, "%"));

                row++;
                sheet.addCell(getCell(0, row, "Machos"));
                sheet.addCell(getCellInteger(1, row, r.getQtAnimaisMacho()));
                sheet.addCell(getCellPerc(2, row,100));
                sheet.addCell(getCellInteger(3, row, r.getQtAnimaisMachoCeip()));
                sheet.addCell(getCellPerc(4, row,percCeipMacho));
                sheet.addCell(getCellInteger(5, row, r.getQtAnimaisMachoSuplente()));
                sheet.addCell(getCellPerc(6, row, percSuplenteMacho));

                row++;
                sheet.addCell(getCell(0, row, "Fêmeas"));
                sheet.addCell(getCellInteger(1, row,r.getQtAnimaisFemea()));
                sheet.addCell(getCellPerc(2, row, 100));
                sheet.addCell(getCellInteger(3, row, r.getQtAnimaisFemeaCeip()));
                sheet.addCell(getCellPerc(4, row, percCeipFemea));
                sheet.addCell(getCellInteger(5, row, r.getQtAnimaisFemeaSuplente()));
                sheet.addCell(getCellPerc(6, row,percSuplenteFemea));

                ///////////////////////////////////////////////////////////////////////////////////
                //Avaliados

                Long percMachoAvaliado = Math.round(Double.valueOf(r.getQtAnimaisMachoAvaliado())/Double.valueOf(r.getQtAnimaisMacho())*100);
                Long percFemeaAvaliado = Math.round(Double.valueOf(r.getQtAnimaisFemeaAvaliado())/Double.valueOf(r.getQtAnimaisFemea())*100);

                percCeipMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoCeipAvaliado())/
                        Double.valueOf(r.getQtAnimaisMachoAvaliado())*100);
                percCeipFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaCeipAvaliado())/
                        Double.valueOf(r.getQtAnimaisFemeaAvaliado())*100);

                percSuplenteMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoSuplenteAvaliado())
                        /Double.valueOf(r.getQtAnimaisMachoAvaliado())*100);
                percSuplenteFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaSuplenteAvaliado())
                        /Double.valueOf(r.getQtAnimaisFemeaAvaliado())*100);


                row++;
                row++;
                sheet.addCell(getCell(0, row, "Avaliados"));
                sheet.addCell(getCell(1, row, "Total"));
                sheet.addCell(getCell(2, row, "%"));
                sheet.addCell(getCell(3, row, "Ceip"));
                sheet.addCell(getCell(4, row, "%"));
                sheet.addCell(getCell(5, row, "Suplentes"));
                sheet.addCell(getCell(6, row, "%"));

                row++;
                sheet.addCell(getCell(0, row, "Machos"));
                sheet.addCell(getCellInteger(1, row, r.getQtAnimaisMachoAvaliado()));
                sheet.addCell(getCellPerc(2, row, percMachoAvaliado));
                sheet.addCell(getCellInteger(3, row, r.getQtAnimaisMachoCeipAvaliado()));
                sheet.addCell(getCellPerc(4, row, percCeipMacho));
                sheet.addCell(getCellInteger(5, row, r.getQtAnimaisMachoSuplenteAvaliado()));
                sheet.addCell(getCellPerc(6, row, percSuplenteMacho));


                row++;
                sheet.addCell(getCell(0, row, "Fêmeas"));
                sheet.addCell(getCellInteger(1, row, r.getQtAnimaisFemeaAvaliado()));
                sheet.addCell(getCellPerc(2, row, percFemeaAvaliado));
                sheet.addCell(getCellInteger(3, row, r.getQtAnimaisFemeaCeipAvaliado()));
                sheet.addCell(getCellPerc(4, row, percCeipFemea));
                sheet.addCell(getCellInteger(5, row, r.getQtAnimaisFemeaSuplenteAvaliado()));
                sheet.addCell(getCellPerc(6, row, percSuplenteFemea));

                ///////////////////////////////////////////////////////////////////////////////////
                //Certificados

                Long percMachoCertificado = Math.round(Double.valueOf(r.getQtAnimaisMachoCertificado())/Double.valueOf(r.getQtAnimaisMacho())*100);
                Long percFemeaCertificado = Math.round(Double.valueOf(r.getQtAnimaisFemeaCertificado())/Double.valueOf(r.getQtAnimaisFemea())*100);

                percCeipMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoCertificadoCeip()) /
                        Double.valueOf(r.getQtAnimaisMachoCertificado()) * 100);
                percCeipFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaCertificadoCeip()) /
                        Double.valueOf(r.getQtAnimaisFemeaCertificado()) * 100);


                percSuplenteMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoCertificadoSuplente()) /
                        Double.valueOf(r.getQtAnimaisMachoCertificado()) * 100);
                percSuplenteFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaCertificadoSuplente()) /
                        Double.valueOf(r.getQtAnimaisFemeaCertificado()) * 100);

                row++;
                row++;
                sheet.addCell(getCell(0, row, "Certificados"));
                sheet.addCell(getCell(1, row, "Total"));
                sheet.addCell(getCell(2, row, "%"));
                sheet.addCell(getCell(3, row, "Ceip"));
                sheet.addCell(getCell(4, row, "%"));
                sheet.addCell(getCell(5, row, "Suplente"));
                sheet.addCell(getCell(6, row, "%"));

                row++;
                sheet.addCell(getCell(0, row, "Machos"));
                sheet.addCell(getCellInteger(1, row, r.getQtAnimaisMachoCertificado()));
                sheet.addCell(getCellPerc(2, row, percMachoCertificado));
                sheet.addCell(getCellInteger(3, row, r.getQtAnimaisMachoCertificadoCeip()));
                sheet.addCell(getCellPerc(4, row, percCeipMacho));
                sheet.addCell(getCellInteger(5, row, r.getQtAnimaisMachoCertificadoSuplente()));
                sheet.addCell(getCellPerc(6, row,percSuplenteMacho));

                row++;
                sheet.addCell(getCell(0, row, "Fêmeas"));
                sheet.addCell(getCellInteger(1, row, r.getQtAnimaisFemeaCertificado()));
                sheet.addCell(getCellPerc(2, row, percFemeaCertificado));
                sheet.addCell(getCellInteger(3, row, r.getQtAnimaisFemeaCertificadoCeip()));
                sheet.addCell(getCellPerc(4, row, percCeipFemea));
                sheet.addCell(getCellInteger(5, row, r.getQtAnimaisFemeaCertificadoSuplente()));
                sheet.addCell(getCellPerc(6, row, percSuplenteFemea));


                ////////////////////////////////////////////////////////////////////////////////////
                //Desclassificados
                Long percMachoDescarte = Math.round(Double.valueOf(r.getQtAnimaisMachoDescarte())/Double.valueOf(r.getQtAnimaisMacho())*100);
                Long percFemeaDescarte = Math.round(Double.valueOf(r.getQtAnimaisFemeaDescarte())/Double.valueOf(r.getQtAnimaisFemea())*100);

                percCeipMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoCeipDescarte())/
                        Double.valueOf(r.getQtAnimaisMachoDescarte())*100);
                percCeipFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaCeipDescarte())/
                        Double.valueOf(r.getQtAnimaisFemeaDescarte())*100);


                percSuplenteMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoSuplenteDescarte())/
                        Double.valueOf(r.getQtAnimaisMachoDescarte())*100);
                percSuplenteFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaSuplenteDescarte())/
                        Double.valueOf(r.getQtAnimaisFemeaDescarte())*100);

                row++;
                row++;
                sheet.addCell(getCell(0, row, "Desclassificados"));
                sheet.addCell(getCell(1, row, "Total"));
                sheet.addCell(getCell(2, row, "%"));
                sheet.addCell(getCell(3, row, "Ceip"));
                sheet.addCell(getCell(4, row, "%"));
                sheet.addCell(getCell(5, row, "Suplente"));
                sheet.addCell(getCell(6, row, "%"));


                row++;
                sheet.addCell(getCell(0, row, "Machos"));
                sheet.addCell(getCellInteger(1, row, r.getQtAnimaisMachoDescarte()));
                sheet.addCell(getCellPerc(2, row, percMachoDescarte));
                sheet.addCell(getCellInteger(3, row, r.getQtAnimaisMachoCeipDescarte()));
                sheet.addCell(getCellPerc(4, row, percCeipMacho));
                sheet.addCell(getCellInteger(5, row, r.getQtAnimaisMachoSuplenteDescarte()));
                sheet.addCell(getCellPerc(6, row, percSuplenteMacho));


                row++;
                sheet.addCell(getCell(0, row, "Fêmeas"));
                sheet.addCell(getCellInteger(1, row, r.getQtAnimaisFemeaDescarte()));
                sheet.addCell(getCellPerc(2, row, percFemeaDescarte));
                sheet.addCell(getCellInteger(3, row, r.getQtAnimaisFemeaCeipDescarte()));
                sheet.addCell(getCellPerc(4, row, percCeipFemea));
                sheet.addCell(getCellInteger(5, row, r.getQtAnimaisFemeaSuplenteDescarte()));
                sheet.addCell(getCellPerc(6, row, percSuplenteFemea));

                ////////////////////////////////////////////////////////////////////////////////////
                //Media Certificados
                row++;
                row++;
                sheet.addCell(getCell(0, row, "Certificados"));
                sheet.addCell(getCell(1, row, "Peso"));
                sheet.addCell(getCell(2, row, "CE"));
                sheet.addCell(getCell(3, row, "Classificação"));
                sheet.addCell(getCell(4, row, "I.Qualitas"));

                row++;
                sheet.addCell(getCell(0, row, "Machos"));
                sheet.addCell(getCellNumber(1, row, r.getmPesoCertMacho()));
                sheet.addCell(getCellNumber(2, row, r.getmCeCertMacho()));
                sheet.addCell(getCellNumber(3, row, r.getmCertClassMacho()));
                sheet.addCell(getCellNumber(4, row, r.getmCertIQMacho()));

                row++;
                sheet.addCell(getCell(0, row, "Fêmeas"));
                sheet.addCell(getCellNumber(1, row, r.getmPesoCertFemea()));
                sheet.addCell(getCellNumber(2, row, r.getmCeCertFemea()));
                sheet.addCell(getCellNumber(3, row, r.getmCertClassFemea()));
                sheet.addCell(getCellNumber(4, row, r.getmCertIQFemea()));

                ////////////////////////////////////////////////////////////////////////////////////
                //Media Certificados P e F
                row++;
                row++;
                sheet.addCell(getCell(0, row, "Certificados"));
                sheet.addCell(getCell(1, row, "Peso"));
                sheet.addCell(getCell(2, row, "CE"));
                sheet.addCell(getCell(3, row, "Classificação"));
                sheet.addCell(getCell(4, row, "I.Qualitas"));
                sheet.addCell(getCell(5, row, "Total"));

                row++;
                sheet.addCell(getCell(0, row, "Machos P"));
                sheet.addCell(getCellNumber(1, row, r.getmPesoCertMachoP()));
                sheet.addCell(getCellNumber(2, row, r.getmCeCertMachoP()));
                sheet.addCell(getCellNumber(3, row, r.getmCertClassMachoP()));
                sheet.addCell(getCellNumber(4, row, r.getmCertIQMachoP()));
                sheet.addCell(getCellInteger(5, row, r.getmCertTotalP()));

                row++;
                sheet.addCell(getCell(0, row, "Machos F"));
                sheet.addCell(getCellNumber(1, row, r.getmPesoCertMachoF()));
                sheet.addCell(getCellNumber(2, row, r.getmCeCertMachoF()));
                sheet.addCell(getCellNumber(3, row, r.getmCertClassMachoF()));
                sheet.addCell(getCellNumber(4, row, r.getmCertIQMachoF()));
                sheet.addCell(getCellInteger(5, row, r.getmCertTotalF()));

                workbookCopy.write();

                //Gravação dos Dados
                workbookCopy.close();


            } catch (WriteException ex) {
                ex.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            txtStatus.setText("");
            txtMensagem.setText("Exportação Concluída!");
            txtStatus.setVisibility(View.GONE);
            circularBar.setVisibility(View.GONE);
        }

        WritableCell getCell(int col, int row, String conteudo) {
            Label l = new Label(col, row, conteudo);
            WritableCell cell = (WritableCell) l;
            return cell;
        }

        WritableCell getCellNumber(int col, int row, Double conteudo) {
            WritableCellFormat f = new WritableCellFormat(new NumberFormat("#,##0.0"));
            Number n = new Number(col, row, conteudo, f);
            WritableCell cell = (WritableCell) n;
            return cell;
        }

        WritableCell getCellPerc(int col, int row, int conteudos) {
            WritableCellFormat f = new WritableCellFormat(NumberFormats.PERCENT_INTEGER);
            Double conteudo;
            if (conteudos>0)
                conteudo = Double.valueOf(conteudos)/100;
            else
                conteudo = Double.valueOf(conteudos)/100;

            Number n = new Number(col, row, conteudo, f);
            WritableCell cell = (WritableCell) n;
            return cell;
        }

        WritableCell getCellPerc(int col, int row, Long conteudos) {
            WritableCellFormat f = new WritableCellFormat(NumberFormats.PERCENT_INTEGER);
            Double conteudo;
            if (conteudos>0)
                conteudo = Double.valueOf(conteudos)/100;
            else
                conteudo = Double.valueOf(conteudos)/100;

            Number n = new Number(col, row, conteudo, f);
            WritableCell cell = (WritableCell) n;
            return cell;
        }

        WritableCell getCellInteger(int col, int row, int conteudo) {
            WritableCellFormat f = new WritableCellFormat(NumberFormats.INTEGER);
            Number n = new Number(col, row, conteudo, f);
            WritableCell cell = (WritableCell) n;
            return cell;
        }


        public void writeSheet(WritableSheet sheet) {
            int i;
            i = 1;
            String sheetName = sheet.getName();

            //Log.d("Alterando Planilha",sheetName);
            while (true) {
                Cell[] row1 = sheet.getRow(i);
                Cell[] row2 = sheet.getRow(i + 1);
                String codigoCriador;

                if (row1.length == 0)
                    break;
                else
                    codigoCriador = row1[0].getContents();

                if (codigoCriador.isEmpty())
                    break;

                String cIdAnimalStr = row1[24].getContents();


                Log.d("Gravando Animal",cIdAnimalStr);
                Long idAnimal = Long.valueOf(cIdAnimalStr);


                MTFDados animal = app.getDaoSession().getMTFDadosDao()
                        .queryBuilder()
                        .where(MTFDadosDao.Properties.Id.eq(idAnimal))
                        .unique();

                try {

                    //Grava Marcacao
                    if (animal.getMarcacao() != null) {
                        Label l;
                        if (animal.getMarcacao() == Long.valueOf(1)) {
                            l = new Label(18, i, "S");
                        } else {
                            l = new Label(18, i, "N");
                        }
                        WritableCell cell = (WritableCell) l;
                        sheet.addCell(cell);
                    }

                    //Grava Classificacao
                    if (animal.getClassificacao() != null) {

                        Label l;
                        if (animal.getClassificacaoFP() != null) {
                            l = new Label(19, i,
                                    String.valueOf(animal.getClassificacao().toString()) +
                                            String.valueOf(animal.getClassificacaoFP()));
                        } else {
                            l = new Label(19, i, String.valueOf(animal.getClassificacao()));
                        }
                        WritableCell cell = (WritableCell) l;
                        sheet.addCell(cell);

                    }

                    //Grava Mocho
                    if (animal.getMocho() != null) {
                        Label l;
                        if (animal.getMocho() == Long.valueOf(1)) {
                            l = new Label(20, i, "S");
                        } else {
                            l = new Label(20, i, "N");
                        }
                        WritableCell cell = (WritableCell) l;
                        sheet.addCell(cell);
                    }

                    //Grava Peso
                    if (animal.getP_marcacao() != null) {
                        Label l;
                        String peso = String.valueOf(animal.getP_marcacao().toString().replace(".", ","));
                        l = new Label(21, i, peso);
                        WritableCell cell = (WritableCell) l;
                        sheet.addCell(cell);

                    }

                    //Grava CE
                    if (animal.getCe_marcacao() != null) {
                        Label l;
                        String ce;
                        ce = String.valueOf(animal.getCe_marcacao().toString().replace(".", ","));
                        l = new Label(22, i, ce);
                        WritableCell cell = (WritableCell) l;
                        sheet.addCell(cell);
                    }

                    //Grava Observacao
                    if (animal.getMotivoDescarteAnimais() != null) {
                        String motivos;
                        motivos = "";
                        for (int k = 0; k < animal.getMotivoDescarteAnimais().size(); k++) {
                            if (k > 0) {
                                motivos = motivos + "; ";
                            }

                            motivos = motivos + animal.getMotivoDescarteAnimais()
                                    .get(k)
                                    .getMotivoId()
                                    .toString();

                        }
                        Label l;
                        l = new Label(23, i, motivos);
                        WritableCell cell = (WritableCell) l;
                        sheet.addCell(cell);
                    }

                    Blank b = new Blank(24, i);
                    sheet.addCell(b);

                    Blank b1 = new Blank(25, i);
                    sheet.addCell(b1);

                } catch (WriteException e) {
                    e.printStackTrace();
                }
                i = i + 2;

            }
            //Remove coluna com o ID do Animal
            //sheet.removeColumn(24);

        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        //Intent in = new Intent(getApplicationContext(), MainCertificacaoActivity.class);
        //startActivity(in);
        finish();
        return true;
    }
}

