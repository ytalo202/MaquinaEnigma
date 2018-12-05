package com.example.yoshino.maquinaenigma.saltos;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Alfabeto invertido de 26 letras que empieza en la letra P
        List<Character> alphabet1 = Crypto.generateAlphabet(Alphabet.EN, true, 'P');
        display(alphabet1);
        // Aplicamos saltos sucesivos a alphabet1; EMPIEZA en P con variable 353IDI
        alphabet1 = Crypto.successiveJumps(alphabet1, "352IDI", true);
        display(alphabet1);

        System.out.println();

//        // Alfabeto ordenado de 27 letras que empieza en la letra A
//        List<Character> alphabet2 = Crypto.generateAlphabet(Alphabet.ES, false, 'A');
//        display(alphabet2);
//        // Aplicamos saltos sucesivos a alphabet2; INICIA en A con variable 572DID
//        alphabet2 = Crypto.successiveJumps(alphabet2, "572DID", true);
//        display(alphabet2);
//
//        System.out.println();
//
//        // Alfabeto invertido de 26 letras que empieza en la letra K
//        List<Character> alphabet3 = Crypto.generateAlphabet(Alphabet.EN, true, 'K');
//        display(alphabet3);
//        // Aplicamos saltos sucesivos a alphabet3; EMPIEZA en K con variable 423
//        alphabet3 = Crypto.successiveJumps(alphabet3, "423", false);
//        display(alphabet3);
//
//        System.out.println();
//
//        // Alfabeto invertido de 27 letras que empieza en la letra Ñ
//        List<Character> alphabet4 = Crypto.generateAlphabet(Alphabet.ES, true, 'Ñ');
//        display(alphabet4);
//        // Aplicamos TRANSPOSICION a alphabet4 con variable 7586213
//        alphabet4 = Crypto.transposition(alphabet4, "7586213");
//        display(alphabet4);
//
//        System.out.println();
//
//        // Alfabeto ordenado de 26 letras que empieza en la letra Q
//        List<Character> alphabet5 = Crypto.generateAlphabet(Alphabet.EN, false, 'Q');
//        display(alphabet5);
//        // Aplicamos TRAMA a alphabet5 con variable 5147
//        alphabet5 = Crypto.plot(alphabet5, "1547");
//        display(alphabet5);
    }

    public static void display(List<Character> alphabet) {
        for (Character c : alphabet) {
            System.out.printf("%c", c);
        }
        System.out.println();
    }
}
