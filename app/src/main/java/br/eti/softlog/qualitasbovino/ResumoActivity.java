package br.eti.softlog.qualitasbovino;

import android.content.Intent;
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


    //Configuracao Graficos
    ArrayList<Integer> colors = new ArrayList<>();
    ArrayList<Integer> colors2 = new ArrayList<>();
    ArrayList<Integer> colors3 = new ArrayList<>();

    @BindView(R.id.chartTotais)
    PieChart chartTotais;

    @BindView(R.id.chartMachos)
    PieChart chartMachos;

    @BindView(R.id.chartFemeas)
    PieChart chartFemeas;

    @BindView(R.id.chartDescarte)
    BarChart chartDescarte;

    private String[][] resumo;

    @BindView(R.id.tableView)
    TableView tableView;


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



        //Renderizacao dos Graficos
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors2.add(c);

        colors2.add(ColorTemplate.getHoloBlue());

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors3.add(c);

        colors3.add(ColorTemplate.getHoloBlue());

        getResumo(Prefs.getLong("filtro_fazenda",0));
        generateChart();


    }

    private void generateChart() {

        Description description = new Description();
        description.setText("");

        //Grafico Pizza Total Avaliado
        ArrayList<PieEntry> entriesTotais = new ArrayList<>();

        entriesTotais.add(new PieEntry(qtAnimais - qtAnimaisAvaliados, "A avaliar"));
        entriesTotais.add(new PieEntry(qtAnimaisAvaliados, "Avaliados"));

        PieDataSet dsTotais = new PieDataSet(entriesTotais, "");

        dsTotais.setDrawIcons(false);

        dsTotais.setSliceSpace(3f);
        dsTotais.setIconsOffset(new MPPointF(0, 40));
        dsTotais.setSelectionShift(5f);

        dsTotais.setColors(colors2);
        PieData dataTotais = new PieData(dsTotais);
        chartTotais.setData(dataTotais);
        chartTotais.setCenterText("TOTAL");
        //chartTotais.setUsePercentValues(true);
        chartTotais.setDescription(description);


        chartTotais.highlightValues(null);
        chartTotais.invalidate();
        chartTotais.getLegend().setTextSize(18);

        //Grafico Pizza Machos Avaliados
        ArrayList<PieEntry> entriesTotaisM = new ArrayList<>();

        entriesTotaisM.add(new PieEntry(qtAnimaisMacho - qtAnimaisMachoAvaliado, "A avaliar"));
        entriesTotaisM.add(new PieEntry(qtAnimaisMachoAvaliado, "Avaliados"));

        PieDataSet dsTotaisM = new PieDataSet(entriesTotaisM, "");

        dsTotaisM.setDrawIcons(false);

        dsTotaisM.setSliceSpace(3f);
        dsTotaisM.setIconsOffset(new MPPointF(0, 40));
        dsTotaisM.setSelectionShift(5f);

        dsTotaisM.setColors(colors);
        PieData dataTotaisM = new PieData(dsTotaisM);
        chartMachos.setData(dataTotaisM);
        chartMachos.setCenterText("MACHO");
        //chartMachos.setUsePercentValues(true);
        chartMachos.setDescription(description);
        chartMachos.getLegend().setTextSize(18);

        chartMachos.highlightValues(null);
        chartMachos.invalidate();

        //Grafico Pizza Femeas Avaliadas
        ArrayList<PieEntry> entriesTotaisF = new ArrayList<>();

        entriesTotaisF.add(new PieEntry(qtAnimaisFemea - qtAnimaisFemeaAvaliado, "A avaliar"));
        entriesTotaisF.add(new PieEntry(qtAnimaisFemeaAvaliado, "Avaliados"));

        PieDataSet dsTotaisF = new PieDataSet(entriesTotaisF, "");

        dsTotaisF.setDrawIcons(false);

        dsTotaisF.setSliceSpace(3f);
        dsTotaisF.setIconsOffset(new MPPointF(0, 40));
        dsTotaisF.setSelectionShift(5f);

        dsTotaisF.setColors(colors3);
        PieData dataTotaisF = new PieData(dsTotaisF);
        chartFemeas.setData(dataTotaisF);
        chartFemeas.setCenterText("FÊMEA");
        //chartTotais.setUsePercentValues(true);
        chartFemeas.setDescription(description);
        chartFemeas.getLegend().setTextSize(18);
        //chartFemeas.setContentDescription("");
        chartFemeas.highlightValues(null);
        chartFemeas.invalidate();


        //Chart Barras Descarte

        chartDescarte.getDescription().setEnabled(false);
        chartDescarte.setMaxVisibleValueCount(10);

        // scaling can now only be done on x- and y-axis separately
        chartDescarte.setPinchZoom(false);

        chartDescarte.setDrawGridBackground(false);
        chartDescarte.setDrawBarShadow(false);

        chartDescarte.setDrawValueAboveBar(false);
        chartDescarte.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = chartDescarte.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        chartDescarte.getAxisRight().setEnabled(false);

        XAxis xLabels = chartDescarte.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xLabels.setValueFormatter(new MyAxisAnimalValueFormatter());

        Legend l = chartDescarte.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        ArrayList<BarEntry> values = new ArrayList<>();

        Float val1;
        Float val2;

        val1 = Float.valueOf(qtAnimaisMachoAvaliado - qtAnimaisMachoDescarte);
        val2 = Float.valueOf(qtAnimaisMachoDescarte);

        values.add(new BarEntry(0, new float[] {val1,val2}));

        val1 = Float.valueOf(qtAnimaisFemeaAvaliado - qtAnimaisFemeaDescarte);
        val2 = Float.valueOf(qtAnimaisFemeaDescarte);

        values.add(new BarEntry(1, new float[] {val1,val2}));

        BarDataSet dsDescarte;
        dsDescarte = new BarDataSet(values, "");
        dsDescarte.setDrawIcons(false);
        dsDescarte.setColors(getColors());
        dsDescarte.setStackLabels(new String[]{"Não descartados", "Descartados"});

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dsDescarte);

        BarData data = new BarData(dataSets);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextColor(Color.WHITE);

        dsDescarte.setValues(values);

        chartDescarte.setData(data);
        chartDescarte.getLegend().setTextSize(18);
        chartDescarte.setFitBars(true);
        chartDescarte.invalidate();


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
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(in);
        finish();
        return true;
    }


    private int[] getColors() {

        // have as many colors as stack-values per entry
        int[] colors = new int[2];

        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, 2);

        return colors;
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

        qtAnimaisMacho = 0;
        qtAnimaisFemea = 0;
        qtAnimaisAvaliados = 0;
        qtAnimaisMachoAvaliado = 0;
        qtAnimaisFemeaAvaliado = 0;
        qtAnimaisDescarte = 0;
        qtAnimaisMachoDescarte = 0;
        qtAnimaisFemeaDescarte = 0;

        for (int i = 0; i < animais.size(); i++) {
            animal = animais.get(i);

            if (animal.getSexo().equals("M")) {
                qtAnimaisMacho++;
                if (animal.getAvaliado()) {
                    qtAnimaisAvaliados++;
                    qtAnimaisMachoAvaliado++;

                    if (animal.getMedicoesAnimals().get(0).getValor() != null){
                        qtAnimaisAprovados++;
                        qtAnimaisMachoAprovado++;
                    }
                }

            } else {
                qtAnimaisFemea++;
                if (animal.getAvaliado()) {
                    qtAnimaisAvaliados++;
                    qtAnimaisFemeaAvaliado++;

                    if (animal.getMedicoesAnimals().get(0).getValor() != null){
                        qtAnimaisAprovados++;
                        qtAnimaisFemeaAprovado++;
                    }

                }
            }
        }

        if (idCriador==0){
            medicoes = app.getDaoSession().getMedicoesAnimalDao()
                    .queryBuilder()
                    .where(MedicoesAnimalDao.Properties.MedicaoId.eq(141529))
                    .where(MedicoesAnimalDao.Properties.Valor.eq(1))
                    .list();
        } else {
            QueryBuilder qryMedicoes = app.getDaoSession().getMedicoesAnimalDao().queryBuilder();
            //qryMedicoes.LOG_SQL = true;
            //qryMedicoes.LOG_VALUES = true;
            Join joinMedicoes = qryMedicoes.join(MedicoesAnimalDao.Properties.Animal,MTFDados.class)
                    .where(MTFDadosDao.Properties.CriadorId.eq(idCriador));

            medicoes = qryMedicoes.where(MedicoesAnimalDao.Properties.MedicaoId.eq(141529))
                    .where(MedicoesAnimalDao.Properties.Valor.eq(1))
                    .list();

        }

        qtAnimaisDescarte = medicoes.size();

        for (int i = 0; i < medicoes.size(); i++) {
            if (medicoes.get(i).getAnimalPrincipal().getSexo().equals("M"))
                qtAnimaisMachoDescarte++;
            else
                qtAnimaisFemeaDescarte++;
        }

        String[] headers = {"", "Total", "Avaliados", "Aprov.", "%","Desclass","%"};
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, headers));

        Long percAnimalAprovado = Math.round(Double.valueOf(qtAnimaisAprovados)/Double.valueOf(qtAnimaisAvaliados)*100);
        Long percAnimalMachoAprovado = Math.round(Double.valueOf(qtAnimaisMachoAprovado)/Double.valueOf(qtAnimaisMachoAvaliado)*100);
        Long percAnimalFemeaAprovado = Math.round(Double.valueOf(qtAnimaisFemeaAprovado)/Double.valueOf(qtAnimaisFemeaAvaliado)*100);
        String[][] resumo = {
                {
                        "Machos",
                        String.valueOf(qtAnimaisMacho),
                        String.valueOf(qtAnimaisMachoAvaliado),
                        String.valueOf(qtAnimaisMachoAprovado),
                        String.valueOf(percAnimalMachoAprovado)+ "%",
                        String.valueOf(qtAnimaisMachoAvaliado - qtAnimaisMachoAprovado),
                        String.valueOf(100-percAnimalMachoAprovado) + "%"
                },
                {
                        "Fêmeas",
                        String.valueOf(qtAnimaisFemea),
                        String.valueOf(qtAnimaisFemeaAvaliado),
                        String.valueOf(qtAnimaisFemeaAprovado),
                        String.valueOf(percAnimalFemeaAprovado)+ "%",
                        String.valueOf(qtAnimaisFemeaAvaliado - qtAnimaisFemeaAprovado),
                        String.valueOf(100-percAnimalFemeaAprovado)+ "%"
                },
                {
                        "Total",
                        String.valueOf(qtAnimais),
                        String.valueOf(qtAnimaisAvaliados),
                        String.valueOf(qtAnimaisAprovados),
                        String.valueOf(percAnimalAprovado)+ "%",
                        String.valueOf(qtAnimaisAvaliados - qtAnimaisAprovados),
                        String.valueOf(100 - percAnimalAprovado)+ "%"
                }
        };
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, resumo));
        generateChart();

    }
}
