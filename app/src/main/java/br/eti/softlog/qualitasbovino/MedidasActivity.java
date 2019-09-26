package br.eti.softlog.qualitasbovino;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.Date;

import br.eti.softlog.model.Medicao;
import br.eti.softlog.model.MedicaoDao;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MedidasActivity extends AppCompatActivity {

    Long idMedida;
    AppMain app;
    Medicao medicao;
    Manager manager;

    @BindView(R.id.txtlayout_descricao)
    TextInputLayout layoutDescricao;
    @BindView(R.id.et_descricao)
    AppCompatEditText editDescricao;

    @BindView(R.id.txtlayout_abrev)
    TextInputLayout layoutAbrev;
    @BindView(R.id.et_abrev)
    AppCompatEditText editAbrev;

    @BindView(R.id.txtlayout_menor_valor)
    TextInputLayout layoutMenorValor;
    @BindView(R.id.et_menor_valor)
    AppCompatEditText editMenorValor;

    @BindView(R.id.txtlayout_maior_valor)
    TextInputLayout layoutMaiorValor;
    @BindView(R.id.et_maior_valor)
    AppCompatEditText editMaiorValor;

    @BindView(R.id.txtlayout_restricao)
    TextInputLayout layoutRestricao;
    @BindView(R.id.et_restricao)
    AppCompatEditText editRestricao;

    @BindView(R.id.txtlayout_descarte1)
    TextInputLayout layoutDescarte1;
    @BindView(R.id.et_descarte1)
    AppCompatEditText editDescarte1;


    @BindView(R.id.txtlayout_descarte2)
    TextInputLayout layoutDescarte2;
    @BindView(R.id.et_descarte2)
    AppCompatEditText editDescarte2;

    @BindView(R.id.txtlayout_descarte3)
    TextInputLayout layoutDescarte3;
    @BindView(R.id.et_descarte3)
    AppCompatEditText editDescarte3;

    @BindView(R.id.txtlayout_descarte4)
    TextInputLayout layoutDescarte4;
    @BindView(R.id.et_descarte4)
    AppCompatEditText editDescarte4;

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
        setContentView(R.layout.activity_medidas);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Medidas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (AppMain)getApplication();
        manager = new Manager(app);

        Intent inCall = getIntent();

        idMedida = inCall.getLongExtra("idMedida",Long.valueOf(0));
        medicao = app.getDaoSession().getMedicaoDao()
                .queryBuilder().where(MedicaoDao.Properties.Id.eq(idMedida)).unique();

        editDescricao.setText(medicao.getDescricao());
        editDescricao.setEnabled(false);
        editAbrev.setText(medicao.getAbrev());
        editAbrev.setEnabled(false);
        editMenorValor.setText(String.valueOf(medicao.getMenorValor()));
        editMenorValor.requestFocus();
        editMaiorValor.setText(String.valueOf(medicao.getMaiorValor()));
        editRestricao.setText(String.valueOf(medicao.getRestricao()));
        editDescarte1.setText(String.valueOf(medicao.getDescarte1()));
        editDescarte2.setText(String.valueOf(medicao.getDescarte2()));
        editDescarte3.setText(String.valueOf(medicao.getDescarte3()));
        editDescarte4.setText(String.valueOf(medicao.getDescarte4()));



        if (medicao.getSexo().equals("F"))
            optFemea.setChecked(true);
        else
            optMacho.setChecked(true);

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();

                if (onValidateAdd()){
                    Toast.makeText(getApplicationContext(),"Medida gravada com sucesso!",Toast.LENGTH_LONG).show();
                    onSupportNavigateUp();
                };


            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
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

        String descricao = editDescricao.getText().toString();
        String abrev = editAbrev.getText().toString();
        int menorValor = Integer.valueOf(editMenorValor.getText().toString());
        int maiorValor = Integer.valueOf(editMaiorValor.getText().toString());
        int restricao =  Integer.valueOf(editRestricao.getText().toString());
        int descarte1 = Integer.valueOf(editDescarte1.getText().toString());
        int descarte2 = Integer.valueOf(editDescarte2.getText().toString());
        int descarte3 = Integer.valueOf(editDescarte3.getText().toString());
        int descarte4 = Integer.valueOf(editDescarte4.getText().toString());

//        String nome = etNome.getText().toString();
//        String dataNascimento = dpDataNascimento.getText().toString();
        final String sexo;
        if (rdSexo.getCheckedRadioButtonId() == R.id.opt_macho)
            sexo = "M";
        else
            sexo = "F";

        if (descricao.isEmpty()) {
            layoutDescricao.setErrorEnabled(true);
            layoutDescricao.setError("Informe a descrição.");
            return false;
        } else {
            layoutDescricao.setErrorEnabled(false);
        }
//
//        if (dataNascimento.isEmpty()) {
//            layoutDataNascimento.setErrorEnabled(true);
//            layoutDataNascimento.setError("Informe a data de Nascimento");
//            return false;
//        } else {
//            layoutDataNascimento.setErrorEnabled(false);
//        }
//
//
//        Long fazendaId = Prefs.getLong("filtro_fazenda",0);
//
//        Date dtRegistro = new Date();
//        String cDtRegisro = util.getDateFormatYMD(dtRegistro);
//
//        manager.addAnimalNovo(codigo,nome,sexo,dataNascimento,cDtRegisro,fazendaId,fazendaId);

        medicao.setDescricao(descricao);
        medicao.setAbrev(abrev);
        medicao.setSexo(sexo);
        medicao.setMaiorValor(maiorValor);
        medicao.setMenorValor(menorValor);
        medicao.setRestricao(restricao);
        medicao.setDescarte1(descarte1);
        medicao.setDescarte2(descarte2);
        medicao.setDescarte3(descarte3);
        medicao.setDescarte4(descarte4);
        return true;
    }


}
