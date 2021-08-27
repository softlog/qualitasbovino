package br.eti.softlog.qualitasbovino;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MedicoesAnimal;
import br.eti.softlog.model.MedicoesAnimalDao.Properties;
import com.blankj.utilcode.util.ActivityUtils;
import java.util.List;
import org.greenrobot.greendao.query.WhereCondition;


public class AnimalAdapter extends Adapter<AnimalViewHolder> {
    public List<MTFDados> animais;
    public AppMain app;
    public Context mContext;
    public AnimalViewHolder mHolder;
    public Util util = new Util();

    public AnimalAdapter(List<MTFDados> animais, Context context, AppMain app) {
        this.animais = animais;
        this.mContext = context;
        this.app = app;
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnimalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_animal, parent, false));
    }

    public void onBindViewHolder(final AnimalViewHolder holder, int position) {

        final MTFDados animal = (MTFDados) this.animais.get(position);
        holder.txtAvaliado.setText(animal.getAvaliadoDesc());
        holder.txtIdAnimal.setText(util.trataIdf(String.valueOf(animal.getAnimalPrincipal().getIdf())));
        TextView textView = holder.txtDataNasc;
        Util util = this.util;
        textView.setText(Util.getDateFormatDMY(animal.getDataNasc()));
        holder.txtSexo.setText(animal.getSexoDesc());
        holder.txtFazenda.setText(animal.getCriador().getCodigo().toString());

        if (position==14){
            //Log.d("Animal Recycler", animal.getAnimal().toString());
            //Log.d("Animal Posição", String.valueOf(position));
        }

        if (animal.getAvaliado()) {
            holder.btnInverter.setImageResource(R.drawable.ic_bloqueado);
            holder.btnInverter.setEnabled(false);
        } else {
            if (animal.getAlterado() == 0){
                holder.btnInverter.setImageResource(R.drawable.ic_inverter);
                holder.btnInverter.setEnabled(true);
            } else {
                holder.btnInverter.setImageResource(R.drawable.ic_cancelar);
                holder.btnInverter.setEnabled(true);
            }
        }

        holder.btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent in = new Intent(AnimalAdapter.this.mContext, AnimalMainActivity.class);
                in.putExtra("id_animal", animal.getAnimal());
                ActivityUtils.startActivity(in);
            }
        });

        holder.btnInverter.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!animal.getAvaliado().booleanValue()) {
                    if (animal.getSexo().equals("F")) {
                        animal.setSexo("M");
                    } else {
                        animal.setSexo("F");
                    }

                    if (animal.getAlterado() == 1){
                        animal.setAlterado(0);
                        holder.btnInverter.setImageResource(R.drawable.ic_inverter);
                    } else {
                        animal.setAlterado(1);
                        holder.btnInverter.setImageResource(R.drawable.ic_cancelar);
                    }
                    holder.txtSexo.setText(animal.getSexoDesc());
                    int i = 0;
                    List<MedicoesAnimal> medicoesAnimal = AnimalAdapter.this.app.getDaoSession()
                            .getMedicoesAnimalDao().queryBuilder()
                            .where(Properties.Animal.eq(animal.getAnimal()), new WhereCondition[0])
                            .list();

                    AnimalAdapter.this.app.getDaoSession().update(animal);

                    for (MedicoesAnimal medicao:medicoesAnimal){
                        AnimalAdapter.this.app.getDaoSession().delete(medicao);
                    }
                    Toast.makeText(AnimalAdapter.this.mContext, "Sexo do animal foi alterado.",
                            Toast.LENGTH_LONG).show();
                    return ;
                }
            }
        });
    }

    public int getItemCount() {
        return this.animais.size();
    }
}