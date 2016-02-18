package com.chornyiua.geekbrains.dz1_sqlite;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.chornyiua.geekbrains.dz1_sqlite.adapters.CompanyListAdapter;
import com.chornyiua.geekbrains.dz1_sqlite.adapters.DataBaseHelper;
import com.chornyiua.geekbrains.dz1_sqlite.dto.CompanyDTO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<CompanyDTO> data;
    DataBaseHelper dbHelper;
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

        dbHelper = new DataBaseHelper(this);

        rv = (RecyclerView)findViewById(R.id.rvCompany);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CompanyListAdapter(readDataFromDB());
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
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                contentValues.put(dbHelper.KEY_NAME, etCompanyName.getText().toString());

                database.insert(dbHelper.COMPANY_TABLE, null, contentValues);
                dbHelper.close();
                adapter.notifyDataSetChanged();
                rv.invalidate();

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

    private List<CompanyDTO> readDataFromDB() {
        data = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(dbHelper.COMPANY_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(dbHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(dbHelper.KEY_NAME);
            do {
                data.add(new CompanyDTO(cursor.getString(nameIndex)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        dbHelper.close();
        return data;
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
