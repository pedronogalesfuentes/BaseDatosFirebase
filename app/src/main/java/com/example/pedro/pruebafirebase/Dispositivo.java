package com.example.pedro.pruebafirebase;

/**
 * Created by pedro on 28/04/2017.
 */

/*
MAKING A POJO FOR FIREBASE
Podemos almacenar objetos JAVA, Plane Old Java Objects (POJO). Para ello, los objetos JAVA han de seguir 3 reglas:
(1) Las variables del objeto han de coincidir con los nombres de los hijos y ser de un tipo válido.
(2) Debe tener al menos un constructor vacío
(3) Ha de tener “getters” públicos para cada variable
 */

public class Dispositivo {
    private String id;
    private String modelo;
    private String imei;

    public Dispositivo() {
    }

    public Dispositivo(String id, String modelo, String imei) {
        this.id = id;
        this.modelo = modelo;
        this.imei = imei;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
