package com.chornyiua.geekbrains.dz1_sqlite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chornyiua.geekbrains.dz1_sqlite.adapters.WorkerListAdapter;

public class WorkersActivity extends AppCompatActivity {
    RecyclerView rv;
    WorkerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        rv = (RecyclerView) findViewById(R.id.rvCompany);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new WorkerListAdapter(this);
        rv.setAdapter(adapter);
    }

}
