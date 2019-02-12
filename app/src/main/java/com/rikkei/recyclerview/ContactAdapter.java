package com.rikkei.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private Context context;
    private List<Contact> contacts;
    private OnItemClickListener itemClickListener;

    public ContactAdapter(Context context, List<Contact> contacts, OnItemClickListener itemClickListener) {
        this.context = context;
        this.contacts = contacts;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_contact, viewGroup, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.binbView(contacts.get(i));
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvPhoneNumber;
        private ConstraintLayout layoutItem;
        private OnItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            initView();
        }

        private void initView() {
            tvName = itemView.findViewById(R.id.text_name);
            tvPhoneNumber = itemView.findViewById(R.id.text_phone_number);
            layoutItem = itemView.findViewById(R.id.constraint_contact);
            layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }

        private void binbView(Contact contact) {
            tvName.setText(contact.getName());
            tvPhoneNumber.setText(contact.getNumberPhone());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
