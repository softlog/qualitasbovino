package br.eti.softlog.qualitasbovino;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MedicoesAnimal;

public class AvaliacaoAdapter extends RecyclerView.Adapter<AvaliacaoAdapter.AvaliacaoViewHolder>  {

    public List<MedicoesAnimal> medicoesAnimais;
    MedicoesAnimal medicaoAnimal;
    public Context mContext;
    private AlertDialog alertDialog;
    public ItemClickListener clickListener;

    public AlertDialog alertAvaliacao;



    public AvaliacaoAdapter(List<MedicoesAnimal> medicoesAnimais, Context context) {
        this.medicoesAnimais = medicoesAnimais;
        this.mContext = context;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @NonNull
    @Override
    public AvaliacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_avaliacao,parent,false);
        return new AvaliacaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvaliacaoViewHolder holder, final int position) {

        String medida;

        medicaoAnimal = medicoesAnimais.get(position);

        medida = String.valueOf(position+1) + " - " +
                medicaoAnimal.getMedicoes().getDescricao() ;

        holder.txtMedida.setText(medida);


        if (medicoesAnimais.get(position).getValor() != null){
            holder.txtValor.setText(String.valueOf(medicoesAnimais.get(position).getValor()));
        } else {
            holder.txtValor.setText("");
        }




        final String[] notas;

        Long valor;
        int positionSpinner;
        int maiorValor, menorValor;
        int restricao;
        int descarte1;
        int descarte2;
        int descarte3;
        int descarte4;

        valor = medicaoAnimal.getValor();

        maiorValor = medicaoAnimal.getMedicoes().getMaiorValor();
        menorValor = medicaoAnimal.getMedicoes().getMenorValor();
        restricao = medicaoAnimal.getMedicoes().getRestricao();
        descarte1 = medicaoAnimal.getMedicoes().getDescarte1();
        descarte2 = medicaoAnimal.getMedicoes().getDescarte2();
        descarte3 = medicaoAnimal.getMedicoes().getDescarte3();
        descarte4 = medicaoAnimal.getMedicoes().getDescarte4();

        final List<String> listaNotas = new ArrayList<>();
        listaNotas.add("");
        for(int i = menorValor;i<=maiorValor;i++){
            listaNotas.add(String.valueOf(i));
        }

        if (valor == null){
            holder.txtStatus.setBackgroundColor(Color.YELLOW);
        } else {
            holder.txtStatus.setBackgroundColor(Color.GREEN);
            if (medicaoAnimal.getDescarte()){
                holder.txtStatus.setBackgroundColor(Color.RED);
            }

        }

    }

    @Override
    public int getItemCount() {
        return medicoesAnimais.size();
    }

    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClick();
    }

    public void salvaMedicao(String valor, int position){

        MedicoesAnimal medicaoAnimal2 = medicoesAnimais.get(position);
        //Log.d("Posição",String.valueOf(position));
        //Log.d("Valor a ser gravado", valor);
        Long nValor;
        if (valor.equals("")){
            if (medicaoAnimal2.getValor() == null) {
                return ;
            } else {
                medicaoAnimal2.setValor(null);
            }
        } else {
            nValor = Long.valueOf(valor);
            if (medicaoAnimal2.getValor() == null){
                medicaoAnimal2.setValor(nValor);
            } else {
                if (medicaoAnimal2.getValor().equals(nValor)) {
                    return;
                } else {
                    medicaoAnimal2.setValor(nValor);
                }
            }
        }

        final AppMain myApp = (AppMain) mContext.getApplicationContext();
        myApp.getDaoSession().update(medicaoAnimal2);
        //Log.d("Medida",medicaoAnimal2.getMedicoes().getDescricao());
        //Log.d("Gravando Valor",String.valueOf(medicaoAnimal2.getValor()));

    }


    public class AvaliacaoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtMedida;
        public TextView txtValor;
        public TextView txtStatus;
        public AppCompatButton btn;



        public AvaliacaoViewHolder(View itemView) {
            super(itemView);
            this.txtMedida = itemView.findViewById(R.id.txtMedida);
            this.txtValor = itemView.findViewById(R.id.txtValor);
            this.txtStatus = itemView.findViewById(R.id.txt_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }



}
