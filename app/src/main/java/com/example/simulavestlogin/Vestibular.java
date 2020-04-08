package com.example.simulavestlogin;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class Vestibular extends AppCompatActivity {
private Button jogar;
private TextView selecionevest;
private TextView assuntovesti;
private TextView enunciadovesti;
private ImageView imgquest;
private RadioGroup radiovesti;
private Spinner combo;
private RadioButton altvestia;
private RadioButton altvestib;
private RadioButton altvestic;
private RadioButton altvestid;
private RadioButton altvestie;
private TextView enunciado;
private TextView assuntorecebe;
int contadorvesti=1;
private String certa="";
String assunto;
String url="";
String contadorr="5";
String altvvestia;
String altvvestib;
String altvvestic;
String altvvestid;
String altvvestie;
String enunciadovale;
String altvalecorreta;
String vestibularale;
Firebase refe;
Firebase refequest=new Firebase("https://fir-vestibular.firebaseio.com/Questões/");
ArrayList<Integer> list = getRandomNonRepeatingIntegers(4, 1, Integer.parseInt(contadorr));
Firebase refeuser;
int meta =0;
public static int perguntascertas1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vestibular);
        Firebase.setAndroidContext(this);
        imgquest = (ImageView) findViewById(R.id.img);
        jogar= (Button) findViewById(R.id.btnjogovesti);
        selecionevest= (TextView) findViewById(R.id.tx);
        combo = (Spinner) findViewById(R.id.cbovesti);
        assuntovesti = (TextView) findViewById(R.id.txtassuntovesti);
        enunciadovesti = (TextView) findViewById(R.id.txtenunciadooo);
        enunciado = (TextView) findViewById(R.id.txtenunciadorecebevest);
        assuntorecebe = (TextView) findViewById(R.id.txtassuntovestirecebe);
        radiovesti= (RadioGroup) findViewById(R.id.rdgrupovesti);
        altvestia = (RadioButton) findViewById(R.id.rdbvestialta);
        altvestib = (RadioButton) findViewById(R.id.rdbvestialtb);
        altvestic = (RadioButton) findViewById(R.id.rdbvestialtc);
        altvestid = (RadioButton) findViewById(R.id.rdbvestialtd);
        altvestie = (RadioButton) findViewById(R.id.rdbvestialte);
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

        jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiovesti.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton button2 = (RadioButton) group.findViewById(checkedId);
                        certa = button2.getText().toString();
                    }
                });
                switch(contadorvesti){
                    case 1:
                        selecionevest.setVisibility(TextView.INVISIBLE);
                        combo.setVisibility(Spinner.INVISIBLE);
                        assuntovesti.setVisibility(TextView.VISIBLE);
                        enunciadovesti.setVisibility(TextView.VISIBLE);
                        radiovesti.setVisibility(RadioGroup.VISIBLE);
                        refe= new Firebase("https://fir-vestibular.firebaseio.com/Contador/Contador_"+combo.getSelectedItem().toString()+"/");
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
                        queryquest1 = refequest.child(combo.getSelectedItem().toString()).orderByChild("ID").equalTo(String.valueOf(list.get(0)));
                        queryquest1.addValueEventListener(new ValueEventListener() {
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
                                            imgquest.setVisibility(ImageView.INVISIBLE);
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
                                        enunciado.setText("("+vestibularale +")"+ " " + enunciadovale);
                                        contadorvesti++;
                                        certa="";

                                    }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

                        jogar.setText("Avançar");
                        break;
                    case 2:
                        if(certa.equals(altvalecorreta)) {
                            radiovesti.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    RadioButton button2 = (RadioButton) group.findViewById(checkedId);
                                    certa = button2.getText().toString();
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
                                                imgquest.setVisibility(ImageView.INVISIBLE);
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
                                            perguntascertas1++;
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
                                                imgquest.setVisibility(ImageView.INVISIBLE);
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
                        if(certa.equals(altvalecorreta)) {
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
                                                imgquest.setVisibility(ImageView.INVISIBLE);
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
                                            perguntascertas1++;
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
                                                imgquest.setVisibility(ImageView.INVISIBLE);
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
                        if(certa.equals(altvalecorreta)) {
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
                                                imgquest.setVisibility(ImageView.INVISIBLE);
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
                                            perguntascertas1++;
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
                                                imgquest.setVisibility(ImageView.INVISIBLE);
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
                        if(certa.equals(altvalecorreta)) {
                            Toast.makeText(getApplicationContext(), "Parabéns, você acertou", Toast.LENGTH_LONG).show();
                            refeuser.child("Meta Diária").child(MainActivity.identifica).child("Meta").setValue(meta + 10);
                            perguntascertas1++;
                           Intent it = new Intent(Vestibular.this, ResulVest.class);
                            startActivity(it);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Você errou, tente novamente em outra oportunidade", Toast.LENGTH_LONG ).show();
                            Intent it = new Intent(Vestibular.this, ResulVest.class);
                            startActivity(it);
                            finish();

                        }
                        break;
                }

            }
        });
    }
    private void loadImageFromUrl(String url){
        Picasso.with(this).load(url).error(R.mipmap.ic_launcher).into(imgquest, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

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
