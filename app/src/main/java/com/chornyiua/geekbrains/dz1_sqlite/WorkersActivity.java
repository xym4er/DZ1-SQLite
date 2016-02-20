package com.chornyiua.geekbrains.dz1_sqlite;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.chornyiua.geekbrains.dz1_sqlite.adapters.WorkerListAdapter;

public class WorkersActivity extends AppCompatActivity {
    RecyclerView rv;
    WorkerListAdapter adapter;
    int companyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.wtoolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.wfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddWorkerDialog();
            }
        });

        Intent intent = getIntent();
        companyID = intent.getIntExtra("companyID", -1);

        rv = (RecyclerView) findViewById(R.id.rvWorkers);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new WorkerListAdapter(this, companyID);
        rv.setAdapter(adapter);
    }

    private void showAddWorkerDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_worker_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText etWorkerName = (EditText) dialogView.findViewById(R.id.etWorkerName);
        final EditText etWorkerSalary = (EditText) dialogView.findViewById(R.id.etWorkerSalary);
        dialogBuilder.setTitle("Add worker");
        dialogBuilder.setMessage("Enter worker name and salary");
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SQLiteDatabase database = adapter.getDbHelper().getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(adapter.getDbHelper().WORKERS_NAME, etWorkerName.getText().toString());
                contentValues.put(adapter.getDbHelper().WORKERS_SALARY, etWorkerSalary.getText().toString());
                contentValues.put(adapter.getDbHelper().WORKERS_COMPANY_ID, companyID);
                database.insert(adapter.getDbHelper().WORKERS_TABLE, null, contentValues);
                adapter.getDbHelper().close();
                adapter.readDataFromDB();
                adapter.notifyDataSetChanged();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
