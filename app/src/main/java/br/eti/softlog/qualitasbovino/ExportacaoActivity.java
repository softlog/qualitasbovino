package br.eti.softlog.qualitasbovino;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.celites.androidexternalfilewriter.AppExternalFileWriter;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.omega_r.libs.OmegaCenterIconButton;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import br.eti.softlog.Readers.ReaderCriador;
import br.eti.softlog.Readers.ReaderMTFDados;
import br.eti.softlog.Readers.ReaderPedigree;
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
import butterknife.BindView;
import butterknife.ButterKnife;
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
                fileAvaliacaoCsv = nomeBD.replace(" ","_") + "_avaliacao_planilha";
                fileAlterado = nomeBD + "_log_alteracao";
                fileNovo = nomeBD + "_animal_novos";
                filePendente = nomeBD + "_pendentes";

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
                    writerAvaliacao = new PrintWriter(new OutputStreamWriter(byteStream,"UTF8"));


                    for (int i = 0; i < animais.size(); i++) {
                        List<MedicoesAnimal> medidas = animais.get(i).getMedicoesAnimals();

                        //Se o animal esta avaliado, mas primeira nota esta vazia, entao foi avaliado
                        //apenas para venda
                        if (medidas.get(0).getValor()==null)
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

                            //Ignorar se for Lote
                            if (medidas.get(j).getMedicaoId() == 222530){
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
                    if (animais.get(i).getMedicoesAnimals().get(0).getValor()==null)
                        continue;

                    for (int j = 0; j < medidas.size(); j++) {
                        Medicao medida = medidas.get(j);

                        if (medida.getAbrev().equals("VEN"))
                            continue;


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

                        if (m.getValor()== null){
                            linha.add("");
                            continue;
                        }

                        if (m.getValor()==0)
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


                List<List<String>> contents = new ArrayList<>();

                for (int i = 0; i < animaisNovos.size(); i++) {
                    List<String> linha = new ArrayList<>();
                    linha.add(util.trataIdf(animaisNovos.get(i).getIdf()));
                    linha.add(animaisNovos.get(i).getAnimalPrincipal().getNome());
                    linha.add(animaisNovos.get(i).getSexo());
                    linha.add(animaisNovos.get(i).getDataNasc());
                    linha.add(animaisNovos.get(i).getAnimalPrincipal().getCriador().getCodigo());
                    contents.add(linha);
                }

                if (animaisNovos.size() > 0)
                    CsvWriter.toCsvFile(header, contents, fileNovo, dirCriador);

                ////////////////////////////////////////////////////////////////////////////////////
                //Rotina de Exportacao Log Alteração
                //Animal Novo
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

                List<List<String>> contents2 = new ArrayList<>();

                for (int i = 0; i < animaisEditados.size(); i++) {
                    List<String> linha = new ArrayList<>();
                    linha.add(util.trataIdf(animaisEditados.get(i).getIdf()));
                    linha.add(animaisEditados.get(i).getSexo());
                    linha.add(animaisEditados.get(i).getAnimalPrincipal().getCriador().getCodigo());
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
                        contents2.add(linha);
                    }
                }

                if (contents2.size() > 0)
                    CsvWriter.toCsvFile(header2, contents2, fileAlterado, dirCriador);


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


