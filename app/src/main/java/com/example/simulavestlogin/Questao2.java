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

import com.example.simulavestlogin.modelo.Pessoa;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class Questao2 extends AppCompatActivity {

    private Button prosseguir;
    private RadioButton alta;
    private RadioGroup grupo;
    private TextView enunc;
Questao a = new Questao();
    private RadioButton altb;
    private RadioButton altc;
    private RadioButton altd;
    private RadioButton alte;
    private String alternativa;
    private String alternativb;
    private String alternativc;
    private String alternativd;
    private String alternative;
    private String alternativcorreta;
    private String respostacerta;
    private String enunciado;
    private TextView resul;
    Firebase referencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questao2);
        Firebase.setAndroidContext(this);
        resul= (TextView) findViewById(R.id.txtresultado);
        grupo = (RadioGroup) findViewById(R.id.radio);
        prosseguir = (Button) findViewById(R.id.btnprosseguir);
        alta = (RadioButton) findViewById(R.id.rdbalta);
        enunc = (TextView) findViewById(R.id.txtenun);
        altb = (RadioButton) findViewById(R.id.rdbaltb);
        altc = (RadioButton) findViewById(R.id.rdbaltc);
        altd = (RadioButton) findViewById(R.id.rdbaltd);
        alte= (RadioButton) findViewById(R.id.rdbalte);
        referencia = new Firebase("https://fir-vestibular.firebaseio.com/Questões/");
        Query query;
        query=referencia.child("FUVEST").orderByChild("Assunto").equalTo("Respiratório");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot teste: dataSnapshot.getChildren())
                    if (teste.exists()) {
                        enunciado = (teste.child("Enunciado").getValue(String.class));
                        alternativa= (teste.child("Alternativa_A").getValue(String.class));
                        alternativb= (teste.child("Alternativa_B").getValue(String.class));
                        alternativc= (teste.child("Alternativa_c").getValue(String.class));
                        alternativd= (teste.child("Alternativa_D").getValue(String.class));
                        alternative= (teste.child("Alternativa_E").getValue(String.class));
                        alternativcorreta= (teste.child("Correta").getValue(String.class));
                        alta.setText(alternativa);
                        altb.setText(alternativb);
                        altc.setText(alternativc);
                        altd.setText(alternativd);
                        alte.setText(alternative);
                        enunc.setText("(Unicamp)"+enunciado);
                    }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = (RadioButton) group.findViewById(checkedId);
                respostacerta = button.getText().toString();


            }
        });
        prosseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(respostacerta.equals(alternativcorreta)) {
                    Toast.makeText(getApplicationContext(), "Acertou, parabéns", Toast.LENGTH_LONG).show();


                }
                else{
                    Toast.makeText(getApplicationContext(),"Errou, tente novamente mais tarde", Toast.LENGTH_LONG).show();
                    referencia = new Firebase("https://fir-vestibular.firebaseio.com/Clientes/");
                    Query query;
                    query = referencia.orderByChild("E-mail").equalTo("ruanmelo@gmail.com");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot teste: dataSnapshot.getChildren())
                                if (teste.exists()) {
                                    String recebe = (teste.child("Meta").getValue(String.class));




                                    Intent it = new Intent(Questao2.this, Cronograma.class);
                                    startActivity(it);
                                }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                    Intent it = new Intent(Questao2.this, Questao2.class);
                    startActivity(it);
                }
            }
        });
    }
}
