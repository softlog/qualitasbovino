package br.eti.softlog.qualitasbovino;


import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pixplicity.easyprefs.library.Prefs;

import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.MTFDados;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimalMainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigationView;
    public Long idAnimal;
    public MTFDados animal;
    public AppMain app;
    public Util util;
    public Manager manager;
    public Menu menu;
    public boolean isEdit;


    ActionBar actionBar;


    @BindView(R.id.btn_voltar)
    FloatingActionButton btnSalvar;

    @BindView(R.id.txt_idf)
    TextView txt_idf;
    @BindView(R.id.txt_criador)
    TextView txt_criador;
    @BindView(R.id.txt_proprietario)
    TextView txt_proprietario;
    @BindView(R.id.txt_sexo)
    TextView txt_sexo;
    @BindView(R.id.txt_data_nascimento)
    TextView txt_data_nascimento;
    @BindView(R.id.txt_idade)
    TextView txtIdade;
    @BindView(R.id.txt_situ_repro)
    TextView txt_situ_repro;
    @BindView(R.id.txt_livro)
    TextView txt_livro;

    @BindView(R.id.txt_avaliacao_ordem)
    TextView txtAvaliacao;

    @BindView(R.id.img_qsp)
    ImageView imgQsp;

    public int ordemAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_main);

        ButterKnife.bind(this);

        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        //setSupportActionBar(toolbar);

        app = (AppMain) getApplicationContext();
        manager = new Manager(app);
        util = new Util();

        Intent inCaller = getIntent();

        //Busca animal no banco de dados
        idAnimal = inCaller.getLongExtra("id_animal", 0);
        Prefs.putLong("animalCurrent", idAnimal);
        animal = manager.findMTFDadosByAnimal(idAnimal);

        //Preenche os dados das views
        //txt_animal.setText(animal.getAnimalTitle());


        txt_idf.setText("IDF: " + util.trataIdf(animal.getAnimalPrincipal().getIdf()));
        txt_criador.setText(animal.getCriador().getCodigo() + " - " + animal.getCriador().getFazenda());
        txt_proprietario.setText(animal.getProprietario().getCodigo() + " - " + animal.getProprietario().getFazenda());
        txt_sexo.setText(animal.getSexoDesc());


        String dataNasc = util.getDateFormatDMY(animal.getDataNasc(), "dd/MM/yy");
        txt_data_nascimento.setText(dataNasc);

        Double idade = animal.getIdade();
        String cIdade = String.format("%.1f", idade);
        txtIdade.setText(cIdade);

        txt_situ_repro.setText(animal.getSituRepro());
        txt_livro.setText(animal.getLivro());

        navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setSelectedItemId(R.id.menu_avaliacao);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, AvaliacaoFragment.newInstance());
        transaction.commit();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();
            }
        });


        ordemAvaliacao = animal.getOrdem_avaliacao();

        if (ordemAvaliacao > 0){
            txtAvaliacao.setText("Avaliação Número: " + String.valueOf(ordemAvaliacao));
        } else {
            String qry = "SELECT count(*) as qt FROM " +
                    "mtf_dados WHERE avaliado = 1 " +
                    "AND sexo = '" + animal.getSexo() + "'" +
                    "AND criador_id = " + animal.getCriadorId();

            Cursor cursorOrdem = app.getDb().rawQuery(qry,null);

            while (cursorOrdem.moveToNext()){
                ordemAvaliacao = cursorOrdem.getInt(0) + 1;
            }
            txtAvaliacao.setText("Avaliação Número: " + String.valueOf(ordemAvaliacao));
        }


        if (animal.getIdMae() != 1){
            imgQsp.setVisibility(View.INVISIBLE);
            imgQsp.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selectFragment = null;
        switch (item.getItemId()) {

            case R.id.menu_animal: {
                selectFragment = AnimalFragment.newInstance();
                break;
            }
            case R.id.menu_observacao: {
                selectFragment = ObservacaoFragment.newInstance();
                break;
            }
            case R.id.menu_avaliacao: {
                selectFragment = AvaliacaoFragment.newInstance();
                break;
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectFragment);
        transaction.commit();
        return true;
    }

    public MTFDados getAnimal() {
        return animal;
    }

    public Manager getManager() {
        return manager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.animal_main_menu_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Boolean iniciou;
        iniciou = false;
        for (int i = 0; i<animal.getMedicoesAnimals().size();i++){
            if (animal.getMedicoesAnimals().get(i).getMedicaoId() == 222530) {
                continue;
            }
            if (animal.getMedicoesAnimals().get(i).getValor() != null) {
                iniciou = true;
                break;
            }
        }

        if (isEdit && iniciou) {
            new MaterialDialog.Builder(AnimalMainActivity.this)
                    .title("Mensagem")
                    .content("Há dados em edição. Favor salvar ou cancelar para poder sair. ")
                    .positiveText("OK")
                    .show();
            return false;
        } else {
            isEdit = false;
            Prefs.putBoolean("isEdit", isEdit);
            Prefs.putLong("animalCurrent", 0);
            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(in);
            finish();
            return true;
        }
    }
}
