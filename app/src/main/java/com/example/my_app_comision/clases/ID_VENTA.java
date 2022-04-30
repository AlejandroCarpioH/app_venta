package com.example.my_app_comision.clases;

public class ID_VENTA {

    static Integer id=0;

    public ID_VENTA(){
        id++;
    }

    public static Integer Get_id(){
        return id;
    }
}
