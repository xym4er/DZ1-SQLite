package com.chornyiua.geekbrains.dz1_sqlite.adapters;

import android.content.Context;
import android.content.DialogInterface;
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
import com.chornyiua.geekbrains.dz1_sqlite.dto.WorkerDTO;

import java.util.ArrayList;
import java.util.List;

public class WorkerListAdapter extends RecyclerView.Adapter<WorkerListAdapter.WorkerViewHolder> {

    private List<WorkerDTO> data;
    private DataBaseHelper dbHelper;
    private Context context;
    private int companyID;

    public WorkerListAdapter(Context context, int companyID) {
        this.context = context;
        this.companyID = companyID;
        this.dbHelper = new DataBaseHelper(this.context);
        readDataFromDB();
    }

    public DataBaseHelper getDbHelper() {
        return dbHelper;
    }

    public List<WorkerDTO> readDataFromDB() {
        data = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(dbHelper.WORKERS_TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(dbHelper.WORKERS_KEY_ID);
            int nameIndex = cursor.getColumnIndex(dbHelper.WORKERS_NAME);
            int salaryIndex = cursor.getColumnIndex(dbHelper.WORKERS_SALARY);
            int companyIdIndex = cursor.getColumnIndex(dbHelper.WORKERS_COMPANY_ID);
            do {
                if (companyID == cursor.getInt(companyIdIndex)) {
                    data.add(new WorkerDTO(cursor.getInt(idIndex), cursor.getString(nameIndex), cursor.getInt(companyIdIndex), cursor.getString(salaryIndex)));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
        return data;
    }

    @Override
    public WorkerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_item, parent, false);
        return new WorkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkerViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getName());
        holder.salary.setText(data.get(position).getSalary());
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
                            database.delete(dbHelper.WORKERS_TABLE, "_id = " + id, null);
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

    public class WorkerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name;
        TextView salary;
        Button button;

        public WorkerViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvWorkerName);
            salary = (TextView) itemView.findViewById(R.id.tvWorkerSalary);
            button = (Button) itemView.findViewById(R.id.btnWorker);
            cardView = (CardView) itemView.findViewById(R.id.cvWorker);
        }
    }
}
