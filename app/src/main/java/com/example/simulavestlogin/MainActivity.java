package com.example.simulavestlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.simulavestlogin.modelo.Pessoa;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseApp;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    /*Declaração de variaveis para serem usadas no codigo inteiro*/
    private EditText txtSenha;
    private EditText txtEmail;
    private TextView trocaSenha;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Firebase objetoReff;
    Firebase objetoReff2;
    private String te;
    Boolean verifica=true;
    public static String identifica = null;
    public static String nomejogador1 = null;

    /*Classe interna que trava o campo do e-mail para um determinado estilo*/
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Passagem do objeto para a variavel
        Firebase.setAndroidContext(this);
        trocaSenha = (TextView) findViewById(R.id.txtrocasenha);
        trocaSenha.setText(getString(R.string.sublinhado));
        Button btncadastrar = (Button) findViewById(R.id.btncadastrar);
        Button btnacessar = (Button) findViewById(R.id.btnacesso);
        txtEmail = (EditText) findViewById(R.id.txtemail);
        txtSenha = (EditText) findViewById(R.id.txtsenha);

        //Codigo em html que deixa o texto sublinhado
        Spanned text = Html.fromHtml("<u>Clique aqui</u>");
        trocaSenha.setText(text);


        //evento que chama outra tela caso o usuario clique em trocar a senha
        trocaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, TrocaSenha.class);
                startActivity(it);
                finish();
            }
        });

        //evento que chama outra tela caso o usuario queira se cadastrar
        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, Cadastro.class);
                startActivity(it);
            }
        });

        //evento que realiza a checkagem se todos os campos foram preenchidos devidamente e
        btnacessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = txtEmail.getText().toString().trim();
                final String senhalogin= txtSenha.getText().toString();

        //estrutura de decisão que verifica se o campo do e-mail ou da senha recebeu algum digito, caso não tenha recebido, uma mensagem de erro é exibida
                if("".equals(senhalogin) || "".equals(email)){
                    Toast.makeText(getApplicationContext(), "Alguns campos não estão preenchidos", Toast.LENGTH_LONG ).show();
                    switch (senhalogin){
                        case "":
                            txtSenha.setError("Preencha corretamente");
                            break;

                        default:
                            break;
                    }
                    switch (email){
                        case "":
                            txtEmail.setError("Preencha corretamente");
                            break;

                        default:
                            break;
                    }
                }
                else{

                    //estrutura de decisão que verifica se o e-mail digitado segue o padrão estabelecido
                    if(!emailValidator(email)){
                        Toast.makeText(getApplicationContext(), "O campo e-mail está preenchido indevidamente", Toast.LENGTH_LONG ).show();
                        txtEmail.setError("Seu email deve seguir o exemplo: nome.sobrenome@exemplo.com");
                    }
                    else{

                        //caso todos os dados estejam corretos, uma pesquisa no banco é iniciada
                        objetoReff = new Firebase("https://fir-vestibular.firebaseio.com/Clientes/");


                        Query query;
                        query=objetoReff.orderByChild("E-mail").equalTo(txtEmail.getText().toString().trim());
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {

                                    //caso o e-mail exista no banco, o id é coletado e se inicia a pesquisa pela senha
                                    for (DataSnapshot teste : dataSnapshot.getChildren()) {
                                        identifica = (teste.child("ID").getValue(String.class));
                                        nomejogador1 = (teste.child("Nome").getValue(String.class));

                                        Query query2;
                                        query2 = objetoReff.child(identifica).orderByChild("SenhaConfi").equalTo(md5.criptografar(senhalogin));
                                        query2.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                //caso a senha exista naquele e-mail do banco, uma nova tela é carregada
                                                if (dataSnapshot.exists()) {
                                                    objetoReff.child(identifica).child("Status").setValue("Online");
                                                    Intent it = new Intent(MainActivity.this, Selecao.class);
                                                    startActivity(it);
                                                    finish();

                                                }

                                                //caso não exista e-mail ou senha no banco, uma mensagem de erro é exibida
                                                else {
                                                    Toast.makeText(getApplicationContext(), "E-mail ou senha não cadastrado", Toast.LENGTH_LONG).show();
                                                    txtEmail.setText("");
                                                    txtSenha.setText("");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(FirebaseError firebaseError) {

                                            }
                                        });
                                    }
                                }
                                else{
                                        Toast.makeText(getApplicationContext(), "E-mail ou senha não cadastrado", Toast.LENGTH_LONG).show();
                                        txtEmail.setText("");
                                        txtSenha.setText("");
                                    }

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });



                    }
                }


            }
        });

    }
}
