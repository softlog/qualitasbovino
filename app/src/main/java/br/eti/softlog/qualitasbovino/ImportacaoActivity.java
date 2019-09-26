package br.eti.softlog.qualitasbovino;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.omega_r.libs.OmegaCenterIconButton;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnDismissListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.eti.softlog.Readers.ReaderCriador;
import br.eti.softlog.Readers.ReaderMTFDados;
import br.eti.softlog.Readers.ReaderPedigree;
import br.eti.softlog.Utils.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ImportacaoActivity extends AppCompatActivity {

    private AppMain app;
    private Manager manager;
    private Util util;
    private String dirDefault;
    private String dirRoot;
    private String nomeBD;
    private DialogProperties properties = new DialogProperties();


    private ProgressBar circularBar;


    @BindView(R.id.btn_importacao)
    OmegaCenterIconButton btnImportacao;

    @BindView(R.id.btn_criador)
    Button btnCriador;

    @BindView(R.id.btn_pedigree)
    Button btnPedigree;

    @BindView(R.id.btn_animal)
    Button btnAnimal;

    @BindView(R.id.txt_criador)
    TextView txtCriador;

    @BindView(R.id.txt_pedigree)
    TextView txtPedigree;

    @BindView(R.id.txt_animal)
    TextView txtAnimal;

    @BindView(R.id.txt_status)
    TextView txtStatus;

    @BindView(R.id.txt_banco_dados_atual)
    TextView txtBancoDadosAtual;

    @BindView(R.id.txt_banco_dados_novo)
    TextView txtBancoDadosNovo;

    @BindView(R.id.chkBackup)
    AppCompatCheckBox chkBackup;

    @BindView(R.id.chkDelete)
    AppCompatCheckBox chkDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importacao);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Importação de Dados");

        app = (AppMain) getApplication();
        manager = new Manager(app);

        //Local de Importação, Cria se não existe
        dirDefault = "/mnt/sdcard/qualitas_bovino/importacao";
        final String dirRoot = "/mnt/sdcard";

        //Procura por arquivos
        File directory = new File(dirDefault);
        File[] files = directory.listFiles();


        nomeBD = null;

        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getName();
            String[] partes = filename.split("_");

            if (partes.length != 4)
                break;

            if (partes[1].equals("CRIADORES")) {
                txtCriador.setText(files[i].getPath());
            }

            if (partes[1].equals("DADOS")) {
                txtAnimal.setText(files[i].getPath());
            }

            if (partes[1].equals("PEDIGREE")) {
                txtPedigree.setText(files[i].getPath());
            }

            nomeBD = "qualitas_" + partes[2];
        }

        if (nomeBD == null) {
            Toast.makeText(getApplicationContext(), "Arquivos para importação não encontrados!", Toast.LENGTH_LONG).show();
            btnImportacao.setEnabled(false);
        } else {
            txtBancoDadosNovo.setText(nomeBD);
            chkBackup.setChecked(true);
            chkDelete.setChecked(true);
        }

        btnAnimal.setEnabled(false);
        btnPedigree.setEnabled(false);
        btnCriador.setEnabled(false);

        if (Prefs.getString("bancoDadosCurrent", "base_teste").equals("base_teste"))
            txtBancoDadosAtual.setText("Base Inicial");
        else
            txtBancoDadosAtual.setText(Prefs.getString("bancoDadosCurrent", "Base Inicial"));


        circularBar = findViewById(R.id.circularBar);
        circularBar.setVisibility(View.GONE);
        txtStatus.setVisibility(View.GONE);

        properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.SINGLE_MODE;
        properties.root = new File(dirRoot);
        properties.error_dir = new File(dirDefault);
        properties.offset = new File(dirDefault);
        String[] extensoes = {"csv", "CSV"};
        properties.extensions = extensoes;



        btnImportacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncCircular().execute();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        String bd = Prefs.getString("bancoDadosCurrent","base_teste");
        Prefs.clear();
        Prefs.putInt("tipo_operacao",1);
        Prefs.putString("bancoDadosCurrent",bd);
        startActivity(in);
        finish();
        return true;
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

            String bancoBackup = Prefs.getString("bancoDadosCurrent", "base_teste");

            if (chkBackup.isChecked())
                app.backupBD(getApplicationContext(), bancoBackup);

            if (chkDelete.isChecked())
                app.getApplicationContext().deleteDatabase(bancoBackup + ".db");

            Prefs.putString("bancoDadosCurrent", nomeBD);
            app.setBD(nomeBD);

            ReaderCriador readerCriador;
            ReaderPedigree readerPedigree;
            ReaderMTFDados readerMTFDados;

            readerCriador = new ReaderCriador(app);
            readerPedigree = new ReaderPedigree(app);
            readerMTFDados = new ReaderMTFDados(app);


            manager.addAllMedicoes();

            if (!txtCriador.getText().toString().isEmpty())
                publishProgress(0);
            readerCriador.read(txtCriador.getText().toString());

            if (!txtPedigree.getText().toString().isEmpty())
                publishProgress(1);
            readerPedigree.read(txtPedigree.getText().toString());
            if (!txtAnimal.getText().toString().isEmpty())
                publishProgress(2);
            readerMTFDados.read(txtAnimal.getText().toString());


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            switch (values[0]) {
                case 0: {
                    txtStatus.setText("Importando criadores");
                    break;
                }
                case 1: {
                    txtStatus.setText("Importando Pedigree");
                    break;
                }
                case 2: {
                    txtStatus.setText("Importando Dados dos Animais");
                    break;
                }
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            txtStatus.setText("");
            txtStatus.setVisibility(View.GONE);
            circularBar.setVisibility(View.GONE);

            new MaterialDialog.Builder(ImportacaoActivity.this)
                    .title("Importação")
                    .content("Importação concluída")
                    .positiveText("OK")
                    .show();

        }
    }
}
