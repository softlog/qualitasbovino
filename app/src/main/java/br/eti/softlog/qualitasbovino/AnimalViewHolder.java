package br.eti.softlog.qualitasbovino;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.omega_r.libs.OmegaCenterIconButton;

public class AnimalViewHolder extends RecyclerView.ViewHolder {

    public TextView txtFazenda;
    public TextView txtIdAnimal;
    public TextView txtDataNasc;
    public TextView txtAvaliado;
    public TextView txtSexo;
    public AppCompatButton btn;
    public ImageButton btnInverter;

    public AnimalViewHolder(View itemView) {
        super(itemView);
        this.txtFazenda = itemView.findViewById(R.id.txt_fazenda);
        this.txtIdAnimal = itemView.findViewById(R.id.txt_id_animal);
        this.txtDataNasc = itemView.findViewById(R.id.txt_dt_nasc);
        this.txtAvaliado = itemView.findViewById(R.id.txt_avaliado);
        this.txtSexo = itemView.findViewById(R.id.txt_sexo);
        this.btn = itemView.findViewById(R.id.btn_acesso);
        this.btnInverter = itemView.findViewById(R.id.btnInverter);

    }
}
