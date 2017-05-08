package co.org.ceindetec.physilab.app.Activities.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.org.ceindetec.physilab.R;

/**
 * Created by ceidentec04 on 09/06/2016.
 */
public class MovimientoCircularFragment extends Fragment {

    public MovimientoCircularFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movimiento_circular, container, false);
    }
}
