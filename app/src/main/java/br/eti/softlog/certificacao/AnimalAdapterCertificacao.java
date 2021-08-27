package br.eti.softlog.certificacao;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MedicoesAnimal;
import br.eti.softlog.model.MedicoesAnimalDao;
import br.eti.softlog.qualitasbovino.AnimalMainActivity;

import br.eti.softlog.qualitasbovino.AnimalViewHolder;
import br.eti.softlog.qualitasbovino.AppMain;
import br.eti.softlog.qualitasbovino.R;

public class AnimalAdapterCertificacao extends RecyclerView.Adapter<AnimalCertificacaoViewHolder> {
    public List<MTFDados> animais;
    public AppMain app;
    public Context mContext;
    public AnimalViewHolder mHolder;
    public Util util = new Util();

    public AnimalAdapterCertificacao(List<MTFDados> animais, Context context, AppMain app) {
        this.animais = animais;
        this.mContext = context;
        this.app = app;
    }

    @Override
    public AnimalCertificacaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnimalCertificacaoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_animal_certificacao, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalCertificacaoViewHolder holder, int position) {

        final MTFDados animal = (MTFDados) this.animais.get(position);
        holder.txtAvaliado.setText(animal.getAvaliadoDesc());
        holder.txtIdAnimal.setText(util.trataIdf(String.valueOf(animal.getAnimalPrincipal().getIdf())));
        TextView textView = holder.txtDataNasc;

        Util util = this.util;
        textView.setText(Util.getDateFormatDMY(animal.getDataNasc()));
        holder.txtSexo.setText(animal.getSexoDesc());

        if (animal.getMarcacao() == null){
            holder.txtMarcacao.setText("NAO");
        } else {
            if (animal.getMarcacao()==1)
                holder.txtMarcacao.setText("SIM");
            else
                holder.txtMarcacao.setText("NAO");
        }

        holder.txtCeip.setText(animal.getCeip().trim());

        if (!animal.getCeip().equals("P")){
            holder.imgPremium.setVisibility(View.INVISIBLE);
            holder.imgPremium.setVisibility(View.GONE);
        } else {
            holder.imgPremium.setVisibility(View.VISIBLE);
        }

        if (animal.getIdMae()==1){
            holder.imgQsp.setVisibility(View.VISIBLE);
        } else {
            holder.imgQsp.setVisibility(View.INVISIBLE);
            holder.imgQsp.setVisibility(View.GONE);
        }


        if (position==14){
            //Log.d("Animal Recycler", animal.getAnimal().toString());
            //Log.d("Animal Posição", String.valueOf(position));
        }


        holder.btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent in = new Intent(AnimalAdapterCertificacao.this.mContext, AnimalMainCertificacaoActivity.class);
                in.putExtra("id_animal", animal.getAnimal());
                ActivityUtils.startActivity(in);
            }
        });


    }

    public int getItemCount() {
        return this.animais.size();
    }
}