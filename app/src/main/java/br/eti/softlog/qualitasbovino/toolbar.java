package br.eti.softlog.qualitasbovino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class toolbar extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_menu: {
                Toast.makeText(this,"Buscar",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.cart_menu: {
                Toast.makeText(this,"Carrinho",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.config_menu: {
                Toast.makeText(this,"Config",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.about_menu: {
                Toast.makeText(this,"Sobre",Toast.LENGTH_LONG).show();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
