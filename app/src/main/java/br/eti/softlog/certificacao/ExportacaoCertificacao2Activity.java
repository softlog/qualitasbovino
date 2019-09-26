package br.eti.softlog.certificacao;



import android.content.Intent;
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

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import br.eti.softlog.Utils.AvaliacaoJxls;
import br.eti.softlog.Utils.CsvWriter;
import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.Criador;
import br.eti.softlog.model.CriadorDao;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MTFDadosDao;
import br.eti.softlog.model.Medicao;
import br.eti.softlog.model.MedicaoDao;
import br.eti.softlog.model.MedicoesAnimal;
import br.eti.softlog.model.MedicoesAnimalDao;
import br.eti.softlog.qualitasbovino.AppMain;
import br.eti.softlog.qualitasbovino.ExportacaoActivity;
import br.eti.softlog.qualitasbovino.MainActivity;
import br.eti.softlog.qualitasbovino.Manager;
import br.eti.softlog.qualitasbovino.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExportacaoCertificacao2Activity extends AppCompatActivity {
    private AppMain app;
    private Manager manager;
    private Util util;
    private String dirDefault;
    private String dirDefault2;
    private String fileOriginal;
    private String fileNovo;
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

            if (partes.length != 4 && partes.length !=6)
                break;

            if (partes.length == 6 && partes[3].equals("Marcacao")) {
                fileOriginal = files[i].getPath();
                fileNovo = files[i].getName();
            }

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Importação de Dados");

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
                new ExportacaoCertificacao2Activity.AsyncCircular().execute();
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
                XLSSettings.setInitialFileSize(2048);
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


        public void writeSheet(WritableSheet sheet) {
            int i;
            i = 1;
            String sheetName = sheet.getName();

            Log.d("Alterando Planilha",sheetName);
            while (true) {
                Cell[] row1 = sheet.getRow(i);
                Cell[] row2 = sheet.getRow(i + 1);

                String codigoCriador = row1[0].getContents();

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
                        l = new Label(21, i, String.valueOf(animal.getP_marcacao().toString().replace(".",",")));
                        WritableCell cell = (WritableCell) l;
                        sheet.addCell(cell);

                    }
                    //Grava CE
                    if (animal.getCe_marcacao() != null) {
                        Label l;
                        l = new Label(22, i, String.valueOf(animal.getCe_marcacao().toString().replace(".",",")));
                        WritableCell cell = (WritableCell) l;
                        sheet.addCell(cell);
                    }

                    //Grava Observacao
                    if (animal.getMotDescId() != null) {
                        Label l;
                        l = new Label(23, i, String.valueOf(animal.getMotDescId()));
                        WritableCell cell = (WritableCell) l;
                        sheet.addCell(cell);
                    }


                } catch (WriteException e) {
                    e.printStackTrace();
                }
                i = i + 2;

            }

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
