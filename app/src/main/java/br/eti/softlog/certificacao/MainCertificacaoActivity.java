package br.eti.softlog.certificacao;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.ProgressBar;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.idescout.sql.SqlScoutServer;
import com.jaredrummler.materialspinner.MaterialSpinner;

import com.pixplicity.easyprefs.library.Prefs;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.eti.softlog.Readers.ReaderXLSCert;
import br.eti.softlog.Utils.AvaliacaoJxls;
import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.Criador;
import br.eti.softlog.model.CriadorDao;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MTFDadosDao;
import br.eti.softlog.qualitasbovino.AnimalAdapter;
import br.eti.softlog.qualitasbovino.AnimalNovoActivity;
import br.eti.softlog.qualitasbovino.AppMain;
import br.eti.softlog.qualitasbovino.ExportacaoActivity;
import br.eti.softlog.qualitasbovino.ImportacaoActivity;
import br.eti.softlog.qualitasbovino.Manager;
import br.eti.softlog.qualitasbovino.MedidasListActivity;
import br.eti.softlog.qualitasbovino.MenuActivity;
import br.eti.softlog.qualitasbovino.R;
import br.eti.softlog.qualitasbovino.ResumoActivity;
import br.eti.softlog.qualitasbovino.SettingsActivity;
import butterknife.ButterKnife;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class MainCertificacaoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private AnimalAdapterCertificacao animalAdapter;
    private List<MTFDados> animais;
    private AppMain app;
    private Manager manager;
    private Util util;
    private AvaliacaoJxls avaliacaoJxls;
    private Long animalCurrent;
    List<Criador> criadores;
    private DialogProperties properties = new DialogProperties();
    ProgressBar pb;

    private static int formatoSelecionado;
    private static String nomeArqSelecionado;
    private static ListView lista;
    private static java.io.File sdcard;

//    private AppCompatEditText editTextEmail;
//    private AppCompatEditText editTextPassword;
//    private TextInputLayout textLayoutEmail;
//    private TextInputLayout textLayoutPassword;
//    private Button btnLogin;

    EditText editAnimal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SqlScoutServer sqlScoutServer = SqlScoutServer.create(this, getPackageName());


        ButterKnife.bind(this);
        //Prefs.clear();
        setContentView(R.layout.activity_main_certificacao);


        AndPermission.with(this)
                .permission(
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                //alert("Sem estas permissões o aplicativo não pode funcionar.");
            }
        }).start();


        //Cria diretorio padrão de importacao
        String dirMain = "/mnt/sdcard/qualitas_bovino";
        File folder = new File(dirMain);
        if (!folder.exists()) {
            folder.mkdir();
        }

        String dirDefault = "/mnt/sdcard/qualitas_bovino/importacao_certificacao";

        File folder2 = new File(dirDefault);
        if (!folder2.exists()) {
            folder2.mkdir();
        }

        //Inicializacao
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Certificação");

        //copyWorkbook();
        app = (AppMain) getApplicationContext();
        manager = new Manager(app);
        util = new Util();

        //ReaderXLSCert readerXLSCert = new ReaderXLSCert(app);
        //readerXLSCert.read(dirDefault + "/QLT_JM_2017_Marcacao_20190402_1435.xls");

        pb = findViewById(R.id.pb);
        pb.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.GONE);

        editAnimal = findViewById(R.id.ev_animal);



        //Configuracao RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );


        this.configData(false);

        animalAdapter = new AnimalAdapterCertificacao(animais, getApplicationContext(), app);
        recyclerView.setAdapter(animalAdapter);


        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), ResumoCertificacaoActivity.class);
                startActivity(in);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navBancoDados = (TextView) headerView.findViewById(R.id.textView);
        navBancoDados.setText("Banco Dados: " + Prefs.getString("bancoDadosCurrentCert", ""));
        //navBancoDados.setTextColor(Color.BLACK);

        TextView navTitulo = (TextView) headerView.findViewById(R.id.txt_titulo);
        //navTitulo.setTextColor(Color.BLACK);


        editAnimal.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String cAnimal = editAnimal.getText().toString();

                        if (cAnimal.isEmpty()) {
                            searchAnimais(true, null);
                        }

                        searchAnimais(true, cAnimal);

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        Log.d("Aviso","Mudei");
                    }
                }
        );

        String dirRoot = "/data/data/br.eti.softlog.qualitasbovino/databases";
        properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.SINGLE_MODE;
        properties.root = new File(dirRoot);
        properties.error_dir = new File(dirRoot);
        properties.offset = new File(dirRoot);
        String[] extensoes = {"db", "DB"};
        properties.extensions = extensoes;

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        //Recupera Configurações de Banco de Dados e Filtros
        final Long fazendaCurrent = Prefs.getLong("filtro_fazenda", 0);
        final String sexoCurrent = Prefs.getString("filtro_sexo", "M");

        QueryBuilder queryBuilder = app.getDaoSession().getCriadorDao().queryBuilder();

        //Seletor da Fazenda
        criadores = queryBuilder
                .orderAsc(CriadorDao.Properties.Codigo)
                .list();

        //.orderAsc().list();
        //where(new WhereCondition.StringCondition(
        // Properties.Contact_number.eq(phonenumber) +
        // "GROUP BY group_name"))
        // .orderDesc(Properties.Date_time).build().list();

        final List<String> listaCriadores = new ArrayList<>();
        listaCriadores.add("FAZENDAS");


        for (int i = 0; i < criadores.size(); i++) {
            if (criadores.get(i).getAnimais().size() > 0) {
                if (criadores.get(i).getDescricao().length() > 20)
                    listaCriadores.add(criadores.get(i).getCodigo() + " - " + criadores.get(i).getDescricao().substring(0, 19) + "...");
                else
                    listaCriadores.add(criadores.get(i).getCodigo() + " - " + criadores.get(i).getDescricao());
            }
        }

        MenuItem itemFazenda = menu.findItem(R.id.spinnerFazenda);
        MaterialSpinner spinnerFazenda = (MaterialSpinner) itemFazenda.getActionView();
        spinnerFazenda.setBackgroundColor(Color.parseColor("#388E3C"));
        spinnerFazenda.setTextColor(Color.WHITE);
        spinnerFazenda.setVerticalScrollBarEnabled(true);
        spinnerFazenda.setDropdownHeight(600);
        spinnerFazenda.setTextSize(16);

        spinnerFazenda.setItems(listaCriadores);

        //Se existe Fazenda configurada, posiciona o seletor na mesma
        if (criadores.size() > 0) {
            if (fazendaCurrent > 0) {
                Criador criador = app.getDaoSession().getCriadorDao().queryBuilder()
                        .where(CriadorDao.Properties.Id.eq(fazendaCurrent)).unique();
                int position;
                position = -1;
                for (int i = 0; i < listaCriadores.size(); i++) {

                    if (criador.getCodigo().equals(listaCriadores.get(i).substring(0, 2))) {
                        position = i;
                        spinnerFazenda.setSelectedIndex(position);
                        break;
                    }
                }
            }
        }

        spinnerFazenda.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Long fazendaSelected;
                if (position == 0) {
                    fazendaSelected = Long.valueOf(0);
                    Prefs.putLong("filtro_fazenda", 0);
                    Prefs.putString("sexo_animal_novo","S");
                    Prefs.putString("data_animal_novo","S");
                } else {
                    fazendaSelected = manager.codigoToId(item.substring(0, 2));
                    //Long idCriador = criadores.get(position - 1).getId();
                    Prefs.putLong("filtro_fazenda", fazendaSelected);
                    Prefs.putString("sexo_animal_novo","S");
                    Prefs.putString("data_animal_novo","S");
                }

                searchAnimais(true, null);

                //                if (fazendaCurrent != fazendaSelected) {
                //                    searchAnimais(true,null);
                //                }
            }
        });

        //Seletor do Sexo
        MenuItem itemSexo = menu.findItem(R.id.spinnerSexo);
        MaterialSpinner spinnerSexo = (MaterialSpinner) itemSexo.getActionView();
        spinnerSexo.setBackgroundColor(Color.parseColor("#388E3C"));
        spinnerSexo.setTextColor(Color.WHITE);
        //spinner.setDropdownHeight(80);
        spinnerSexo.setItems("MACHO", "FÊMEA", "AMBOS");
        spinnerSexo.setTextSize(18);

        //Se o filtro de sexo atual for diferente do Masculino, posiciona no correto
        if (sexoCurrent.equals("F")) {
            spinnerSexo.setSelectedIndex(1);
        } else if (sexoCurrent.equals("F")) {
            spinnerSexo.setSelectedIndex(2);
        }

        spinnerSexo.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                String cSexo;
                if (position == 0) {
                    cSexo = "M";
                } else if (position == 1) {
                    cSexo = "F";
                } else {
                    cSexo = "A";
                }

                Prefs.putString("filtro_sexo", cSexo);

//                if (sexoCurrent!=cSexo){
//                    searchAnimais(true,null);
//                }
                searchAnimais(true, null);

            }
        });



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setting) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        }

        if (id == R.id.action_database) {
            FilePickerDialog dialog = new FilePickerDialog(MainCertificacaoActivity.this, properties);
            dialog.setTitle("Selecione um Arquivo");
            dialog.setNegativeBtnName("Cancelar");
            dialog.setPositiveBtnName("Selecionado");

            dialog.setDialogSelectionListener(new DialogSelectionListener() {
                @Override
                public void onSelectedFilePaths(String[] files) {
                    if (files.length > 0) {

                        String file = files[0];
                        String[] partes = file.split("/");
                        String nomeBD = partes[partes.length - 1]
                                .replace(".db", "");
                        Prefs.putString("bancoDadosCurrentCert", nomeBD);

                        configData(true);

                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        navigationView.setNavigationItemSelectedListener(MainCertificacaoActivity.this);

                        View headerView = navigationView.getHeaderView(0);
                        TextView navBancoDados = (TextView) headerView.findViewById(R.id.textView);
                        navBancoDados.setText("Banco Dados: " + Prefs.getString("bancoDadosCurrentCert", ""));
                    }
                }
            });

            dialog.show();

        }

        if (id == R.id.action_bakcup) {

            pb.setVisibility(View.VISIBLE);

            if (app.backupBD(getApplicationContext(),
                    Prefs.getString("bancoDadosCurrentCert", "base_teste"))) {

                MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                        .title("Backup")
                        .content("Backup realizado com sucesso!")
                        .positiveText("Ok")
                        .show();
            } else {

                MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                        .title("Erro")
                        .content("Ocorreu um erro ao tentar fazer backup do Banco de Dados!")
                        .positiveText("Ok")
                        .show();
            }

            pb.setVisibility(View.INVISIBLE);
            pb.setVisibility(View.GONE);

        }


        if (id == R.id.action_restore) {

            String dirMain = "/mnt/sdcard/qualitas_bovino";

            String dirBackup = dirMain + "/backup";
            File folderBackup = new File(dirBackup);
            if (!folderBackup.exists()) {
                folderBackup.mkdir();
            }

            DialogProperties properties = new DialogProperties();


            String dirRoot = "/data/data/br.eti.softlog.qualitasbovino/databases";
            properties = new DialogProperties();
            properties.selection_mode = DialogConfigs.SINGLE_MODE;
            properties.selection_type = DialogConfigs.SINGLE_MODE;
            properties.root = new File(dirBackup);
            properties.error_dir = new File(dirBackup);
            properties.offset = new File(dirBackup);
            String[] extensoes = {"db", "DB"};
            properties.extensions = extensoes;

            FilePickerDialog dialog = new FilePickerDialog(MainCertificacaoActivity.this, properties);
            dialog.setTitle("Selecione um Arquivo");
            dialog.setNegativeBtnName("Cancelar");
            dialog.setPositiveBtnName("Selecionado");

            dialog.setDialogSelectionListener(new DialogSelectionListener() {
                @Override
                public void onSelectedFilePaths(String[] files) {
                    if (files.length > 0) {
                        String file = files[0];
                        String[] partes = file.split("/");
                        String nomeBD = partes[partes.length - 1].replace(".db", "");
                        pb.setVisibility(View.VISIBLE);
                        if (app.restoreBD(getApplicationContext(), nomeBD)) {
                            Prefs.putString("bancoDadosCurrentCert", nomeBD);
                            configData(true);

                            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                            navigationView.setNavigationItemSelectedListener(MainCertificacaoActivity.this);

                            View headerView = navigationView.getHeaderView(0);
                            TextView navBancoDados = (TextView) headerView.findViewById(R.id.textView);
                            navBancoDados.setText("Banco Dados: " + Prefs.getString("bancoDadosCurrentCert", ""));

                            MaterialDialog materialDialog = new MaterialDialog.Builder(MainCertificacaoActivity.this)
                                    .title("Restauração")
                                    .content("Banco de Dados restaurado com sucesso!")
                                    .positiveText("Ok")
                                    .show();
                        }

                        pb.setVisibility(View.INVISIBLE);
                        pb.setVisibility(View.GONE);

                    }
                }
            });

            dialog.show();


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_importar) {
            Intent in = new Intent(this.getApplicationContext(), ImportacaoCertificacaoActivity.class);
            startActivity(in);
            finish();
        } else if (id == R.id.nav_resumo) {
            Intent in = new Intent(this.getApplicationContext(),ResumoActivity.class);
            startActivity(in);
        } else if (id == R.id.nav_exportar) {
            Intent in = new Intent(this.getApplicationContext(), ExportacaoCertificacaoActivity.class);
            startActivity(in);
        } else if (id == R.id.nav_sair) {
            Intent in = new Intent(this.getApplicationContext(), MenuActivity.class);
            startActivity(in);
            String bd = Prefs.getString("bancoDadosCurrentCert","base_teste");
            String bd2 = Prefs.getString("bancoDadosCurrent","base_teste");
            Prefs.clear();
            Prefs.putString("bancoDadosCurrentCert",bd);
            Prefs.putString("bancoDadosCurrent",bd2);
            Prefs.putInt("tipo_operacao",0);
            finish();
        } else if (id == R.id.nav_medidas) {
            Intent in = new Intent(this.getApplicationContext(), MedidasListActivity.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void searchAnimais(boolean reloadView, String filtroAnimal) {

        Long idCriador = Prefs.getLong("filtro_fazenda", 0);
        String sexo = Prefs.getString("filtro_sexo", "M");


        QueryBuilder queryBuilder;
        queryBuilder = app.getDaoSession().getMTFDadosDao().queryBuilder();

        QueryBuilder queryBuilder1;
        QueryBuilder queryBuilder2;
        QueryBuilder queryBuilder3;

        if (idCriador > 0) {
            queryBuilder1 = queryBuilder.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
            if (sexo != "A") {
                queryBuilder2 = queryBuilder1.where(MTFDadosDao.Properties.Sexo.eq(sexo));
            } else {
                queryBuilder2 = queryBuilder1;
            }

            if (filtroAnimal == null) {
                animais = queryBuilder2
                        .orderAsc(MTFDadosDao.Properties.PrefixIdf,MTFDadosDao.Properties.CodigoIdf)
                        .list();
            } else {
                queryBuilder3 = queryBuilder1.where(MTFDadosDao.Properties.Idf2.like("%" + filtroAnimal.trim().toUpperCase() + "%"));
                animais = queryBuilder2
                        .orderAsc(MTFDadosDao.Properties.PrefixIdf,MTFDadosDao.Properties.CodigoIdf)
                        .list();

                if (animais == null) {
                    //Log.d("Mensagem", "Animal não encontrado");
                    //Perguntar se quer inserir animal
                } else if (animais.size() == 1) {
                    if (animais.get(0).getSexo() != sexo) {
                        //Log.d("Mensagem", "Animal encontrado, porém com sexo diferente");
                        //Perguntar se quer mudar de sexo
                    }
                }
            }
        } else {
            animais = queryBuilder.where(MTFDadosDao.Properties.CriadorId.eq(-1)).list();
        }


        //queryBuilder2.LOG_SQL = true;

        //queryBuilder2.LOG_VALUES = true;


        if (reloadView) {
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_fall_down);
            recyclerView.setLayoutAnimation(controller);
            animalAdapter.animais.clear();
            animalAdapter.animais.addAll(animais);
            animalAdapter.notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }
    }


    public void configData(boolean reloadView) {
        String baseDados = Prefs.getString("bancoDadosCurrentCert", "base_teste");
        app.setBD(baseDados);
        manager.addAllMedicoes();
        invalidateOptionsMenu();
        searchAnimais(reloadView, null);
    }


}
