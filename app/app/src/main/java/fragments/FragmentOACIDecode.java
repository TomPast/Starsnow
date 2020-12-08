package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starsnow.R;

public class FragmentOACIDecode extends Fragment {
    public FragmentOACIDecode(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oaci_decode, container, false);
        RecyclerView txt = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }
}
