package com.juangut.actividad1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button guardar, irRegistro;
    EditText usuario, clave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        usuario = findViewById(R.id.TextUsuario);
        clave = findViewById(R.id.TextContrasena);

        guardar = findViewById(R.id.ButtonIniciarSesion);
        irRegistro = findViewById(R.id.BtnIrRegistro);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario.setError(null);

                String correo = usuario.getText().toString();
                String contrasena = clave.getText().toString();

                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher mather = pattern.matcher(correo);

                if (correo.equals("") || correo.length() < 5 || !mather.find()) {
                    usuario.setError("Usuario invalido");
                    usuario.requestFocus();
                    return;
                }

                if (contrasena.equals("") || contrasena.length() < 3) {
                    clave.setError("Contrasena invalida");
                    clave.requestFocus();
                    return;
                }


                AdminSQlite admin = new AdminSQlite(getApplicationContext(), "Biblioteca", null, 1);
                SQLiteDatabase db = admin.getWritableDatabase();

//                Verificar si el usuario existe

                try {
                    Cursor fila = db.rawQuery("select * from usuario where correo='" + correo + "' and contrasena='" + contrasena+"'", null);
                    if (!fila.moveToFirst()) {
                        Toast.makeText(MainActivity.this, "El usuario no existe. Verifique credenciales", Toast.LENGTH_LONG).show();
                        return;
                    }
                    db.close();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "No se pudo consultar el usuario", Toast.LENGTH_LONG).show();
                    return;
                }


//                Enviar al menu principal
                Intent intent = new Intent(getApplicationContext(), MenuUsuario.class);
                startActivity(intent);

            }
        });

        irRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Enviar al menu de registro
                Intent intent = new Intent(getApplicationContext(), RegistroUsuario.class);
                startActivity(intent);
            }
        });

    }
}