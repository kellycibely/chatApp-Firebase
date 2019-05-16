package com.chatapp.chatapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainFragment extends BaseActivity  {

    private Button add_room;
    private EditText room_name;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>(); // Listar as salas de bate papo
    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot(); // usado para acessar o banco de dados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        setUpToolbar();
        setupNavDrawer();

        add_room = (Button) findViewById(R.id.btn_add_room);
        room_name = (EditText) findViewById(R.id.room_name_editText);
        listView = (ListView) findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_of_rooms);
        listView.setAdapter(arrayAdapter);
        request_user_name();


        //Tratar o evento do botão de criar sala de chat
        add_room.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(room_name.getText().toString(),""); // chave e valor (nome da sala do edit text e valor vazio)
                root.updateChildren(map); //o método updateChildren passa o mapa criado para raiz do projeto

            }

        });
        // seta o valor criado no listView
        root.addValueEventListener(new ValueEventListener() {
            @Override       // quando setado no root ele cria dois métodos
            public void onDataChange(DataSnapshot dataSnapshot) { // é chamado sobre que carregamos o app para acessar o bd ou sempre que
                Set<String> set = new HashSet<String>();                // for adcionado o novo filho ao bd
                Iterator i = dataSnapshot.getChildren().iterator(); // getChildren acessa o interator pega apenas o primeiro primeiro filho

                while(i.hasNext()){  //hasNext é usado para ler linha por linha do bd
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                list_of_rooms.clear(); //utiliza o hasSet para evitar nomes repetidos
                list_of_rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged(); // Atualiza o listView
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // chama a sala de chat ao clicar nela e passa o nome do usuário e o nome da sala
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Intent intent = new Intent(getApplicationContext(), Chat_Room.class);
                intent.putExtra("room_name", ((TextView)view).getText().toString());
                intent.putExtra("user_name", name);
                startActivity(intent);
            }
        });
    }


    // Esse método pede o nome de usuário que o usuário vai usar no chat
    public void request_user_name(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("qual seu nome?");

        final EditText input_field = new EditText(this);
        builder.setView(input_field);                             // Edit Text
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){ // botão para confirmar
                name = input_field.getText().toString();
            }
        });
        builder.setNegativeButton("cancelar" , new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                dialogInterface.cancel();
                request_user_name(); // Refaz o método
            }
        });

        builder.show();

    }
    protected void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }


}
