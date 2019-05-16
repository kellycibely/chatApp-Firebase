package com.chatapp.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chat_Room extends AppCompatActivity {
    private Button bnt_send_msg;
    private EditText input_msg;
    private TextView chat_conversation;
    private String user_name, room_name;  // referencia todas as views
    private DatabaseReference root;
    private String temp_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__room);
        setUpToolbar();

       // setTitle("Sala -"+ room_name);



        bnt_send_msg = (Button) findViewById(R.id.bnt_send_msg);
        input_msg = (EditText) findViewById(R.id.input_msg);
        chat_conversation = (TextView) findViewById(R.id.textView);

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString(); // pega os valores enviados

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("sala - " + room_name);



        root = FirebaseDatabase.getInstance().getReference().child(room_name); // referencia da sala // a sala do chat fica como "raiz"

    bnt_send_msg.setOnClickListener(new View.OnClickListener(){
        @Override
            public void onClick(View view){ // add um filho do tipo message
            Map<String, Object> map = new HashMap<String, Object>();
            temp_key = root.push().getKey(); // gera a chave única
            root.updateChildren(map);


            DatabaseReference message_root = root.child(temp_key); // liga a chave aleatória ao usuário

            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("name", user_name);
            map2.put("msg", input_msg.getText().toString()); // map com chave e valor como nome e mds

            message_root.updateChildren(map2);
        }


    });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


        private String chat_msg, chat_user_name;
    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while(i.hasNext()){
            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

            chat_conversation.append(chat_user_name + " : "+ chat_msg + " \n" ); // adiciona os valores ao textView
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

}
