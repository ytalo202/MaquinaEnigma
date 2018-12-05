package com.example.yoshino.maquinaenigma;

public class Diccionario {
    String letra;
    int numero;

    public Diccionario(String letra, int numero) {
        this.letra = letra;
        this.numero = numero;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
