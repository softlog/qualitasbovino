package br.eti.softlog.qualitasbovino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.eti.softlog.model.Medicao;
import br.eti.softlog.model.MedicaoDao;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MedidasListActivity extends AppCompatActivity {


    @BindView(R.id.lista_medidas)
    ListView lista ;
    Medicao medicao;
    List<Medicao> medicoes;
    ArrayAdapter<Medicao> adapter;
    AppMain app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidas_list);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Lista de Medidas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (AppMain)getApplication();


        //lists.add(new String());

        medicoes = app.getDaoSession().getMedicaoDao().queryBuilder().orderAsc(
                MedicaoDao.Properties.Ordem
        ).list();

        adapter = new ArrayAdapter<Medicao>(this,android.R.layout.simple_list_item_1,medicoes);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(getApplicationContext(),MedidasActivity.class);
                in.putExtra("idMedida",medicoes.get(i).getId());
                startActivity(in);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent in = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(in);
        finish();
        return true;
    }
}
