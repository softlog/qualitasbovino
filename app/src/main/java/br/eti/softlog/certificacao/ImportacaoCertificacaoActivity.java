package br.eti.softlog.certificacao;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.omega_r.libs.OmegaCenterIconButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;

import br.eti.softlog.Readers.ReaderCriador;
import br.eti.softlog.Readers.ReaderMTFDados;
import br.eti.softlog.Readers.ReaderMotDescarte;
import br.eti.softlog.Readers.ReaderPedigree;
import br.eti.softlog.Readers.ReaderXLSCert;
import br.eti.softlog.Utils.Util;
import br.eti.softlog.qualitasbovino.AppMain;
import br.eti.softlog.qualitasbovino.ImportacaoActivity;
import br.eti.softlog.qualitasbovino.MainActivity;
import br.eti.softlog.qualitasbovino.Manager;
import br.eti.softlog.qualitasbovino.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ImportacaoCertificacaoActivity extends AppCompatActivity {

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

    @BindView(R.id.txt_criador)
    TextView txtCriador;

    @BindView(R.id.txt_mot_descarte)
    TextView txtMotDescarte;

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
        setContentView(R.layout.activity_importacao_certificacao);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Importação de Dados Certificação");

        app = (AppMain) getApplication();
        manager = new Manager(app);

        //Local de Importação, Cria se não existe
        dirDefault = "/mnt/sdcard/qualitas_bovino/importacao_certificacao";
        final String dirRoot = "/mnt/sdcard";

        //Procura por arquivos
        File directory = new File(dirDefault);
        File[] files = directory.listFiles();

        nomeBD = null;

        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getName();
            String[] partes = filename.split("_");

            if (partes.length != 4 && partes.length !=6)
                break;

            if (partes.length == 4 && partes[1].equals("CRIADORES")) {
                txtCriador.setText(files[i].getPath());
            }

            if (partes.length == 4 && partes[1].equals("DESCARTE")) {
                txtMotDescarte.setText(files[i].getPath());
            }


            if (partes.length == 6 && partes[3].equals("Marcacao")) {
                txtAnimal.setText(files[i].getPath());
                nomeBD = "qualitas_" + partes[0] + "_" +
                   partes[1] + "_" + partes[4];
            }

            if (partes.length == 4 && partes[1].equals("PEDIGREE")) {
                txtPedigree.setText(files[i].getPath());
            }
        }

        if (nomeBD == null) {
            Toast.makeText(getApplicationContext(), "Arquivos para importação não encontrados!", Toast.LENGTH_LONG).show();
            btnImportacao.setEnabled(false);
        } else {
            txtBancoDadosNovo.setText(nomeBD);
            chkBackup.setChecked(true);
            chkDelete.setChecked(true);
        }


        if (Prefs.getString("bancoDadosCurrentCert", "base_teste").equals("base_teste"))
            txtBancoDadosAtual.setText("Base Inicial");
        else
            txtBancoDadosAtual.setText(Prefs.getString("bancoDadosCurrentCert", "Base Inicial"));


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

    @Override
    public boolean onSupportNavigateUp() {
        Intent in = new Intent(getApplicationContext(), MainCertificacaoActivity.class);
        String bd = Prefs.getString("bancoDadosCurrentCert","base_teste");
        Prefs.clear();
        Prefs.putInt("tipo_operacao",2);
        Prefs.putString("bancoDadosCurrentCert",bd);
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

            String bancoBackup = Prefs.getString("bancoDadosCurrentCert", "base_teste");

            if (chkBackup.isChecked())
                app.backupBD(getApplicationContext(), bancoBackup);

            if (chkDelete.isChecked())
                app.getApplicationContext().deleteDatabase(bancoBackup + ".db");

            Prefs.putString("bancoDadosCurrentCert", nomeBD);
            app.setBD(nomeBD);

            ReaderCriador readerCriador;
            ReaderMotDescarte readerMotDescarte;
            ReaderPedigree readerPedigree;
            ReaderXLSCert readerXLSCert;

            readerCriador = new ReaderCriador(app);
            readerMotDescarte = new ReaderMotDescarte(app);
            readerPedigree = new ReaderPedigree(app);
            readerXLSCert = new ReaderXLSCert(app);


            manager.addAllMedicoes();

            if (!txtCriador.getText().toString().isEmpty())
                publishProgress(0);
            readerCriador.read(txtCriador.getText().toString());

            if (!txtMotDescarte.getText().toString().isEmpty())
                publishProgress(1);
            readerMotDescarte.read(txtMotDescarte.getText().toString());

            if (!txtPedigree.getText().toString().isEmpty())
                publishProgress(2);
            readerPedigree.read(txtPedigree.getText().toString());
            if (!txtAnimal.getText().toString().isEmpty())
                publishProgress(3);
           readerXLSCert.read(txtAnimal.getText().toString());


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
                    txtStatus.setText("Importando Motivos de Descarte");
                    break;
                }
                case 2: {
                    txtStatus.setText("Importando Pedigree");
                    break;
                }
                case 3: {
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

            new MaterialDialog.Builder(ImportacaoCertificacaoActivity.this)
                    .title("Importação")
                    .content("Importação concluída")
                    .positiveText("OK")
                    .show();

        }
    }
}
