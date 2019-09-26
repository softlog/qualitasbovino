package br.eti.softlog.qualitasbovino;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ToastUtils;
import com.omega_r.libs.OmegaCenterIconButton;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MedicoesAnimal;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AvaliacaoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AvaliacaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AvaliacaoFragment extends Fragment implements ItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private RecyclerView recyclerView;
    AvaliacaoAdapter avaliacaoAdapter;
    public MTFDados animal;
    public List<MedicoesAnimal> medicoesAnimal;
    public ArrayAdapter<String> adapter;

    public OmegaCenterIconButton btnSalvar;
    public OmegaCenterIconButton btnAvaliar;
    public OmegaCenterIconButton btnExcluir;
    public OmegaCenterIconButton btnCancelar;

    public int positionDescarte;
    public boolean avaliado;

    public Activity activity;
    //private OnFragmentInteractionListener mListener;

    public AvaliacaoFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AvaliacaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvaliacaoFragment newInstance() {
        AvaliacaoFragment fragment = new AvaliacaoFragment();
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
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_avaliacao, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));

        animal = ((AnimalMainActivity) activity).getAnimal();

        ((AnimalMainActivity) activity).getManager().addAllMedicaoAnimal(animal.getAnimal());
        medicoesAnimal = animal.getMedicoesAnimals();

        int cont;
        cont = 0;
        for (MedicoesAnimal m : medicoesAnimal){
            if (m.getMedicaoId()==141529)
                positionDescarte = cont;

            cont++;
        }
        avaliacaoAdapter = new AvaliacaoAdapter(medicoesAnimal,
                ((AnimalMainActivity) activity).getApplicationContext());
        //recyclerView.addItemDecoration(new EqualSpacingItemDecoration(4, EqualSpacingItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(activity, DividerItemDecoration.HORIZONTAL));

        recyclerView.setAdapter(avaliacaoAdapter);
        avaliacaoAdapter.setClickListener(this);

        //((AnimalMainActivity) activity).isEdit = Prefs.getBoolean("isEdit", false);
        btnAvaliar = view.findViewById(R.id.btn_avaliar);
        btnExcluir = view.findViewById(R.id.btn_excluir);
        btnSalvar = view.findViewById(R.id.btn_salvar);
        btnCancelar = view.findViewById(R.id.btn_cancelar);

        //Se animal não está avaliado, abrir para avaliação
        if (!animal.getAvaliado()){
            ((AnimalMainActivity) activity).isEdit = true;
            Prefs.putBoolean("isEdit", ((AnimalMainActivity) activity).isEdit);
        } else {
            ((AnimalMainActivity) activity).isEdit = false;
            Prefs.putBoolean("isEdit", ((AnimalMainActivity) activity).isEdit);
        }

        configBtn();

        btnAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AnimalMainActivity) activity).isEdit = true;

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
                                for (int i = 0; i < medicoesAnimal.size(); i++) {
                                    medicoesAnimal.get(i).setValor(null);
                                    medicoesAnimal.get(i).setDataMedicao(null);
                                    medicoesAnimal.get(i).setDescarte(false);
                                    ((AnimalMainActivity) activity).app.getDaoSession().update(medicoesAnimal.get(i));
                                }
                                animal.setAvaliado(false);
                                animal.setDataAvaliacao(null);

                                ((AnimalMainActivity) activity).app.getDaoSession().update(animal);
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

                                ((AnimalMainActivity) activity).isEdit = false;
                                Prefs.putBoolean("isEdit", false);
                                configBtn();
                                ((AnimalMainActivity) activity).onSupportNavigateUp();
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
                Boolean vendaAvaliado;
                vendaAvaliado = false;

                int qtAvaliado;
                qtAvaliado = 0;

                for (int i = 0; i < medicoesAnimal.size(); i++) {
                    if (medicoesAnimal.get(i).getValor() == null
                            && medicoesAnimal.get(i).getMedicoes().getObrigatorio()) {
                        avaliado = false;
                    }
                    if (!(medicoesAnimal.get(i).getValor()== null)){
                        qtAvaliado++;
                        if (medicoesAnimal.get(i).getMedicaoId() == 321524)
                            vendaAvaliado = true;
                    }
                }

                //Log.d("Qt Avaliado ",String.valueOf(qtAvaliado));
                if (!avaliado)
                    if (qtAvaliado==2 && vendaAvaliado)
                        avaliado = true;

                //Log.d("Medicao ",String.valueOf(medicoesAnimal.get(1).getMedicaoId()));
                //Log.d("Quantidade ",String.valueOf(medicoesAnimal.size()));

                if (!avaliado) {
                    MaterialDialog dialog = new MaterialDialog.Builder(activity)
                            .title("Mensagem")
                            .content("Não foi possível salvar, avaliação não está completa!")
                            .positiveText("OK")
                            .show();

                } else {
                    for (int i = 0; i < medicoesAnimal.size(); i++) {
                        ((AnimalMainActivity) activity).app.getDaoSession().update(medicoesAnimal.get(i));
                    }

                    if (avaliado) {
                        Date date = new Date();
                        String cDate = ((AnimalMainActivity) activity).util.getDateTimeFormatYMD(date);
                        animal.setAvaliado(true);
                        animal.setDataAvaliacao(cDate);
                        ((AnimalMainActivity) activity).app.getDaoSession().update(animal);

                    }

                    ((AnimalMainActivity) activity).isEdit = false;
                    Prefs.putBoolean("isEdit", false);

                    MaterialDialog dialog = new MaterialDialog.Builder(activity)
                            .title("Mensagem")
                            .content("Os dados foram gravados com sucesso!")
                            .positiveText("Ok")
                            .autoDismiss(true)
                            .show();

                    ((AnimalMainActivity) activity).isEdit = false;
                    Prefs.putBoolean("isEdit", ((AnimalMainActivity) activity).isEdit);
                    configBtn();

                    ((AnimalMainActivity) activity).onSupportNavigateUp();
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view, final int position) {
        //Toast.makeText(activity.getApplicationContext(),"Posição "+ String.valueOf(position),Toast.LENGTH_LONG).show();


        if (!((AnimalMainActivity) activity).isEdit) {
            MaterialDialog dialog = new MaterialDialog.Builder(activity)
                    .title("Mensagem")
                    .content("Para editar os dados é preciso clicar no botão Alterar.")
                    .positiveText("Ok")
                    .show();
            return;
        }

        final TextView txtStatus;
        final TextView txtValor;

        txtStatus = view.findViewById(R.id.txt_status);
        txtValor = view.findViewById(R.id.txtValor);

        Long valor;
        int positionSpinner;
        final int maiorValor, menorValor;
        int restricao;
        int descarte1;
        int descarte2;
        int descarte3;
        int descarte4;


        final MedicoesAnimal medicaoAnimal = avaliacaoAdapter.medicoesAnimais.get(position);

        if (medicaoAnimal.getMedicaoId() == 141529) {
            MaterialDialog dialog = new MaterialDialog.Builder(activity)
                    .title("Mensagem")
                    .content("Descarte não pode ser alterado manualmente.")
                    .positiveText("Ok")
                    .show();
            return;
        }

        maiorValor = medicaoAnimal.getMedicoes().getMaiorValor();
        menorValor = medicaoAnimal.getMedicoes().getMenorValor();
        restricao = medicaoAnimal.getMedicoes().getRestricao();
        descarte1 = medicaoAnimal.getMedicoes().getDescarte1();
        descarte2 = medicaoAnimal.getMedicoes().getDescarte2();
        descarte3 = medicaoAnimal.getMedicoes().getDescarte3();
        descarte4 = medicaoAnimal.getMedicoes().getDescarte4();

        final int posisao = position;

        final List<String> listaNotas = new ArrayList<>();
        if (maiorValor == 1) {
            listaNotas.add("Excluir");
            listaNotas.add("1");
        } else {
            for (int i = menorValor; i <= maiorValor; i++) {
                listaNotas.add(String.valueOf(i));
            }
        }

        if (descarte1 == 9 || descarte2 == 9 || descarte3 == 9 || descarte4 == 9) {
            listaNotas.add("9");
        }
        adapter = new ArrayAdapter<String>(activity,
                R.layout.simple_list, listaNotas);
//        int valorAux;
//
//        if (menorValor==0){
//            valorAux = 1;
//        } else {
//            valorAux = 0;
//        }


        DialogPlus dialog = DialogPlus.newDialog(activity)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, final int position2) {
                        //Toast.makeText(activity.getApplicationContext(),"Posição "+ String.valueOf(position),Toast.LENGTH_LONG).show();

                        if (position2 == 0 && maiorValor == 1) {
                            medicaoAnimal.setValor(null);
                            animal.setDataAvaliacao(null);
                            animal.validDescarte();
                            //txtValor.setText("");
                            //txtStatus.setBackgroundColor(Color.YELLOW);
                        } else {

                            medicaoAnimal.setValor(Long.valueOf(listaNotas.get(position2)));
                            Date date = new Date();
                            String cDate = ((AnimalMainActivity) activity).util.getDateTimeFormatYMD(date);
                            medicaoAnimal.setDataMedicao(cDate);

                            //txtValor.setText(listaNotas.get(position2));
                            animal.validDescarte();
                        }
                            //txtStatus.setBackgroundColor(Color.BLUE);
                        //}
                        avaliacaoAdapter.notifyItemChanged(position);
                        avaliacaoAdapter.notifyItemChanged(positionDescarte);
                        //avaliacaoAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setExpanded(false)
                .setOverlayBackgroundResource(R.color.dialogplus_black_overlay)
                .setContentBackgroundResource(R.color.colorWhite)
                .setPadding(8, 8, 8, 8)
                .setContentHolder(new GridHolder(3))
                .setGravity(Gravity.CENTER)
                .create();
        dialog.show();

    }


    public void configBtn() {
        if (((AnimalMainActivity) activity).isEdit) {
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

}
