package com.hotel.hotelpro.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hotel.hotelpro.Hotel;
import com.hotel.hotelpro.HotelInfo;
import com.hotel.hotelpro.HotelResult;
import com.hotel.hotelpro.R;
import com.hotel.hotelpro.RecommendationAdapter;
import com.hotel.hotelpro.UserHotel;
import com.hotel.hotelpro.databinding.FragmentDashboardBinding;
import com.google.gson.Gson;
import com.hotel.hotelpro.ui.notifications.NotificationsFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class DashboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static Set<String> tagSet = new HashSet<>();
    RecommendationAdapter recommendationAdapter;
    TextView placeholderEmpty;
    Button payButton;


    private HotelResult hotelResult;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Recommendation.
     */
    // TODO: Rename and change types and number of parameters
    public static com.hotel.hotelpro.Recommendation newInstance(String param1, String param2) {
        com.hotel.hotelpro.Recommendation fragment = new com.hotel.hotelpro.Recommendation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recommendation, container, false);

        placeholderEmpty=v.findViewById(R.id.emptyRecommendationPlaceHolder);
        RecyclerView recyclerView = v.findViewById(R.id.hotelList);

        this.recommendationAdapter = new RecommendationAdapter(getContext());


        recommendationAdapter.setHotels(getHotelWithTags());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recommendationAdapter);

        payButton = v.findViewById(R.id.payNow);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(DashboardFragment.this.getContext(), NotificationsFragment.class);
//                DashboardFragment.this.getContext().startActivity(i);
            }
        });


        return v;


    }

    @Override
    public void onResume() {
        super.onResume();

        if(recommendationAdapter.getItemCount()==0){
            placeholderEmpty.setVisibility(View.VISIBLE);
        }
        else{
            placeholderEmpty.setVisibility(View.INVISIBLE);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("activityresult", "onActivityResult: " + resultCode);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        String sa = "";

        for (String sss : com.hotel.hotelpro.Recommendation.tagSet) {
            sa += sss;
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public Set<Hotel> getHotelWithTags() {

        Gson gson = new Gson();

        if (getHotels() == null) {


            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("hotels.json")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            hotelResult = gson.fromJson(br, HotelResult.class);


        } else {
            hotelResult = gson.fromJson(getHotels(), HotelResult.class);
        }

        Set<String> registeredHotels = new HashSet<>();

        Set<Hotel> hotelList = new HashSet<>();

        for (UserHotel userHotel : HotelInfo.booking.getUserHotels()) {
            registeredHotels.add(userHotel.getName());
        }

        for (Hotel hotel : hotelResult.getHotels()) {
            for (String hh : hotel.getTags().split("\n")) {
                if (com.hotel.hotelpro.Recommendation.tagSet.contains(hh) && !registeredHotels.contains(hotel.getName())) {

                    hotelList.add(hotel);

                }
            }
        }

        return hotelList;

    }

    public void updateList() {
        if (recommendationAdapter != null) {

            recommendationAdapter.setHotels(getHotelWithTags());

        }
    }


    public String getHotels() {
        SharedPreferences sp = getActivity().getSharedPreferences("hotel", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        if (sp.contains("data")) {
            return sp.getString("data", null);
        } else {
            return null;
        }
    }
}

