package br.eti.softlog.qualitasbovino;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.AnimalNovo;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MTFDadosDao;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.blackbox_vision.datetimepickeredittext.view.DatePickerInputEditText;

public class AnimalNovoActivity extends AppCompatActivity {

    AppMain app;
    Manager manager;
    Util util;

    boolean alterar;
    List<MTFDados> animais;

    MTFDados animal;

    @BindView(R.id.txtlayout_codigo)
    TextInputLayout layoutCodigo;
    @BindView(R.id.et_prefixo)
    AppCompatEditText etPrefixo;
    @BindView(R.id.et_codigo)
    AppCompatEditText etCodigo;


    @BindView(R.id.txtlayout_nome)
    TextInputLayout layoutNome;
    @BindView(R.id.et_nome)
    AppCompatEditText etNome;

    @BindView(R.id.txtlayout_data_nascimento)
    TextInputLayout layoutDataNascimento;
    @BindView(R.id.dp_data_nascimento)
    DatePickerInputEditText dpDataNascimento;

    @BindView(R.id.rd_sexo)
    RadioGroup rdSexo;
    @BindView(R.id.opt_macho)
    RadioButton optMacho;
    @BindView(R.id.opt_femea)
    RadioButton optFemea;

    @BindView(R.id.btn_gravar)
    Button btnGravar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_novo);
        ButterKnife.bind(this);

        app = (AppMain) getApplication();
        manager = new Manager(app);
        util = new Util();
        alterar = false;

        ArrayList<InputFilter> curInputFilters = new ArrayList<InputFilter>(Arrays.asList(etPrefixo.getFilters()));
        curInputFilters.add(0, new AlphaNumericInputFilter());
        curInputFilters.add(1, new InputFilter.AllCaps());
        InputFilter[] newInputFilters = curInputFilters.toArray(new InputFilter[curInputFilters.size()]);
        etPrefixo.setFilters(newInputFilters);

        String dataAnimalNovo = Prefs.getString("data_animal_novo","S");
        dpDataNascimento.setManager(getSupportFragmentManager());

        if (!dataAnimalNovo.equals("S"))
            dpDataNascimento.setText(dataAnimalNovo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Inclusão de Animal");

        String prefixo = Prefs.getString("prefixo_fazenda","");

        if (!prefixo.equals("")){
            etPrefixo.setText(prefixo);
            etCodigo.requestFocus();
        }

        String sexoAnimalNovo = Prefs.getString("sexo_animal_novo", "S");

        if (sexoAnimalNovo.equals("S")) {
            if (Prefs.getString("filtro_sexo", "M").equals("F"))
                optFemea.setChecked(true);
            else
                optMacho.setChecked(true);
        } else {
            if (sexoAnimalNovo.equals("F"))
                optFemea.setChecked(true);
            else
                optMacho.setChecked(true);
        }

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();

                if (onValidateAdd()) {
                    Toast.makeText(getApplicationContext(), "Animal incluido com sucesso!", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(getApplicationContext(), AnimalMainActivity.class);
                    in.putExtra("id_animal",animal.getId());
                    startActivity(in);
                    finish();
                } else {
                    if (alterar){
                        MTFDados animal = animais.get(0);
                        //TODO: Trazer dados para a tela

                    }
                };




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

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean onValidateAdd() {
        Long fazendaId = Prefs.getLong("filtro_fazenda", 0);

        String prefixo;
        String numero;
        String codigo;

        prefixo = etPrefixo.getText().toString();
        numero = etCodigo.getText().toString();

        if (!prefixo.isEmpty())
        {
            String prefixoAnterior = Prefs.getString("prefixo_fazenda","");
            //Se nao for igual ao prefixo anterior, grava o novo
            if (!prefixoAnterior.equals(prefixo))
                Prefs.putString("prefixo_fazenda",prefixo);
            codigo = prefixo + " " + numero;
        } else{
            codigo = numero;
            Prefs.putString("prefixo_fazenda","");
        }


        String nome = etNome.getText().toString();
        String dataNascimento = dpDataNascimento.getText().toString();

        final String sexo;

        if (rdSexo.getCheckedRadioButtonId() == R.id.opt_macho)
            sexo = "M";
        else
            sexo = "F";

        Prefs.putString("sexo_animal_novo", sexo);

        if (codigo.isEmpty()) {
            layoutCodigo.setErrorEnabled(true);
            layoutCodigo.setError("Informe o código do Animal.");
            return false;
        } else {
            layoutCodigo.setErrorEnabled(false);
        }


        if (dataNascimento.isEmpty()) {
            layoutDataNascimento.setErrorEnabled(true);
            layoutDataNascimento.setError("Informe a data de Nascimento.");
            return false;
        } else {
            Prefs.putString("data_animal_novo", dataNascimento);
            layoutDataNascimento.setErrorEnabled(false);
        }

        //Se animal for novo
        if (!alterar){
            searchAnimais(codigo);
            if (animais.size() > 0) {
                if (animais.size() == 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Alerta");
                    builder.setMessage("Animal já existe, deseja alterar?");
                    // Set up the buttons
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alterar = true;
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                    return false;

                }
                layoutCodigo.setErrorEnabled(true);
                layoutCodigo.setError("Código do animal já existe para este criador.");
                return false;
            } else {
                layoutCodigo.setErrorEnabled(false);
            }
        }

        Date dDataNascimento;
        String cDataNascimento;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dDataNascimento = formato.parse(dataNascimento);
            formato.applyPattern("yyyy-MM-dd");
            cDataNascimento = formato.format(dDataNascimento);
        } catch (ParseException e) {
            e.printStackTrace();
            cDataNascimento = "";
        }

        Date dtRegistro = new Date();
        String cDtRegisro = util.getDateFormatYMD(dtRegistro);

        if (alterar)
            animal = manager.editAnimalNovo(animais.get(0).getId(),codigo, nome, sexo, cDataNascimento, cDtRegisro, fazendaId, fazendaId);
        else
            animal = manager.addAnimalNovo(codigo, nome, sexo, cDataNascimento, cDtRegisro, fazendaId, fazendaId);

        return true;
    }

    public void searchAnimais(String filtroAnimal) {

        Long idCriador = Prefs.getLong("filtro_fazenda", 0);
        String sexo = Prefs.getString("filtro_sexo", "M");


        QueryBuilder queryBuilder;
        queryBuilder = app.getDaoSession().getMTFDadosDao().queryBuilder();

        QueryBuilder queryBuilder1;
        QueryBuilder queryBuilder2;
        QueryBuilder queryBuilder3;

        if (idCriador > 0) {
            queryBuilder1 = queryBuilder.where(MTFDadosDao.Properties.CriadorId.eq(idCriador));
        } else {
            queryBuilder1 = queryBuilder;
        }
        queryBuilder2 = queryBuilder1;


        if (filtroAnimal == null) {
            animais = queryBuilder2.orderAsc(MTFDadosDao.Properties.Animal).list();
        } else {
            queryBuilder3 = queryBuilder1.where(MTFDadosDao.Properties.Idf2.eq(filtroAnimal));
            animais = queryBuilder2.orderAsc(MTFDadosDao.Properties.Animal).list();

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
    }

    public static class AlphaNumericInputFilter implements InputFilter {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {

            // Only keep characters that are alphanumeric
            StringBuilder builder = new StringBuilder();
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (Character.isLetterOrDigit(c)) {
                    builder.append(c);
                }
            }

            // If all characters are valid, return null, otherwise only return the filtered characters
            boolean allCharactersValid = (builder.length() == end - start);
            return allCharactersValid ? null : builder.toString();
        }
    }

}
