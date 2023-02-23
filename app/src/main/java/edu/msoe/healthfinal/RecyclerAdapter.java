package edu.msoe.healthfinal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private List<WorkoutSchema> items;

    public RecyclerAdapter(List<WorkoutSchema> items){
        this.items=items;
    }



    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        return new RecyclerHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.text.setText(items.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder{

        public TextView text;
        private RecyclerAdapter adapter;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.recycler_text);


        }

        public RecyclerHolder linkAdapter(RecyclerAdapter adapter){
            this.adapter=adapter;
            return this;
        }

    }
}
