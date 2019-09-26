package br.eti.softlog.certificacao;

import android.content.Intent;
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
import br.eti.softlog.qualitasbovino.AppMain;
import br.eti.softlog.qualitasbovino.MainActivity;
import br.eti.softlog.qualitasbovino.Manager;
import br.eti.softlog.qualitasbovino.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ResumoCertificacaoActivity extends AppCompatActivity {

    List<Criador> criadores;
    private AppMain app;
    private Manager manager;
    private Util util;

    private List<MTFDados> animais;
    private MTFDados animal;
    private List<MedicoesAnimal> medicoes;
    private MedicoesAnimal medicao;

    private int qtAnimais;
    private int qtAnimaisMacho;
    private int qtAnimaisFemea;

    private int qtAnimaisAvaliados;
    private int qtAnimaisMachoAvaliado;
    private int qtAnimaisFemeaAvaliado;



    private int qtAnimaisAprovados;
    private int qtAnimaisMachoAprovado;
    private int qtAnimaisFemeaAprovado;

    private int qtAnimaisCeip;
    private int qtAnimaisMachoCeip;
    private int qtAnimaisFemeaCeip;

    private int qtAnimaisSuplente;
    private int qtAnimaisMachoSuplente;
    private int qtAnimaisFemeaSuplente;

    private int qtAnimaisDescarte;
    private int qtAnimaisMachoDescarte;
    private int qtAnimaisFemeaDescarte;

    private String[][] resumo;

    @BindView(R.id.tableView)
    TableView tableView;

    //Configuracao Graficos
    ArrayList<Integer> colors = new ArrayList<>();
    ArrayList<Integer> colors2 = new ArrayList<>();
    ArrayList<Integer> colors3 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_certificacao);


        ButterKnife.bind(this);

        app = (AppMain) getApplication();
        manager = new Manager(app);
        util = new Util();

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

        qtAnimais=0;
        qtAnimaisMacho=0;
        qtAnimaisFemea=0;
        qtAnimaisAvaliados=0;
        qtAnimaisMachoAvaliado=0;
        qtAnimaisFemeaAvaliado=0;

        qtAnimaisAprovados = 0;
        qtAnimaisMachoAprovado = 0;
        qtAnimaisFemeaAprovado = 0;

        qtAnimaisSuplente = 0;
        qtAnimaisMachoSuplente = 0;
        qtAnimaisFemeaSuplente = 0;

        qtAnimaisDescarte=0;
        qtAnimaisMachoDescarte=0;
        qtAnimaisFemeaDescarte=0;

        if (idCriador==0) {
            animais = app.getDaoSession().getMTFDadosDao()
                    .queryBuilder().orderAsc(MTFDadosDao.Properties.Id)
                    .list();
        } else {
            animais = app.getDaoSession().getMTFDadosDao()
                    .queryBuilder()
                    .where(MTFDadosDao.Properties.CriadorId.eq(idCriador))
                    .orderAsc(MTFDadosDao.Properties.Id)
                    .list();
        }

        qtAnimais = animais.size();

        for (int i = 0; i < animais.size(); i++) {
            animal = animais.get(i);

            if (animal.getSexo().equals("M")) {
                qtAnimaisMacho++;
                if (animal.getAvaliado()) {
                    qtAnimaisAvaliados++;
                    qtAnimaisMachoAvaliado++;

                    if (animal.getMarcacao()!=null){
                        if (animal.getMarcacao()== Long.valueOf(1)){
                            qtAnimaisAprovados++;
                            qtAnimaisMachoAprovado++;
                            if (animal.getCeip().equals("S")) {
                                qtAnimaisSuplente++;
                                qtAnimaisMachoSuplente++;
                            }
                        }
                    }
                }

            } else {
                qtAnimaisFemea++;
                if (animal.getAvaliado()) {
                    qtAnimaisAvaliados++;
                    qtAnimaisFemeaAvaliado++;

                    if (animal.getMarcacao()!=null){
                        if (animal.getMarcacao()== Long.valueOf(1)){
                            qtAnimaisAprovados++;
                            qtAnimaisFemeaAprovado++;
                            if (animal.getCeip().equals("S")) {
                                qtAnimaisSuplente++;
                                qtAnimaisFemeaSuplente++;
                            }
                        }
                    }

                }
            }
        }


        String[] headers = {"", "Total", "Marcados", "Descartados","CEIP","Suplentes", "N.Avaliados"};
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, headers));

        Long percAnimalAprovado = Math.round(Double.valueOf(qtAnimaisAprovados)/Double.valueOf(qtAnimaisAvaliados)*100);
        Long percAnimalMachoAprovado = Math.round(Double.valueOf(qtAnimaisMachoAprovado)/Double.valueOf(qtAnimaisMachoAvaliado)*100);
        Long percAnimalFemeaAprovado = Math.round(Double.valueOf(qtAnimaisFemeaAprovado)/Double.valueOf(qtAnimaisFemeaAvaliado)*100);
        String[][] resumo = {
                {
                        "Machos",
                        String.valueOf(qtAnimaisMacho),
                        String.valueOf(qtAnimaisMachoAprovado),
                        String.valueOf(qtAnimaisMachoAvaliado - qtAnimaisMachoAprovado),
                        String.valueOf(qtAnimaisMachoAvaliado - qtAnimaisMachoSuplente),
                        String.valueOf(qtAnimaisMachoSuplente),
                        String.valueOf(qtAnimaisMacho-qtAnimaisMachoAvaliado)
                },
                {
                        "Fêmeas",
                        String.valueOf(qtAnimaisFemea),
                        String.valueOf(qtAnimaisFemeaAprovado),
                        String.valueOf(qtAnimaisFemeaAvaliado - qtAnimaisFemeaAprovado),
                        String.valueOf(qtAnimaisFemeaAvaliado - qtAnimaisFemeaSuplente),
                        String.valueOf(qtAnimaisFemeaSuplente),
                        String.valueOf(qtAnimaisFemea-qtAnimaisFemeaAvaliado)
                },
                {
                        "Total",
                        String.valueOf(qtAnimais),
                        String.valueOf(qtAnimaisAprovados),
                        String.valueOf(qtAnimaisAvaliados - qtAnimaisAprovados),
                        String.valueOf(qtAnimaisAvaliados - qtAnimaisSuplente),
                        String.valueOf(qtAnimaisSuplente),
                        String.valueOf(qtAnimais-qtAnimaisAvaliados)
                }
        };
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, resumo));

    }
}
