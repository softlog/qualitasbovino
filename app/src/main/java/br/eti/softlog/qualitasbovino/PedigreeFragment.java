package br.eti.softlog.qualitasbovino;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.omega_r.libs.OmegaCenterIconButton;
import com.pixplicity.easyprefs.library.Prefs;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import br.eti.softlog.certificacao.AnimalMainCertificacaoActivity;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.Pedigree;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PedigreeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    MTFDados animal;
    Activity activity;


    List <Pedigree> animais;
    //private OnFragmentInteractionListener mListener;

    @BindView(R.id.txt_animal)
    TextView txt_animal;

    @BindView(R.id.txt_pai)
    TextView txt_pai;

    @BindView(R.id.txt_mae)
    TextView txt_mae;

    @BindView(R.id.txt_avoh_paterno)
    TextView txt_avoh_paterno;

    @BindView(R.id.txt_avo_paterno)
    TextView txt_avo_paterno;

    @BindView(R.id.txt_avoh_materno)
    TextView txt_avoh_materno;

    @BindView(R.id.txt_avo_materno)
    TextView txt_avo_materno;

    @BindView(R.id.btn_voltar)
    OmegaCenterIconButton btnVoltar;

    private Unbinder unbinder;

    public PedigreeFragment() {
        // Required empty public constructor
    }


    public static PedigreeFragment newInstance() {
        PedigreeFragment fragment = new PedigreeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedigree, container, false);

        unbinder = ButterKnife.bind(this,view);

        if (Prefs.getInt("tipo_operacao",0)==1){
            animal = ((AnimalMainActivity) activity).getAnimal();
            animais =  new ArrayList<>();
            animais.add(((AnimalMainActivity) activity).manager.findPedigreeByAnimal(animal.getId()));
        } else {
            animal = ((AnimalMainCertificacaoActivity) activity).getAnimal();
            animais =  new ArrayList<>();
            animais.add(((AnimalMainCertificacaoActivity) activity).manager.findPedigreeByAnimal(animal.getId()));
        }


        getPedigree();


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animais.remove(animais.size()-1);
                getPedigree();
            }
        });

        txt_pai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pedigree animalChart = animais.get(animais.size()-1);
                if (!txt_pai.getText().toString().equals("ND"))
                    setPedigree(animalChart.getPedigreePai().getId());
            }
        });


        txt_mae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pedigree animalChart = animais.get(animais.size()-1);
                if (!txt_mae.getText().toString().equals("ND"))
                    setPedigree(animalChart.getPedigreeMae().getId());
            }
        });

        txt_avoh_paterno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pedigree animalChart = animais.get(animais.size()-1);
                if (!(txt_avoh_paterno.getText().toString().equals("ND")))
                    setPedigree(animalChart.getPedigreePai().getPedigreePai().getId());
            }
        });

        txt_avo_paterno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pedigree animalChart = animais.get(animais.size()-1);
                if (!(txt_avo_paterno.getText().toString().equals("ND")))
                    setPedigree(animalChart.getPedigreePai().getPedigreeMae().getId());
            }
        });

        txt_avoh_materno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pedigree animalChart = animais.get(animais.size()-1);
                if (!(txt_avoh_materno.getText().toString().equals("ND")))
                    setPedigree(animalChart.getPedigreeMae().getPedigreePai().getId());
            }
        });

        txt_avo_materno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pedigree animalChart = animais.get(animais.size()-1);
                if (!(txt_avo_materno.getText().toString().equals("ND")))
                    setPedigree(animalChart.getPedigreeMae().getPedigreeMae().getId());
            }
        });

        return view;
    }

    public void setPedigree(Long idAnimal) {
        Pedigree a;
        if (Prefs.getInt("tipo_operacao",0)==1)
            a = ((AnimalMainActivity) activity).manager
                    .findPedigreeByAnimal(idAnimal);
        else
            a = ((AnimalMainCertificacaoActivity) activity).manager
                    .findPedigreeByAnimal(idAnimal);

        animais.add(a);
        getPedigree();
    }

    public void getPedigree(){

        Pedigree animalChart;
        animalChart = animais.get(animais.size()-1);

        if (animais.size() > 1) {
            btnVoltar.setEnabled(true);
        } else {
            btnVoltar.setEnabled(false);
        }

        txt_animal.setText("ND");
        txt_pai.setText("ND");
        txt_mae.setText("ND");
        txt_avo_materno.setText("ND");
        txt_avoh_materno.setText("ND");
        txt_avo_paterno.setText("ND");
        txt_avoh_paterno.setText("ND");

        txt_animal.setText(animalChart.getAliasPedigree());

        //Pai do Animal
        if ((animalChart.getPedigreePai() != null)){
            txt_pai.setText(animalChart.getPedigreePai().getAliasPedigree());

            //Avós paternos
            try {
                txt_avoh_paterno.setText(animalChart.getPedigreePai().getPedigreePai().getAliasPedigree());
            } catch (Exception e){
                txt_avoh_paterno.setText("ND");
            }

            try {
                txt_avo_paterno.setText(animalChart.getPedigreePai().getPedigreeMae().getAliasPedigree());
            } catch (Exception e){
                txt_avo_paterno.setText("ND");
            }
        } else {
            txt_pai.setText("ND");
        }

        //Mae do Animal
        if (!(animalChart.getPedigreeMae() == null)){
            txt_mae.setText(animalChart.getPedigreeMae().getAliasPedigree());

            try {
                txt_avoh_materno.setText(animalChart.getPedigreeMae().getPedigreePai().getAliasPedigree());
            } catch (Exception e) {
                txt_avoh_materno.setText("ND");
            }

            try {
                txt_avo_materno.setText(animalChart.getPedigreeMae().getPedigreeMae().getAliasPedigree());
            } catch (Exception e){
                txt_avo_materno.setText("ND");
            }

        } else {
            txt_mae.setText("ND");
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
