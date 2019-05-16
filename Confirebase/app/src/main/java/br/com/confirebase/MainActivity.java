package br.com.confirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Firebase obg; // referencia do firebase

    Button botaoAzul;
    Button botaoVermelho;
    TextView meuTexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        obg = new Firebase("https://configefirebase.firebaseio.com/");
        botaoAzul = (Button) findViewById(R.id.button);
        botaoVermelho = (Button) findViewById(R.id.button2);
        meuTexto = (TextView) findViewById(R.id.textView);


        obg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { // é disparado sempre que o dado muda no firebase
                String messagem = dataSnapshot.getValue(String.class); // envia pro firebase o string por meio do snapshort
                meuTexto.setText(messagem);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
