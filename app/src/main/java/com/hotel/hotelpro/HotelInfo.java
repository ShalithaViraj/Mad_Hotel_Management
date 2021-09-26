package com.hotel.hotelpro;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelInfo extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView hotelImage;
    private TextView hotelDesc, views, drafts, completed;
    private Button book, draftBook;
    private RecommendationAdapter.HotelViewHolder hotelViewHolder;
    Hotel hotel;
    int pos;
    com.hotel.hotelpro.HotelResult hotelResult;
    public static com.hotel.hotelpro.Booking booking = new com.hotel.hotelpro.Booking();
    private ImageView singleBed;
    private ImageView doubleBed;
    private ImageView kingBed;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    
    private boolean singleCheck = false;
    private boolean doubleCheck = false;
    private boolean kingCheck = false;
    
    private Button plusButton1;
    private Button plusButton2;
    private Button plusButton3;

    private TextView counter1;
    private TextView counter2;
    private TextView counter3;

    private Button minusButton1;
    private Button minusButton2;
    private Button minusButton3;

    private RatingBar ratingBar;
    private EditText editText;
    private Button saveFeedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);

        toolbar = findViewById(R.id.toolbarInfo);
        hotelImage = findViewById(R.id.hotelImage);
        //hotelDesc = findViewById(R.id.hotelDesc);
        book = findViewById(R.id.confirmBooking);
        draftBook = findViewById(R.id.draftBooking);
        //drafts = findViewById(R.id.draftText);
        singleBed = findViewById(R.id.single);
        doubleBed = findViewById(R.id.doubleB);
        kingBed = findViewById(R.id.king);

        editText = findViewById(R.id.editTextBox);
        saveFeedBack = findViewById(R.id.save_feedback);

        checkBox1  = findViewById(R.id.checkBox1);
        checkBox2  = findViewById(R.id.checkBox1);
        checkBox3  = findViewById(R.id.checkBox1);

        plusButton1 = findViewById(R.id.plusButton);
        plusButton2 = findViewById(R.id.plusButton2);
        plusButton3 = findViewById(R.id.plusButton3);

        counter1 = findViewById(R.id.counter);
        counter2 = findViewById(R.id.counter2);
        counter3 = findViewById(R.id.counter3);

        minusButton1 = findViewById(R.id.minusButton);
        minusButton2 = findViewById(R.id.minusButton2);
        minusButton3 = findViewById(R.id.minusButton3);

        ratingBar = findViewById(R.id.ratings_bar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        plusButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] counterText = counter1.getText().toString().split(" ");
                int count1 = Integer.parseInt(counterText[0]) + 1;
                counter1.setText(count1 +" rooms");

            }
        });

        minusButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] counterText = counter1.getText().toString().split(" ");
                int count1 = Integer.parseInt(counterText[0]);
                if (count1>0){
                    count1--;
                    counter1.setText(count1 +" rooms");
                }
            }
        });

        plusButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] counterText = counter2.getText().toString().split(" ");
                int count1 = Integer.parseInt(counterText[0]) + 1;
                counter2.setText(count1 +" rooms");

            }
        });

        minusButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] counterText = counter2.getText().toString().split(" ");
                int count1 = Integer.parseInt(counterText[0]);
                if (count1>0){
                    count1--;
                    counter2.setText(count1 +" rooms");
                }
            }
        });

        plusButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] counterText = counter3.getText().toString().split(" ");
                int count1 = Integer.parseInt(counterText[0]) + 1;
                counter3.setText(count1 +" rooms");

            }
        });

        minusButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] counterText = counter3.getText().toString().split(" ");
                int count1 = Integer.parseInt(counterText[0]);
                if (count1>0){
                    count1--;
                    counter3.setText(count1 +" rooms");
                }
            }
        });

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    singleCheck = true;
                } else {
                    singleCheck = false;
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    doubleCheck = true;
                } else {
                    doubleCheck = false;
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    kingCheck = true;
                } else {
                    kingCheck = false;
                }
            }
        });

        System.out.println(singleCheck);
        System.out.println(doubleCheck);
        System.out.println(kingCheck);


        hotel = (Hotel) getIntent().getExtras().getSerializable("data");

        hotelResult = new Gson().fromJson(getHotels(), com.hotel.hotelpro.HotelResult.class);
        pos = getIntent().getExtras().getInt("pos");

        System.out.println(hotel.getName());

        ratingBar.setRating(Float.parseFloat(hotel.getRatings()));

//        toolbar.setTitle(hotel.getName());
//
//        setSupportActionBar(toolbar);
        Picasso
                .with(HotelInfo.this)
                .load(Uri.parse(hotel.getImageUrl()))
                .into(hotelImage);
        //hotelDesc.setText(hotel.getDescription());

        Picasso
                .with(HotelInfo.this)
                .load(Uri.parse("https://setupmyhotel.com/images/Room-Type-Single-Room.jpg?ezimgfmt=rs:300x250/rscb333/ng:webp/ngcb333"))
                .into(singleBed);

        Picasso
                .with(HotelInfo.this)
                .load(Uri.parse("https://setupmyhotel.com/images/Room-Type-Double-Room.jpg?ezimgfmt=rs:300x250/rscb333/ng:webp/ngcb333"))
                .into(doubleBed);

        Picasso
                .with(HotelInfo.this)
                .load(Uri.parse("https://webbox.imgix.net/images/hjxiskrriyojrndn/d322c424-c65d-4c52-8587-fdfda21087d0.jpg?auto=format,compress&fit=crop&crop=entropy"))
                .into(kingBed);

        //views.setText(hotelResult.getHotels().get(pos).getVisits() + " views");
        //drafts.setText(hotelResult.getHotels().get(pos).getDraftBookings() + " drafts");
        //completed.setText(hotelResult.getHotels().get(pos).getCompletedBookings() + " booked");

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setBooking(true);
                finish();

            }
        });
        draftBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setBooking(false);
                //MainActivity.updatec(1);
                finish();

            }
        });

        saveFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String feedback = String.valueOf(editText.getText());
                int rating = (int) ratingBar.getRating();
                hotel.setRatings(String.valueOf(rating));
                updateJson();
            }
        });
    }

    public String getHotels() {
        SharedPreferences sp = getSharedPreferences("hotel", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        if (sp.contains("data")) {
            return sp.getString("data", null);
        } else {
            return null;
        }
    }

    public void updateJson() {

        JSONObject jsonObject = new JSONObject();
        try {
        jsonObject.put("name",hotel.getName());
        jsonObject.put("ratings",hotel.getRatings());
        jsonObject.put("visits",hotel.getVisits());
        jsonObject.put("completedBookings",hotel.getCompletedBookings());
        jsonObject.put("draftBookings",hotel.getDraftBookings());
        jsonObject.put("tags",hotel.getTags());
        jsonObject.put("description",hotel.getDescription());
        jsonObject.put("imageUrl",hotel.getImageUrl());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        String key = jsonObject.toString();

        try {
            FileOutputStream fos = this.openFileOutput("jsonfile222.json", Context.MODE_PRIVATE);
            fos.write(key.getBytes());
            fos.close();
            Log.d("JSON" , jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setBooking(Boolean complete) {

        UserHotel hotel1 = new UserHotel();
        hotel1.setName(hotel.getName());
        hotel1.setCompleted(complete);
        hotel1.setTags(hotel.getTags());

        List<UserHotel> userHotels = booking.getUserHotels();
        userHotels.add(hotel1);

        booking.setUserHotels(userHotels);

        Set<String> s = new HashSet<>();
        for (UserHotel userHotel : booking.getUserHotels()) {
            if (userHotel.getCompleted()) {
                for (String ss : userHotel.getTags().split("\n")) {
                    ss = ss.replace("null", "");
                    s.add(ss);
                }

            }
        }
        String sa = "";

        for (String sss : s) {
            sa += sss;
            Recommendation.tagSet.add(sss);
        }

        if (complete) {
            int c = Integer.valueOf(hotelResult.getHotels().get(pos).getCompletedBookings());
            hotelResult.getHotels().get(pos).setCompletedBookings(String.valueOf(c + 1));
            storeUpdates(hotelResult);
        }
        else {
            int c = Integer.valueOf(hotelResult.getHotels().get(pos).getDraftBookings());
            hotelResult.getHotels().get(pos).setDraftBookings(String.valueOf(c + 1));
            storeUpdates(hotelResult);
        }
    }

    public void storeUpdates(com.hotel.hotelpro.HotelResult hotelResult) {
        SharedPreferences.Editor spe = getSharedPreferences("hotel", Context.MODE_PRIVATE).edit();
        String save = new Gson().toJson(hotelResult);
        spe.putString("data", save);
        spe.apply();
    }
}

