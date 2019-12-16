package br.eti.softlog.qualitasbovino;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idescout.sql.SqlScoutServer;
import com.pixplicity.easyprefs.library.Prefs;


import br.eti.softlog.certificacao.MainCertificacaoActivity;
import br.eti.softlog.qualitasbovino.MainActivity;
import br.eti.softlog.qualitasbovino.R;

public class SplashActive extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000;
    AppMain myapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_active);

        SqlScoutServer.create(this, getPackageName());

        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal
                myapp = (AppMain) getApplicationContext();
                //Intent i = new Intent(SplashActive.this,MainActivity.class);
                int tipoOperacao = Prefs.getInt("tipo_operacao",0);
                Intent i;
                if (tipoOperacao == 0){
                    i = new Intent(SplashActive.this,MenuActivity.class);
                } else if (tipoOperacao==1) {
                    i = new Intent(SplashActive.this,MainActivity.class);
                    String bd = Prefs.getString("bancoDadosCurrent","base_teste");
                    String lote = Prefs.getString("lote","");
                    Prefs.clear();
                    Prefs.putString("bancoDadosCurrent",bd);
                    Prefs.putString("lote",lote);
                    Prefs.putInt("tipo_operacao",1);
                } else {
                    i = new Intent(SplashActive.this, MainCertificacaoActivity.class);
                    String bd = Prefs.getString("bancoDadosCurrentCert","base_teste");
                    Prefs.clear();
                    Prefs.putString("bancoDadosCurrentCert",bd);
                    Prefs.putInt("tipo_operacao",2);
                }
                startActivity(i);
                // Fecha esta activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
