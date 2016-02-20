package com.chornyiua.geekbrains.dz1_sqlite.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chornyiua.geekbrains.dz1_sqlite.R;
import com.chornyiua.geekbrains.dz1_sqlite.WorkersActivity;
import com.chornyiua.geekbrains.dz1_sqlite.dto.CompanyDTO;

import java.util.ArrayList;
import java.util.List;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder> {

    private List<CompanyDTO> data;
    private DataBaseHelper dbHelper;
    private Context context;

    public CompanyListAdapter(Context context) {
        this.context = context;
        this.dbHelper = new DataBaseHelper(this.context);
        readDataFromDB();
    }

    public DataBaseHelper getDbHelper() {
        return dbHelper;
    }

    public List<CompanyDTO> readDataFromDB() {
        data = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(dbHelper.COMPANY_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(dbHelper.COMPANY_KEY_ID);
            int nameIndex = cursor.getColumnIndex(dbHelper.COMPANY_NAME);
            do {
                data.add(new CompanyDTO(cursor.getString(nameIndex), cursor.getInt(idIndex)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
        return data;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WorkersActivity.class);
                intent.putExtra("companyID", data.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SQLiteDatabase database = dbHelper.getWritableDatabase();
                final int id = data.get(position).getId();
                if (id != 0) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    dialogBuilder.setTitle("Delete");
                    dialogBuilder.setMessage("Really?");
                    dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            database.delete(dbHelper.COMPANY_TABLE, dbHelper.COMPANY_KEY_ID + " = " + id, null);
                            database.delete(dbHelper.WORKERS_TABLE, dbHelper.WORKERS_COMPANY_ID + " = " + id, null);
                            readDataFromDB();
                            notifyDataSetChanged();
                            Toast.makeText(context, "remove",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                    dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            Toast.makeText(context, "cancel", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                    dialogBuilder.setCancelable(true);
                    dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            Toast.makeText(context, "cancel",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                    dialogBuilder.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name;
        Button button;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvCompany);
            button = (Button) itemView.findViewById(R.id.btnCompany);
            cardView = (CardView) itemView.findViewById(R.id.cvCampany);
        }
    }


}
