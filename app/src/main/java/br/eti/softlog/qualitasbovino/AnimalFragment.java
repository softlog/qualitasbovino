package br.eti.softlog.qualitasbovino;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Formatter;
import java.util.Locale;

import br.eti.softlog.Utils.Util;
import br.eti.softlog.model.MTFDados;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AnimalFragment extends Fragment {

    MTFDados animal;
    public Activity activity;
    private Unbinder unbinder;
    private Util util;
    private Formatter formatter;

    @BindView(R.id.ra_nasc)
    TextView txtRaNasc;

    @BindView(R.id.r_p_nasc)
    TextView txtRpNasc;

    @BindView(R.id.ra_desm)
    TextView txtRaDesm;

    @BindView(R.id.i_p_desm)
    TextView txtIPDesm;

    @BindView(R.id.d_p_desm)
    TextView txtDPDesm;

    @BindView(R.id.r_p_desm)
    TextView txtRPDesm;

    @BindView(R.id.ra_365)
    TextView txtRa365;

    @BindView(R.id.i_p_365)
    TextView txtIP365;

    @BindView(R.id.d_p_365)
    TextView txtDP365;

    @BindView(R.id.r_p_365)
    TextView txtRP365;

    @BindView(R.id.ra_450)
    TextView txtRa450;

    @BindView(R.id.i_p_450)
    TextView txtIP450;

    @BindView(R.id.d_p_450)
    TextView txtDP450;

    @BindView(R.id.r_p_450)
    TextView txtRP450;

    @BindView(R.id.ra_550)
    TextView txtRa550;

    @BindView(R.id.i_p_550)
    TextView txtIP550;

    @BindView(R.id.d_p_550)
    TextView txtDP550;

    @BindView(R.id.r_p_550)
    TextView txtRP550;

    @BindView(R.id.i_ce)
    TextView txtICe;

    @BindView(R.id.r_ce)
    TextView txtRCe;

    @BindView(R.id.i_mus)
    TextView txtIMus;

    @BindView(R.id.r_mus)
    TextView txtRMus;

    public AnimalFragment() {



        // Required empty public constructor
    }

    public static AnimalFragment newInstance() {
        AnimalFragment fragment = new AnimalFragment();
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
        View view = inflater.inflate(R.layout.fragment_animal, container, false);
        unbinder = ButterKnife.bind(this,view);

        animal = ((AnimalMainActivity) activity).getAnimal();

        //Se o Animal for Novo, não contém informações extras
        if (animal.getImportado() == 0) {
            return view;
        }

        util = new Util();

        Locale ptBr = new Locale("pt", "BR");
        formatter = new Formatter(ptBr);

        txtRaNasc.setText(animal.getRaNasc().toString());
        txtRpNasc.setText(animal.getRPNasc().toString());

        txtRaDesm.setText(animal.getRaDesm().toString());
        txtIPDesm.setText(animal.getIPDesm().toString());
        txtDPDesm.setText(util.getDateFormatDMY(animal.getDPDesm().toString(),"dd/MM/yy"));
        txtRPDesm.setText(animal.getRPDesm().toString());


        txtRa365.setText(animal.getRa365().toString());
        txtIP365.setText(animal.getIP365().toString());
        txtDP365.setText(util.getDateFormatDMY(animal.getDP365().toString(),"dd/MM/yy"));
        txtRP365.setText(animal.getRP365().toString());

        txtRa450.setText(animal.getRa450().toString());
        txtIP450.setText(animal.getIP450().toString());
        txtDP450.setText(util.getDateFormatDMY(animal.getDP450().toString(),"dd/MM/yy"));
        txtRP450.setText(animal.getRP450().toString());

        txtRa550.setText(animal.getRa550().toString());
        txtIP550.setText(animal.getIP550().toString());
        txtDP550.setText(util.getDateFormatDMY(animal.getDP550().toString(),"dd/MM/yy"));
        txtRP550.setText(animal.getRP550().toString());

        txtICe.setText(animal.getICe().toString());
        txtRCe.setText(animal.getRCe().toString());

        txtIMus.setText(animal.getIMus().toString());
        txtRMus.setText(animal.getRMus().toString());

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }
}
