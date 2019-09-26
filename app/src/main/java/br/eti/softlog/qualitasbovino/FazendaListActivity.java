package br.eti.softlog.qualitasbovino;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

public class FazendaListActivity extends AppCompatActivity {


    private SwitchCompat switch1;
    private AppCompatButton btn1;
    private AppCompatButton btn2;

    private AlertDialog alertDialog;
    private AlertDialog alertDialogItens;
    private String[] items = {"Safra 2015", "Safra 2016", "Safra 2017", "Safra 2018"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fazenda_list);

        switch1 = findViewById(R.id.switch1);
        switch1.setChecked(true);


        switch1.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            Toast.makeText(getApplicationContext(),"Ligado",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"Desligado",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialog);

        builder.setMessage("Deseja excluir?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Sim",Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Não",Toast.LENGTH_LONG).show();
            }
        });

        alertDialog = builder.create();

        btn1 = findViewById(R.id.btn_alert);

        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.show();
                    }
                }
        );

        AlertDialog.Builder builderItem = new AlertDialog.Builder(this);

        builderItem.setTitle("Escolha um dos itens abaixo");


      /*  builderItem.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),items[i],Toast.LENGTH_LONG).show();
            }
        });*/

        builderItem.setMultiChoiceItems(items,null,null);
        builderItem.setPositiveButton("Ok",null);
        builderItem.setNegativeButton("Cancelar",null);

        alertDialogItens = builderItem.create();

        btn2 = findViewById(R.id.btn2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogItens.show();
            }
        });
    }
}
