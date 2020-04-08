package com.example.simulavestlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class ResulAleatorio extends AppCompatActivity {
private Button retornar;
private TextView metadiaria;
private TextView acertos;
private TextView desempenho;
Firebase referencia;
int pegameta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resul_aleatorio);
        Firebase.setAndroidContext(this);

        referencia = new Firebase("https://fir-vestibular.firebaseio.com/Clientes/");
        metadiaria= (TextView) findViewById(R.id.txtmetadiariaa);
        acertos= (TextView) findViewById(R.id.txtacertos);
        desempenho= (TextView) findViewById(R.id.txtdesempenho);
        retornar= (Button) findViewById(R.id.btnatt);

        acertos.setText("Você acertou "+aleatorio.perguntascertas+" perguntas de 4 no total");

        if(aleatorio.perguntascertas==4){
            desempenho.setText("Excelente, seu desempenho foi perfeito");
        }
        if(aleatorio.perguntascertas>=2 && aleatorio.perguntascertas <=3){
            desempenho.setText("Foi bem, porém pratique mais para alcançar a perfeição");
        }
        if(aleatorio.perguntascertas==1){
            desempenho.setText("Razoável, você precisa estudar mais");
        }
        if(aleatorio.perguntascertas==0){
            desempenho.setText("Ruim, experimente ler nossos resumos antes de praticar");
        }

        Query querymeta;
        querymeta = referencia.child("Meta Diária").orderByChild("ID").equalTo(MainActivity.identifica);
        querymeta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot teste : dataSnapshot.getChildren())
                    if (teste.exists()){
                        pegameta = (teste.child("Meta").getValue(int.class));
                        metadiaria.setText("Meta diária: "+pegameta +"xp/100xp" );
                    }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aleatorio.perguntascertas=0;
                Intent it = new Intent(ResulAleatorio.this, Selecao.class);
                startActivity(it);
                finish();
            }
        });


    }
}
