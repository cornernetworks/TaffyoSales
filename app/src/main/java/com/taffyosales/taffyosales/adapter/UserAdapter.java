package com.taffyosales.taffyosales.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.taffyosales.taffyosales.R;
import com.taffyosales.taffyosales.model.Users;

public class UserAdapter extends FirestoreRecyclerAdapter<Users, UserAdapter.UserHolder> {

    public UserAdapter(@NonNull FirestoreRecyclerOptions<Users> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull Users model) {
        holder.textViewTitle.setText(model.getName());
        holder.textViewDescription.setText(model.getEmail_id());
        holder.textViewPriority.setText(model.getPassword());
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
        return new UserHolder(v);
    }

    class UserHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewPriority;

        public UserHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewDescription = itemView.findViewById(R.id.user_id);
            textViewPriority = itemView.findViewById(R.id.email_id);
        }
    }

}
