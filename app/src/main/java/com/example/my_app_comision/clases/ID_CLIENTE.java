package com.example.my_app_comision.clases;

public class ID_CLIENTE {
    static Integer id=0;

    public ID_CLIENTE(){
        id++;
    }

    public static Integer Get_id(){
        return id;
    }

}
