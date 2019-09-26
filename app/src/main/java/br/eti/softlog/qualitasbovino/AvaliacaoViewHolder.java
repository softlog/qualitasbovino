package br.eti.softlog.qualitasbovino;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import br.eti.softlog.model.MTFDados;

public class AvaliacaoViewHolder extends RecyclerView.ViewHolder  {

    public TextView txtMedida;
    public TextView txtValor;
    public TextView txtStatus;
    public AppCompatButton btn;


    public AvaliacaoViewHolder(View itemView) {
        super(itemView);
        this.txtMedida = itemView.findViewById(R.id.txtMedida);
        this.txtValor = itemView.findViewById(R.id.txtValor);
        this.txtStatus = itemView.findViewById(R.id.txt_status);

    }


}
