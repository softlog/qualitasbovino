package br.eti.softlog.qualitasbovino;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.eti.softlog.Utils.MyAxisAnimalValueFormatter;
import br.eti.softlog.Utils.MyAxisValueFormatter;
import br.eti.softlog.Utils.MyValueFormatter;
import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.Criador;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MTFDadosDao;
import br.eti.softlog.model.MedicoesAnimal;
import br.eti.softlog.model.MedicoesAnimalDao;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ResumoActivity extends AppCompatActivity {

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

    private int qtAnimaisDescarte;
    private int qtAnimaisMachoDescarte;
    private int qtAnimaisFemeaDescarte;

    private int qtAnimaisVenda;
    private int qtAnimaisMachoVenda;
    private int qtAnimaisFemeaVenda;

    private int qtAnimaisSoVenda;
    private int qtAnimaisMachoSoVenda;
    private int qtAnimaisFemeaSoVenda;

    private int qtAnimaisDescarteVenda;
    private int qtAnimaisMachoDescarteVenda;
    private int qtAnimalFemeaDescarteVenda;

    private int qtNasc;
    private int qtNascM;
    private int qtNascF;

    private int qtDesm;
    private int qtDesmM;
    private int qtDesmF;

    private int qt365;
    private int qt365M;
    private int qt365F;

    private int qt450;
    private int qt450M;
    private int qt450F;


    private int qt550;
    private int qt550M;
    private int qt550F;

    private int qtCe;
    private int qtCeM;
    private int qtCeF;

    private Double mNasc;
    private Double mNascM;
    private Double mNascF;

    private Double mDesm;
    private Double mDesmM;
    private Double mDesmF;

    private Double m365;
    private Double m365M;
    private Double m365F;

    private Double m450;
    private Double m450M;
    private Double m450F;


    private Double m550;
    private Double m550M;
    private Double m550F;

    private Double mCe;
    private Double mCeM;
    private Double mCeF;

    private Double vNascM;
    private Double vDesmM;
    private Double v365M;
    private Double v450M;
    private Double v550M;
    private Double vCeM;

    private Double vNascF;
    private Double vDesmF;
    private Double v365F;
    private Double v450F;
    private Double v550F;
    private Double vCeF;



    private String mRepM;
    private String mRepF;

    private String mUbeM;
    private String mUbeF;

    private String mMuscM;
    private String mMuscF;

    private String mFraM;
    private String mFraF;

    private String mAprM;
    private String mAprF;

    private String mOssM;
    private String mOssF;

    private String mProM;
    private String mProF;

    private String mGarM;
    private String mGarF;

    private String mUmbM;
    private String mUmbF;

    private String mBocM;
    private String mBocF;

    private String mCauM;
    private String mCauF;

    private String mPlaM;
    private String mPlaF;

    private String mTemM;
    private String mTemF;

    private String mTteM;
    private String mTteF;

    private String mRacM;
    private String mRacF;

    //Configuracao Graficos
    ArrayList<Integer> colors = new ArrayList<>();
    ArrayList<Integer> colors2 = new ArrayList<>();
    ArrayList<Integer> colors3 = new ArrayList<>();


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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);

        ButterKnife.bind(this);

        app = (AppMain) getApplication();
        manager = new Manager(app);
        util = new Util();

        getSupportActionBar().setTitle("Resumo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        getResumo(Prefs.getLong("filtro_fazenda",0));



    }


    @Override
    public boolean onSupportNavigateUp() {
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(in);
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

        qtAnimaisDescarte=0;
        qtAnimaisMachoDescarte=0;
        qtAnimaisFemeaDescarte=0;

        qtAnimaisVenda = 0;
        qtAnimaisMachoVenda = 0;
        qtAnimaisFemeaVenda = 0;

        qtAnimaisDescarteVenda = 0;
        qtAnimaisMachoDescarteVenda = 0;
        qtAnimalFemeaDescarteVenda = 0;

        qtAnimaisDescarteVenda = 0;
        qtAnimaisMachoDescarteVenda = 0;
        qtAnimalFemeaDescarteVenda = 0;


        //Total Animal Macho
        QueryBuilder qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"));
        animais = qryAnimal.list();
        qtAnimaisMacho = animais.size();

        //Total Animal Femea
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"));

        animais = qryAnimal.list();

        qtAnimaisFemea = animais.size();

        qtAnimais = qtAnimaisMacho + qtAnimaisFemea;

       //Animais Avaliados Macho
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"))
                .where(MTFDadosDao.Properties.Avaliado.eq(true));

        animais = qryAnimal.list();

        qtAnimaisMachoAvaliado = animais.size();

        //Animais Avaliados Femea
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"))
                .where(MTFDadosDao.Properties.Avaliado.eq(true));

        animais = qryAnimal.list();

        qtAnimaisFemeaAvaliado = animais.size();

        qtAnimaisAvaliados = qtAnimaisMachoAvaliado + qtAnimaisFemeaAvaliado;

        //Animais Venda Macho Descartado
        WhereCondition.StringCondition condition = new WhereCondition.StringCondition("" +
                "animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id = 321524 AND valor = 1)" +
                " AND animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id = 141529 AND valor = 1)");

        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }


        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimaisMachoDescarteVenda = animais.size();

        //Animais Venda Femea Descartado
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimalFemeaDescarteVenda = animais.size();

        qtAnimaisDescarteVenda = qtAnimaisMachoDescarteVenda + qtAnimalFemeaDescarteVenda;


        condition = new WhereCondition.StringCondition("" +
                "animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id = 321524 AND valor = 1)");


        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }


        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimaisMachoVenda = animais.size();

        //Animais Venda Femea Descartado
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimaisFemeaVenda = animais.size();

        qtAnimaisVenda = qtAnimaisMachoVenda + qtAnimaisFemeaVenda;

        //Animais Descartados Macho
        condition = new WhereCondition.StringCondition("" +
                "animal IN (SELECT animal FROM medicoes_animal WHERE medicao_id = 141529 AND valor = 1)");

        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }


        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("M"))
                .where(condition);
        animais = qryAnimal.list();

        qtAnimaisMachoDescarte = animais.size() - qtAnimaisMachoDescarteVenda;

        //Animais Descartados Fêmea
        qryAnimal = app.getDaoSession().getMTFDadosDao()
                .queryBuilder().orderAsc(MTFDadosDao.Properties.Id);

        if (idCriador>0) {
            qryAnimal.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        }

        qryAnimal.where(MTFDadosDao.Properties.Sexo.eq("F"))
                .where(condition);

        animais = qryAnimal.list();

        qtAnimaisFemeaDescarte = animais.size() - qtAnimalFemeaDescarteVenda;

        qtAnimaisDescarte = qtAnimaisMachoDescarte + qtAnimaisFemeaDescarte;

        qtAnimaisSoVenda = qtAnimaisVenda - qtAnimaisDescarteVenda;
        qtAnimaisMachoSoVenda = qtAnimaisMachoVenda - qtAnimaisMachoDescarteVenda;
        qtAnimaisFemeaSoVenda = qtAnimaisFemeaVenda - qtAnimalFemeaDescarteVenda;

        qtAnimaisAprovados = qtAnimaisAvaliados - qtAnimaisVenda - qtAnimaisDescarte;
        qtAnimaisMachoAprovado = qtAnimaisMachoAvaliado - qtAnimaisMachoVenda - qtAnimaisMachoDescarte;
        qtAnimaisFemeaAprovado = qtAnimaisFemeaAvaliado - qtAnimaisFemeaVenda - qtAnimaisFemeaDescarte;

        String[] headers = {"", "Total", "Avaliado","%", "Aprov.", "%","Desc","%","Venda","%"};

        SimpleTableHeaderAdapter h1 = new SimpleTableHeaderAdapter(this, headers);
        h1.setTextSize(12);
        h1.setPaddingTop(10);
        h1.setPaddingBottom(10);
        tableView.setHeaderAdapter(h1);

        Long percAnimalAprovado = Math.round(Double.valueOf(qtAnimaisAprovados)/Double.valueOf(qtAnimaisAvaliados)*100);
        Long percAnimalMachoAprovado = Math.round(Double.valueOf(qtAnimaisMachoAprovado)/Double.valueOf(qtAnimaisMachoAvaliado)*100);
        Long percAnimalFemeaAprovado = Math.round(Double.valueOf(qtAnimaisFemeaAprovado)/Double.valueOf(qtAnimaisFemeaAvaliado)*100);

        Long percAnimalAvaliado = Math.round(Double.valueOf(qtAnimaisAvaliados)/Double.valueOf(qtAnimais)*100);
        Long percAnimalMachoAvaliado = Math.round(Double.valueOf(qtAnimaisMachoAvaliado)/Double.valueOf(qtAnimaisMacho)*100);
        Long percAnimalFemeaAvaliado = Math.round(Double.valueOf(qtAnimaisFemeaAvaliado)/Double.valueOf(qtAnimaisFemea)*100);

        Long percAnimalDescarte = Math.round(Double.valueOf(qtAnimaisDescarte)/Double.valueOf(qtAnimaisAvaliados)*100);
        Long percAnimalMachoDescarte = Math.round(Double.valueOf(qtAnimaisMachoDescarte)/Double.valueOf(qtAnimaisMachoAvaliado)*100);
        Long percAnimalFemeaDescarte = Math.round(Double.valueOf(qtAnimaisFemeaDescarte)/Double.valueOf(qtAnimaisFemeaAvaliado)*100);

        Long percAnimalVenda = 100 - percAnimalDescarte - percAnimalAprovado;
        Long percAnimalMachoVenda = 100 - percAnimalMachoDescarte - percAnimalMachoAprovado;
        Long percAnimalFemeaVenda = 100 - percAnimalFemeaDescarte - percAnimalFemeaAprovado;

        String[][] resumo = {
                {
                        "Machos",
                        String.valueOf(qtAnimaisMacho),
                        String.valueOf(qtAnimaisMachoAvaliado),
                        String.valueOf(percAnimalMachoAvaliado) + "%",
                        String.valueOf(qtAnimaisMachoAprovado),
                        String.valueOf(percAnimalMachoAprovado)+ "%",
                        String.valueOf(qtAnimaisMachoDescarte),
                        String.valueOf(percAnimalMachoDescarte) + "%",
                        String.valueOf(qtAnimaisMachoVenda),
                        String.valueOf(percAnimalMachoVenda) + "%"
                },
                {
                        "Fêmeas",
                        String.valueOf(qtAnimaisFemea),
                        String.valueOf(qtAnimaisFemeaAvaliado),
                        String.valueOf(percAnimalFemeaAvaliado) + "%",
                        String.valueOf(qtAnimaisFemeaAprovado),
                        String.valueOf(percAnimalFemeaAprovado)+ "%",
                        String.valueOf(qtAnimaisFemeaDescarte),
                        String.valueOf(percAnimalFemeaDescarte)+ "%",
                        String.valueOf(qtAnimaisFemeaVenda),
                        String.valueOf(percAnimalFemeaVenda) + "%"

                },
                {
                        "Total",
                        String.valueOf(qtAnimais),
                        String.valueOf(qtAnimaisAvaliados),
                        String.valueOf(percAnimalAvaliado) + "%",
                        String.valueOf(qtAnimaisAprovados),
                        String.valueOf(percAnimalAprovado)+ "%",
                        String.valueOf(qtAnimaisDescarte),
                        String.valueOf(percAnimalDescarte)+ "%",
                        String.valueOf(qtAnimaisVenda),
                        String.valueOf(percAnimalVenda) + "%"
                }
        };

        final TableColumnWeightModel tableColumnModel;
        tableColumnModel = new TableColumnWeightModel(10);

        tableColumnModel.setColumnWeight(0,3);
        tableColumnModel.setColumnWeight(1,2);
        tableColumnModel.setColumnWeight(2,2);
        tableColumnModel.setColumnWeight(3,2);
        tableColumnModel.setColumnWeight(4,2);
        tableColumnModel.setColumnWeight(5,2);
        tableColumnModel.setColumnWeight(6,2);
        tableColumnModel.setColumnWeight(7,2);
        tableColumnModel.setColumnWeight(8,2);
        tableColumnModel.setColumnWeight(9,2);



        tableView.setColumnModel(tableColumnModel);

        SimpleTableDataAdapter da1 = new SimpleTableDataAdapter(this, resumo);
        da1.setTextSize(14);
        tableView.setDataAdapter(da1);


        String[] headers2 = {"", "Venda", "Avaliado","Não Avaliado"};

        SimpleTableHeaderAdapter h2 = new SimpleTableHeaderAdapter(this, headers2);
        h2.setTextSize(14);
        h2.setPaddingTop(10);
        h2.setPaddingBottom(10);
        tableView2.setHeaderAdapter(h2);

        String[][] resumo2 = {
                {
                        "Machos",
                        String.valueOf(qtAnimaisMachoVenda),
                        String.valueOf(qtAnimaisMachoVenda-qtAnimaisMachoSoVenda),
                        String.valueOf(qtAnimaisMachoSoVenda)
                },
                {
                        "Fêmeas",
                        String.valueOf(qtAnimaisFemeaVenda),
                        String.valueOf(qtAnimaisFemeaVenda-qtAnimaisFemeaSoVenda),
                        String.valueOf(qtAnimaisFemeaSoVenda)

                },
                {
                        "Total",
                        String.valueOf(qtAnimaisVenda),
                        String.valueOf(qtAnimaisVenda-qtAnimaisSoVenda),
                        String.valueOf(qtAnimaisSoVenda)
                }
        };

        SimpleTableDataAdapter da2 = new SimpleTableDataAdapter(this, resumo2);
        da2.setTextSize(14);
        tableView2.setDataAdapter(da2);


        //Media das medidas Macho

        String qryMediaM;
        if (idCriador>0){
            qryMediaM = "SELECT m.medicao_id, me.abrev, a.sexo, COUNT(*) as qt, ROUND(AVG(m.valor)) as media \n" +
                    "FROM medicoes_animal m\n" +
                    "LEFT JOIN mtf_dados a ON a._id = m.animal \n" +
                    "LEFT JOIN medicao me ON me._id = m.medicao_id\n" +
                    "WHERE a.sexo = 'M' AND m.valor IS NOT null AND m.valor <> 9 AND m.medicao_id not in (131129,131811,141529,142528,222530,321524) \n" +
                    " AND a.criador_id = " + String.valueOf(idCriador) + " \n" +
                    " GROUP BY m.medicao_id, me.abrev, a.sexo \n" +
                    "ORDER BY me.ordem, m.medicao_id";
        } else {
            qryMediaM = "SELECT m.medicao_id, me.abrev, a.sexo, COUNT(*) as qt, ROUND(AVG(m.valor)) as media \n" +
                    "FROM medicoes_animal m\n" +
                    "LEFT JOIN mtf_dados a ON a._id = m.animal \n" +
                    "LEFT JOIN medicao me ON me._id = m.medicao_id\n" +
                    "WHERE a.sexo = 'M' AND m.valor IS NOT null AND m.valor <> 9 AND m.medicao_id not in (131129,131811,141529,142528,222530,321524) GROUP BY m.medicao_id, me.abrev, a.sexo \n" +
                    "ORDER BY me.ordem, m.medicao_id";
        }


        Cursor curMediaM = app.getDb().rawQuery(qryMediaM,null);

        while (curMediaM.moveToNext()){
            String abrev = curMediaM.getString(1);
            String media = String.valueOf(curMediaM.getLong(4));

            switch (abrev){
                case "REP":
                    mRepM = media;
                    break;
                case "UBE":
                    mUbeM = media;
                    break;
                case "MUS":
                    mMuscM = media;
                    break;
                case "FRA":
                    mFraM = media;
                    break;
                case "APR":
                    mAprM = media;
                    break;
                case "OSS":
                    mOssM = media;
                    break;
                case "PRO":
                    mProM = media;
                    break;
                case "GAR":
                    mGarM = media;
                    break;
                case "UMB":
                    mUmbM = media;
                    break;
                case "BOC":
                    mBocM = media;
                    break;
                case "CAU":
                    mCauM = media;
                    break;
                case "PLA":
                    mPlaM = media;
                    break;
                case "TEM":
                    mTemM = media;
                    break;
                case "TTE":
                    mTteM = media;
                    break;
                case "RAC":
                    mRacM = media;
                    break;

            }

        }


        //Media das medidas Femea
        String qryMediaF;
        if (idCriador>0){
            qryMediaF = "SELECT m.medicao_id, me.abrev, a.sexo, COUNT(*) as qt, ROUND(AVG(m.valor)) as media \n" +
                    "FROM medicoes_animal m\n" +
                    "LEFT JOIN mtf_dados a ON a._id = m.animal \n" +
                    "LEFT JOIN medicao me ON me._id = m.medicao_id\n" +
                    "WHERE a.sexo = 'F' AND m.valor IS NOT null AND m.valor <> 9 AND m.medicao_id not in (131129,131811,141529,142528,222530,321524) \n" +
                        " AND a.criador_id = " + String.valueOf(idCriador) + "\n" +
                    "GROUP BY m.medicao_id, me.abrev, a.sexo \n" +

            "ORDER BY me.ordem, m.medicao_id";
        } else {
            qryMediaF = "SELECT m.medicao_id, me.abrev, a.sexo, COUNT(*) as qt, ROUND(AVG(m.valor)) as media \n" +
                    "FROM medicoes_animal m\n" +
                    "LEFT JOIN mtf_dados a ON a._id = m.animal \n" +
                    "LEFT JOIN medicao me ON me._id = m.medicao_id\n" +
                    "WHERE a.sexo = 'F' AND m.valor IS NOT null AND m.valor <> 9 AND m.medicao_id not in (131129,131811,141529,142528,222530,321524) GROUP BY m.medicao_id, me.abrev, a.sexo \n" +
                    "ORDER BY me.ordem, m.medicao_id";
        }


        Cursor curMediaF = app.getDb().rawQuery(qryMediaF,null);

        while (curMediaF.moveToNext()){
            String abrev = curMediaF.getString(1);
            String media = String.valueOf(curMediaF.getLong(4));

            switch (abrev){
                case "REP":
                    mRepF = media;
                    break;
                case "UBE":
                    mUbeF = media;
                    break;
                case "MUS":
                    mMuscF = media;
                    break;
                case "FRA":
                    mFraF = media;
                    break;
                case "APR":
                    mAprF = media;
                    break;
                case "OSS":
                    mOssF = media;
                    break;
                case "PRO":
                    mProF = media;
                    break;
                case "GAR":
                    mGarF = media;
                    break;
                case "UMB":
                    mUmbF = media;
                    break;
                case "BOC":
                    mBocF = media;
                    break;
                case "CAU":
                    mCauF = media;
                    break;
                case "PLA":
                    mPlaF = media;
                    break;
                case "TEM":
                    mTemF = media;
                    break;
                case "TTE":
                    mTteF = media;
                    break;
                case "RAC":
                    mRacF = media;
                    break;
            }
        }

        //Tabela 3
        String[] headers3 = {"Media", "Rep", "Ube","Mus","Fra","Apr","Oss","Pro","Gar"};

        SimpleTableHeaderAdapter h3 = new SimpleTableHeaderAdapter(this, headers3);
        h3.setTextSize(14);
        h3.setPaddingTop(10);
        h3.setPaddingBottom(10);
        tableView3.setHeaderAdapter(h3);

        String[][] resumo3 = {
                {
                        "Machos",
                        mRepM,
                        "-",
                        mMuscM,
                        mFraM,
                        mAprM,
                        mOssM,
                        mProM,
                        mGarM
                },
                {
                        "Fêmeas",
                        mRepF,
                        mUbeF,
                        mMuscF,
                        mFraF,
                        mAprF,
                        mOssF,
                        mProF,
                        mGarF
                }
        };


        tableView.setColumnModel(tableColumnModel);

        SimpleTableDataAdapter da3 = new SimpleTableDataAdapter(this, resumo3);
        da3.setTextSize(14);
        tableView3.setDataAdapter(da3);

        //Tabela 6
        String[] headers6 =  {"Media", "Umb", "Boc","Cau","Pla","Tem","Tte","Rac"};

        SimpleTableHeaderAdapter h6 = new SimpleTableHeaderAdapter(this, headers6);
        h6.setTextSize(14);
        h6.setPaddingTop(10);
        h6.setPaddingBottom(10);
        tableView6.setHeaderAdapter(h6);

        String[][] resumo6 =  {
                {
                        "Machos",
                        mUmbM,
                        mBocM,
                        mCauM,
                        mPlaM,
                        mTemM,
                        mTteM,
                        mRacM
                },
                {
                        "Fêmeas",
                        mUmbF,
                        mBocF,
                        mCauF,
                        mPlaF,
                        mTemF,
                        "-",
                        mRacF
                }
        };


        tableView6.setColumnModel(tableColumnModel);

        SimpleTableDataAdapter da6 = new SimpleTableDataAdapter(this, resumo6);
        da6.setTextSize(14);
        tableView6.setDataAdapter(da6);

        //Medidas Animais
        qtNascM = 0;
        qtNascF = 0;
        qtDesmM = 0;
        qtDesmF = 0;
        qt365M = 0;
        qt365M = 0;
        qt450M = 0;
        qt450F = 0;
        qt550M = 0;
        qt550F = 0;
        qtCeM = 0;
        qtCeF = 0;

        mNasc = 0.00;
        mNascM = 0.00;
        mNascF = 0.00;

        mDesm = 0.00;
        mDesmM = 0.00;
        mDesmF = 0.00;

        m365 = 0.00;
        m365M = 0.00;
        m365F = 0.00;

        m450 = 0.00;
        m450M = 0.00;
        m450F = 0.00;

        m550 = 0.00;
        m550M = 0.00;
        m550F = 0.00;

        mCe = 0.00;
        mCeM = 0.00;
        mCeF = 0.00;

        vNascM = 0.00;
        vDesmM = 0.00;
        v365M = 0.00;
        v450M = 0.00;
        v550M = 0.00;
        vCeM = 0.00;

        vNascF = 0.00;
        vDesmF = 0.00;
        v365F = 0.00;
        v450F = 0.00;
        v550F = 0.00;
        vCeF = 0.00;


        if (idCriador>0)
            animais = app.getDaoSession().getMTFDadosDao().queryBuilder()
                    .where(MTFDadosDao.Properties.CriadorId.eq(idCriador)).list();
        else
            animais = app.getDaoSession().getMTFDadosDao().queryBuilder().list();


        for (int i = 0; i<animais.size();i++){
            animal = animais.get(i);

            if (animal.getSexo().equals("M"))
            {
                if (animal.getRPNasc()>0){
                    qtNascM++;
                    vNascM = vNascM + animal.getRPNasc();
                }


                if (animal.getRPDesm()>0){
                    qtDesmM++;
                    vDesmM = vDesmM + animal.getRPDesm();
                }


                if (animal.getRP365()>0){
                    qt365M++;
                    v365M = v365M + animal.getRP365();
                }


                if (animal.getRP450()>0){
                    qt450M++;
                    v450M = v450M + animal.getRP450();
                }


                if (animal.getRP550()>0){
                    qt550M++;
                    v550M = v550M + animal.getRP550();
                }


                if (animal.getRCe()>0){
                    qtCeM++;
                    vCeM = vCeM + animal.getRCe();
                }

            } else {
                if (animal.getRPNasc()>0){
                    qtNascF++;
                    vNascF = vNascF + animal.getRPNasc();
                }


                if (animal.getRPDesm()>0){
                    qtDesmF++;
                    vDesmF = vDesmF + animal.getRPDesm();
                }


                if (animal.getRP365()>0){
                    qt365F++;
                    v365F = v365F + animal.getRP365();
                }


                if (animal.getRP450()>0){
                    qt450F++;
                    v450F = v450F + animal.getRP450();
                }


                if (animal.getRP550()>0){
                    qt550F++;
                    v550F = v550F + animal.getRP550();
                }


                if (animal.getRCe()>0){
                    qtCeF++;
                    vCeF = vCeF + animal.getRCe();
                }
            }

        }

        mNascM = vNascM/qtNascM;
        mDesmM = vDesmM/qtDesmM;
        m365M = v365M/qt365M;
        m450M = v450M/qt450M;
        m550M = v550M/qt550M;
        mCeM = vCeM/qtCeM;

        mNascF = vNascF/qtNascF;
        mDesmF = vDesmF/qtDesmF;
        m365F = v365F/qt365F;
        m450F = v450F/qt450F;
        m550F = v550F/qt550F;
        mCeF = vCeF/qtCeF;


        //Tabela 4
        String[] headers4 = {"Animais", "Nasc", "Desm","365 d", "450 d", "550 d","CE"};

        SimpleTableHeaderAdapter h4 = new SimpleTableHeaderAdapter(this, headers4);
        h4.setTextSize(12);
        h4.setPaddingTop(10);
        h4.setPaddingBottom(10);
        tableView4.setHeaderAdapter(h4);
        String[][] resumo4 = {
                {
                        "Machos",
                        String.valueOf(qtNascM),
                        String.valueOf(qtDesmM),
                        String.valueOf(qt365M) ,
                        String.valueOf(qt450M),
                        String.valueOf(qt550M),
                        String.valueOf(qtCeM)
                },
                {
                        "Fêmeas",
                        String.valueOf(qtNascF),
                        String.valueOf(qtDesmF),
                        String.valueOf(qt365F) ,
                        String.valueOf(qt450F),
                        String.valueOf(qt550F),
                        String.valueOf(qtCeF)

                }
        };

        /*
        final TableColumnWeightModel tableColumnModel;
        tableColumnModel = new TableColumnWeightModel(10);

        tableColumnModel.setColumnWeight(0,3);
        tableColumnModel.setColumnWeight(1,2);
        tableColumnModel.setColumnWeight(2,2);
        tableColumnModel.setColumnWeight(3,2);
        tableColumnModel.setColumnWeight(4,2);
        tableColumnModel.setColumnWeight(5,2);
        tableColumnModel.setColumnWeight(6,2);
        tableColumnModel.setColumnWeight(7,2);
        tableColumnModel.setColumnWeight(8,2);
        tableColumnModel.setColumnWeight(9,2);
        tableView.setColumnModel(tableColumnModel);
        */

        SimpleTableDataAdapter da4 = new SimpleTableDataAdapter(this, resumo4);
        da4.setTextSize(14);
        tableView4.setDataAdapter(da4);



        //Tabela 5
        String[] headers5 = {"Animais", "Nasc", "Desm","365 d", "450 d", "550 d","CE"};

        SimpleTableHeaderAdapter h5 = new SimpleTableHeaderAdapter(this, headers4);
        h5.setTextSize(12);
        h5.setPaddingTop(10);
        h5.setPaddingBottom(10);
        tableView5.setHeaderAdapter(h5);
        String[][] resumo5 = {
                {
                        "Machos",
                        String.valueOf(converterDoubleString(mNascM)),
                        String.valueOf(converterDoubleString(mDesmM)),
                        String.valueOf(converterDoubleString(m365M)) ,
                        String.valueOf(converterDoubleString(m450M)),
                        String.valueOf(converterDoubleString(m550M)),
                        String.valueOf(converterDoubleString(mCeM))
                },
                {
                        "Fêmeas",
                        String.valueOf(converterDoubleString(mNascF)),
                        String.valueOf(converterDoubleString(mDesmF)),
                        String.valueOf(converterDoubleString(m365F)) ,
                        String.valueOf(converterDoubleString(m450F)),
                        String.valueOf(converterDoubleString(m550F)),
                        String.valueOf("-")

                }
        };

        /*
        final TableColumnWeightModel tableColumnModel;
        tableColumnModel = new TableColumnWeightModel(10);

        tableColumnModel.setColumnWeight(0,3);
        tableColumnModel.setColumnWeight(1,2);
        tableColumnModel.setColumnWeight(2,2);
        tableColumnModel.setColumnWeight(3,2);
        tableColumnModel.setColumnWeight(4,2);
        tableColumnModel.setColumnWeight(5,2);
        tableColumnModel.setColumnWeight(6,2);
        tableColumnModel.setColumnWeight(7,2);
        tableColumnModel.setColumnWeight(8,2);
        tableColumnModel.setColumnWeight(9,2);
        tableView.setColumnModel(tableColumnModel);
        */

        SimpleTableDataAdapter da5 = new SimpleTableDataAdapter(this, resumo5);
        da5.setTextSize(14);
        tableView5.setDataAdapter(da5);

    }


    public static String converterDoubleString(double value) {
        if (Double.isNaN(value))
            return "0.0";
        /*Transformando um double em 2 casas decimais*/
        DecimalFormat fmt = new DecimalFormat("0.0");   //limita o número de casas decimais
        String string = fmt.format(value);
        return string;
        /*String[] part = string.split("[,]");
        String sValor = part[0]+"."+part[1];
        return sValor;*/
    }
}
