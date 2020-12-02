package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.starsnow.R;

public class FragmentOACICode extends Fragment {
    public FragmentOACICode(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_oaci_code, container, false);
        TextView txt = (TextView) view.findViewById(R.id.section_label);
        txt.setText("Chargement du snowtam");
        return view;
    }
}
