package com.juangut.actividad1;

import com.orm.SugarRecord;

public class Usuario extends SugarRecord<Usuario> {

    String documento;
    String nombre;
    String correo;

    public Usuario() {
    }

    public Usuario(String documento, String nombre, String correo) {
        this.documento = documento;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
