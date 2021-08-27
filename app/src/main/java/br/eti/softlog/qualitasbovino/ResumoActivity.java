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
import br.eti.softlog.model.Resumo;
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

    Resumo r;


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



        r = new Resumo(getApplicationContext(),idCriador);


        String[] headers = {"", "Total", "Avaliado","%", "Aprov.", "%","Desc","%","Outros","%"};

        SimpleTableHeaderAdapter h1 = new SimpleTableHeaderAdapter(this, headers);
        h1.setTextSize(12);
        h1.setPaddingTop(10);
        h1.setPaddingBottom(10);
        tableView.setHeaderAdapter(h1);

        Long percAnimalAprovado = Math.round(Double.valueOf(r.getQtAnimaisAprovados())/Double.valueOf(r.getQtAnimaisAvaliados())*100);
        Long percAnimalMachoAprovado = Math.round(Double.valueOf(r.getQtAnimaisMachoAprovado())/Double.valueOf(r.getQtAnimaisMachoAvaliado())*100);
        Long percAnimalFemeaAprovado = Math.round(Double.valueOf(r.getQtAnimaisFemeaAprovado())/Double.valueOf(r.getQtAnimaisFemeaAvaliado())*100);

        Long percAnimalAvaliado = Math.round(Double.valueOf(r.getQtAnimaisAvaliados())/Double.valueOf(r.getQtAnimais())*100);
        Long percAnimalMachoAvaliado = Math.round(Double.valueOf(r.getQtAnimaisMachoAvaliado())/Double.valueOf(r.getQtAnimaisMacho())*100);
        Long percAnimalFemeaAvaliado = Math.round(Double.valueOf(r.getQtAnimaisFemeaAvaliado())/Double.valueOf(r.getQtAnimaisFemea())*100);

        Long percAnimalDescarte = Math.round(Double.valueOf(r.getQtAnimaisDescarte())/Double.valueOf(r.getQtAnimaisAvaliados())*100);
        Long percAnimalMachoDescarte = Math.round(Double.valueOf(r.getQtAnimaisMachoDescarte())/Double.valueOf(r.getQtAnimaisMachoAvaliado())*100);
        Long percAnimalFemeaDescarte = Math.round(Double.valueOf(r.getQtAnimaisFemeaDescarte())/Double.valueOf(r.getQtAnimaisFemeaAvaliado())*100);

        Long percAnimalVenda = Math.round(Double.valueOf(r.getQtAnimaisOutros())/Double.valueOf(r.getQtAnimaisAvaliados())*100);
        Long percAnimalMachoVenda = Math.round(Double.valueOf(r.getQtAnimaisMachoOutros())/Double.valueOf(r.getQtAnimaisMachoAvaliado())*100);
        Long percAnimalFemeaVenda = Math.round(Double.valueOf(r.getQtAnimaisFemeaOutros())/Double.valueOf(r.getQtAnimaisFemeaAvaliado())*100);

        String[][] resumo = {
                {
                        "Machos",
                        String.valueOf(r.getQtAnimaisMacho()),
                        String.valueOf(r.getQtAnimaisMachoAvaliado()),
                        String.valueOf(percAnimalMachoAvaliado) + "%",
                        String.valueOf(r.getQtAnimaisMachoAprovado()),
                        String.valueOf(percAnimalMachoAprovado)+ "%",
                        String.valueOf(r.getQtAnimaisMachoDescarte()),
                        String.valueOf(percAnimalMachoDescarte) + "%",
                        String.valueOf(r.getQtAnimaisMachoOutros()),
                        String.valueOf(percAnimalMachoVenda) + "%"
                },
                {
                        "Fêmeas",
                        String.valueOf(r.getQtAnimaisFemea()),
                        String.valueOf(r.getQtAnimaisFemeaAvaliado()),
                        String.valueOf(percAnimalFemeaAvaliado) + "%",
                        String.valueOf(r.getQtAnimaisFemeaAprovado()),
                        String.valueOf(percAnimalFemeaAprovado)+ "%",
                        String.valueOf(r.getQtAnimaisFemeaDescarte()),
                        String.valueOf(percAnimalFemeaDescarte)+ "%",
                        String.valueOf(r.getQtAnimaisFemeaOutros()),
                        String.valueOf(percAnimalFemeaVenda) + "%"

                },
                {
                        "Total",
                        String.valueOf(r.getQtAnimais()),
                        String.valueOf(r.getQtAnimaisAvaliados()),
                        String.valueOf(percAnimalAvaliado) + "%",
                        String.valueOf(r.getQtAnimaisAprovados()),
                        String.valueOf(percAnimalAprovado)+ "%",
                        String.valueOf(r.getQtAnimaisDescarte()),
                        String.valueOf(percAnimalDescarte)+ "%",
                        String.valueOf(r.getQtAnimaisOutros()),
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


        String[] headers2 = {"", "Avaliado","Não Avaliado","Venda", "Comercial"};

        SimpleTableHeaderAdapter h2 = new SimpleTableHeaderAdapter(this, headers2);
        h2.setTextSize(14);
        h2.setPaddingTop(10);
        h2.setPaddingBottom(10);
        tableView2.setHeaderAdapter(h2);

        String[][] resumo2 = {
                {
                        "Machos",
                        String.valueOf(r.getQtAnimaisMachoAvaliado()),
                        String.valueOf(r.getQtAnimaisMacho()-r.getQtAnimaisMachoAvaliado()),
                        String.valueOf(r.getQtAnimaisMachoVenda()),
                        String.valueOf(r.getQtAnimaisMachoComercial())
                },
                {
                        "Fêmeas",
                        String.valueOf(r.getQtAnimaisFemeaAvaliado()),
                        String.valueOf(r.getQtAnimaisFemea()-r.getQtAnimaisFemeaAvaliado()),
                        String.valueOf(r.getQtAnimaisFemeaVenda()),
                        String.valueOf(r.getQtAnimaisFemeaComercial())

                },
                {
                        "Total",
                        String.valueOf(r.getQtAnimaisAvaliados()),
                        String.valueOf(r.getQtAnimais()-r.getQtAnimaisAvaliados()),
                        String.valueOf(r.getQtAnimaisVenda()),
                        String.valueOf(r.getQtAnimaisComercial())

                }
        };

        SimpleTableDataAdapter da2 = new SimpleTableDataAdapter(this, resumo2);
        da2.setTextSize(14);
        tableView2.setDataAdapter(da2);

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
                        r.getmRepM(),
                        "-",
                        r.getmMuscM(),
                        r.getmFraM(),
                        r.getmAprM(),
                        r.getmOssM(),
                        r.getmProM(),
                        r.getmGarM()
                },
                {
                        "Fêmeas",
                        r.getmRepF(),
                        r.getmUbeF(),
                        r.getmMuscF(),
                        r.getmFraF(),
                        r.getmAprF(),
                        r.getmOssF(),
                        r.getmProF(),
                        r.getmGarF()
                }
        };


        tableView.setColumnModel(tableColumnModel);

        SimpleTableDataAdapter da3 = new SimpleTableDataAdapter(this, resumo3);
        da3.setTextSize(14);
        tableView3.setDataAdapter(da3);

        //Tabela 6
        String[] headers6 =  {"Media", "Umb", "Boc","Cau","Pla","Tem","Tte","Rac",""};

        SimpleTableHeaderAdapter h6 = new SimpleTableHeaderAdapter(this, headers6);
        h6.setTextSize(14);
        h6.setPaddingTop(10);
        h6.setPaddingBottom(10);
        tableView6.setHeaderAdapter(h6);

        String[][] resumo6 =  {
                {
                        "Machos",
                        r.getmUmbM(),
                        r.getmBocM(),
                        r.getmCauM(),
                        r.getmPlaM(),
                        r.getmTemM(),
                        r.getmTteM(),
                        r.getmRacM(),
                        ""
                },
                {
                        "Fêmeas",
                        r.getmUmbF(),
                        r.getmBocF(),
                        r.getmCauF(),
                        r.getmPlaF(),
                        r.getmTemF(),
                        "-",
                        r.getmRacF(),
                        ""
                }
        };


        tableView6.setColumnModel(tableColumnModel);

        SimpleTableDataAdapter da6 = new SimpleTableDataAdapter(this, resumo6);
        da6.setTextSize(14);
        tableView6.setDataAdapter(da6);

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
                        String.valueOf(r.getQtNascM()),
                        String.valueOf(r.getQtDesmM()),
                        String.valueOf(r.getQt365M()) ,
                        String.valueOf(r.getQt450M()),
                        String.valueOf(r.getQt550M()),
                        String.valueOf(r.getQtCeM())
                },
                {
                        "Fêmeas",
                        String.valueOf(r.getQtNascF()),
                        String.valueOf(r.getQtDesmF()),
                        String.valueOf(r.getQt365F()) ,
                        String.valueOf(r.getQt450F()),
                        String.valueOf(r.getQt550F()),
                        String.valueOf(r.getQtCeF())

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
                        String.valueOf(converterDoubleString(r.getmNascM())),
                        String.valueOf(converterDoubleString(r.getmDesmM())),
                        String.valueOf(converterDoubleString(r.getM365M())) ,
                        String.valueOf(converterDoubleString(r.getM450M())),
                        String.valueOf(converterDoubleString(r.getM550M())),
                        String.valueOf(converterDoubleString(r.getmCeM()))
                },
                {
                        "Fêmeas",
                        String.valueOf(converterDoubleString(r.getmNascF())),
                        String.valueOf(converterDoubleString(r.getmDesmF())),
                        String.valueOf(converterDoubleString(r.getM365F())) ,
                        String.valueOf(converterDoubleString(r.getM450F())),
                        String.valueOf(converterDoubleString(r.getM550F())),
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
