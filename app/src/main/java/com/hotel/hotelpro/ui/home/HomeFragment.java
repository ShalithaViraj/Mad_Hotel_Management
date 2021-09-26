package com.hotel.hotelpro.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hotel.hotelpro.Hotel;
import com.hotel.hotelpro.HotelAdapter;
import com.hotel.hotelpro.HotelResult;
import com.hotel.hotelpro.R;
import com.hotel.hotelpro.databinding.FragmentHomeBinding;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    HotelResult hotelResult;
    RecyclerView recyclerView;
    int lastPos=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
        recyclerView = binding.hotelList;

//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    public String getHotels(){
        SharedPreferences sp=getActivity().getSharedPreferences("hotel", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        if(sp.contains("data")){

            return sp.getString("data",null);
        }
        else{
            return null;
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        HotelAdapter hotelAdapter=new HotelAdapter(getContext());
        Gson gson=new Gson();

        if(getHotels()==null) {

            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(getContext().getAssets().open("hotels.json")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            hotelResult = gson.fromJson(br, HotelResult.class);


        }
        else {
            hotelResult=gson.fromJson(getHotels(),HotelResult.class);
        }

        hotelAdapter.setHotels(hotelResult.getHotels());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(hotelAdapter);

        recyclerView.getLayoutManager().scrollToPosition(lastPos);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}