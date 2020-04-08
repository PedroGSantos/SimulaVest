package com.example.simulavestlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class aleatorio extends AppCompatActivity {
    private TextView assun;
    private TextView enunciadoale;
    private Button iniciar;
    private RadioGroup grupoale;
    private RadioButton altalea;
    private RadioButton altaleb;
    private RadioButton altalec;
    private RadioButton altaled;
    private RadioButton altalee;
    private String certa="";
    String assunto;
    String contadorr="5";
    String altvalea;
    String altvaleb;
    String altvalec;
    String altvaled;
    String altvalee;
    String enunciadovale;
    String altvalecorreta;
    String vestibularale;
    Firebase refe;
    Firebase refequest;
    ArrayList<Integer> list = getRandomNonRepeatingIntegers(4, 1, Integer.parseInt(contadorr));
    Firebase refeuser;
    int contale=1;
    int meta =0;
    public static int perguntascertas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aleatorio);
        Firebase.setAndroidContext(this);
        assun = (TextView) findViewById(R.id.txtaleassuntorecebe);
        enunciadoale = (TextView) findViewById(R.id.txtaleenun);
        iniciar = (Button) findViewById(R.id.btniniciar);
        altalea = (RadioButton) findViewById(R.id.rdbalealta);
        altaleb = (RadioButton) findViewById(R.id.rdbalealtb);
        altalec = (RadioButton) findViewById(R.id.rdbalealtc);
        altaled = (RadioButton) findViewById(R.id.rdbalealtd);
        altalee = (RadioButton) findViewById(R.id.rdbalealte);
        grupoale = (RadioGroup)findViewById(R.id.rdgrup);

        refeuser = new Firebase("https://fir-vestibular.firebaseio.com/Clientes/");
        refequest = new Firebase("https://fir-vestibular.firebaseio.com/Questões/");
        refe = new Firebase("https://fir-vestibular.firebaseio.com/Contador/Contador_todas_questões/");
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



        iniciar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                grupoale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton button2 = (RadioButton) group.findViewById(checkedId);
                        certa = button2.getText().toString();
                    }
                });


                iniciar.setText("Avançar");
                switch(contale) {

                    case 1:
                        list = getRandomNonRepeatingIntegers(4, 1, Integer.parseInt(contadorr));
                        Query queryquest1;
                        queryquest1 = refequest.child("Todas_Questões").orderByChild("ID").equalTo(String.valueOf(list.get(0)));
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

                       if(certa.equals(altvalecorreta)){
                           Toast.makeText(getApplicationContext(), "Parabéns, você acertou", Toast.LENGTH_LONG ).show();
                           refeuser.child("Meta Diária").child(MainActivity.identifica).child("Meta").setValue(meta+10);
                           altalea.setSelected(false);
                           altaleb.setSelected(false);
                           altalec.setSelected(false);
                           altaled.setSelected(false);
                           altalee.setSelected(false);
                           Query queryquest2;
                           queryquest2 = refequest.child("Todas_Questões").orderByChild("ID").equalTo(String.valueOf(list.get(1)));
                           queryquest2.addValueEventListener(new ValueEventListener() {
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
                                           enunciadoale.setText("("+vestibularale+")" + " " + enunciadovale);
                                           contale++;
                                           perguntascertas++;
                                           certa="";

                                       }
                               }

                               @Override
                               public void onCancelled(FirebaseError firebaseError) {

                               }
                           });
                       }
                       else{
                           Toast.makeText(getApplicationContext(), "Você errou, tente novamente em outra oportunidade", Toast.LENGTH_LONG ).show();
                           altalea.setChecked(false);
                           altaleb.setChecked(false);
                           altalec.setChecked(false);
                           altaled.setChecked(false);
                           altalee.setChecked(false);
                           Query queryquest2;
                           queryquest2 = refequest.child("Todas_Questões").orderByChild("ID").equalTo(String.valueOf(list.get(1)));
                           queryquest2.addValueEventListener(new ValueEventListener() {
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
                                           enunciadoale.setText("("+vestibularale+")"+ " " + enunciadovale);
                                           contale++;
                                           certa="";
                                       }
                               }

                               @Override
                               public void onCancelled(FirebaseError firebaseError) {

                               }
                           });
                       }

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
                            refeuser.child("Meta Diária").child(MainActivity.identifica).child("Meta").setValue(meta+10);
                            altalea.setChecked(false);
                            altaleb.setChecked(false);
                            altalec.setChecked(false);
                            altaled.setChecked(false);
                            altalee.setChecked(false);
                            Query queryquest2;
                            queryquest2 = refequest.child("Todas_Questões").orderByChild("ID").equalTo(String.valueOf(list.get(2)));
                            queryquest2.addValueEventListener(new ValueEventListener() {
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
                                            enunciadoale.setText("("+vestibularale+")" + " " + enunciadovale);
                                            contale++;
                                            perguntascertas++;
                                            certa="";
                                        }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Você errou, tente novamente em outra oportunidade", Toast.LENGTH_LONG ).show();
                            altalea.setChecked(false);
                            altaleb.setChecked(false);
                            altalec.setChecked(false);
                            altaled.setChecked(false);
                            altalee.setChecked(false);
                            Query queryquest2;
                            queryquest2 = refequest.child("Todas_Questões").orderByChild("ID").equalTo(String.valueOf(list.get(2)));
                            queryquest2.addValueEventListener(new ValueEventListener() {
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
                                            enunciadoale.setText("("+vestibularale+")" + " " + enunciadovale);
                                            contale++;
                                            certa="";
                                        }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                        break;
                    case 4:
                        grupoale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                RadioButton button2 = (RadioButton) group.findViewById(checkedId);
                                certa = button2.getText().toString();
                            }
                        });
                        if(certa.equals(altvalecorreta)){

                            Toast.makeText(getApplicationContext(), "Parabéns, você acertou", Toast.LENGTH_LONG ).show();
                            refeuser.child("Meta Diária").child(MainActivity.identifica).child("Meta").setValue(meta+10);
                            altalea.setChecked(false);
                            altaleb.setChecked(false);
                            altalec.setChecked(false);
                            altaled.setChecked(false);
                            altalee.setChecked(false);
                            Query queryquest2;
                            queryquest2 = refequest.child("Todas_Questões").orderByChild("ID").equalTo(String.valueOf(list.get(3)));
                            queryquest2.addValueEventListener(new ValueEventListener() {
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
                                            enunciadoale.setText("("+vestibularale+")" + " " + enunciadovale);
                                            contale++;
                                            iniciar.setText("Terminar");
                                            perguntascertas++;
                                            certa="";
                                        }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Você errou, tente novamente em outra oportunidade", Toast.LENGTH_LONG ).show();
                            altalea.setChecked(false);
                            altaleb.setChecked(false);
                            altalec.setChecked(false);
                            altaled.setChecked(false);
                            altalee.setChecked(false);
                            Query queryquest2;
                            queryquest2 = refequest.child("Todas_Questões").orderByChild("ID").equalTo(String.valueOf(list.get(3)));
                            queryquest2.addValueEventListener(new ValueEventListener() {
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
                                            enunciadoale.setText("("+vestibularale+")" + " " + enunciadovale);
                                            contale++;
                                            iniciar.setText("Terminar");
                                            certa="";
                                        }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });
                        }

                        break;
                    case 5:
                        grupoale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                RadioButton button2 = (RadioButton) group.findViewById(checkedId);
                                certa = button2.getText().toString();
                            }
                        });
                        if(certa.equals(altvalecorreta)) {
                            Toast.makeText(getApplicationContext(), "Parabéns, você acertou", Toast.LENGTH_LONG).show();
                            refeuser.child("Meta Diária").child(MainActivity.identifica).child("Meta").setValue(meta + 10);
                            perguntascertas++;
                            Intent it = new Intent(aleatorio.this, ResulAleatorio.class);
                            startActivity(it);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Você errou, tente novamente em outra oportunidade", Toast.LENGTH_LONG ).show();
                            Intent it = new Intent(aleatorio.this, ResulAleatorio.class);
                            startActivity(it);
                            finish();

                        }
                        break;
                       default:
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

        return numbers;
    }
}
