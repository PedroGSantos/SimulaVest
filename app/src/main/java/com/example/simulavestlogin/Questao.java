package com.example.simulavestlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class Questao extends AppCompatActivity {
private Button prosseguir;
private RadioButton alta;
private RadioGroup grupo;
private TextView enunc;
private TextView ass;
private RadioButton altb;
private RadioButton altc;
private RadioButton altd;
private RadioButton alte;
private String alternativa;
private String alternativb;
private String alternativc;
private String alternativd;
private String alternative;
private String vestibularnome;
private String alternativcorreta;
private String respostacerta;
private String enunciado;
private String assunto;
private String certa;
String contadorr="5";
int meta=0;
public static int perguntascertas2=0;

int contadorvesti=1;
Firebase refeuser;
Firebase refequest=new Firebase("https://fir-vestibular.firebaseio.com/Questões/");
Firebase refe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questao);
        Firebase.setAndroidContext(this);
        grupo = (RadioGroup) findViewById(R.id.radio);
        prosseguir = (Button) findViewById(R.id.btnprosseguir);
        alta = (RadioButton) findViewById(R.id.rdbalta);
        enunc = (TextView) findViewById(R.id.txtenun);
        altb = (RadioButton) findViewById(R.id.rdbaltb);
        altc = (RadioButton) findViewById(R.id.rdbaltc);
        altd = (RadioButton) findViewById(R.id.rdbaltd);
        alte= (RadioButton) findViewById(R.id.rdbalte);
        ass = (TextView) findViewById(R.id.txtass);

        refeuser = new Firebase("https://fir-vestibular.firebaseio.com/Clientes/");
        Query queryMETA;
        queryMETA=refeuser.child("Meta Diária").orderByChild("ID").equalTo(MainActivity.identifica);
        queryMETA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot teste: dataSnapshot.getChildren())
                    if(teste.exists()){
                        meta =(teste.child("Meta").getValue(int.class));


                    }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        /*grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = (RadioButton) group.findViewById(checkedId);
             respostacerta = button.getText().toString();


            }
        });
        prosseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton button = (RadioButton) group.findViewById(checkedId);
                        respostacerta = button.getText().toString();


                    }
                });


                switch(contadorvesti){
                    case 1:
                        refe= new Firebase("https://fir-vestibular.firebaseio.com/Contador/Contador_Assunto "+Cronograma.marca+"/");
                        Query queryalea;
                        queryalea=refe.orderByChild("Verificar").equalTo("Sim");
                        queryalea.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot teste: dataSnapshot.getChildren())
                                    if(teste.exists()){
                                        contadorr = (teste.child("Cont").getValue(String.class));

                                    }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                        list = getRandomNonRepeatingIntegers(4, 1, Integer.parseInt(contadorr));
                        Query queryquest1;
                        queryquest1 = refequest.child("Assuntos").child(Cronograma.marca).orderByChild("ID").equalTo(String.valueOf(list.get(0)));
                        queryquest1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot teste : dataSnapshot.getChildren())
                                    if (teste.exists()) {
                                        enunciado = (teste.child("Enunciado").getValue(String.class));
                                        alternativa = (teste.child("Alternativa_A").getValue(String.class));
                                        alternativb = (teste.child("Alternativa_B").getValue(String.class));
                                        alternativc = (teste.child("Alternativa_c").getValue(String.class));
                                        alternativd = (teste.child("Alternativa_D").getValue(String.class));
                                        alternative = (teste.child("Alternativa_E").getValue(String.class));
                                        alternativcorreta = (teste.child("Correta").getValue(String.class));
                                        vestibularnome = (teste.child("Vestibular").getValue(String.class));
                                        assunto = (teste.child("Assunto").getValue(String.class));
                                        ass.setText(assunto);
                                        alta.setText(alternativa);
                                        altb.setText(alternativb);
                                        altc.setText(alternativc);
                                        altd.setText(alternativd);
                                        alte.setText(alternative);
                                        enunc.setText("("+vestibularnome+")"+ " " + enunciado);
                                        contadorvesti++;
                                        certa="";

                                    }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

                        prosseguir.setText("Avançar");
                        break;
                    case 2:
                        if(respostacerta.equals(alternativcorreta)) {
                            grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    RadioButton button = (RadioButton) group.findViewById(checkedId);
                                    respostacerta = button.getText().toString();


                                }
                            });
                            Toast.makeText(getApplicationContext(), "Parabéns, você acertou", Toast.LENGTH_LONG).show();
                            refeuser.child("Meta Diária").child(MainActivity.identifica).child("Meta").setValue(meta + 10);
                            alta.setSelected(false);
                            altb.setSelected(false);
                            altc.setSelected(false);
                            altd.setSelected(false);
                            alte.setSelected(false);
                            Query queryquest2;
                            queryquest2 = refequest.child("Assuntos").child(Cronograma.marca).orderByChild("ID").equalTo(String.valueOf(list.get(0)));
                            queryquest2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot teste : dataSnapshot.getChildren())
                                        if (teste.exists()) {
                                            enunciado = (teste.child("Enunciado").getValue(String.class));
                                            alternativa = (teste.child("Alternativa_A").getValue(String.class));
                                            alternativb = (teste.child("Alternativa_B").getValue(String.class));
                                            alternativc = (teste.child("Alternativa_c").getValue(String.class));
                                            alternativd = (teste.child("Alternativa_D").getValue(String.class));
                                            alternative = (teste.child("Alternativa_E").getValue(String.class));
                                            alternativcorreta = (teste.child("Correta").getValue(String.class));
                                            vestibularnome = (teste.child("Vestibular").getValue(String.class));
                                            assunto = (teste.child("Assunto").getValue(String.class));
                                            ass.setText(assunto);
                                            alta.setText(alternativa);
                                            altb.setText(alternativb);
                                            altc.setText(alternativc);
                                            altd.setText(alternativd);
                                            alte.setText(alternative);
                                            enunc.setText("("+vestibularnome+")"+ " " + enunciado);
                                            contadorvesti++;
                                            certa="";

                                        }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });

                            prosseguir.setText("Avançar");
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Você errou, tente novamente em outra oportunidade", Toast.LENGTH_LONG ).show();
                            altvestia.setSelected(false);
                            altvestib.setSelected(false);
                            altvestic.setSelected(false);
                            altvestid.setSelected(false);
                            altvestie.setSelected(false);
                            Query queryquest2;
                            queryquest2 = refequest.child(combo.getSelectedItem().toString()).orderByChild("ID").equalTo(String.valueOf(list.get(1)));
                            queryquest2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot teste : dataSnapshot.getChildren())
                                        if (teste.exists()) {
                                            enunciadovale = (teste.child("Enunciado").getValue(String.class));
                                            altvvestia = (teste.child("Alternativa_A").getValue(String.class));
                                            altvvestib = (teste.child("Alternativa_B").getValue(String.class));
                                            altvvestic = (teste.child("Alternativa_C").getValue(String.class));
                                            altvvestid = (teste.child("Alternativa_D").getValue(String.class));
                                            altvvestie = (teste.child("Alternativa_E").getValue(String.class));
                                            altvalecorreta = (teste.child("Correta").getValue(String.class));
                                            vestibularale = (teste.child("Vestibular").getValue(String.class));
                                            assunto = (teste.child("Assunto").getValue(String.class));
                                            url = (teste.child("Img").getValue(String.class));
                                            if(url.equals("")){

                                            }
                                            else{
                                                loadImageFromUrl(url);
                                            }
                                            assuntorecebe.setText(assunto);
                                            altvestia.setText(altvvestia);
                                            altvestib.setText(altvvestib);
                                            altvestic.setText(altvvestic);
                                            altvestid.setText(altvvestid);
                                            altvestie.setText(altvvestie);
                                            enunciado.setText("(" + vestibularale + ")" + " " + enunciadovale);
                                            contadorvesti++;
                                            certa = "";

                                        }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                        break;
                    case 3:
                        if(respostacerta.equals(alternativcorreta)) {
                            grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    RadioButton button = (RadioButton) group.findViewById(checkedId);
                                    respostacerta = button.getText().toString();


                                }
                            });
                            Toast.makeText(getApplicationContext(), "Parabéns, você acertou", Toast.LENGTH_LONG).show();
                            refeuser.child("Meta Diária").child(MainActivity.identifica).child("Meta").setValue(meta + 10);
                            altvestia.setSelected(false);
                            altvestib.setSelected(false);
                            altvestic.setSelected(false);
                            altvestid.setSelected(false);
                            altvestie.setSelected(false);
                            Query queryquest2;
                            queryquest2 = refequest.child(combo.getSelectedItem().toString()).orderByChild("ID").equalTo(String.valueOf(list.get(2)));
                            queryquest2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot teste : dataSnapshot.getChildren())
                                        if (teste.exists()) {
                                            enunciadovale = (teste.child("Enunciado").getValue(String.class));
                                            altvvestia = (teste.child("Alternativa_A").getValue(String.class));
                                            altvvestib = (teste.child("Alternativa_B").getValue(String.class));
                                            altvvestic = (teste.child("Alternativa_C").getValue(String.class));
                                            altvvestid = (teste.child("Alternativa_D").getValue(String.class));
                                            altvvestie = (teste.child("Alternativa_E").getValue(String.class));
                                            altvalecorreta = (teste.child("Correta").getValue(String.class));
                                            vestibularale = (teste.child("Vestibular").getValue(String.class));
                                            assunto = (teste.child("Assunto").getValue(String.class));
                                            url = (teste.child("Img").getValue(String.class));
                                            if(url.equals("")){

                                            }
                                            else{
                                                loadImageFromUrl(url);
                                            }
                                            assuntorecebe.setText(assunto);
                                            altvestia.setText(altvvestia);
                                            altvestib.setText(altvvestib);
                                            altvestic.setText(altvvestic);
                                            altvestid.setText(altvvestid);
                                            altvestie.setText(altvvestie);
                                            enunciado.setText("(" + vestibularale + ")" + " " + enunciadovale);
                                            contadorvesti++;
                                            certa = "";

                                        }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Você errou, tente novamente em outra oportunidade", Toast.LENGTH_LONG ).show();
                            altvestia.setSelected(false);
                            altvestib.setSelected(false);
                            altvestic.setSelected(false);
                            altvestid.setSelected(false);
                            altvestie.setSelected(false);
                            Query queryquest2;
                            queryquest2 = refequest.child(combo.getSelectedItem().toString()).orderByChild("ID").equalTo(String.valueOf(list.get(2)));
                            queryquest2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot teste : dataSnapshot.getChildren())
                                        if (teste.exists()) {
                                            enunciadovale = (teste.child("Enunciado").getValue(String.class));
                                            altvvestia = (teste.child("Alternativa_A").getValue(String.class));
                                            altvvestib = (teste.child("Alternativa_B").getValue(String.class));
                                            altvvestic = (teste.child("Alternativa_C").getValue(String.class));
                                            altvvestid = (teste.child("Alternativa_D").getValue(String.class));
                                            altvvestie = (teste.child("Alternativa_E").getValue(String.class));
                                            altvalecorreta = (teste.child("Correta").getValue(String.class));
                                            vestibularale = (teste.child("Vestibular").getValue(String.class));
                                            assunto = (teste.child("Assunto").getValue(String.class));
                                            url = (teste.child("Img").getValue(String.class));
                                            if(url.equals("")){

                                            }
                                            else{
                                                loadImageFromUrl(url);
                                            }
                                            assuntorecebe.setText(assunto);
                                            altvestia.setText(altvvestia);
                                            altvestib.setText(altvvestib);
                                            altvestic.setText(altvvestic);
                                            altvestid.setText(altvvestid);
                                            altvestie.setText(altvvestie);
                                            enunciado.setText("(" + vestibularale + ")" + " " + enunciadovale);
                                            contadorvesti++;
                                            certa = "";

                                        }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                        break;
                    case 4:
                        if(respostacerta.equals(alternativcorreta)) {
                            grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    RadioButton button = (RadioButton) group.findViewById(checkedId);
                                    respostacerta = button.getText().toString();


                                }
                            });
                            Toast.makeText(getApplicationContext(), "Parabéns, você acertou", Toast.LENGTH_LONG).show();
                            refeuser.child("Meta Diária").child(MainActivity.identifica).child("Meta").setValue(meta + 10);
                            altvestia.setSelected(false);
                            altvestib.setSelected(false);
                            altvestic.setSelected(false);
                            altvestid.setSelected(false);
                            altvestie.setSelected(false);
                            Query queryquest2;
                            queryquest2 = refequest.child(combo.getSelectedItem().toString()).orderByChild("ID").equalTo(String.valueOf(list.get(3)));
                            queryquest2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot teste : dataSnapshot.getChildren())
                                        if (teste.exists()) {
                                            enunciadovale = (teste.child("Enunciado").getValue(String.class));
                                            altvvestia = (teste.child("Alternativa_A").getValue(String.class));
                                            altvvestib = (teste.child("Alternativa_B").getValue(String.class));
                                            altvvestic = (teste.child("Alternativa_C").getValue(String.class));
                                            altvvestid = (teste.child("Alternativa_D").getValue(String.class));
                                            altvvestie = (teste.child("Alternativa_E").getValue(String.class));
                                            altvalecorreta = (teste.child("Correta").getValue(String.class));
                                            vestibularale = (teste.child("Vestibular").getValue(String.class));
                                            assunto = (teste.child("Assunto").getValue(String.class));
                                            url = (teste.child("Img").getValue(String.class));
                                            if(url.equals("")){

                                            }
                                            else{
                                                loadImageFromUrl(url);
                                            }
                                            assuntorecebe.setText(assunto);
                                            altvestia.setText(altvvestia);
                                            altvestib.setText(altvvestib);
                                            altvestic.setText(altvvestic);
                                            altvestid.setText(altvvestid);
                                            altvestie.setText(altvvestie);
                                            enunciado.setText("(" + vestibularale + ")" + " " + enunciadovale);
                                            contadorvesti++;
                                            certa = "";

                                        }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Você errou, tente novamente em outra oportunidade", Toast.LENGTH_LONG ).show();
                            altvestia.setSelected(false);
                            altvestib.setSelected(false);
                            altvestic.setSelected(false);
                            altvestid.setSelected(false);
                            altvestie.setSelected(false);
                            Query queryquest2;
                            queryquest2 = refequest.child(combo.getSelectedItem().toString()).orderByChild("ID").equalTo(String.valueOf(list.get(3)));
                            queryquest2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot teste : dataSnapshot.getChildren())
                                        if (teste.exists()) {
                                            enunciadovale = (teste.child("Enunciado").getValue(String.class));
                                            altvvestia = (teste.child("Alternativa_A").getValue(String.class));
                                            altvvestib = (teste.child("Alternativa_B").getValue(String.class));
                                            altvvestic = (teste.child("Alternativa_C").getValue(String.class));
                                            altvvestid = (teste.child("Alternativa_D").getValue(String.class));
                                            altvvestie = (teste.child("Alternativa_E").getValue(String.class));
                                            altvalecorreta = (teste.child("Correta").getValue(String.class));
                                            vestibularale = (teste.child("Vestibular").getValue(String.class));
                                            assunto = (teste.child("Assunto").getValue(String.class));
                                            url = (teste.child("Img").getValue(String.class));
                                            if(url.equals("")){

                                            }
                                            else{
                                                loadImageFromUrl(url);
                                            }
                                            assuntorecebe.setText(assunto);
                                            altvestia.setText(altvvestia);
                                            altvestib.setText(altvvestib);
                                            altvestic.setText(altvvestic);
                                            altvestid.setText(altvvestid);
                                            altvestie.setText(altvvestie);
                                            enunciado.setText("(" + vestibularale + ")" + " " + enunciadovale);
                                            contadorvesti++;
                                            certa = "";

                                        }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                        break;


                    case 5:
                        if(respostacerta.equals(alternativcorreta)) {
                            Toast.makeText(getApplicationContext(), "Parabéns, você acertou", Toast.LENGTH_LONG).show();
                            refeuser.child("Meta Diária").child(MainActivity.identifica).child("Meta").setValue(meta + 10);
                            perguntascertas2++;
                           Intent it = new Intent(Questao.this, ResulAleatorio.class);
                            startActivity(it);
                            finish();
                       }
                        else{
                            Toast.makeText(getApplicationContext(), "Você errou, tente novamente em outra oportunidade", Toast.LENGTH_LONG ).show();
                            Intent it = new Intent(Questao.this, ResulAleatorio.class);
                            startActivity(it);
                            finish();

                        }
                        break;
                }

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

        return numbers;*/
    }
}

