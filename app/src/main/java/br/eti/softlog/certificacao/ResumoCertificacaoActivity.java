package br.eti.softlog.certificacao;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.Criador;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MTFDadosDao;
import br.eti.softlog.model.MedicoesAnimal;
import br.eti.softlog.model.MedicoesAnimalDao;
import br.eti.softlog.model.ResumoCertificacao;
import br.eti.softlog.qualitasbovino.AppMain;
import br.eti.softlog.qualitasbovino.MainActivity;
import br.eti.softlog.qualitasbovino.Manager;
import br.eti.softlog.qualitasbovino.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ResumoCertificacaoActivity extends AppCompatActivity {




    private String[][] resumo;

    @BindView(R.id.tableView)
    TableView tableView;

    @BindView(R.id.tableView2)
    TableView tableView2;

    @BindView(R.id.tableView3)
    TableView tableView3;

    @BindView(R.id.tableView4)
    TableView tableView4;

    @BindView(R.id.tableView5)
    TableView tableView5;

    @BindView(R.id.tableView6)
    TableView tableView6;

    //Configuracao Graficos
    ArrayList<Integer> colors = new ArrayList<>();
    ArrayList<Integer> colors2 = new ArrayList<>();
    ArrayList<Integer> colors3 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_certificacao);


        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Resumo Certificação");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getResumo(Prefs.getLong("filtro_fazenda",0));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        //Intent in = new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(in);
        finish();
        return true;
    }


    public void getResumo(Long idCriador){

        ResumoCertificacao r;
        r = new ResumoCertificacao(getApplicationContext());

        String[] headers = {"Candidatos", "Total", "%","Ceip", "%", "Suplente","%"};

        SimpleTableHeaderAdapter h1 = new SimpleTableHeaderAdapter(this, headers);
        h1.setTextSize(12);
        h1.setPaddingTop(10);
        h1.setPaddingBottom(10);
        tableView.setHeaderAdapter(h1);

        Long percCeipMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoCeip())/Double.valueOf(r.getQtAnimaisMacho())*100);
        Long percCeipFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaCeip())/Double.valueOf(r.getQtAnimaisFemea())*100);

        Long percSuplenteMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoSuplente())/Double.valueOf(r.getQtAnimaisMacho())*100);
        Long percSuplenteFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaSuplente())/Double.valueOf(r.getQtAnimaisFemea())*100);

        String[][] resumo = {
                {
                        "Machos",
                        String.valueOf(r.getQtAnimaisMacho()),
                        "100",
                        String.valueOf(r.getQtAnimaisMachoCeip()),
                        String.valueOf(percCeipMacho) + "%",
                        String.valueOf(r.getQtAnimaisMachoSuplente()),
                        String.valueOf(percSuplenteMacho) + "%"
                },
                {
                        "Fêmeas",
                        String.valueOf(r.getQtAnimaisFemea()),
                        "100",
                        String.valueOf(r.getQtAnimaisFemeaCeip()),
                        String.valueOf(percCeipFemea) + "%",
                        String.valueOf(r.getQtAnimaisFemeaSuplente()),
                        String.valueOf(percSuplenteFemea) + "%"
                }
        };

        SimpleTableDataAdapter da = new SimpleTableDataAdapter(this, resumo);
        da.setTextSize(14);
        tableView.setDataAdapter(da);

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Tabela 2 - Certificados

        String[] headers2 = {"Certificados", "Total", "%", "Ceip", "%","Suplente","%"};

        SimpleTableHeaderAdapter h2 = new SimpleTableHeaderAdapter(this, headers2);
        h2.setTextSize(12);
        h2.setPaddingTop(10);
        h2.setPaddingBottom(10);
        tableView2.setHeaderAdapter(h2);

        Long percMachoCertificado = Math.round(Double.valueOf(r.getQtAnimaisMachoCertificado())/Double.valueOf(r.getQtAnimaisMacho())*100);
        Long percFemeaCertificado = Math.round(Double.valueOf(r.getQtAnimaisFemeaCertificado())/Double.valueOf(r.getQtAnimaisFemea())*100);

        percCeipMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoCertificadoCeip())/Double.valueOf(r.getQtAnimaisMachoCertificado())*100);
        percCeipFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaCertificadoCeip())/Double.valueOf(r.getQtAnimaisFemeaCertificado())*100);


        percSuplenteMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoCertificadoSuplente())/Double.valueOf(r.getQtAnimaisMachoCertificado())*100);
        percSuplenteFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaCertificadoSuplente())/Double.valueOf(r.getQtAnimaisFemeaCertificado())*100);


        String[][] resumo2 = {
                {
                        "Machos",
                        String.valueOf(r.getQtAnimaisMachoCertificado()),
                        String.valueOf(percMachoCertificado) + "%",
                        String.valueOf(r.getQtAnimaisMachoCertificadoCeip()),
                        String.valueOf(percCeipMacho) + "%",
                        String.valueOf(r.getQtAnimaisMachoCertificadoSuplente()),
                        String.valueOf(percSuplenteMacho) + "%"
                },
                {
                        "Fêmeas",
                        String.valueOf(r.getQtAnimaisFemeaCertificado()),
                        String.valueOf(percFemeaCertificado) + "%",
                        String.valueOf(r.getQtAnimaisFemeaCertificadoCeip()),
                        String.valueOf(percCeipFemea) + "%",
                        String.valueOf(r.getQtAnimaisFemeaCertificadoSuplente()),
                        String.valueOf(percSuplenteFemea) + "%"
                }
        };

        //tableView.setColumnModel(tableColumnModel);
        SimpleTableDataAdapter da2 = new SimpleTableDataAdapter(this, resumo2);
        da2.setTextSize(14);
        tableView2.setDataAdapter(da2);

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Tabela 3 - Desclassificados
        String[] headers3 = {"Desclassificados", "Total", "%", "Ceip", "%","Suplente","%"};

        SimpleTableHeaderAdapter h3 = new SimpleTableHeaderAdapter(this, headers3);
        h3.setTextSize(12);
        h3.setPaddingTop(10);
        h3.setPaddingBottom(10);
        tableView3.setHeaderAdapter(h3);

        //r.setQtAnimaisMachoDescarte(r.getQtAnimaisMachoCeipDescarte() + r.getQtAnimaisMachoSuplenteDescarte());
        //r.setQtAnimaisFemeaDescarte(r.getQtAnimaisFemeaCeipDescarte() + r.getQtAnimaisFemeaSuplenteDescarte());

        Long percMachoDescarte = Math.round(Double.valueOf(r.getQtAnimaisMachoDescarte())/Double.valueOf(r.getQtAnimaisMacho())*100);
        Long percFemeaDescarte = Math.round(Double.valueOf(r.getQtAnimaisFemeaDescarte())/Double.valueOf(r.getQtAnimaisFemea())*100);


        percCeipMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoCeipDescarte())/Double.valueOf(r.getQtAnimaisMachoDescarte())*100);
        percCeipFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaCeipDescarte())/Double.valueOf(r.getQtAnimaisFemeaDescarte())*100);


        percSuplenteMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoSuplenteDescarte())/Double.valueOf(r.getQtAnimaisMachoDescarte())*100);
        percSuplenteFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaSuplenteDescarte())/Double.valueOf(r.getQtAnimaisFemeaDescarte())*100);


        String[][] resumo3 = {
                {
                        "Machos",
                        String.valueOf(r.getQtAnimaisMachoDescarte()),
                        String.valueOf(percMachoDescarte) + "%",
                        String.valueOf(r.getQtAnimaisMachoCeipDescarte()),
                        String.valueOf(percCeipMacho) + "%",
                        String.valueOf(r.getQtAnimaisMachoSuplenteDescarte()),
                        String.valueOf(percSuplenteMacho) + "%"
                },
                {
                        "Fêmeas",
                        String.valueOf(r.getQtAnimaisFemeaDescarte()),
                        String.valueOf(percFemeaDescarte) + "%",
                        String.valueOf(r.getQtAnimaisFemeaCeipDescarte()),
                        String.valueOf(percCeipFemea) + "%",
                        String.valueOf(r.getQtAnimaisFemeaSuplenteDescarte()),
                        String.valueOf(percSuplenteFemea) + "%"
                }
        };

        //tableView.setColumnModel(tableColumnModel);
        SimpleTableDataAdapter da3 = new SimpleTableDataAdapter(this, resumo3);
        da3.setTextSize(14);
        tableView3.setDataAdapter(da3);

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Tabela 4 - Media Certificados
        String[] headers4 = {"Certificados", "Peso", "CE", "Classificação", "I.Qualitas"};

        SimpleTableHeaderAdapter h4 = new SimpleTableHeaderAdapter(this, headers4);
        h4.setTextSize(12);
        h4.setPaddingTop(10);
        h4.setPaddingBottom(10);
        tableView4.setHeaderAdapter(h4);

        String[][] resumo4 = {
                {
                        "Machos",
                        String.valueOf(r.getmPesoCertMacho()),
                        String.valueOf(r.getmCeCertMacho()),
                        String.valueOf(r.getmCertClassMacho()),
                        String.valueOf(r.getmCertIQMacho())
                },
                {
                        "Fêmeas",
                        String.valueOf(r.getmPesoCertFemea()),
                        String.valueOf(r.getmCeCertFemea()),
                        String.valueOf(r.getmCertClassFemea()),
                        String.valueOf(r.getmCertIQFemea())
                }
        };

        //tableView.setColumnModel(tableColumnModel);
        SimpleTableDataAdapter da4 = new SimpleTableDataAdapter(this, resumo4);
        da4.setTextSize(14);
        tableView4.setDataAdapter(da4);

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Tabela 5 - Media Certificados P e F
        String[] headers5 = {"Certificados", "Peso", "CE", "Classificação", "I.Qualitas", "Total"};

        SimpleTableHeaderAdapter h5 = new SimpleTableHeaderAdapter(this, headers5);
        h5.setTextSize(12);
        h5.setPaddingTop(10);
        h5.setPaddingBottom(10);
        tableView5.setHeaderAdapter(h5);

        String[][] resumo5 = {
                {
                        "Machos P",
                        String.valueOf(r.getmPesoCertMachoP()),
                        String.valueOf(r.getmCeCertMachoP()),
                        String.valueOf(r.getmCertClassMachoP()),
                        String.valueOf(r.getmCertIQMachoP()),
                        String.valueOf(r.getmCertTotalP())
                },
                {
                        "Machos F",
                        String.valueOf(r.getmPesoCertMachoF()),
                        String.valueOf(r.getmCeCertMachoF()),
                        String.valueOf(r.getmCertClassMachoF()),
                        String.valueOf(r.getmCertIQMachoF()),
                        String.valueOf(r.getmCertTotalF())
                }
        };

        //tableView.setColumnModel(tableColumnModel);
        SimpleTableDataAdapter da5 = new SimpleTableDataAdapter(this, resumo5);
        da5.setTextSize(14);
        tableView5.setDataAdapter(da5);


        ///////////////////////////////////////////////////////////////////////////////////////////
        //Tabela 6 - Avaliados
        String[] headers6 = {"Avaliados", "Total", "%", "Ceip", "%","Suplente","%"};

        SimpleTableHeaderAdapter h6 = new SimpleTableHeaderAdapter(this, headers6);
        h6.setTextSize(12);
        h6.setPaddingTop(10);
        h6.setPaddingBottom(10);
        tableView6.setHeaderAdapter(h6);

        //r.setQtAnimaisMachoAvaliado(r.getQtAnimaisMachoAvaliado() + r.getQtAnimaisMachoAvaliado());
        //r.setQtAnimaisFemeaAvaliado(r.getQtAnimaisFemeaCeip() + r.getQtAnimaisFemeaSuplente());

        Long percMachoAvaliado = Math.round(Double.valueOf(r.getQtAnimaisMachoAvaliado())/Double.valueOf(r.getQtAnimaisMacho())*100);
        Long percFemeaAvaliado = Math.round(Double.valueOf(r.getQtAnimaisFemeaAvaliado())/Double.valueOf(r.getQtAnimaisFemea())*100);

        if (r.getQtAnimaisMachoCeipAvaliado()>0)
            percCeipMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoCeipAvaliado())/Double.valueOf(r.getQtAnimaisMachoAvaliado())*100);
        else
            percCeipMacho = Long.valueOf(0);


        if (r.getQtAnimaisFemeaCeipAvaliado()>0)
            percCeipFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaCeipAvaliado())/Double.valueOf(r.getQtAnimaisFemeaAvaliado())*100);
        else
            percCeipFemea = Long.valueOf(0);


        percSuplenteMacho = Math.round(Double.valueOf(r.getQtAnimaisMachoSuplenteAvaliado())/Double.valueOf(r.getQtAnimaisMachoAvaliado())*100);
        percSuplenteFemea = Math.round(Double.valueOf(r.getQtAnimaisFemeaSuplenteAvaliado())/Double.valueOf(r.getQtAnimaisFemeaAvaliado())*100);

        String[][] resumo6 = {
                {
                        "Machos",
                        String.valueOf(r.getQtAnimaisMachoAvaliado()),
                        String.valueOf(percMachoAvaliado) + "%",
                        String.valueOf(r.getQtAnimaisMachoCeipAvaliado()),
                        String.valueOf(percCeipMacho) + "%",
                        String.valueOf(r.getQtAnimaisMachoSuplenteAvaliado()),
                        String.valueOf(percSuplenteMacho) + "%"
                },
                {
                        "Fêmeas",
                        String.valueOf(r.getQtAnimaisFemeaAvaliado()),
                        String.valueOf(percFemeaAvaliado) + "%",
                        String.valueOf(r.getQtAnimaisFemeaCeipAvaliado()),
                        String.valueOf(percCeipFemea) + "%",
                        String.valueOf(r.getQtAnimaisFemeaSuplenteAvaliado()),
                        String.valueOf(percSuplenteFemea) + "%"
                }
        };

        //tableView.setColumnModel(tableColumnModel);
        SimpleTableDataAdapter da6 = new SimpleTableDataAdapter(this, resumo6);
        da6.setTextSize(14);
        tableView6.setDataAdapter(da6);

    }

}
