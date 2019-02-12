package com.rikkei.recyclerview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, ContactAdapter.OnItemClickListener {

    private static final int SPAN_CONUT = 2;

    private RecyclerView recyclerContact;
    private ContactAdapter contactAdapter;
    private List<Contact> contacts;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView imageButton;
    private boolean isList = true;

    private ContactDAO contactDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContactDatabase contactDatabase = ContactDatabase.getInstance(this);
        contactDAO = contactDatabase.contactDAO();
        initView();
    }

    private void initView() {
        recyclerContact = findViewById(R.id.recycler_contact);
        contacts = new ArrayList<>();
        contactAdapter = new ContactAdapter(this, contacts, this);
        recyclerContact.setAdapter(contactAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerContact.setLayoutManager(layoutManager);

        imageButton = findViewById(R.id.image_button);
        imageButton.setOnClickListener(this);
        findViewById(R.id.floating_button_add).setOnClickListener(this);

        contacts.addAll(contactDAO.getAllContact());
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_button:
                isList = !isList;
                setListMode(isList);
                break;
            case R.id.floating_button_add:
                showAddDialog();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        showEditDialog(position);
    }

    private void setListMode(boolean isList) {
        if(isList) {
            layoutManager = new LinearLayoutManager(this);
            imageButton.setImageResource(R.drawable.ic_grid_on_black_24dp);
        } else {
            layoutManager = new GridLayoutManager(this, SPAN_CONUT);
            imageButton.setImageResource(R.drawable.ic_format_list_bulleted_black_24dp);
        }
        recyclerContact.setLayoutManager(layoutManager);
    }

    private void showAddDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_contact);
        final EditText etName = dialog.findViewById(R.id.edit_name);
        final EditText etPhoneNumber = dialog.findViewById(R.id.edit_phone_number);

        dialog.findViewById(R.id.button_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String phoneNumber = etPhoneNumber.getText().toString();
                Contact contact = new Contact(name, phoneNumber);
                contacts.add(0, contact);
                contactAdapter.notifyItemInserted(0);
                dialog.cancel();
                contactDAO.insertContact(contact);
            }
        });
        dialog.show();
    }

    private void showEditDialog(final int position) {
        final Contact contact = contacts.get(position);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_contact);
        final EditText etName = dialog.findViewById(R.id.edit_name);
        final EditText etPhoneNumber = dialog.findViewById(R.id.edit_phone_number);
        etName.setText(contact.getName());
        etPhoneNumber.setText(contact.getNumberPhone());

        dialog.findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts.remove(position);
                contactAdapter.notifyItemRemoved(position);
                dialog.cancel();
                contactDAO.deleteContact(contact);
            }
        });

        dialog.findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String phoneNumber = etPhoneNumber.getText().toString();
                contact.setName(name);
                contact.setNumberPhone(phoneNumber);
                contactAdapter.notifyItemChanged(position);
                dialog.cancel();
                contactDAO.updateContact(contact);
            }
        });
        dialog.show();
    }
}
