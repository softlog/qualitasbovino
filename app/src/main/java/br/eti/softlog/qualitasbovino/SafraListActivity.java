package br.eti.softlog.qualitasbovino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import br.eti.softlog.model.Safra;
import br.eti.softlog.model.SafraDao;

public class SafraListActivity extends AppCompatActivity {

    AppMain app;
    Manager manager;
    ListView listView;

    List<Safra> safras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safra_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (AppMain)getApplicationContext();
        manager = new Manager(app);

        safras = app.getDaoSession().getSafraDao().
                queryBuilder().orderAsc(SafraDao.Properties.Safra).list();

        listView = findViewById(R.id.list_safra);

        ArrayAdapter<Safra> adapter = new ArrayAdapter<Safra>(this,
                android.R.layout.simple_list_item_1,safras);

        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Long idSafra = safras.get(i).getId();
                Intent in = new Intent(getApplicationContext(),SafraActivity.class);
                in.putExtra("id_safra",idSafra);
                startActivity(in);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_safra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_adiciona: {
                Intent in = new Intent(this.getApplicationContext(),SafraActivity.class);
                startActivity(in);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent in = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(in);
        finish();
        return true;
    }
}
