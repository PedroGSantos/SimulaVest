package com.example.simulavestlogin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class Selecao extends AppCompatActivity {
private Button vesti;
private Button aleato;
private Button aprenda;
private Button mp;
Firebase ref;
String jogad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://fir-vestibular.firebaseio.com/");
        Query jogador2;
        jogador2=ref.child("Online").orderByChild("Jogador_2").equalTo(MainActivity.nomejogador1);
        jogador2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot objsnapsho:dataSnapshot.getChildren()) {
                    jogad = (objsnapsho.child("Jogador_1").getValue(String.class));
                    Intent intent = new Intent(Selecao.this, Multiplayer.class);

                    int id = (int) (Math.random() * 1000);
                    PendingIntent pi = PendingIntent.getActivity(getBaseContext(), id,
                            intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification notification = new Notification.Builder(getBaseContext())
                            .setContentTitle("Convite para desafio" )
                            .setContentText("O jogador " +jogad+ " te desafiou")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pi).build();
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    notificationManager.notify(id, notification);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        vesti= (Button) findViewById(R.id.btnvestibu);
        vesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Selecao.this, Vestibular.class);
                startActivity(it);
                finish();
            }
        });
        aleato= (Button) findViewById(R.id.aletorioooo);
        aleato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Selecao.this, aleatorio.class);
                startActivity(it);
                finish();
            }
        });
        aprenda=(Button) findViewById(R.id.btnaprendaejogue);
        aprenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Selecao.this, Cronograma.class);
                startActivity(it);
                finish();
            }
        });

        mp = (Button) findViewById(R.id.btnmp);
        mp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Selecao.this,  Multiplayer.class);
                startActivity(it);
                finish();
            }
        });
    }
}
