package br.eti.softlog.certificacao;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omega_r.libs.OmegaCenterIconButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.Pedigree;
import br.eti.softlog.qualitasbovino.AnimalMainActivity;
import br.eti.softlog.qualitasbovino.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnimalCertificacaoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnimalCertificacaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimalCertificacaoFragment extends Fragment {
    MTFDados animal;
    public Activity activity;
    private Unbinder unbinder;
    private Util util;
    private Formatter formatter;


    List<Pedigree> animais;
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

    @BindView(R.id.dep_nasc)
    TextView txtDepNasc;

    @BindView(R.id.perc_nasc)
    TextView txtPercNasc;

    @BindView(R.id.r_p_nasc)
    TextView txtPNasc;

    @BindView(R.id.dep_desm)
    TextView txtDepDesm;

    @BindView(R.id.perc_desm)
    TextView txtPercDesm;

    @BindView(R.id.p_desm)
    TextView txtPDesm;

    @BindView(R.id.dep_gpd)
    TextView txtDepGPD;

    @BindView(R.id.perc_gpd)
    TextView txtPercGpd;

    @BindView(R.id.p_gpd)
    TextView txtPGpd;

    @BindView(R.id.dep_sob)
    TextView txtDepSob;

    @BindView(R.id.perc_sob)
    TextView txtPercSob;

    @BindView(R.id.p_sob)
    TextView txtPSob;

    @BindView(R.id.dep_ce)
    TextView txtDepCe;

    @BindView(R.id.perc_ce)
    TextView txtPercCe;

    @BindView(R.id.rc_ce)
    TextView txtRcCe;

    @BindView(R.id.txtCaptionIdadeCe)
    TextView txtCaptionIdadeCe;

    @BindView(R.id.ce)
    TextView txtCe;

    @BindView(R.id.txtCaptionCe)
    TextView txtCaptionCe;



    @BindView(R.id.dep_musc)
    TextView txtDepMusc;

    @BindView(R.id.perc_musc)
    TextView txtPercMusc;

    @BindView(R.id.musc)
    TextView txtMusc;

    @BindView(R.id.ind_qlt)
    TextView txtIndQlt;

    @BindView(R.id.perc_qlt)
    TextView txtPercQlt;

    @BindView(R.id.rank_qlt)
    TextView txtRankQlt;

    private OnFragmentInteractionListener mListener;

    public AnimalCertificacaoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AnimalCertificacaoFragment newInstance() {
        AnimalCertificacaoFragment fragment = new AnimalCertificacaoFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animal_certificacao, container, false);

        // Inflate the layout for this fragment
        activity = getActivity();

        unbinder = ButterKnife.bind(this, view);

        animal = ((AnimalMainCertificacaoActivity) activity).getAnimal();

        util = new Util();

        Locale ptBr = new Locale("pt", "BR");
        formatter = new Formatter(ptBr);

        txtDepNasc.setText(animal.getDepNasc().toString());
        txtPercNasc.setText(animal.getPercNasc().toString());
        txtPNasc.setText(animal.getRPNasc().toString());

        txtDepDesm.setText(animal.getDepDesm().toString());
        txtPercDesm.setText(animal.getPercDesm().toString() + "%");
        txtPDesm.setText(animal.getRPDesm().toString());

        txtDepGPD.setText(animal.getDepGPD().toString());
        txtPercGpd.setText(animal.getPercGPD().toString() + "%");
        txtPGpd.setText(animal.getPGPD().toString());

        txtDepSob.setText(animal.getDepSob().toString());
        txtPercSob.setText(animal.getPercSob().toString() + "%");
        txtPSob.setText(animal.getPSob().toString());

        txtDepCe.setText(animal.getDepCE().toString());
        txtPercCe.setText(animal.getPercCE().toString() + "%");

        if (animal.getSexo().equals("M")) {
            txtRcCe.setText(animal.getRcCE().toString());
            txtCe.setText(animal.getCe().toString());
        } else {
            txtCaptionCe.setVisibility(View.GONE);
            txtCaptionIdadeCe.setVisibility(View.GONE);
            txtRcCe.setVisibility(View.GONE);
            txtCe.setVisibility(View.GONE);
        }



        txtDepMusc.setText(animal.getDep_musc().toString());
        txtPercMusc.setText(animal.getPerc_musc().toString() + "%");
        txtMusc.setText(animal.getMusc().toString());

        txtIndQlt.setText(animal.getIndQlt().toString());
        txtPercQlt.setText(animal.getPercQlt().toString() + "%");
        txtRankQlt.setText(animal.getRank_qlt().toString().replace(".0", ""));


        if (Prefs.getInt("tipo_operacao",0)==1){
            animais =  new ArrayList<>();
            animais.add(((AnimalMainActivity) activity).manager.findPedigreeByAnimal(animal.getId()));
        } else {
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void setPedigree(Long idAnimal) {
        Pedigree a;
        if (Prefs.getInt("tipo_operacao", 0) == 1)
            a = ((AnimalMainActivity) activity).manager
                    .findPedigreeByAnimal(idAnimal);
        else
            a = ((AnimalMainCertificacaoActivity) activity).manager
                    .findPedigreeByAnimal(idAnimal);

        animais.add(a);
        getPedigree();
    }

    public void getPedigree() {

        Pedigree animalChart;
        animalChart = animais.get(animais.size() - 1);

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
        if ((animalChart.getPedigreePai() != null)) {
            txt_pai.setText(animalChart.getPedigreePai().getAliasPedigree());

            //Avós paternos
            try {
                txt_avoh_paterno.setText(animalChart.getPedigreePai().getPedigreePai().getAliasPedigree());
            } catch (Exception e) {
                txt_avoh_paterno.setText("ND");
            }

            try {
                txt_avo_paterno.setText(animalChart.getPedigreePai().getPedigreeMae().getAliasPedigree());
            } catch (Exception e) {
                txt_avo_paterno.setText("ND");
            }
        } else {
            txt_pai.setText("ND");
        }

        //Mae do Animal
        if (!(animalChart.getPedigreeMae() == null)) {
            txt_mae.setText(animalChart.getPedigreeMae().getAliasPedigree());

            try {
                txt_avoh_materno.setText(animalChart.getPedigreeMae().getPedigreePai().getAliasPedigree());
            } catch (Exception e) {
                txt_avoh_materno.setText("ND");
            }

            try {
                txt_avo_materno.setText(animalChart.getPedigreeMae().getPedigreeMae().getAliasPedigree());
            } catch (Exception e) {
                txt_avo_materno.setText("ND");
            }

        } else {
            txt_mae.setText("ND");
        }
    }
}
