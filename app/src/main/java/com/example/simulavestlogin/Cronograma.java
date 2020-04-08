package com.example.simulavestlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class Cronograma extends AppCompatActivity {
private ImageView imgsistemaresp;
Firebase referencia;
private TextView status;
public static String marca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_cronograma);
        imgsistemaresp = (ImageView) findViewById(R.id.imgsistema);
        status = (TextView) findViewById(R.id.txtmeta);
        referencia = new Firebase("https://fir-vestibular.firebaseio.com/Clientes/");
        Query query;
        query = referencia.child("Meta Diária").orderByChild("ID").equalTo(MainActivity.identifica);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot teste: dataSnapshot.getChildren())
                    if (teste.exists()) {
                        String recebe = (teste.child("Meta").getValue(String.class));
                        status.setText("Meta diária: "+recebe+"xp/100xp");

                    }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        imgsistemaresp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cronograma.this, testeexemplo.class);
                startActivity(i);
                marca = "Sistema respiratório";
            }
        });
    }
}
