package com.chornyiua.geekbrains.dz1_sqlite;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.chornyiua.geekbrains.dz1_sqlite.adapters.CompanyListAdapter;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    CompanyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCompanyDialog();
            }
        });

        rv = (RecyclerView) findViewById(R.id.rvCompany);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CompanyListAdapter(this);
        rv.setAdapter(adapter);
    }

    private void showAddCompanyDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_company_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText etCompanyName = (EditText) dialogView.findViewById(R.id.etCompanyName);
        dialogBuilder.setTitle("Add company");
        dialogBuilder.setMessage("Enter company name");
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SQLiteDatabase database = adapter.getDbHelper().getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(adapter.getDbHelper().COMPANY_NAME, etCompanyName.getText().toString());
                database.insert(adapter.getDbHelper().COMPANY_TABLE, null, contentValues);
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
//        AlertDialog b = dialogBuilder.create();
//        b.show();
        dialogBuilder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
