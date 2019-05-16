package com.chatapp.chatapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tela extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("BoraLÁ");
        actionBar.setIcon(R.drawable.icone);

    }
    public void ProximaTela(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

}
