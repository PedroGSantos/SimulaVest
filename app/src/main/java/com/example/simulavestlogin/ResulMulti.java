package com.example.simulavestlogin;

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

public class ResulMulti extends AppCompatActivity {
private TextView txtjog1;
private TextView txtjog2;
private TextView txtpont1;
private TextView txtpont2;
private TextView txtvencedor;
private Button exibir;
String pont1;
String pont2;
String jog1;
String jog2;

Firebase ref = new Firebase("https://fir-vestibular.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resul_multi);
        Firebase.setAndroidContext(this);
        txtjog1 = (TextView) findViewById(R.id.txtnomejog1);
        txtjog2 = (TextView) findViewById(R.id.txtnomejog2);
        txtpont1 = (TextView) findViewById(R.id.txtpont1);
        txtpont2 = (TextView) findViewById(R.id.txtpont2);
        txtvencedor = (TextView) findViewById(R.id.txtvencedor);
        exibir = (Button) findViewById(R.id.btnresul);
        exibir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query;
                query = ref.child("Online").orderByChild("ID").equalTo(String.valueOf(Multiplayer.cont+1));
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot teste: dataSnapshot.getChildren()) {
                            if (teste.exists()) {
                                jog1 = (teste.child("Jogador_1").getValue(String.class));
                                jog2 = (teste.child("Jogador_2").getValue(String.class));
                                pont1 = (teste.child("Pontuação_jog1").getValue(String.class));
                                pont2 = (teste.child("Pontuação_jog2").getValue(String.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                txtjog1.setText("Jogador 1: "+jog1);
                txtjog2.setText("Jogador 2: "+jog2);
                txtpont1.setText("Pontuação jogador 1: "+pont1);
                txtpont2.setText("Pontuação jogador 2: "+pont2);
                if(Integer.parseInt(pont1)>Integer.parseInt(pont2)){
                    txtvencedor.setText("O jogador 1 venceu a partida");
                }
                if(Integer.parseInt(pont1)<Integer.parseInt(pont2)){
                    txtvencedor.setText("O jogador 2 venceu a partida");
                }
                if(Integer.parseInt(pont1)==Integer.parseInt(pont2)){
                    txtvencedor.setText("A partida empatou");
                }
            }
        });

    }
}
