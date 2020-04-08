package com.example.simulavestlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


public class TrocaSenha extends AppCompatActivity {

//Declaração de variáveis
private EditText procuraemail;
private TextView nometroca;
private TextView perguntatroca;
private TextView respostaperg;
private Button btnenvio;
private EditText novasenhac;
private String iddd="";
Firebase ref;
Firebase ref2;
Firebase ref3;
Boolean teste= true;
Boolean teste2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troca_senha);

        //Passagem do objeto para a variavel
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://fir-vestibular.firebaseio.com/Clientes/");
        novasenhac = (EditText) findViewById(R.id.txtnovasenha);
        btnenvio = (Button) findViewById(R.id.btnenvioo);
        respostaperg = (EditText) findViewById(R.id.txtrespostaeee);
        procuraemail = (EditText)findViewById(R.id.txtemailtroca);
        nometroca = (TextView) findViewById(R.id.txtnometroca);
        perguntatroca = (TextView) findViewById(R.id.txtperguntatroca);

        //Inicio do comandos de evento ao digitar no TextView do e-mail
        procuraemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(teste==true){
                    procuraemail.setError("Você precisa digitar um e-mail cadastrado e correto");


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            //caso o usuário tenha acabado de digitar, os seguintes codigos serão executados, ou seja, esse metodo será chamado
                Query query2;
                query2=ref.orderByChild("E-mail").equalTo(procuraemail.getText().toString());
                query2.addValueEventListener(new ValueEventListener() {
                    @Override

                    //caso essa query que recebe o valor do campo E-mail no Firebase seja igual ao valor digitado no campo do email no app, os seguintes codigos serão executados
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot single: dataSnapshot.getChildren()) {

                            //Se o email existir, dos dados da pergunta e nome serão exibidos e o id coletado para uma variavel
                            if (single.exists()) {
                                String perguntatrocaa = (single.child("Pergunta_Secreta").getValue(String.class));
                                String nome = (single.child("Nome").getValue(String.class));
                                String idd = (single.child("ID").getValue(String.class));
                                iddd = idd;
                                nometroca.setText("Olá, " + nome);
                                perguntatroca.setText(perguntatrocaa);
                                teste = false;
                                procuraemail.setError(null);


                            }




                        }
                    }


                    @Override

                    public void onCancelled(FirebaseError firebaseError) {


                    }
                });

            }
        });

        //Inicio do codigo caso o evento do botão seja chamado pelo usuário
        btnenvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Estrutura de decisão que só é executada caso exista um email no banco de dados
                if(teste==false){

                    Query query3;
                    query3 = ref.child(iddd).orderByChild("RespostaVeri").equalTo(respostaperg.getText().toString());
                    query3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                        //estrutura de decisão que verifica se a resposta fornecida pelo usuário está correta e atribui assim um valor a variavel booleana que autoriza a mudança de senha
                                if (dataSnapshot.exists()) {

                                teste2=false;
                            }

                        //Caso a resposta esteja incorreta, uma mensagem será mostrada ao usuario
                            else{
                                    Toast.makeText(getApplicationContext(), "Sua resposta está incorreta", Toast.LENGTH_LONG).show();
                                }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }

                //estrutura de decisão que verifica se a nova senha fornecida possui pelo menos 8 caracteres
                if(novasenhac.getText().length()>=8) {

                    //estrutura de decisão que só é executada caso a resposta esteja correta
                    if (teste2 == false) {
                        novasenhac.getText().toString();
                        ref.child(iddd).child("Senha").setValue(md5.criptografar(novasenhac.getText().toString()));
                        ref.child(iddd).child("Confirmação_de_senha").setValue(md5.criptografar(novasenhac.getText().toString()));
                        ref.child(iddd).child("Verificar").child("SenhaConfi").setValue(md5.criptografar(novasenhac.getText().toString()));
                        Toast.makeText(getApplicationContext(), "Sua senha foi alterada", Toast.LENGTH_LONG).show();
                        teste2 = true;
                        Intent i = new Intent(TrocaSenha.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Sua nova senha está com erro", Toast.LENGTH_LONG).show();
                    novasenhac.setError("Sua senha precisa de pelo menos 8 caracteres");
                }
            }
        });


    }
}
