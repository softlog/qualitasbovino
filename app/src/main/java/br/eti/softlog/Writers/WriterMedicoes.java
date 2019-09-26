package br.eti.softlog.Writers;

import android.content.Context;

import br.eti.softlog.model.MTFDados;
import br.eti.softlog.model.MedicoesAnimal;
import br.eti.softlog.qualitasbovino.AppMain;

public class WriterMedicoes {

    Context mContext;
    AppMain app;
    MedicoesAnimal medicoes;

    public WriterMedicoes(Context context){
        mContext = context;
        app = (AppMain)mContext.getApplicationContext();

    }

    public void writer(String filename){



    }


}
