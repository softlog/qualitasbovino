package br.eti.softlog.qualitasbovino;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import br.eti.softlog.model.Safra;
import br.eti.softlog.model.SafraDao;
import br.eti.softlog.qualitasbovino.R;

public class SafraActivity extends AppCompatActivity {


    AppMain app;
    Manager manager;
    ListView listView;
    Long id_safra;

    Safra safra;

    EditText editSafra;
    Button btnGravar;
    Button btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safra);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (AppMain)getApplicationContext();

        editSafra = findViewById(R.id.editSafra);
        btnGravar = findViewById(R.id.btn_gravar);
        btnExcluir = findViewById(R.id.btn_excluir);

        Intent inCall = getIntent();

        id_safra = inCall.getLongExtra("id_safra",0);

        if (id_safra == 0){
            btnExcluir.setEnabled(false);
        } else {
            btnGravar.setEnabled(false);
            safra = app.getDaoSession().getSafraDao().queryBuilder()
                    .where(SafraDao.Properties.Id.eq(id_safra)).unique();

            if (safra!=null){
                editSafra.setText(String.valueOf(safra.getSafra()));
                editSafra.setEnabled(false);
            }


        }

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                safra = new Safra();
                String cSafra = editSafra.getText().toString();
                if (cSafra.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Informe a Safra.",Toast.LENGTH_LONG).show();
                    return ;
                }

                safra.setSafra(Integer.valueOf(cSafra));
                safra.criarDiretorioSafra();
                app.getDaoSession().insert(safra);

                Intent in = new Intent(getApplicationContext(),SafraListActivity.class);
                startActivity(in);
                finish();

            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialDialog.Builder builder = new MaterialDialog.Builder(SafraActivity.this)
                        .title("Exclusão de Safra")
                        .content("Deseja excluir este registro?")
                        .positiveText("Sim")
                        .negativeText("Não")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                app.getDaoSession().delete(safra);
                                Intent in = new Intent(getApplicationContext(),SafraListActivity.class);
                                startActivity(in);
                                finish();
                            }
                        });

                MaterialDialog dialog = builder.build();

                dialog.show();

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
