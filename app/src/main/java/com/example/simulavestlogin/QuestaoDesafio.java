package com.example.simulavestlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class QuestaoDesafio extends AppCompatActivity {
private Button avançar;
private TextView assun;
private RadioButton  altalea;
private RadioButton  altaleb;
private RadioButton  altalec;
private RadioButton  altaled;
private RadioButton altalee;
private TextView enunciadoale;
private RadioGroup grupoale;
String certa="";
String enunciadovale;
String altvalea;
String altvaleb;
String altvalec;
String altvaled;
String altvalee;
String altvalecorreta;
String vestibularale;
String assunto;
int contale=1;
int potuação1=0;
int potuação2=0;
Firebase refequest= new Firebase("https://fir-vestibular.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questao_desafio);
        Firebase.setAndroidContext(this);
        avançar = (Button) findViewById(R.id.btniniciar);
        assun = (TextView) findViewById(R.id.txtaleassuntorecebe);
        enunciadoale = (TextView) findViewById(R.id.txtaleenun);
        grupoale = (RadioGroup) findViewById(R.id.rdgrup);
        altalea = (RadioButton) findViewById(R.id.rdbalealta);
        altaleb = (RadioButton) findViewById(R.id.rdbalealtb);
        altalec = (RadioButton) findViewById(R.id.rdbalealtc);
        altaled = (RadioButton) findViewById(R.id.rdbalealtd);
        altalee = (RadioButton) findViewById(R.id.rdbalealte);

        Query queryquest1;
        queryquest1 = refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).orderByChild("Questão1").equalTo("Sim");
        queryquest1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot teste : dataSnapshot.getChildren())
                    if (teste.exists()) {
                        enunciadovale = (teste.child("Enunciado").getValue(String.class));
                        altvalea = (teste.child("Alternativa_A").getValue(String.class));
                        altvaleb = (teste.child("Alternativa_B").getValue(String.class));
                        altvalec = (teste.child("Alternativa_C").getValue(String.class));
                        altvaled = (teste.child("Alternativa_D").getValue(String.class));
                        altvalee = (teste.child("Alternativa_E").getValue(String.class));
                        altvalecorreta = (teste.child("Correta").getValue(String.class));
                        vestibularale = (teste.child("Vestibular").getValue(String.class));
                        assunto = (teste.child("Assunto").getValue(String.class));
                        assun.setText(assunto);
                        altalea.setText(altvalea);
                        altaleb.setText(altvaleb);
                        altalec.setText(altvalec);
                        altaled.setText(altvaled);
                        altalee.setText(altvalee);
                        enunciadoale.setText("("+vestibularale +")"+ " " + enunciadovale);
                        certa="";

                    }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        avançar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grupoale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton button2 = (RadioButton) group.findViewById(checkedId);
                        certa = button2.getText().toString();
                    }
                });
                switch(contale) {

                    case 1:
                        if(certa.equals(altvalecorreta)){
                            Toast.makeText(getApplicationContext(), "Parabéns, você acertou", Toast.LENGTH_LONG ).show();
                            if(Multiplayer.jogador.equals("jogador_1")){
                                potuação1++;
                                refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).child("Pontuação_jog1").setValue(String.valueOf(potuação1));
                            }
                            else{
                                potuação2++;
                                refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).child("Pontuação_jog2").setValue(String.valueOf(potuação2));
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Você errou", Toast.LENGTH_LONG ).show();
                        }
                        altalea.setChecked(false);
                        altaleb.setChecked(false);
                        altalec.setChecked(false);
                        altaled.setChecked(false);
                        altalee.setChecked(false);
                        Query queryquest;
                        queryquest = refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).orderByChild("Questão2").equalTo("Sim");
                        queryquest.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot teste : dataSnapshot.getChildren())
                                    if (teste.exists()) {
                                        enunciadovale = (teste.child("Enunciado").getValue(String.class));
                                        altvalea = (teste.child("Alternativa_A").getValue(String.class));
                                        altvaleb = (teste.child("Alternativa_B").getValue(String.class));
                                        altvalec = (teste.child("Alternativa_C").getValue(String.class));
                                        altvaled = (teste.child("Alternativa_D").getValue(String.class));
                                        altvalee = (teste.child("Alternativa_E").getValue(String.class));
                                        altvalecorreta = (teste.child("Correta").getValue(String.class));
                                        vestibularale = (teste.child("Vestibular").getValue(String.class));
                                        assunto = (teste.child("Assunto").getValue(String.class));
                                        assun.setText(assunto);
                                        altalea.setText(altvalea);
                                        altaleb.setText(altvaleb);
                                        altalec.setText(altvalec);
                                        altaled.setText(altvaled);
                                        altalee.setText(altvalee);
                                        enunciadoale.setText("("+vestibularale +")"+ " " + enunciadovale);
                                        contale++;
                                        certa="";

                                    }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                        break;


                    case 2:
                        grupoale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                RadioButton button2 = (RadioButton) group.findViewById(checkedId);
                                certa = button2.getText().toString();
                            }
                        });
                        if(certa.equals(altvalecorreta)){
                            Toast.makeText(getApplicationContext(), "Parabéns, você acertou", Toast.LENGTH_LONG ).show();
                            if(Multiplayer.jogador.equals("jogador_1")){
                                potuação1++;
                                refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).child("Pontuação_jog1").setValue(String.valueOf(potuação1));
                            }
                            else{
                                potuação2++;
                                refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).child("Pontuação_jog2").setValue(String.valueOf(potuação2));
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Você errou", Toast.LENGTH_LONG ).show();
                        }
                        altalea.setChecked(false);
                        altaleb.setChecked(false);
                        altalec.setChecked(false);
                        altaled.setChecked(false);
                        altalee.setChecked(false);
                        Query queryquest5;
                        queryquest5 = refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).orderByChild("Questão3").equalTo("Sim");
                        queryquest5.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot teste : dataSnapshot.getChildren())
                                    if (teste.exists()) {
                                        enunciadovale = (teste.child("Enunciado").getValue(String.class));
                                        altvalea = (teste.child("Alternativa_A").getValue(String.class));
                                        altvaleb = (teste.child("Alternativa_B").getValue(String.class));
                                        altvalec = (teste.child("Alternativa_C").getValue(String.class));
                                        altvaled = (teste.child("Alternativa_D").getValue(String.class));
                                        altvalee = (teste.child("Alternativa_E").getValue(String.class));
                                        altvalecorreta = (teste.child("Correta").getValue(String.class));
                                        vestibularale = (teste.child("Vestibular").getValue(String.class));
                                        assunto = (teste.child("Assunto").getValue(String.class));
                                        assun.setText(assunto);
                                        altalea.setText(altvalea);
                                        altaleb.setText(altvaleb);
                                        altalec.setText(altvalec);
                                        altaled.setText(altvaled);
                                        altalee.setText(altvalee);
                                        enunciadoale.setText("("+vestibularale +")"+ " " + enunciadovale);
                                        contale++;
                                        certa="";

                                    }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                        break;
                    case 3:
                        grupoale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                RadioButton button2 = (RadioButton) group.findViewById(checkedId);
                                certa = button2.getText().toString();
                            }
                        });
                        if(certa.equals(altvalecorreta)){
                            Toast.makeText(getApplicationContext(), "Parabéns, você acertou", Toast.LENGTH_LONG ).show();
                            if(Multiplayer.jogador.equals("jogador_1")){
                                potuação1++;
                                refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).child("Pontuação_jog1").setValue(String.valueOf(potuação1));
                                refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).child("Status").child("Termino_jogador1").setValue("Sim");
                                Query queryquest8;
                                queryquest8 = refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).orderByChild("Termino_jogador2").equalTo("Sim");
                                queryquest8.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.exists()) {

                                                Intent it = new Intent(QuestaoDesafio.this, ResulMulti.class);
                                                startActivity(it);
                                                finish();

                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "Aguarde o oponente terminar", Toast.LENGTH_LONG ).show();
                                            }

                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                            }
                            else{
                                potuação2++;
                                refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).child("Pontuação_jog2").setValue(String.valueOf(potuação2));
                                refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).child("Status").child("Termino_jogador2").setValue("Sim");
                                Query queryquest8;
                                queryquest8 = refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).orderByChild("Termino_jogador1").equalTo("Sim");
                                queryquest8.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {

                                                Intent it = new Intent(QuestaoDesafio.this, ResulMulti.class);
                                                startActivity(it);
                                                finish();

                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "Aguarde o oponente terminar", Toast.LENGTH_LONG ).show();
                                            }

                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Você errou", Toast.LENGTH_LONG ).show();
                            if(Multiplayer.jogador.equals("jogador_1")){
                                refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).child("Status").child("Termino_jogador1").setValue("Sim");
                                Query queryquest8;
                                queryquest8 = refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).orderByChild("Termino_jogador2").equalTo("Sim");
                                queryquest8.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot teste : dataSnapshot.getChildren()){
                                            if (teste.exists()) {

                                                Intent it = new Intent(QuestaoDesafio.this, ResulMulti.class);
                                                startActivity(it);
                                                finish();

                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "Aguarde o oponente terminar", Toast.LENGTH_LONG ).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                            }
                            else {
                                refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).child("Status").child("Termino_jogador2").setValue("Sim");
                                Query queryquest8;
                                queryquest8 = refequest.child("Online").child(String.valueOf(Multiplayer.cont+1)).orderByChild("Termino_jogador1").equalTo("Sim");
                                queryquest8.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.exists()) {

                                                Intent it = new Intent(QuestaoDesafio.this, ResulMulti.class);
                                                startActivity(it);
                                                finish();

                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "Aguarde o oponente terminar", Toast.LENGTH_LONG ).show();
                                            }

                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
