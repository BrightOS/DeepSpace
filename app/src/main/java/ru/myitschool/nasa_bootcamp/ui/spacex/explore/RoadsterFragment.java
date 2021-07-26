package ru.myitschool.nasa_bootcamp.ui.spacex.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.myitschool.nasa_bootcamp.R;


public class RoadsterFragment extends Fragment {


    public RoadsterFragment() {
     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_roadster, container, false);
    }
}