package com.example.myapplicationscrap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationscrap.Listeners.OnFetchDataListener;
import com.example.myapplicationscrap.Listeners.SelectListener;
import com.example.myapplicationscrap.Models.NewsApiResponse;
import com.example.myapplicationscrap.Models.NewsHeadline;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    com.example.myapplicationscrap.CustomAdapter customAdapter;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    ProgressDialog progressDialog;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Fetching News Of " + query);
                progressDialog.show();
                com.example.myapplicationscrap.RequestManager manager = new com.example.myapplicationscrap.RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching News...");
        progressDialog.show();

        btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btn_3);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.btn_4);
        btn4.setOnClickListener(this);
        btn5 = findViewById(R.id.btn_5);
        btn5.setOnClickListener(this);
        btn6 = findViewById(R.id.btn_6);
        btn6.setOnClickListener(this);
        btn7 = findViewById(R.id.btn_7);
        btn7.setOnClickListener(this);


        com.example.myapplicationscrap.RequestManager manager = new com.example.myapplicationscrap.RequestManager(MainActivity.this);
        manager.getNewsHeadlines(listener, "general", null);
    }

    private final OnFetchDataListener<NewsApiResponse> listener =new OnFetchDataListener<NewsApiResponse>() {

        @Override
        public void onFetchData(List<NewsHeadline> data,  String message) {
            showNews(data);
            progressDialog.dismiss();
            if (data.isEmpty()){
                Toast.makeText(MainActivity.this, "No Data Found!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadline> headlines) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        customAdapter = new com.example.myapplicationscrap.CustomAdapter(this, headlines, this);
        recyclerView.setAdapter(customAdapter);

    }

    @Override
    public void OnNewsClicked(NewsHeadline headline) {
        startActivity(new Intent(MainActivity.this, com.example.myapplicationscrap.DetailsActivity.class)
        .putExtra("data", headline));

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        progressDialog.setTitle("Fetching News Of " + category);
        progressDialog.show();
        com.example.myapplicationscrap.RequestManager manager = new com.example.myapplicationscrap.RequestManager(MainActivity.this);
        manager.getNewsHeadlines(listener, category, null);
    }
}