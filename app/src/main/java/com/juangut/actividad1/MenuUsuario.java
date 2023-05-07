package com.juangut.actividad1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuUsuario extends AppCompatActivity {


    EditText nombre, documento, correo;
    Button guardar, consultar, actualizar, eliminar;

//    Codigo Sugar ORM
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_menu_usuario);
//
//
//    nombre = findViewById(R.id.TextNombre);
//    documento = findViewById(R.id.TextDocumento);
//    correo = findViewById(R.id.TextCorreo);
//
//    guardar = findViewById(R.id.BtnGuardar);
//    consultar = findViewById(R.id.BtnConsultar);
//    actualizar = findViewById(R.id.BtnActualizar);
//    eliminar = findViewById(R.id.BtnEliminar);
//
//
//    guardar.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//            AdminSQlite admin = new AdminSQlite(getApplicationContext(), "Biblioteca", null, 1);
//            SQLiteDatabase db = admin.getWritableDatabase();
//
//
//            String doc = documento.getText().toString();
//            String nom = nombre.getText().toString();
//            String email = correo.getText().toString();
//
//            Usuario usu = new Usuario(doc,nom,email);
//            usu.save();
//
//            nombre.setText("");
//            correo.setText("");
//            documento.setText("");
//        }
//    });
//
//
//    consultar.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            AdminSQlite admin = new AdminSQlite(getApplicationContext(), "Biblioteca", null, 1);
//            SQLiteDatabase db = admin.getWritableDatabase();
//
//            int doc = Integer.parseInt(documento.getText().toString());
//
//            Cursor fila = db.rawQuery("select * from usuario where documento=" + doc, null);
//
//            if (fila.moveToFirst()) {
//                nombre.setText(fila.getString(1));
//                correo.setText((fila.getString(2)));
//            } else {
//                Toast.makeText(MenuUsuario.this, "El usuario no existe", Toast.LENGTH_LONG).show();
//            }
//
//            db.close();
//        }
//    });
//
//
//    eliminar.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            AdminSQlite admin = new AdminSQlite(getApplicationContext(), "Biblioteca", null, 1);
//            SQLiteDatabase db = admin.getWritableDatabase();
//
//            int doc = Integer.parseInt(documento.getText().toString());
//
//            int c = db.delete("usuario","documento ="+doc, null);
//
//            if (c > 0) {
//                Toast.makeText(MenuUsuario.this, "Usuario eliminado con exito", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(MenuUsuario.this, "Usuario no existe", Toast.LENGTH_LONG).show();
//            }
//
//            nombre.setText("");
//            correo.setText("");
//            documento.setText("");
//
//            db.close();
//        }
//    });
//
//
//    actualizar.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            AdminSQlite admin = new AdminSQlite(getApplicationContext(), "Biblioteca", null, 1);
//            SQLiteDatabase db = admin.getWritableDatabase();
//
//            int doc = Integer.parseInt(documento.getText().toString());
//            String nom = nombre.getText().toString();
//            String email = correo.getText().toString();
//
//            ContentValues updateUsuario = new ContentValues();
//
//            updateUsuario.put("nombre", nom);
//            updateUsuario.put("correo", email);
//
//            db.update("usuario", updateUsuario, "documento="+doc, null);
//
//            db.close();
//
//            nombre.setText("");
//            correo.setText("");
//            documento.setText("");
//        }
//    });
//}


    //    Codigo SqlLite Local
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        nombre = findViewById(R.id.TextNombre);
        documento = findViewById(R.id.TextDocumento);
        correo = findViewById(R.id.TextCorreo);

        guardar = findViewById(R.id.BtnGuardar);
        consultar = findViewById(R.id.BtnConsultar);
        actualizar = findViewById(R.id.BtnActualizar);
        eliminar = findViewById(R.id.BtnEliminar);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdminSQlite admin = new AdminSQlite(getApplicationContext(), "Biblioteca", null, 1);
                SQLiteDatabase db = admin.getWritableDatabase();

                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher mather = pattern.matcher(correo.getText().toString());

                if (documento.getText().toString().equals("") || documento.getText().toString().length() < 3) {
                    documento.setError("Documento invalido");
                    return;
                }

                if (nombre.getText().toString().equals("") || nombre.getText().toString().length() < 2) {
                    nombre.setError("Nombre invalido");
                    return;
                }

                if (correo.getText().toString().equals("") || correo.getText().toString().length() < 3 || !mather.find() == true) {
                    correo.setError("Correo invalido");
                    return;
                }


                int doc = Integer.parseInt(documento.getText().toString());
                String nom = nombre.getText().toString();
                String email = correo.getText().toString();

                ContentValues datos = new ContentValues();

                datos.put("documento", doc);
                datos.put("nombre", nom);
                datos.put("correo", email);

                try {
                    db.insert("usuario", null, datos);
                    db.close();
                    Toast.makeText(MenuUsuario.this, "Usuario creado con exito", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MenuUsuario.this, "No se pudo crear el usuario", Toast.LENGTH_LONG).show();
                    return;
                }

                nombre.setText("");
                correo.setText("");
                documento.setText("");
            }
        });


        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQlite admin = new AdminSQlite(getApplicationContext(), "Biblioteca", null, 1);
                SQLiteDatabase db = admin.getWritableDatabase();

                if (documento.getText().toString().equals("") || documento.getText().toString().length() < 3) {
                    documento.setError("Documento invalido");
                    return;
                }

                int doc = Integer.parseInt(documento.getText().toString());

                try {

                    Cursor fila = db.rawQuery("select * from usuario where documento=" + doc, null);
                    if (fila.moveToFirst()) {
                        nombre.setText(fila.getString(1));
                        correo.setText((fila.getString(2)));
                    } else {
                        Toast.makeText(MenuUsuario.this, "El usuario no existe", Toast.LENGTH_LONG).show();
                    }

                    db.close();

                } catch (Exception e) {
                    Toast.makeText(MenuUsuario.this, "No se pudo consultar el usuario", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });


        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQlite admin = new AdminSQlite(getApplicationContext(), "Biblioteca", null, 1);
                SQLiteDatabase db = admin.getWritableDatabase();

                if (documento.getText().toString().equals("") || documento.getText().toString().length() < 3) {
                    documento.setError("Documento invalido");
                    return;
                }

                int doc = Integer.parseInt(documento.getText().toString());

                try {

                    int c = db.delete("usuario", "documento =" + doc, null);

                    if (c > 0) {
                        Toast.makeText(MenuUsuario.this, "Usuario eliminado con exito", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MenuUsuario.this, "Usuario no existe", Toast.LENGTH_LONG).show();
                    }
                    db.close();

                } catch (Exception e) {
                    Toast.makeText(MenuUsuario.this, "No se pudo eliminar el usuario", Toast.LENGTH_LONG).show();
                    return;
                }

                nombre.setText("");
                correo.setText("");
                documento.setText("");

            }
        });


        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQlite admin = new AdminSQlite(getApplicationContext(), "Biblioteca", null, 1);
                SQLiteDatabase db = admin.getWritableDatabase();

                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher mather = pattern.matcher(correo.getText().toString());

                if (documento.getText().toString().equals("") || documento.getText().toString().length() < 3) {
                    documento.setError("Documento invalido");
                    return;
                }

                if (nombre.getText().toString().equals("") || nombre.getText().toString().length() < 2) {
                    nombre.setError("Nombre invalido");
                    return;
                }

                if (correo.getText().toString().equals("") || correo.getText().toString().length() < 3 || !mather.find()) {
                    correo.setError("Correo invalido");
                    return;
                }

                int doc = Integer.parseInt(documento.getText().toString());
                String nom = nombre.getText().toString();
                String email = correo.getText().toString();

                ContentValues updateUsuario = new ContentValues();

                updateUsuario.put("nombre", nom);
                updateUsuario.put("correo", email);

                try {
                    db.update("usuario", updateUsuario, "documento=" + doc, null);
                    db.close();
                    Toast.makeText(MenuUsuario.this, "Usuario actualizado con exito", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MenuUsuario.this, "No se pudo actualizar el usuario", Toast.LENGTH_LONG).show();
                    return;
                }

                nombre.setText("");
                correo.setText("");
                documento.setText("");
            }
        });
    }

}