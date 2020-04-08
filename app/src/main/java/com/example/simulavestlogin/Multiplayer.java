package com.example.simulavestlogin;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class Multiplayer extends AppCompatActivity {
private ListView LISTA;
private String testando;
private Button btndesafiar;
private Button Btnatt;
private Button Btnaceitar;
private EditText txtjogador;
private TextView aguarde;
private TextView txtJogo;
Firebase refeuser;
Firebase ref;
String contadorr="0";
 String conti="5";
String assunto;
String altvalea;
String altvaleb;
String altvalec;
String altvaled;
String altvalee;
String enunciadovale;
String altvalecorreta;
String vestibularale;
String jogadoradv;
public static String jogador;
int contador2=0;
int contador1=1;
int maisumcontador=0;
int contadorquest=0;
public int contnovo=0;
int ie=0;

ArrayList<Integer> list = getRandomNonRepeatingIntegers(4, 1, Integer.parseInt(conti));
    public static int cont=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        Firebase.setAndroidContext(this);
        btndesafiar = (Button) findViewById(R.id.btndesafiar);
        txtjogador = (EditText) findViewById(R.id.txtdesafio);
        aguarde = (TextView) findViewById(R.id.txtaguarde);
        Btnatt = (Button) findViewById(R.id.btnatt);
        txtJogo = (TextView) findViewById(R.id.txtjogo);
        Btnaceitar = (Button) findViewById(R.id.btnaceitar);
        refeuser = new Firebase("https://fir-vestibular.firebaseio.com/Clientes/");
        ref = new Firebase("https://fir-vestibular.firebaseio.com/");
        final ArrayList<String> strings = new ArrayList<>();
        Query deteste;
        deteste=refeuser.orderByChild("Status").startAt("Online");
        deteste.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot objsnapsho:dataSnapshot.getChildren()){
                    testando = (objsnapsho.child("Nome").getValue(String.class));
                    if(testando.equals(MainActivity.nomejogador1)) {

                    }
                    else{
                        strings.add(testando);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Query jogador2;
        jogador2=ref.child("Online").orderByChild("Jogador_2").equalTo(MainActivity.nomejogador1);
        jogador2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot objsnapsho:dataSnapshot.getChildren()){
                    jogadoradv = (objsnapsho.child("Jogador_1").getValue(String.class));
                    txtJogo.setText(jogadoradv+" acaba de te desafiar, você aceita?");
                    Btnaceitar.setVisibility(TextView.VISIBLE);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Query queryalea;
        queryalea=ref.child("Contador").child("Contador_online").orderByChild("Verificar").equalTo("Sim");
        queryalea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot teste: dataSnapshot.getChildren())
                    if(teste.exists()){
                        contadorr = (teste.child("Cont").getValue(String.class));
                        cont = Integer.parseInt(contadorr);
                    }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Query queryale;
        queryale=ref.child("Contador").child("Contador_todas_questões").orderByChild("Verificar").equalTo("Sim");
        queryale.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot teste: dataSnapshot.getChildren())
                    if(teste.exists()){
                        conti = (teste.child("Cont").getValue(String.class));


                    }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


       final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                strings );
        LISTA = (ListView) findViewById(R.id.listonline);
        LISTA.setAdapter(arrayAdapter);

        btndesafiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query queryalea;
                queryalea=ref.child("Contador").child("Contador_online").orderByChild("Verificar").equalTo("Sim");
                queryalea.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot teste: dataSnapshot.getChildren())
                            if(teste.exists()){
                                contadorr = (teste.child("Cont").getValue(String.class));
                                cont = Integer.parseInt(contadorr);
                            }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                if (contador2 == 0) {
                    ref.child("Online").child(String.valueOf(cont+1)).child("Jogador_1").setValue(MainActivity.nomejogador1);
                    ref.child("Online").child(String.valueOf(cont+1)).child("Jogador_2").setValue(txtjogador.getText().toString());
                    ref.child("Online").child(String.valueOf(cont+1)).child("Status").child("Status_Pedido").setValue("Não aceito");
                    ref.child("Online").child(String.valueOf(cont+1)).child("Pontuação_jog1").setValue("0");
                    ref.child("Online").child(String.valueOf(cont+1)).child("Pontuação_jog2").setValue("0");
                    ref.child("Online").child(String.valueOf(cont+1)).child("ID").setValue(String.valueOf(cont+1));
                    ref.child("Online").child(String.valueOf(cont+1)).child("Status").child("Termino_jogador1").setValue("Não");
                    ref.child("Online").child(String.valueOf(cont+1)).child("Status").child("Termino_jogador2").setValue("Não");
                    contador2++;
                }

                list = getRandomNonRepeatingIntegers(4, 1, Integer.parseInt(conti));
                for (int i = 1; i <= 3; i++) {
                    Query queryquest1;
                    queryquest1 = ref.child("Questões").child("Todas_Questões").orderByChild("ID").equalTo(String.valueOf(list.get(contadorquest)));
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
                                    ref.child("Online").child(String.valueOf(cont+1)).child("Questão" + String.valueOf(contador1)).child("Questão" + String.valueOf(contador1)).setValue("Sim");
                                    ref.child("Online").child(String.valueOf(cont+1)).child("Questão" + String.valueOf(contador1)).child("Enunciado").setValue(enunciadovale);
                                    ref.child("Online").child(String.valueOf(cont+1)).child("Questão" + String.valueOf(contador1)).child("Alternativa_A").setValue(altvalea);
                                    ref.child("Online").child(String.valueOf(cont+1)).child("Questão" + String.valueOf(contador1)).child("Alternativa_B").setValue(altvaleb);
                                    ref.child("Online").child(String.valueOf(cont+1)).child("Questão" + String.valueOf(contador1)).child("Alternativa_C").setValue(altvalec);
                                    ref.child("Online").child(String.valueOf(cont+1)).child("Questão" + String.valueOf(contador1)).child("Alternativa_D").setValue(altvaled);
                                    ref.child("Online").child(String.valueOf(cont+1)).child("Questão" + String.valueOf(contador1)).child("Alternativa_E").setValue(altvalee);
                                    ref.child("Online").child(String.valueOf(cont+1)).child("Questão" + String.valueOf(contador1)).child("Correta").setValue(altvalecorreta);
                                    ref.child("Online").child(String.valueOf(cont+1)).child("Questão" + String.valueOf(contador1)).child("Vestibular").setValue(vestibularale);
                                    ref.child("Online").child(String.valueOf(cont+1)).child("Questão" + String.valueOf(contador1)).child("Assunto").setValue(assunto);
                                    ref.child("Contador").child("Contador_online").child("Node").child("Cont").setValue(String.valueOf(cont));
                                    contador1++;
                                    jogador="jogador_1";


                                }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                    contadorquest++;
                }
                while (contnovo<2){

                    Query query1;
                    query1 = ref.child("Online").child(String.valueOf(cont+1)).orderByChild("Status_Pedido").equalTo("Aceito");
                    query1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                aguarde.setText("FOI");

                                Intent it = new Intent(Multiplayer.this, QuestaoDesafio.class);
                                startActivity(it);
                                finish();

                            } else {
                                aguarde.setText("AGUARDANDO");
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                    contnovo++;
                }


            }
        });
        Btnatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strings.clear();
                Query deteste;
                deteste=refeuser.orderByChild("Status").startAt("Online");
                deteste.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot objsnapsho:dataSnapshot.getChildren()){
                            if(objsnapsho.exists()) {
                                testando = (objsnapsho.child("Nome").getValue(String.class));
                                if(testando.equals(MainActivity.nomejogador1)){

                                }
                                else {
                                    strings.add(testando);
                                    LISTA.setAdapter(arrayAdapter);
                                }
                            }
                            else{
                                LISTA.setAdapter(arrayAdapter);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }

        });
        Btnaceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jogador="jogador_2";
                ref.child("Online").child(String.valueOf(cont+1)).child("Status").child("Status_Pedido").setValue("Aceito");
                Intent it = new Intent(Multiplayer.this, QuestaoDesafio.class);
                startActivity(it);
                finish();
            }
        });


    }
    public static int getRandomInt(int min, int max) {
        Random random = new Random();

        return random.nextInt((max - min) + 1) + min;
    }

    public static ArrayList<Integer> getRandomNonRepeatingIntegers(int size, int min,
                                                                   int max) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        while (numbers.size() < size) {
            int random = getRandomInt(min, max);

            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }

        return numbers;
    }
}
