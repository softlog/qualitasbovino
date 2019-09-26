package br.eti.softlog.certificacao;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.omega_r.libs.OmegaCenterIconButton;
import com.pixplicity.easyprefs.library.Prefs;

import com.thomashaertel.widget.MultiSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.eti.softlog.Utils.NumberTextWatcherForThousand;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MedicoesAnimal;
import br.eti.softlog.model.MotivoDescarte;
import br.eti.softlog.model.MotivoDescarteAnimais;
import br.eti.softlog.model.MotivoDescarteDao;
import br.eti.softlog.qualitasbovino.AvaliacaoAdapter;
import br.eti.softlog.qualitasbovino.ItemClickListener;
import br.eti.softlog.qualitasbovino.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AvaliacaoCertificacaoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AvaliacaoCertificacaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvaliacaoCertificacaoFragment extends Fragment implements ItemClickListener {

    // TODO: Rename and change types of parameters
    private RecyclerView recyclerView;
    AvaliacaoAdapter avaliacaoAdapter;
    public MTFDados animal;
    public List<MedicoesAnimal> medicoesAnimal;
    public ArrayAdapter<String> adapter;


    //private final List<CheckableSpinnerAdapter.SpinnerItem<MotivoDescarte>> spinner_items = new ArrayList<>();
    //private final Set<MotivoDescarte> selected_items = new HashSet<>();

    public OmegaCenterIconButton btnSalvar;
    public OmegaCenterIconButton btnAvaliar;
    public OmegaCenterIconButton btnExcluir;
    public OmegaCenterIconButton btnCancelar;

    private Unbinder unbinder;

    public boolean avaliado;

    public Activity activity;
    //private OnFragmentInteractionListener mListener;

    @BindView(R.id.txt_marcacao_sim)
    TextView txtMarcacaoSim;
    @BindView(R.id.txt_marcacao_nao)
    TextView txtMarcacaoNao;


    @BindView(R.id.txt_mocho_sim)
    TextView txtMochoSim;
    @BindView(R.id.txt_mocho_nao)
    TextView txtMochoNao;

    @BindView(R.id.txt_classificacao_1)
    TextView txtClassificacao1;

    @BindView(R.id.txt_classificacao_2)
    TextView txtClassificacao2;

    @BindView(R.id.txt_classificacao_3)
    TextView txtClassificacao3;

    @BindView(R.id.txt_classificacao_4)
    TextView txtClassificacao4;

    @BindView(R.id.txt_classificacao_f)
    TextView txtClassificacaoF;

    @BindView(R.id.txt_classificacao_p)
    TextView txtClassificacaoP;


    @BindView(R.id.et_peso)
    EditText etPeso;

    @BindView(R.id.et_ce)
    EditText etCe;

    @BindView(R.id.txt_caption_ce)
    TextView txtCaptionCe;

    @BindView(R.id.spnMotivo)
    MultiSpinner spnMotivo;



    private OnFragmentInteractionListener mListener;

    public AvaliacaoCertificacaoFragment() {

        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AvaliacaoCertificacaoFragment newInstance() {
        AvaliacaoCertificacaoFragment fragment = new AvaliacaoCertificacaoFragment();
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
        // Inflate the layout for this fragment

        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_avaliacao_certificacao, container, false);

        unbinder = ButterKnife.bind(this, view);

        animal = ((AnimalMainCertificacaoActivity) activity).getAnimal();

        ((AnimalMainCertificacaoActivity) activity).getManager().addAllMedicaoAnimal(animal.getAnimal());


        //((AnimalMainActivity) activity).isEdit = Prefs.getBoolean("isEdit", false);
        btnAvaliar = view.findViewById(R.id.btn_avaliar);
        btnExcluir = view.findViewById(R.id.btn_excluir);
        btnSalvar = view.findViewById(R.id.btn_salvar);
        btnCancelar = view.findViewById(R.id.btn_cancelar);

        //Se animal não está avaliado, abrir para avaliação
        if (!animal.getAvaliado()) {
            ((AnimalMainCertificacaoActivity) activity).isEdit = true;
            Prefs.putBoolean("isEdit", ((AnimalMainCertificacaoActivity) activity).isEdit);
        } else {
            ((AnimalMainCertificacaoActivity) activity).isEdit = false;
            Prefs.putBoolean("isEdit", ((AnimalMainCertificacaoActivity) activity).isEdit);
        }

        //Definir conteudo dos controles
        this.resetClassificacao();

        if (animal.getClassificacao() != null) {
            if (animal.getClassificacao() == Long.valueOf(1)) {
                txtClassificacao1.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtClassificacao1.setTextColor(Color.WHITE);
            } else if (animal.getClassificacao() == Long.valueOf(2)) {
                txtClassificacao2.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtClassificacao2.setTextColor(Color.WHITE);
            } else if (animal.getClassificacao() == Long.valueOf(3)) {
                txtClassificacao3.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtClassificacao3.setTextColor(Color.WHITE);
            } else if (animal.getClassificacao() == Long.valueOf(4)) {
                txtClassificacao4.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtClassificacao4.setTextColor(Color.WHITE);
            }
        }

        txtClassificacao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!canEdit())
                    return;

                resetClassificacao();
                boolean reverter;
                reverter = true;
                if (animal.getClassificacao() == null) {
                    animal.setClassificacao(Long.valueOf(1));
                    txtMarcacaoNao.callOnClick();
                    reverter = false;
                }

                if (animal.getClassificacao() != Long.valueOf(1)) {
                    animal.setClassificacao(Long.valueOf(1));
                    txtMarcacaoNao.callOnClick();
                    reverter = false;
                }


                if (animal.getClassificacao() == 1 && reverter) {
                    animal.setClassificacao(null);
                    txtMarcacaoNao.callOnClick();
                    return;
                }


                txtClassificacao1.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtClassificacao1.setTextColor(Color.WHITE);


            }
        });


        txtClassificacao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canEdit())
                    return;

                resetClassificacao();
                boolean reverter;
                reverter = true;
                if (animal.getClassificacao() == null) {
                    animal.setClassificacao(Long.valueOf(2));
                    reverter = false;
                }

                if (animal.getClassificacao() != Long.valueOf(2)) {
                    animal.setClassificacao(Long.valueOf(2));
                    reverter = false;
                }

                if (animal.getClassificacao() == 2 && reverter) {
                    animal.setClassificacao(null);
                    return;
                }

                txtClassificacao2.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtClassificacao2.setTextColor(Color.WHITE);
            }
        });


        txtClassificacao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canEdit())
                    return;
                resetClassificacao();
                boolean reverter;
                reverter = true;
                if (animal.getClassificacao() == null) {
                    animal.setClassificacao(Long.valueOf(3));
                    reverter = false;
                }

                if (animal.getClassificacao() != Long.valueOf(3)) {
                    animal.setClassificacao(Long.valueOf(3));
                    reverter = false;
                }

                if (animal.getClassificacao() == 3 && reverter) {
                    animal.setClassificacao(null);
                    return;
                }

                txtClassificacao3.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtClassificacao3.setTextColor(Color.WHITE);
            }
        });


        txtClassificacao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canEdit())
                    return;
                resetClassificacao();
                boolean reverter;
                reverter = true;
                if (animal.getClassificacao() == null) {
                    animal.setClassificacao(Long.valueOf(4));
                    reverter = false;
                }

                if (animal.getClassificacao() != Long.valueOf(4)) {
                    animal.setClassificacao(Long.valueOf(4));
                    reverter = false;
                }

                if (animal.getClassificacao() == 4 && reverter) {
                    animal.setClassificacao(null);
                    return;
                }

                txtClassificacao4.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtClassificacao4.setTextColor(Color.WHITE);
            }
        });


        resetClassificacaoFP();
        if (animal.getClassificacaoFP() != null) {
            if (animal.getClassificacaoFP().equals("P")) {
                txtClassificacaoP.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtClassificacaoP.setTextColor(Color.WHITE);
            } else if (animal.getClassificacaoFP().equals("F")) {
                txtClassificacaoF.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtClassificacaoF.setTextColor(Color.WHITE);
            }

        }

        txtClassificacaoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canEdit())
                    return;

                resetClassificacaoFP();


                if (animal.getClassificacaoFP() == null) {
                    animal.setClassificacaoFP("P");
                    txtClassificacaoP.setBackground(ContextCompat.getDrawable(
                            activity.getApplicationContext(), R.drawable.border_checked));
                    txtClassificacaoP.setTextColor(Color.WHITE);

                } else {
                    if (animal.getClassificacaoFP().equals("P")){
                        animal.setClassificacaoFP(null);
                    } else {
                        animal.setClassificacaoFP("P");
                        txtClassificacaoP.setBackground(ContextCompat.getDrawable(
                                activity.getApplicationContext(), R.drawable.border_checked));
                        txtClassificacaoP.setTextColor(Color.WHITE);
                    }
                }
            }
        });

        txtClassificacaoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canEdit())
                    return;

                resetClassificacaoFP();


                if (animal.getClassificacaoFP() == null) {
                    animal.setClassificacaoFP("F");
                    txtClassificacaoF.setBackground(ContextCompat.getDrawable(
                            activity.getApplicationContext(), R.drawable.border_checked));
                    txtClassificacaoF.setTextColor(Color.WHITE);

                } else {
                    if (animal.getClassificacaoFP().equals("F")){
                        animal.setClassificacaoFP(null);
                    } else {
                        animal.setClassificacaoFP("F");
                        txtClassificacaoF.setBackground(ContextCompat.getDrawable(
                                activity.getApplicationContext(), R.drawable.border_checked));
                        txtClassificacaoF.setTextColor(Color.WHITE);
                    }
                }
            }
        });

        if (animal.getSexo().equals("F")){
            txtClassificacaoF.setVisibility(View.GONE);
            txtClassificacaoP.setVisibility(View.GONE);
        }


        txtMarcacaoSim.setTextColor(Color.BLACK);
        txtMarcacaoNao.setTextColor(Color.BLACK);

        if (animal.getMarcacao() != null) {
            if (animal.getMarcacao() == Long.valueOf(1)) {
                txtMarcacaoSim.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtMarcacaoSim.setTextColor(Color.WHITE);
            } else if (animal.getMarcacao() == Long.valueOf(0)) {
                txtMarcacaoNao.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked_red));
                txtMarcacaoNao.setTextColor(Color.WHITE);
            }

        }

        txtMarcacaoSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!canEdit())
                    return;

                boolean desmarca;
                desmarca = true;
                if (animal.getMarcacao() == null) {
                    animal.setMarcacao(Long.valueOf(0));
                    desmarca = false;
                }

                if (animal.getMarcacao() == 1) {
                    animal.setMarcacao(null);
                    spnMotivo.setEnabled(true);
                    txtMarcacaoSim.setBackground(ContextCompat.getDrawable(
                            activity.getApplicationContext(), R.drawable.border));
                    txtMarcacaoSim.setTextColor(Color.BLACK);
                    return;
                }

                if (animal.getMarcacao() == 0) {
                    animal.setMarcacao(Long.valueOf(1));

                    animal.setMotDescId(null);
                    //spnMotivo.setSelectedIndex(0);
                    spnMotivo.setEnabled(false);

                    txtMarcacaoSim.setBackground(ContextCompat.getDrawable(
                            activity.getApplicationContext(), R.drawable.border_checked));
                    txtMarcacaoSim.setTextColor(Color.WHITE);

                    if (desmarca) {
                        txtMarcacaoNao.setBackground(ContextCompat.getDrawable(
                                activity.getApplicationContext(), R.drawable.border));
                        txtMarcacaoNao.setTextColor(Color.BLACK);
                    }

                }

            }
        });


        txtMarcacaoNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!canEdit())
                    return;
                boolean desmarca;
                desmarca = true;
                if (animal.getMarcacao() == null) {
                    animal.setMarcacao(Long.valueOf(1));
                    desmarca = false;
                }

                if (animal.getMarcacao() == 0) {
                    animal.setMarcacao(null);
                    spnMotivo.setEnabled(true);
                    txtMarcacaoNao.setBackground(ContextCompat.getDrawable(
                            activity.getApplicationContext(), R.drawable.border));
                    txtMarcacaoNao.setTextColor(Color.BLACK);
                    return;
                }

                if (animal.getMarcacao() == 1) {
                    animal.setMarcacao(Long.valueOf(0));
                    spnMotivo.setEnabled(true);
                    txtMarcacaoNao.setBackground(ContextCompat.getDrawable(
                            activity.getApplicationContext(), R.drawable.border_checked_red));
                    txtMarcacaoNao.setTextColor(Color.WHITE);

                    if (desmarca) {
                        txtMarcacaoSim.setBackground(ContextCompat.getDrawable(
                                activity.getApplicationContext(), R.drawable.border));
                        txtMarcacaoSim.setTextColor(Color.BLACK);
                    }
                }
            }
        });


        txtMochoSim.setTextColor(Color.BLACK);
        txtMochoNao.setTextColor(Color.BLACK);

        if (animal.getMocho() != null) {
            if (animal.getMocho() == Long.valueOf(1)) {
                txtMochoSim.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked));
                txtMochoSim.setTextColor(Color.WHITE);
            } else if (animal.getMocho() == Long.valueOf(0)) {
                txtMochoNao.setBackground(ContextCompat.getDrawable(
                        activity.getApplicationContext(), R.drawable.border_checked_red));
                txtMochoNao.setTextColor(Color.WHITE);
            }

        }

        txtMochoSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!canEdit())
                    return;

                boolean desmarca;
                desmarca = true;
                if (animal.getMocho() == null) {
                    animal.setMocho(Long.valueOf(0));
                    desmarca = false;
                }

                if (animal.getMocho() == 1) {
                    animal.setMocho(null);
                    txtMochoSim.setBackground(ContextCompat.getDrawable(
                            activity.getApplicationContext(), R.drawable.border));
                    txtMochoSim.setTextColor(Color.BLACK);
                    return;
                }

                if (animal.getMocho() == 0) {
                    animal.setMocho(Long.valueOf(1));
                    txtMochoSim.setBackground(ContextCompat.getDrawable(
                            activity.getApplicationContext(), R.drawable.border_checked));
                    txtMochoSim.setTextColor(Color.WHITE);

                    if (desmarca) {
                        txtMochoNao.setBackground(ContextCompat.getDrawable(
                                activity.getApplicationContext(), R.drawable.border));
                        txtMochoNao.setTextColor(Color.BLACK);
                    }
                }

            }
        });


        txtMochoNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!canEdit())
                    return;

                boolean desmarca;
                desmarca = true;
                if (animal.getMocho() == null) {
                    animal.setMocho(Long.valueOf(1));
                    desmarca = false;
                }

                if (animal.getMocho() == 0) {
                    animal.setMocho(null);
                    txtMochoNao.setBackground(ContextCompat.getDrawable(
                            activity.getApplicationContext(), R.drawable.border));
                    txtMochoNao.setTextColor(Color.BLACK);
                    return;
                }

                if (animal.getMocho() == 1) {
                    animal.setMocho(Long.valueOf(0));
                    txtMochoNao.setBackground(ContextCompat.getDrawable(
                            activity.getApplicationContext(), R.drawable.border_checked_red));
                    txtMochoNao.setTextColor(Color.WHITE);

                    if (desmarca) {
                        txtMochoSim.setBackground(ContextCompat.getDrawable(
                                activity.getApplicationContext(), R.drawable.border));
                        txtMochoSim.setTextColor(Color.BLACK);
                    }
                }
            }
        });


        adapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item);
        //final List<String> listaMotivos = new ArrayList<>();
        //listaMotivos.add("SEM MOTIVO");

        final List<MotivoDescarte> motivoDescartes = ((AnimalMainCertificacaoActivity) activity)
                .app.getDaoSession()
                .getMotivoDescarteDao()
                .queryBuilder()
                .orderAsc(MotivoDescarteDao.Properties.Descricao)
                .list();


        for (int i = 0; i < motivoDescartes.size(); i++) {
            adapter.add(motivoDescartes.get(i).getDescricao());
        }


        spnMotivo.setAdapter(adapter, false, onSelectedListener);
        boolean[] selectedItems = new boolean[adapter.getCount()];

        if (animal.getMotivoDescarteAnimais() != null){
            for (int i = 0; i < animal.getMotivoDescarteAnimais().size();i++){
                String motivo = animal.getMotivoDescarteAnimais().get(i).getMotivoDescarte().getDescricao();
                for (int j = 0; j < adapter.getCount(); j++) {
                    if (adapter.getItem(j).equals(motivo)){
                        selectedItems[j] = true;
                    }
                    //spnMotivo.setSelectedIndex(i);
                }
            }
        }


        spnMotivo.setTextSize(16);
        spnMotivo.setPadding(8,16,8,16);

        spnMotivo.setSelected(selectedItems);



        etPeso.addTextChangedListener(new NumberTextWatcherForThousand(etPeso));
        etPeso.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,1)});
        if (animal.getP_marcacao() != null)
            etPeso.setText(String.valueOf(animal.getP_marcacao()));


        etCe.addTextChangedListener(new NumberTextWatcherForThousand(etCe));
        etCe.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3,1)});
        if (animal.getCe_marcacao() != null)
            etCe.setText(String.valueOf(animal.getCe_marcacao()));

        if (!((AnimalMainCertificacaoActivity) activity).isEdit) {
            etPeso.setEnabled(false);
            etCe.setEnabled(false);
            spnMotivo.setEnabled(false);
        }

        if (animal.getSexo().equals("F")){
            txtCaptionCe.setVisibility(View.GONE);
            etCe.setVisibility(View.GONE);
        }


        configBtn();

        btnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AnimalMainCertificacaoActivity) activity).isEdit = true;
                etPeso.setEnabled(true);
                etCe.setEnabled(true);

                if (animal.getMarcacao()!= null){
                    if (animal.getMarcacao() == Long.valueOf(0)){
                        spnMotivo.setEnabled(true);
                    }
                } else {
                    spnMotivo.setEnabled(true);
                }

                configBtn();

            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                        .title("Exclusão de avaliações")
                        .content("Deseja mesmo excluir estas avaliações?")
                        .positiveText("SIM")
                        .negativeText("NÃO")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                clearCertificacao();
                                Toast.makeText(activity.getApplicationContext(), "Os dados foram excluídos com sucesso!", Toast.LENGTH_LONG).show();
                                configBtn();
                            }
                        });

                MaterialDialog dialog = builder.build();
                dialog.show();


            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                        .title("Cancelamento")
                        .content("Deseja cancelar?")
                        .positiveText("SIM")
                        .negativeText("NÃO")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {


                                ((AnimalMainCertificacaoActivity) activity).isEdit = false;
                                Prefs.putBoolean("isEdit", false);
                                configBtn();
                                ((AnimalMainCertificacaoActivity) activity).onSupportNavigateUp();
                            }
                        });

                MaterialDialog dialog = builder.build();
                dialog.show();


            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                avaliado = true;

                boolean temClassificacao;
                boolean temClassificacaoFP;
                boolean temMocho;
                boolean temMarcacao;
                boolean descartado;
                boolean temMotivoDescarte;
                boolean temCe;

                temClassificacao = false;
                if (animal.getClassificacao() != null) {
                    if (animal.getClassificacao() > 0)
                        temClassificacao = true;
                }

                temClassificacaoFP = false;
                if (animal.getClassificacaoFP() != null) {
                    if (animal.getClassificacaoFP().equals("F"))
                        temClassificacaoFP = true;

                    if (animal.getClassificacaoFP().equals("P"))
                        temClassificacaoFP = true;
                }

                if (animal.getMocho() != null) {
                    temMocho = true;
                } else {
                    temMocho = false;
                }

                descartado = false;
                if (animal.getMarcacao() != null) {
                    temMarcacao = true;
                    if (animal.getMarcacao() == Long.valueOf(0)) {
                        descartado = true;
                    }
                } else {
                    temMarcacao = false;
                }

                boolean[] motivos = spnMotivo.getSelected();
                temMotivoDescarte = false;


                //temMotivoDescarte = true;

                //Deleta os que estão
                if (animal.getMotivoDescarteAnimais()!=null){
                    for (int i=0;i<animal.getMotivoDescarteAnimais().size();i++){
                        MotivoDescarteAnimais motivo = animal.getMotivoDescarteAnimais().get(i);
                        ((AnimalMainCertificacaoActivity) activity).app.getDaoSession()
                                .getMotivoDescarteAnimaisDao()
                                .delete(motivo);
                    }
                }

                for (int i=0;i<motivos.length; i++){
                    if(motivos[i]){
                        Long idMotivo = motivoDescartes.get(i).getId();
                        MotivoDescarteAnimais motivoDescarte = new MotivoDescarteAnimais();
                        motivoDescarte.setAnimalId(animal.getId());
                        motivoDescarte.setMotivoId(idMotivo);
                        ((AnimalMainCertificacaoActivity) activity).app.getDaoSession()
                                .getMotivoDescarteAnimaisDao()
                                .insert(motivoDescarte);
                        temMotivoDescarte = true;
                    }
                }


                String cCe = etCe.getText().toString();
                temCe = false;
                if (!cCe.equals("")) {
                    animal.setCe_marcacao(Double.valueOf(cCe));
                    temCe = true;
                }

                String cPCe = etPeso.getText().toString();

                if (!cPCe.equals("")) {
                    animal.setP_marcacao(Double.valueOf(cPCe));
                }

                if (temClassificacaoFP && !temClassificacao) {
                    MaterialDialog dialog = new MaterialDialog.Builder(activity)
                            .title("Mensagem")
                            .content("Não foi possível salvar, escolha uma nota de classificação de 1 a 4!")
                            .positiveText("OK")
                            .show();
                    return;
                }


                if (!(temClassificacao && temMocho)) {
                    MaterialDialog dialog = new MaterialDialog.Builder(activity)
                            .title("Mensagem")
                            .content("Não foi possível salvar, certificação não está completa!")
                            .positiveText("OK")
                            .show();
                    return;
                }

                if ((temMarcacao)) {
                    if  (!(temClassificacao && temMocho)){
                        MaterialDialog dialog = new MaterialDialog.Builder(activity)
                                .title("Mensagem")
                                .content("Não foi possível salvar, certificação não está completa!")
                                .positiveText("OK")
                                .show();
                        return;
                    }
                }

                if (descartado && !temMotivoDescarte) {
                    MaterialDialog dialog = new MaterialDialog.Builder(activity)
                            .title("Mensagem")
                            .content("Não foi possível salvar, necessário informar motivo do Descarte!")
                            .positiveText("OK")
                            .show();
                    return;

                }

                if (descartado && temMotivoDescarte) {
                    avaliado = true;
                }


                if (!descartado && temCe)
                    avaliado = true;

                if (avaliado) {
                    Date date = new Date();
                    String cDate = ((AnimalMainCertificacaoActivity) activity).util.getDateTimeFormatYMD(date);
                    animal.setAvaliado(true);
                    animal.setDataAvaliacao(cDate);
                    ((AnimalMainCertificacaoActivity) activity).app.getDaoSession().update(animal);

                } else {
                    ((AnimalMainCertificacaoActivity) activity).app.getDaoSession().update(animal);
                }

                MaterialDialog dialog = new MaterialDialog.Builder(activity)
                        .title("Mensagem")
                        .content("Os dados foram gravados com sucesso!")
                        .positiveText("Ok")
                        .autoDismiss(true)
                        .show();

                ((AnimalMainCertificacaoActivity) activity).isEdit = false;
                Prefs.putBoolean("isEdit", ((AnimalMainCertificacaoActivity) activity).isEdit);
                configBtn();

                ((AnimalMainCertificacaoActivity) activity).onSupportNavigateUp();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    @Override
    public void onClick(View view, final int position) {
        //Toast.makeText(activity.getApplicationContext(),"Posição "+ String.valueOf(position),Toast.LENGTH_LONG).show();


        return;

    }


    public void resetClassificacao() {


        txtClassificacao1.setBackground(ContextCompat.getDrawable(
                activity.getApplicationContext(), R.drawable.border));
        txtClassificacao1.setTextColor(Color.BLACK);


        txtClassificacao2.setBackground(ContextCompat.getDrawable(
                activity.getApplicationContext(), R.drawable.border));
        txtClassificacao2.setTextColor(Color.BLACK);

        txtClassificacao3.setBackground(ContextCompat.getDrawable(
                activity.getApplicationContext(), R.drawable.border));
        txtClassificacao3.setTextColor(Color.BLACK);

        txtClassificacao4.setBackground(ContextCompat.getDrawable(
                activity.getApplicationContext(), R.drawable.border));
        txtClassificacao4.setTextColor(Color.BLACK);

        return;
    }

    public void resetClassificacaoFP() {

        //Se for diferente de F ou P, limpa

        txtClassificacaoF.setBackground(ContextCompat.getDrawable(
                activity.getApplicationContext(), R.drawable.border));
        txtClassificacaoF.setTextColor(Color.BLACK);

        txtClassificacaoP.setBackground(ContextCompat.getDrawable(
                activity.getApplicationContext(), R.drawable.border));
        txtClassificacaoP.setTextColor(Color.BLACK);
        return;
    }


    public void configBtn() {
        if (((AnimalMainCertificacaoActivity) activity).isEdit) {
            btnSalvar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnExcluir.setEnabled(true);
            btnAvaliar.setEnabled(false);

        } else {
            btnSalvar.setEnabled(false);
            btnCancelar.setEnabled(false);
            if (animal.getAvaliado())
                btnExcluir.setEnabled(true);
            else
                btnExcluir.setEnabled(false);

            btnAvaliar.setEnabled(true);
        }
    }

    public boolean canEdit() {

        if (!((AnimalMainCertificacaoActivity) activity).isEdit) {
            MaterialDialog dialog = new MaterialDialog.Builder(activity)
                    .title("Mensagem")
                    .content("Para editar os dados é preciso clicar no botão Alterar.")
                    .positiveText("Ok")
                    .show();
            return false;
        } else {
            return true;
        }
    }

    public void clearCertificacao() {

        animal.setClassificacao(null);
        resetClassificacao();
        resetClassificacaoFP();
        animal.setMarcacao(null);

        txtMarcacaoSim.setBackground(ContextCompat.getDrawable(
                activity.getApplicationContext(), R.drawable.border));
        txtMarcacaoSim.setTextColor(Color.BLACK);

        txtMarcacaoNao.setBackground(ContextCompat.getDrawable(
                activity.getApplicationContext(), R.drawable.border));
        txtMarcacaoNao.setTextColor(Color.BLACK);

        animal.setMocho(null);
        txtMochoSim.setBackground(ContextCompat.getDrawable(
                activity.getApplicationContext(), R.drawable.border));
        txtMochoSim.setTextColor(Color.BLACK);
        txtMochoNao.setBackground(ContextCompat.getDrawable(
                activity.getApplicationContext(), R.drawable.border));
        txtMochoNao.setTextColor(Color.BLACK);

        animal.setP_marcacao(null);
        etPeso.setText("");

        animal.setCe_marcacao(null);
        etCe.setText("");
        //Deleta os que estão
        animal.setMotDescId(null);
        if (animal.getMotivoDescarteAnimais()!=null){
            for (int i=0;i<animal.getMotivoDescarteAnimais().size();i++){
                MotivoDescarteAnimais motivo = animal.getMotivoDescarteAnimais().get(i);
                ((AnimalMainCertificacaoActivity) activity).app.getDaoSession()
                        .getMotivoDescarteAnimaisDao()
                        .delete(motivo);
            }
        }

        animal.setAvaliado(false);
        animal.setDataAvaliacao(null);

        ((AnimalMainCertificacaoActivity) activity).app.getDaoSession()
                .getMTFDadosDao()
                .update(animal);

    }



    private MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {

        public void onItemsSelected(boolean[] selected) {
            // Do something here with the selected items

            StringBuilder builder = new StringBuilder();
            boolean temMotivo;
            temMotivo = false;
            for (int i = 0; i < selected.length; i++) {

                if (selected[i]) {
                    temMotivo = true;
                    builder.append(adapter.getItem(i)).append(" ");
                }
            }
            if (temMotivo){
                animal.setMarcacao(null);
                txtMarcacaoNao.callOnClick();
            }
            //Toast.makeText(MainActivity.this, builder.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    public class DecimalDigitsInputFilter implements InputFilter {
        private int mDigitsBeforeZero;
        private int mDigitsAfterZero;
        private Pattern mPattern;

        private static final int DIGITS_BEFORE_ZERO_DEFAULT = 100;
        private static final int DIGITS_AFTER_ZERO_DEFAULT = 100;

        public DecimalDigitsInputFilter(Integer digitsBeforeZero, Integer digitsAfterZero) {
            this.mDigitsBeforeZero = (digitsBeforeZero != null ? digitsBeforeZero : DIGITS_BEFORE_ZERO_DEFAULT);
            this.mDigitsAfterZero = (digitsAfterZero != null ? digitsAfterZero : DIGITS_AFTER_ZERO_DEFAULT);
            mPattern = Pattern.compile("-?[0-9]{0," + (mDigitsBeforeZero) + "}+((\\.[0-9]{0," + (mDigitsAfterZero)
                    + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String replacement = source.subSequence(start, end).toString();
            String newVal = dest.subSequence(0, dstart).toString() + replacement
                    + dest.subSequence(dend, dest.length()).toString();
            Matcher matcher = mPattern.matcher(newVal);
            if (matcher.matches())
                return null;

            if (TextUtils.isEmpty(source))
                return dest.subSequence(dstart, dend);
            else
                return "";
        }
    }

}
