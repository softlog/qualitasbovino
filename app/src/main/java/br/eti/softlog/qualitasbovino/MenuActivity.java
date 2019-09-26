package br.eti.softlog.qualitasbovino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.pixplicity.easyprefs.library.Prefs;

import br.eti.softlog.Readers.ReaderXLSCert;
import br.eti.softlog.certificacao.MainCertificacaoActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {


    private AppMain app;
    private ReaderXLSCert readerXLSCert;

    @BindView(R.id.btn_avaliacao)
    AppCompatButton btnAvaliacao;

    @BindView(R.id.btn_certificacao)
    AppCompatButton btnCertificacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        app = (AppMain)getApplicationContext();

        //readerXLSCert = new ReaderXLSCert(app);
        //readerXLSCert.read("/sdcard/qualitas_bovino/importacao/xls_cert/QLT_JM_2017_Marcacao_20190402_1435.xls");

        btnAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inCall = new Intent(getApplicationContext(),MainActivity.class);
                String bd = Prefs.getString("bancoDadosCurrent","base_teste");
                Prefs.clear();
                Prefs.putInt("tipo_operacao",1);
                Prefs.putString("bancoDadosCurrent",bd);
                startActivity(inCall);
            }
        });

        btnCertificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inCall = new Intent(getApplicationContext(), MainCertificacaoActivity.class);
                String bd = Prefs.getString("bancoDadosCurrentCert","base_teste");
                Prefs.clear();
                Prefs.putInt("tipo_operacao",2);
                Prefs.putString("bancoDadosCurrentCert",bd);
                startActivity(inCall);
            }
        });
    }

}
