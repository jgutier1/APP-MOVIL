package com.juangut.actividad1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroUsuario extends AppCompatActivity {

    Button registrarseB, iniciarSesionB;
    EditText nombreT, documentoT, correoT, contrasenaT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        nombreT = findViewById(R.id.TextNombreRegistro);
        documentoT = findViewById(R.id.TextDocumentoRegistro);
        correoT = findViewById(R.id.TextCorreoRegistro);
        contrasenaT = findViewById(R.id.TextContrasenaRegistro);

        registrarseB = findViewById(R.id.BtnRegistro);
        iniciarSesionB = findViewById(R.id.BtnIncioSesion);


        registrarseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdminSQlite admin = new AdminSQlite(getApplicationContext(), "Biblioteca", null, 1);
                SQLiteDatabase db = admin.getWritableDatabase();

                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher mather = pattern.matcher(correoT.getText().toString());

                if (documentoT.getText().toString().equals("") || documentoT.getText().toString().length() < 3) {
                    documentoT.setError("Documento invalido");
                    documentoT.requestFocus();
                    return;
                }

                if (nombreT.getText().toString().equals("") || nombreT.getText().toString().length() < 2) {
                    nombreT.setError("Nombre invalido");
                    nombreT.requestFocus();
                    return;
                }

                if (correoT.getText().toString().equals("") || correoT.getText().toString().length() < 3 || !mather.find()) {
                    correoT.setError("Correo invalido");
                    correoT.requestFocus();
                    return;
                }

                if (contrasenaT.getText().toString().equals("") || contrasenaT.getText().toString().length() < 3) {
                    contrasenaT.setError("Contraseña invalida");
                    contrasenaT.requestFocus();
                    return;
                }


                int doc = Integer.parseInt(documentoT.getText().toString());
                String nom = nombreT.getText().toString();
                String email = correoT.getText().toString();
                String contrasena = contrasenaT.getText().toString();

                ContentValues datos = new ContentValues();

                datos.put("documento", doc);
                datos.put("nombre", nom);
                datos.put("correo", email);
                datos.put("contrasena", contrasena);

                try {
                    db.insert("usuario", null, datos);
                    db.close();
                } catch (Exception e) {
                    Toast.makeText(RegistroUsuario.this, "No se pudo crear el usuario", Toast.LENGTH_LONG).show();
                    return;
                }

                nombreT.setText("");
                correoT.setText("");
                documentoT.setText("");
                contrasenaT.setText("");

                // Enviar al menu principal
                Intent intent = new Intent(getApplicationContext(), MenuUsuario.class);
                startActivity(intent);

            }
        });

        iniciarSesionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Enviar a inciar sesion
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }
}