package com.example.my_app_comision.clases;

public class ID_PRODUCTO {

    static Integer id=0;

    public ID_PRODUCTO(){
        id++;
    }

    public static Integer Get_id(){

        return id;
    }


}
