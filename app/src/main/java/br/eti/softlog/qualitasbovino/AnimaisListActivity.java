package br.eti.softlog.qualitasbovino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MTFDadosDao;

public class AnimaisListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimalAdapter animalAdapter;
    private List<MTFDados> animais;
    private AppMain app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animais_list);

        app = (AppMain)getApplicationContext();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        );

        animais = app.getDaoSession().getMTFDadosDao().queryBuilder().
                                orderAsc(MTFDadosDao.Properties.Animal).list();


        animalAdapter = new AnimalAdapter(animais, getApplicationContext(),app);
        recyclerView.setAdapter(animalAdapter);


    }
}
