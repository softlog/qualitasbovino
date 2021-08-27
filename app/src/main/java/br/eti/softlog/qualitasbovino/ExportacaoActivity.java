package br.eti.softlog.qualitasbovino;

import android.content.Context;
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
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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
import br.eti.softlog.model.Resumo;
import br.eti.softlog.model.ResumoCertificacao;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExportacaoActivity extends AppCompatActivity {

    private AppMain app;
    private Manager manager;
    private Util util;
    private String dirDefault;
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
    String fileAvaliacaoCsv;
    String fileAlterado;
    String fileNovo;
    String filePendente;
    String fileResumo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportacao_2);

        ButterKnife.bind(this);

        nomeBD = Prefs.getString("bancoDadosCurrent", "base_teste");

        //Local de Importação, Cria se não existe
        dirDefault = "/mnt/sdcard/qualitas_bovino/exportacao";

        File folderDefault = new File(dirDefault);
        if (!folderDefault.exists()) {
            folderDefault.mkdir();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Importação de Dados");

        app = (AppMain) getApplication();
        manager = new Manager(app);
        util = new Util();
        nomeBD = Prefs.getString("bancoDadosCurrent", "");

        String[] partes = nomeBD.split("_");

        try {
            nomeBD = partes[0] + "_" + partes[1].substring(0, 2) + util.getDateFormatArquivo(new Date());
        } catch (Exception e) {
            Log.d("Erro Data", e.getMessage());
        }


        circularBar = findViewById(R.id.circularBar);
        circularBar.setVisibility(View.GONE);
        txtStatus.setVisibility(View.GONE);

        btnImportacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncCircular().execute();
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

            //Rotina de Exportacao Avaliação
            //Numero interno	Animal	Datamedida	Valor	Tipomedida	Propriedade	Gmanejo
            //9	12	8	10	3	50	20

            QueryBuilder queryBuilder = app.getDaoSession().getCriadorDao().queryBuilder();
            //Seletor da Fazenda

            List<Criador> criadores = queryBuilder
                    .orderAsc(CriadorDao.Properties.Codigo)
                    .list();

            for (int c = 0; c < criadores.size(); c++) {

                if (criadores.get(c).getAnimais().size() == 0)
                    continue;

                String dirCriador = dirDefault + "/" + criadores.get(c).getCodigo();

                File folderCriador = new File(dirCriador);
                if (!folderCriador.exists()) {
                    folderCriador.mkdir();
                }

//              dirMain = dirDefault + "/" + nomeBD;
//              File folder = new File(dirMain);
//              if (!folder.exists()) {
//                  folder.mkdir();
//              }


                fileAvaliacao = dirCriador + "/" + nomeBD + "_avaliacao.csv";
                fileAvaliacaoCsv = nomeBD.replace(" ", "_") + "_avaliacao_planilha";
                fileAlterado = nomeBD + "_log_alteracao";
                fileNovo = nomeBD + "_animal_novos";
                filePendente = nomeBD + "_pendentes";
                fileResumo = nomeBD + "_resumo.xls";

                //Gerar Arquivo de Avaliacao
                List<MTFDados> animais = app.getDaoSession().getMTFDadosDao().queryBuilder()
                        .where(MTFDadosDao.Properties.Avaliado.eq(true))
                        .where(MTFDadosDao.Properties.CriadorId.eq(criadores.get(c).getId()))
                        .list();

                String arquivoAvaliacao;
                arquivoAvaliacao = "";
                writerAvaliacao = null;
                try {
                    FileOutputStream byteStream = new FileOutputStream(fileAvaliacao);
                    writerAvaliacao = new PrintWriter(new OutputStreamWriter(byteStream, "UTF8"));


                    for (int i = 0; i < animais.size(); i++) {
                        List<MedicoesAnimal> medidas = animais.get(i).getMedicoesAnimals();

                        //Se o animal esta avaliado, mas primeira nota esta vazia, entao foi avaliado
                        //apenas para venda
                        if (medidas.get(0).getValor() == null)
                            continue;

                        for (int j = 0; j < medidas.size(); j++) {

                            if (medidas.get(j).getValor() == null) {
                                continue;
                            }

                            if (medidas.get(j).getValor() == 0) {
                                continue;
                            }

                            //Ignorar se for venda
                            if (medidas.get(j).getMedicaoId() == 321524) {
                                continue;
                            }


                            if (medidas.get(j).getMedicaoId() == 132523) {
                                continue;
                            }
                            //Ignorar se for Lote
                            if (medidas.get(j).getMedicaoId() == 222530) {
                                continue;
                            }

                            String linha;
                            String numero_interno;
                            if (animais.get(i).getId() > 0)
                                numero_interno = padLeft(String.valueOf(animais.get(i).getId()), 9);
                            else
                                numero_interno = "         ";

                            String idf = padLeft(util.trataIdf(animais.get(i).getIdf().trim()), 12);

                            String dataMedida;
                            if (medidas.get(j).getMedicaoId() == 141529) {
                                dataMedida = animais.get(i).getDataAvaliacao();
                            } else {
                                dataMedida = medidas.get(j).getDataMedicao();
                            }
                            dataMedida = dataMedida.substring(8, 10) + dataMedida.substring(5, 7) + dataMedida.substring(0, 4);


                            String valor = padLeft(String.valueOf(medidas.get(j).getValor() + ",000"), 10);
                            String tipoMedida = medidas.get(j).getMedicoes().getAbrev();
                            String propriedade = padRight("1", 50);
                            String gManejo = padLeft("", 20);

                            linha = numero_interno + idf + dataMedida + valor + tipoMedida + propriedade + gManejo + (char) 13 + (char) 10;
                            writerAvaliacao.print(linha);
                            //arquivoAvaliacao = arquivoAvaliacao + linha;
                            //Log.d("Linha:", linha);
                        }
                    }


                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                } finally {
                    if (writerAvaliacao != null)
                        writerAvaliacao.close();
                }


                //if (animais.size() > 0)
                //    writeStringAsFile(arquivoAvaliacao, fileAvaliacao);

                ////////////////////////////////////////////////////////////////////////////////////
                /// Gerar Arquivo CSV das avaliacoes


                List<String> headerAval = new ArrayList<>();

                headerAval.add("Animal");
                headerAval.add("Reprodução");
                headerAval.add("Ubere");
                headerAval.add("Musculosidade");
                headerAval.add("Frame");
                headerAval.add("Aprumo");
                headerAval.add("Casco");
                headerAval.add("Ossatura");
                headerAval.add("Profundidade");
                headerAval.add("Dorso");
                headerAval.add("Garupa");
                headerAval.add("Umbigo");
                headerAval.add("Boca");
                headerAval.add("Ins.Cauda");
                headerAval.add("Pelagem");
                headerAval.add("Temperamento");
                headerAval.add("Chanfro");
                headerAval.add("Testículos");
                headerAval.add("Racial");
                headerAval.add("Descarte");
                headerAval.add("Venda");
                headerAval.add("Comercial");
                headerAval.add("Lote");

                List<List<String>> contentsAval = new ArrayList<>();

                for (int i = 0; i < animais.size(); i++) {

                    List<String> linha = new ArrayList<>();

                    linha.add(util.trataIdf(animais.get(i).getIdf()));
                    List<Medicao> medidas = app.getDaoSession()
                            .getMedicaoDao()
                            .queryBuilder()
                            .orderAsc(MedicaoDao.Properties.Ordem)
                            .list();

                    //Se o animal esta avaliado, mas primeira nota esta vazia, entao foi avaliado
                    //apenas para venda
                    if (animais.get(i).getMedicoesAnimals().get(0).getValor() == null)
                        continue;

                    for (int j = 0; j < medidas.size(); j++) {
                        Medicao medida = medidas.get(j);

                        //if (medida.getAbrev().equals("VEN"))
                        //    continue;


                        MedicoesAnimal m = app.getDaoSession()
                                .getMedicoesAnimalDao()
                                .queryBuilder()
                                .where(MedicoesAnimalDao.Properties.MedicaoId.eq(medida.getId()))
                                .where(MedicoesAnimalDao.Properties.Animal.eq(animais.get(i).getAnimal()))
                                .unique();

                        if (m == null) {
                            linha.add("");
                            continue;
                        }

                        if (m.getValor() == null) {
                            linha.add("");
                            continue;
                        }

                        if (m.getValor() == 0)
                            linha.add("");
                        else
                            linha.add(String.valueOf(m.getValor()));
                    }
                    contentsAval.add(linha);
                }

                if (animais.size() > 0)
                    CsvWriter.toCsvFile(headerAval, contentsAval, fileAvaliacaoCsv, dirCriador);

                ////////////////////////////////////////////////////////////////////////////////////
                //Animal Pendente
                List<MTFDados> animaisPendentes = app.getDaoSession().getMTFDadosDao().queryBuilder()
                        .where(MTFDadosDao.Properties.Avaliado.eq(false))
                        .where(MTFDadosDao.Properties.CriadorId.eq(criadores.get(c).getId()))
                        .list();

                String arquivoPendentes;
                arquivoPendentes = "";

                List<String> header0 = new ArrayList<>();
                header0.add("Código IDF");
                header0.add("Sexo");
                header0.add("Fazenda");

                List<List<String>> contents0 = new ArrayList<>();

                for (int i = 0; i < animaisPendentes.size(); i++) {
                    List<String> linha = new ArrayList<>();
                    //Log.d("Animal",String.valueOf(animaisPendentes.get(i).getIdf()));
                    linha.add(util.trataIdf(animaisPendentes.get(i).getIdf()));
                    linha.add(animaisPendentes.get(i).getSexo());
                    linha.add(animaisPendentes.get(i).getAnimalPrincipal().getCriador().getCodigo());
                    contents0.add(linha);
                }

                if (animaisPendentes.size() > 0)
                    CsvWriter.toCsvFile(header0, contents0, filePendente, dirCriador);


                ////////////////////////////////////////////////////////////////////////////////////
                //Animal Novo
                List<MTFDados> animaisNovos = app.getDaoSession().getMTFDadosDao().queryBuilder()
                        .where(MTFDadosDao.Properties.Importado.eq(0))
                        .where(MTFDadosDao.Properties.Avaliado.eq(true))
                        .where(MTFDadosDao.Properties.CriadorId.eq(criadores.get(c).getId()))
                        .list();

                String arquivoNovos;
                arquivoNovos = "";


                List<String> header = new ArrayList<>();
                header.add("codigo");
                header.add("nome");
                header.add("sexo");
                header.add("data_nascimento");
                header.add("fazenda");
                header.add("Status");


                List<List<String>> contents = new ArrayList<>();

                for (int i = 0; i < animaisNovos.size(); i++) {
                    List<String> linha = new ArrayList<>();
                    linha.add(util.trataIdf(animaisNovos.get(i).getIdf()));
                    linha.add(animaisNovos.get(i).getAnimalPrincipal().getNome());
                    linha.add(animaisNovos.get(i).getSexo());
                    linha.add(animaisNovos.get(i).getDataNasc());
                    linha.add(animaisNovos.get(i).getAnimalPrincipal().getCriador().getCodigo());

                    MTFDados animalNovo = manager.findMTFDadosByAnimal(animaisNovos.get(i).getAnimal());

                    if (animalNovo.getIdfAlterado() == 1)
                        linha.add("IDF com alteracao");
                    else
                        linha.add("");


                    contents.add(linha);
                }

                if (animaisNovos.size() > 0)
                    CsvWriter.toCsvFile(header, contents, fileNovo, dirCriador);

                ////////////////////////////////////////////////////////////////////////////////////
                //Rotina de Exportacao Log Alteração

                List<MTFDados> animaisEditados = app.getDaoSession().getMTFDadosDao().queryBuilder()
                        .where(MTFDadosDao.Properties.CriadorId.eq(criadores.get(c).getId()))
                        .where(MTFDadosDao.Properties.Alterado.eq(1))
                        .where(MTFDadosDao.Properties.Importado.eq(1))
                        .where(MTFDadosDao.Properties.Avaliado.eq(true)).list();

                String arquivoEditados;
                arquivoEditados = "";

                List<String> header2 = new ArrayList<>();
                header2.add("codigo");
                header2.add("sexo alterado");
                header2.add("fazenda");
                header2.add("observacao");
                header2.add("venda");
                header2.add("comercial");

                List<List<String>> contents2 = new ArrayList<>();

                for (int i = 0; i < animaisEditados.size(); i++) {
                    List<String> linha = new ArrayList<>();
                    linha.add(util.trataIdf(animaisEditados.get(i).getIdf()));
                    linha.add(animaisEditados.get(i).getSexo());
                    linha.add(animaisEditados.get(i).getAnimalPrincipal().getCriador().getCodigo());
                    linha.add("");
                    linha.add("");
                    linha.add("");
                    contents2.add(linha);

                }

                List<MTFDados> animaisEditados2 = app.getDaoSession().getMTFDadosDao().queryBuilder()
                        .where(MTFDadosDao.Properties.CriadorId.eq(criadores.get(c).getId()))
                        .where(MTFDadosDao.Properties.Observacao.isNotNull()).list();

                for (int i = 0; i < animaisEditados2.size(); i++) {
                    List<String> linha = new ArrayList<>();
                    linha.add(util.trataIdf(animaisEditados2.get(i).getIdf()));
                    linha.add("");
                    linha.add(animaisEditados2.get(i).getAnimalPrincipal().getCriador().getCodigo());
                    linha.add(animaisEditados2.get(i).getObservacao().toString());
                    linha.add("");
                    linha.add("");
                    contents2.add(linha);
                }


                //app.getDaoSession().getMedicoesAnimalDao().queryBuilder().LOG_VALUES = true;
                //app.getDaoSession().getMedicoesAnimalDao().queryBuilder().LOG_SQL = true;

                List<MedicoesAnimal> animaisEditados3 = app.getDaoSession().getMedicoesAnimalDao().queryBuilder()
                        .where(MedicoesAnimalDao.Properties.MedicaoId.eq(321524))
                        .where(MedicoesAnimalDao.Properties.Valor.eq(1))
                        .list();

                for (int i = 0; i < animaisEditados3.size(); i++) {

                    if (animaisEditados3.get(i).getAnimalPrincipal().getCriadorId().equals(criadores.get(c).getId())) {
                        List<String> linha = new ArrayList<>();
                        linha.add(util.trataIdf(animaisEditados3.get(i).getAnimalPrincipal().getIdf()));
                        linha.add("");
                        linha.add(animaisEditados3.get(i).getAnimalPrincipal().getCriador().getCodigo());
                        linha.add("");
                        linha.add("Sim");
                        linha.add("");
                        contents2.add(linha);
                    }
                }


                List<MedicoesAnimal> animaisEditados4 = app.getDaoSession().getMedicoesAnimalDao().queryBuilder()
                        .where(MedicoesAnimalDao.Properties.MedicaoId.eq(132523))
                        .where(MedicoesAnimalDao.Properties.Valor.eq(1))
                        .list();

                for (int i = 0; i < animaisEditados4.size(); i++) {

                    if (animaisEditados4.get(i).getAnimalPrincipal().getCriadorId().equals(criadores.get(c).getId())) {
                        List<String> linha = new ArrayList<>();
                        linha.add(util.trataIdf(animaisEditados4.get(i).getAnimalPrincipal().getIdf()));
                        linha.add("");
                        linha.add(animaisEditados4.get(i).getAnimalPrincipal().getCriador().getCodigo());
                        linha.add("");
                        linha.add("");
                        linha.add("Sim");
                        contents2.add(linha);
                    }
                }

                if (contents2.size() > 0)
                    CsvWriter.toCsvFile(header2, contents2, fileAlterado, dirCriador);


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


                    WritableSheet sheet = workbookCopy.createSheet("Resumo", 0);
                    sheet.getSettings().setRecalculateFormulasBeforeSave(true);

                    Resumo r;
                    r = new Resumo(getApplicationContext(), criadores.get(c).getId());


                    int col, row;
                    col = 0;
                    row = -2;


                    ///////////////////////////////////////////////////////////////////////////////////
                    //Geral
                    Long percAnimalAprovado = Math.round(Double.valueOf(r.getQtAnimaisAprovados()) / Double.valueOf(r.getQtAnimaisAvaliados()) * 100);
                    Long percAnimalMachoAprovado = Math.round(Double.valueOf(r.getQtAnimaisMachoAprovado()) / Double.valueOf(r.getQtAnimaisMachoAvaliado()) * 100);
                    Long percAnimalFemeaAprovado = Math.round(Double.valueOf(r.getQtAnimaisFemeaAprovado()) / Double.valueOf(r.getQtAnimaisFemeaAvaliado()) * 100);

                    Long percAnimalAvaliado = Math.round(Double.valueOf(r.getQtAnimaisAvaliados()) / Double.valueOf(r.getQtAnimais()) * 100);
                    Long percAnimalMachoAvaliado = Math.round(Double.valueOf(r.getQtAnimaisMachoAvaliado()) / Double.valueOf(r.getQtAnimaisMacho()) * 100);
                    Long percAnimalFemeaAvaliado = Math.round(Double.valueOf(r.getQtAnimaisFemeaAvaliado()) / Double.valueOf(r.getQtAnimaisFemea()) * 100);

                    Long percAnimalDescarte = Math.round(Double.valueOf(r.getQtAnimaisDescarte()) / Double.valueOf(r.getQtAnimaisAvaliados()) * 100);
                    Long percAnimalMachoDescarte = Math.round(Double.valueOf(r.getQtAnimaisMachoDescarte()) / Double.valueOf(r.getQtAnimaisMachoAvaliado()) * 100);
                    Long percAnimalFemeaDescarte = Math.round(Double.valueOf(r.getQtAnimaisFemeaDescarte()) / Double.valueOf(r.getQtAnimaisFemeaAvaliado()) * 100);

                    Long percAnimalVenda = Math.round(Double.valueOf(r.getQtAnimaisOutros())/Double.valueOf(r.getQtAnimaisAvaliados())*100);
                    Long percAnimalMachoVenda = Math.round(Double.valueOf(r.getQtAnimaisMachoOutros())/Double.valueOf(r.getQtAnimaisMachoAvaliado())*100);
                    Long percAnimalFemeaVenda = Math.round(Double.valueOf(r.getQtAnimaisFemeaOutros())/Double.valueOf(r.getQtAnimaisFemeaAvaliado())*100);



                    row++;
                    row++;
                    sheet.addCell(getCell(0, row, ""));
                    sheet.addCell(getCell(1, row, "Total"));
                    sheet.addCell(getCell(2, row, "Avaliados"));
                    sheet.addCell(getCell(3, row, "%"));
                    sheet.addCell(getCell(4, row, "Aprovados"));
                    sheet.addCell(getCell(5, row, "%"));
                    sheet.addCell(getCell(6, row, "Descartes"));
                    sheet.addCell(getCell(7, row, "%"));
                    sheet.addCell(getCell(8, row, "Outros"));
                    sheet.addCell(getCell(9, row, "%"));

                    row++;
                    sheet.addCell(getCell(0, row, "Machos"));
                    sheet.addCell(getCellInteger(1, row, r.getQtAnimaisMacho()));
                    sheet.addCell(getCellInteger(2, row, r.getQtAnimaisMachoAvaliado()));
                    sheet.addCell(getCellPerc(3, row, percAnimalMachoAvaliado));
                    sheet.addCell(getCellInteger(4, row, r.getQtAnimaisMachoAprovado()));
                    sheet.addCell(getCellPerc(5, row, percAnimalMachoAprovado));
                    sheet.addCell(getCellInteger(6, row, r.getQtAnimaisMachoDescarte()));
                    sheet.addCell(getCellPerc(7, row, percAnimalMachoDescarte));
                    sheet.addCell(getCellInteger(8, row, r.getQtAnimaisMachoOutros()));
                    sheet.addCell(getCellPerc(9, row, percAnimalMachoVenda));

                    row++;
                    sheet.addCell(getCell(0, row, "Fêmeas"));
                    sheet.addCell(getCellInteger(1, row, r.getQtAnimaisFemea()));
                    sheet.addCell(getCellInteger(2, row, r.getQtAnimaisFemeaAvaliado()));
                    sheet.addCell(getCellPerc(3, row, percAnimalFemeaAvaliado));
                    sheet.addCell(getCellInteger(4, row, r.getQtAnimaisFemeaAprovado()));
                    sheet.addCell(getCellPerc(5, row, percAnimalFemeaAprovado));
                    sheet.addCell(getCellInteger(6, row, r.getQtAnimaisFemeaDescarte()));
                    sheet.addCell(getCellPerc(7, row, percAnimalFemeaDescarte));
                    sheet.addCell(getCellInteger(8, row, r.getQtAnimaisFemeaOutros()));
                    sheet.addCell(getCellPerc(9, row, percAnimalFemeaVenda));

                    row++;
                    sheet.addCell(getCell(0, row, "Total"));
                    sheet.addCell(getCellInteger(1, row, r.getQtAnimais()));
                    sheet.addCell(getCellInteger(2, row, r.getQtAnimaisAvaliados()));
                    sheet.addCell(getCellPerc(3, row, percAnimalAvaliado));
                    sheet.addCell(getCellInteger(4, row, r.getQtAnimaisAprovados()));
                    sheet.addCell(getCellPerc(5, row, percAnimalAprovado));
                    sheet.addCell(getCellInteger(6, row, r.getQtAnimaisDescarte()));
                    sheet.addCell(getCellPerc(7, row, percAnimalDescarte));
                    sheet.addCell(getCellInteger(8, row, r.getQtAnimaisOutros()));
                    sheet.addCell(getCellPerc(9, row, percAnimalVenda));

                    ///////////////////////////////////////////////////////////////////////////////////
                    //Segunda Tabela
                    row++;
                    row++;
                    sheet.addCell(getCell(0, row, ""));
                    sheet.addCell(getCell(1, row, "Avaliado"));
                    sheet.addCell(getCell(2, row, "Não Avaliado"));
                    sheet.addCell(getCell(3, row, "Venda"));
                    sheet.addCell(getCell(4, row, "Comercial"));

                    row++;
                    sheet.addCell(getCell(0, row, "Machos"));
                    sheet.addCell(getCellInteger(1, row, r.getQtAnimaisMachoAvaliado()));
                    sheet.addCell(getCellInteger(2, row, r.getQtAnimaisMacho()-r.getQtAnimaisMachoAvaliado()));
                    sheet.addCell(getCellInteger(3, row, r.getQtAnimaisMachoVenda()));
                    sheet.addCell(getCellInteger(4, row, r.getQtAnimaisMachoComercial()));

                    row++;
                    sheet.addCell(getCell(0, row, "Fêmea"));
                    sheet.addCell(getCellInteger(1, row, r.getQtAnimaisFemeaAvaliado()));
                    sheet.addCell(getCellInteger(2, row, r.getQtAnimaisFemea()-r.getQtAnimaisFemeaAvaliado()));
                    sheet.addCell(getCellInteger(3, row, r.getQtAnimaisFemeaVenda()));
                    sheet.addCell(getCellInteger(4, row, r.getQtAnimaisFemeaComercial()));

                    row++;
                    sheet.addCell(getCell(0, row, "Total"));
                    sheet.addCell(getCellInteger(1, row, r.getQtAnimaisAvaliados()));
                    sheet.addCell(getCellInteger(2, row, r.getQtAnimaisFemea()-r.getQtAnimaisFemeaAvaliado()));
                    sheet.addCell(getCellInteger(3, row, r.getQtAnimaisVenda()));
                    sheet.addCell(getCellInteger(4, row, r.getQtAnimaisComercial()));

                    ///////////////////////////////////////////////////////////////////////////////////
                    //Terceira Tabela
                    row++;
                    row++;
                    sheet.addCell(getCell(0, row, "Media"));
                    sheet.addCell(getCell(1, row, "Rep"));
                    sheet.addCell(getCell(2, row, "Ube"));
                    sheet.addCell(getCell(3, row, "Mus"));
                    sheet.addCell(getCell(4, row, "Fra"));
                    sheet.addCell(getCell(5, row, "Apr"));
                    sheet.addCell(getCell(6, row, "Oss"));
                    sheet.addCell(getCell(7, row, "Pro"));
                    sheet.addCell(getCell(8, row, "Gar"));

                    row++;
                    sheet.addCell(getCell(0, row, "Machos"));
                    try{
                        sheet.addCell(getCellNumber(1, row, Double.valueOf(r.getmRepM().replace(",", "."))));
                        sheet.addCell(getCell(2, row, ""));
                        sheet.addCell(getCellNumber(3, row, Double.valueOf(r.getmMuscM().replace(",", "."))));
                        sheet.addCell(getCellNumber(4, row, Double.valueOf(r.getmFraM().replace(",", "."))));
                        sheet.addCell(getCellNumber(5, row, Double.valueOf(r.getmAprM().replace(",", "."))));
                        sheet.addCell(getCellNumber(6, row, Double.valueOf(r.getmOssM().replace(",", "."))));
                        sheet.addCell(getCellNumber(7, row, Double.valueOf(r.getmProM().replace(",", "."))));
                        sheet.addCell(getCellNumber(8, row, Double.valueOf(r.getmGarM().replace(",", "."))));

                    } catch (Exception e){

                    }

                    row++;
                    sheet.addCell(getCell(0, row, "Fêmea"));
                    try{
                        sheet.addCell(getCellNumber(1, row, Double.valueOf(r.getmRepF().replace(",", "."))));
                        sheet.addCell(getCellNumber(2, row, Double.valueOf(r.getmUbeF().replace(",", "."))));
                        sheet.addCell(getCellNumber(3, row, Double.valueOf(r.getmMuscF().replace(",", "."))));
                        sheet.addCell(getCellNumber(4, row, Double.valueOf(r.getmFraF().replace(",", "."))));
                        sheet.addCell(getCellNumber(5, row, Double.valueOf(r.getmAprF().replace(",", "."))));
                        sheet.addCell(getCellNumber(6, row, Double.valueOf(r.getmOssF().replace(",", "."))));
                        sheet.addCell(getCellNumber(7, row, Double.valueOf(r.getmProF().replace(",", "."))));
                        sheet.addCell(getCellNumber(8, row, Double.valueOf(r.getmGarF().replace(",", "."))));

                    } catch (Exception e) {

                    }

                    ///////////////////////////////////////////////////////////////////////////////////
                    //Quarta Tabela

                    row++;
                    row++;
                    sheet.addCell(getCell(0, row, "Media"));
                    sheet.addCell(getCell(1, row, "Umb"));
                    sheet.addCell(getCell(2, row, "Boc"));
                    sheet.addCell(getCell(3, row, "Cau"));
                    sheet.addCell(getCell(4, row, "Pla"));
                    sheet.addCell(getCell(5, row, "Tem"));
                    sheet.addCell(getCell(6, row, "Tte"));
                    sheet.addCell(getCell(7, row, "Rac"));
                    sheet.addCell(getCell(8, row, ""));

                    row++;
                    sheet.addCell(getCell(0, row, "Machos"));
                    try {
                        sheet.addCell(getCellNumber(1, row, Double.valueOf(r.getmUmbM().replace(",", "."))));
                        sheet.addCell(getCellNumber(2, row, Double.valueOf(r.getmBocM().replace(",", "."))));
                        sheet.addCell(getCellNumber(3, row, Double.valueOf(r.getmCauM().replace(",", "."))));
                        sheet.addCell(getCellNumber(4, row, Double.valueOf(r.getmPlaM().replace(",", "."))));
                        sheet.addCell(getCellNumber(5, row, Double.valueOf(r.getmTemM().replace(",", "."))));
                        sheet.addCell(getCellNumber(6, row, Double.valueOf(r.getmTteM().replace(",", "."))));
                        sheet.addCell(getCellNumber(7, row, Double.valueOf(r.getmRacM().replace(",", "."))));
                    } catch (Exception e) {
                    }

                    row++;
                    sheet.addCell(getCell(0, row, "Fêmea"));
                    try {
                        sheet.addCell(getCellNumber(1, row, Double.valueOf(r.getmUmbF().replace(",", "."))));
                        sheet.addCell(getCellNumber(2, row, Double.valueOf(r.getmBocF().replace(",", "."))));
                        sheet.addCell(getCellNumber(3, row, Double.valueOf(r.getmCauF().replace(",", "."))));
                        sheet.addCell(getCellNumber(4, row, Double.valueOf(r.getmPlaF().replace(",", "."))));
                        sheet.addCell(getCellNumber(5, row, Double.valueOf(r.getmTemF().replace(",", "."))));
                        sheet.addCell(getCell(6, row, ""));
                        sheet.addCell(getCellNumber(7, row, Double.valueOf("0.00")));
                    } catch (Exception e) {

                    }

                    ///////////////////////////////////////////////////////////////////////////////////
                    //Quinta Tabela

                    row++;
                    row++;
                    sheet.addCell(getCell(0, row, "Animais"));
                    sheet.addCell(getCell(1, row, "Nasc"));
                    sheet.addCell(getCell(2, row, "Desm"));
                    sheet.addCell(getCell(3, row, "365 d"));
                    sheet.addCell(getCell(4, row, "450 d"));
                    sheet.addCell(getCell(5, row, "550 d"));
                    sheet.addCell(getCell(6, row, "CE"));

                    row++;
                    sheet.addCell(getCell(0, row, "Macho"));
                    try{
                        sheet.addCell(getCellInteger(1, row, r.getQtNascM()));
                        sheet.addCell(getCellInteger(2, row, r.getQtDesmM()));
                        sheet.addCell(getCellInteger(3, row, r.getQt365M()));
                        sheet.addCell(getCellInteger(4, row, r.getQt450M()));
                        sheet.addCell(getCellInteger(5, row, r.getQt550M()));
                        sheet.addCell(getCellInteger(6, row, r.getQtCeM()));
                    } catch (Exception e){

                    }

                    row++;
                    sheet.addCell(getCell(0, row, "Fêmea"));
                    try{
                        sheet.addCell(getCellInteger(1, row, r.getQtNascF()));
                        sheet.addCell(getCellInteger(2, row, r.getQtDesmF()));
                        sheet.addCell(getCellInteger(3, row, r.getQt365F()));
                        sheet.addCell(getCellInteger(4, row, r.getQt450F()));
                        sheet.addCell(getCellInteger(5, row, r.getQt550F()));
                        sheet.addCell(getCellInteger(6, row, r.getQtCeF()));

                    } catch (Exception e){

                    }


                    ///////////////////////////////////////////////////////////////////////////////////
                    //Sexta Tabela

                    row++;
                    row++;
                    sheet.addCell(getCell(0, row, "Animais"));
                    sheet.addCell(getCell(1, row, "Nasc"));
                    sheet.addCell(getCell(2, row, "Desm"));
                    sheet.addCell(getCell(3, row, "365 d"));
                    sheet.addCell(getCell(4, row, "450 d"));
                    sheet.addCell(getCell(5, row, "550 d"));
                    sheet.addCell(getCell(6, row, "CE"));

                    row++;
                    sheet.addCell(getCell(0, row, "Macho"));
                    try{
                        sheet.addCell(getCellNumber(1, row, r.getmNascM()));
                        sheet.addCell(getCellNumber(2, row, r.getmDesmM()));
                        sheet.addCell(getCellNumber(3, row, r.getM365M()));
                        sheet.addCell(getCellNumber(4, row, r.getM450M()));
                        sheet.addCell(getCellNumber(5, row, r.getM550M()));
                        sheet.addCell(getCellNumber(6, row, r.getmCeM()));

                    } catch (Exception e){

                    }

                    row++;
                    sheet.addCell(getCell(0, row, "Fêmea"));
                    try{
                        sheet.addCell(getCellNumber(1, row, r.getmNascF()));
                        sheet.addCell(getCellNumber(2, row, r.getmDesmF()));
                        sheet.addCell(getCellNumber(3, row, r.getM365F()));
                        sheet.addCell(getCellNumber(4, row, r.getM450F()));
                        sheet.addCell(getCellNumber(5, row, r.getM550F()));
                        sheet.addCell(getCellNumber(6, row, r.getmCeF()));
                    } catch (Exception e){

                    }


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

            Number n = new Number(col, row, conteudo);
            WritableCell cell = (WritableCell) n;
            return cell;
        }

        WritableCell getCellPerc(int col, int row, int conteudos) {
            WritableCellFormat f = new WritableCellFormat(NumberFormats.PERCENT_INTEGER);
            Double conteudo;
            if (conteudos > 0)
                conteudo = Double.valueOf(conteudos) / 100;
            else
                conteudo = Double.valueOf(conteudos) / 100;

            Number n = new Number(col, row, conteudo, f);
            WritableCell cell = (WritableCell) n;
            return cell;
        }

        WritableCell getCellPerc(int col, int row, Long conteudos) {
            WritableCellFormat f = new WritableCellFormat(NumberFormats.PERCENT_INTEGER);
            Double conteudo;
            if (conteudos > 0)
                conteudo = Double.valueOf(conteudos) / 100;
            else
                conteudo = Double.valueOf(conteudos) / 100;

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

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(in);
        finish();
        return true;
    }


    // pad with " " to the right to the given length (n)
    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    // pad with " " to the left to the given length (n)
    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public void writeStringAsFile(final String fileContents, String fileName) {
        Context context = app.getApplicationContext();
        try {
            FileWriter out = new FileWriter(new File(fileName));
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
            //Logger.logError(TAG, e);
        }
    }


}


