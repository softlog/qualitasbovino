package br.eti.softlog.qualitasbovino;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.omega_r.libs.OmegaCenterIconButton;
import com.pixplicity.easyprefs.library.Prefs;

import br.eti.softlog.certificacao.AnimalMainCertificacaoActivity;
import br.eti.softlog.model.MTFDados;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ObservacaoFragment extends Fragment {

    private Unbinder unbinder;
    Activity activity;
    MTFDados animal;
    @BindView(R.id.edit_observacao)
    EditText edit_observacao;

    @BindView(R.id.btn_salvar)
    OmegaCenterIconButton btnSalvar;

    public ObservacaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */

    public static ObservacaoFragment newInstance() {
        ObservacaoFragment fragment = new ObservacaoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_observacao, container, false);
        unbinder = ButterKnife.bind(this,view);

        if (Prefs.getInt("tipo_operacao",0)==1){
            activity = (AnimalMainActivity) getActivity();
            animal = ((AnimalMainActivity) activity).getAnimal();
            edit_observacao.setText(animal.getObservacao());
        } else {
            activity = (AnimalMainCertificacaoActivity) getActivity();
            animal = ((AnimalMainCertificacaoActivity) activity).getAnimal();
            edit_observacao.setText(animal.getObservacao());
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animal.setObservacao(edit_observacao.getText().toString());
                if (Prefs.getInt("tipo_operacao",0)==1)
                    ((AnimalMainActivity) activity).app.getDaoSession().update(animal);
                else
                    ((AnimalMainCertificacaoActivity) activity).app.getDaoSession().update(animal);
                Toast.makeText(activity.getApplicationContext(),"Observação salva com sucesso!",Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
