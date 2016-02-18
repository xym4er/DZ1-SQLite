package com.chornyiua.geekbrains.dz1_sqlite.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chornyiua.geekbrains.dz1_sqlite.R;
import com.chornyiua.geekbrains.dz1_sqlite.dto.CompanyDTO;

import java.util.List;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.NoteViewHolder> {

    private List<CompanyDTO> data;

    public CompanyListAdapter(List<CompanyDTO> data) {
        this.data = data;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView name;
        Button button;

        public NoteViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvCompany);
            button = (Button) itemView.findViewById(R.id.btnCompany);
            cardView = (CardView) itemView.findViewById(R.id.cvCampany);


        }
    }


}
