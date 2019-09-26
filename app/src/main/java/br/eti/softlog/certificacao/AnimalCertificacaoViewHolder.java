package br.eti.softlog.certificacao;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.eti.softlog.qualitasbovino.R;

public class AnimalCertificacaoViewHolder extends RecyclerView.ViewHolder {

public TextView txtFazenda;
public TextView txtIdAnimal;
public TextView txtDataNasc;
public TextView txtAvaliado;
public TextView txtSexo;
public AppCompatButton btn;
public TextView txtCeip;
public ImageView imgPremium;

public AnimalCertificacaoViewHolder(View itemView) {
        super(itemView);
        this.txtFazenda = itemView.findViewById(R.id.txt_fazenda);
        this.txtIdAnimal = itemView.findViewById(R.id.txt_id_animal);
        this.txtDataNasc = itemView.findViewById(R.id.txt_dt_nasc);
        this.txtAvaliado = itemView.findViewById(R.id.txt_avaliado);
        this.txtSexo = itemView.findViewById(R.id.txt_sexo);
        this.btn = itemView.findViewById(R.id.btn_acesso);
        this.txtCeip = itemView.findViewById(R.id.txt_classificacao);
        this.imgPremium = itemView.findViewById(R.id.imgPremium);

        }
}
