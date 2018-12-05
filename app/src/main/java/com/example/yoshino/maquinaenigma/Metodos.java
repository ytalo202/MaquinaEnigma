package com.example.yoshino.maquinaenigma;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Metodos {
    private static final String TAG = "MyActivity";
//    ReflectorActivityFragment reflectorActivityFragment = new ReflectorActivityFragment();
    public ArrayList<Avc> doTrama(ArrayList<String> avcEntra, String clave, boolean isAsc) {

        ArrayList<Avc> avcSale = new ArrayList<>();
        String p = clave;
        char[] l = p.toCharArray();
        int g = 0;
        int h = 0;
        int so = avcEntra.size() % l.length;
        int grupos = avcEntra.size() / l.length;

        for (int i = 0; i < avcEntra.size(); i++) {

            if (so != 0 && i >= avcEntra.size() - so) {
                avcSale.add(new Avc(avcEntra.get(i)));
            } else {
                avcSale.add(new Avc(l[g], avcEntra.get(i)));
                h++;
                if (h == grupos) {
                    g++;
                    h = 0;
                }
                if (isAsc) {
                    Collections.sort(avcSale, new SortbyAsc());
                } else {
                    Collections.sort(avcSale, new SortbyDes());
                }
            }
        }
        return avcSale;
    }


    public String getStringListCharacters(List<Character> list)
    {
        StringBuilder builder = new StringBuilder(list.size());
        for(Character ch: list)
        {
            builder.append(ch);
        }
        return builder.toString();
    }

    public ArrayList<String> TramaInversa(ArrayList<String> avcEntra, String clave, boolean isAsc)
    {
        String p = clave;
        char[] l = p.toCharArray();
        ArrayList<Avc> lol =  new ArrayList<>();
        ArrayList<String> loll2 = avcEntra;
        int grupos = avcEntra.size() / l.length;
        int so = avcEntra.size() % l.length;
        int y=0;
        if (so!=0){
            y=1;
        }
        grupos = grupos+ so;
        for (int i =0 ;i<l.length-1;i++){
            lol =  doTrama(loll2, clave,  isAsc);
            loll2 = (ArrayList<String>) parsearList(lol);
        }

        return loll2 ;

//        ArrayList<Avc> lol = doTrama( avcEntra, clave,  isAsc);
//        ArrayList<String> loll = (ArrayList<String>) parsearList(lol);
//
//
//        ArrayList<Avc> lol12 = doTrama( loll, clave,  isAsc);
//
//        return  loll2;
    }

    public void rebovinarTrama (ArrayList<String> avcEntra, String clave, boolean isAsc){
        ArrayList<Avc> lol = doTrama( avcEntra, clave,  isAsc);
    }



    public ArrayList<Avc> doTransposicion(ArrayList<String> avcEntra, String clave, boolean isAsc) {

        ArrayList<Avc> avcSale  = new ArrayList<>();
        ArrayList<Avc> avcNew  = new ArrayList<>();
        List<Avc> avcMedio  = new ArrayList<>();
        String p = clave;
        char[] l = p.toCharArray();
        int g = 0;

        for (int i = 0; i < avcEntra.size(); i++) {


            avcNew.add(new Avc(l[g], avcEntra.get(i)));
            g++;
            if (g == l.length || i + 1 == avcEntra.size()) {


                avcMedio = avcNew.subList(Math.abs(g - i - 1), i + 1);
                if (isAsc){
                    Collections.sort(avcMedio, new SortbyAsc());
                }
                else {
                    Collections.sort(avcMedio, new SortbyDes());
                }
                avcSale.addAll(avcMedio);
                g = 0;
            }
        }
        return avcSale;
    }

    public static String reverse(String input){
        char[] in = input.toCharArray();
        int begin=0;
        int end=in.length-1;
        char temp;
        while(end>begin){
            temp = in[begin];
            in[begin]=in[end];
            in[end] = temp;
            end--;
            begin++;
        }
        return new String(in);
    }


//    public ArrayList<Avc> doTransposicionInverso(ArrayList<String> avcEntra, String clave, boolean isAsc) {
//
//        ArrayList<Avc> avcSale  = new ArrayList<>();
//        ArrayList<Avc> avcNew  = new ArrayList<>();
//        List<Avc> avcMedio  = new ArrayList<>();
//        String p = reverse(clave);
//        char[] l =  p.toCharArray();
//        int g = 0;
//
//        for (int i = 0; i < avcEntra.size(); i++) {
//
//
//            avcNew.add(new Avc(l[g], avcEntra.get(i)));
//            g++;
//            if (g == l.length || i + 1 == avcEntra.size()) {
//
//
//                avcMedio = avcNew.subList(Math.abs(g - i - 1), i + 1);
//                if (isAsc){
//                    Collections.sort(avcMedio, new SortbyAsc());
//                }
//                else {
//                    Collections.sort(avcMedio, new SortbyDes());
//                }
//                avcSale.addAll(avcMedio);
//                g = 0;
//            }
//        }
//        return avcSale;
//    }


    public List<String> parsearList(ArrayList<Avc> avc) {
        List<String> newList = new ArrayList<>();

        for (int i = 0; i < avc.size(); i++) {
            newList.add(avc.get(i).getLetra());
        }

       return newList;
    }


    public String parsearString(ArrayList<String> avc) {
        String listString = "";

        for (String s : avc)
        {
            listString = listString+s;
        }
        return listString;
    }


    public ArrayList<String> llenarAvc() {

        ArrayList<String> avc = new ArrayList<>();
        avc.add("a");
        avc.add("b");
        avc.add("c");
        avc.add("d");
        avc.add("e");
        avc.add("f");
        avc.add("g");
        avc.add("h");
        avc.add("i");
        avc.add("j");
        avc.add("k");
        avc.add("l");
        avc.add("m");
        avc.add("n");
        avc.add("ñ");
        avc.add("o");
        avc.add("p");
        avc.add("q");
        avc.add("r");
        avc.add("s");
        avc.add("t");
        avc.add("u");
        avc.add("v");
        avc.add("w");
        avc.add("x");
        avc.add("y");
        avc.add("z");
        avc.add(" ");
        avc.add("0");
        avc.add("1");
        avc.add("2");
        avc.add("3");
        avc.add("4");
        avc.add("5");
        avc.add("6");
        avc.add("7");
        avc.add("8");
        avc.add("9");

        return avc;
    }



    public ArrayList<Diccionario> llenarDiccionario() {
        ArrayList<Diccionario> avc = new ArrayList<>();
        avc.add(new Diccionario("a",1));
        avc.add(new Diccionario("b",2));
        avc.add(new Diccionario("c",3));
        avc.add(new Diccionario("d",4));
        avc.add(new Diccionario("e",5));
        avc.add(new Diccionario("f",6));
        avc.add(new Diccionario("g",7));
        avc.add(new Diccionario("h",8));
        avc.add(new Diccionario("i",9));
        avc.add(new Diccionario("j",1));
        avc.add(new Diccionario("k",2));
        avc.add(new Diccionario("l",3));
        avc.add(new Diccionario("m",4));
        avc.add(new Diccionario("n",5));
        avc.add(new Diccionario("ñ",6));
        avc.add(new Diccionario("o",7));
        avc.add(new Diccionario("p",8));
        avc.add(new Diccionario("q",9));
        avc.add(new Diccionario("r",1));
        avc.add(new Diccionario("s",2));
        avc.add(new Diccionario("t",3));
        avc.add(new Diccionario("u",4));
        avc.add(new Diccionario("v",5));
        avc.add(new Diccionario("w",6));
        avc.add(new Diccionario("x",7));
        avc.add(new Diccionario("y",8));
        avc.add(new Diccionario("z",9));


        return avc;
    }


    public int getNumeroXLetra(String letra, ArrayList<Diccionario> avc) {

        int num = 0;
        for (int i = 0; i < avc.size(); i++) {
            if (avc.get(i).getLetra().equals(letra)) {
                num = avc.get(i).getNumero();
            }
        }

        return num;
    }


    public String parsearPalabraClave(String palabra,ArrayList<Diccionario> avc) {
        String listString = "";

        for (int j = 0; j < palabra.length(); j++) { // recorremos la cadena para contar los caracteres del indice cero
            listString = listString +getNumeroXLetra(String.valueOf(palabra.charAt(j)),avc);
        }
        return listString;
    }


    public ArrayList<String> llenarAvcReflector() {

        ArrayList<String> avc = new ArrayList<>();
        avc.add("a");
        avc.add("b");
        avc.add("c");
        avc.add("d");
        avc.add("e");
        avc.add("f");
        avc.add("g");
        avc.add("h");
        avc.add("i");
        avc.add("j");
        avc.add("k");
        avc.add("l");
        avc.add("m");
        avc.add("n");
        avc.add("ñ");
        avc.add("o");
        avc.add("p");
        avc.add("q");
        avc.add("r");

        avc.add("a");
        avc.add("b");
        avc.add("c");
        avc.add("d");
        avc.add("e");
        avc.add("f");
        avc.add("g");
        avc.add("h");
        avc.add("i");
        avc.add("j");
        avc.add("k");
        avc.add("l");
        avc.add("m");
        avc.add("n");
        avc.add("ñ");
        avc.add("o");
        avc.add("p");
        avc.add("q");
        avc.add("r");

        return avc;
    }

    public ArrayList<String> convertStringToArraylist(String str) {
        ArrayList<String> charList = new ArrayList<String>();
        for(int i = 0; i<str.length();i++){
            charList.add(String.valueOf(str.charAt(i)));
        }
        return charList;
    }


    public String cripLetra(String l,ArrayList<String> input,
                            ArrayList<String> rotor3OO,ArrayList<String> rotor3D,
                            ArrayList<String> rotor2O,ArrayList<String> rotor2D,
                            ArrayList<String> rotor1O,ArrayList<String> rotor1D,

                            ArrayList<String> refle,int i) {
        ArrayList<String> rotor3O = new ArrayList<>();
         rotor3O.addAll(rotor3OO);

        int indexImput = buscarLetra(l, input);

        if (i!=0) {
            Collections.rotate(rotor3O, i);
        }
        String letraR3_D = rotor3D.get(indexImput);
        Log.v(TAG, "Rotor 3 Destino -> " + letraR3_D);

        int indexRotor3 = buscarLetra(letraR3_D, rotor3O);

        String letraR2_D = rotor2D.get(indexRotor3);
        Log.v(TAG, "Rotor 2 Destino -> " + letraR2_D);

        int indexRotor2 = buscarLetra(letraR2_D, rotor2O);

        String letraR1_D = rotor1D.get(indexRotor2);
        Log.v(TAG, "Rotor 2 Destino -> " + letraR1_D);

        int indexRotor1 = buscarLetra(letraR1_D, rotor1O);


        String letraR = refle.get(indexRotor1);


        Log.v(TAG, "Reflector -> " + letraR);


        int indexReflejo = LetraReflejo(indexRotor1, letraR,refle);


        String letraR1_O_R = rotor1O.get(indexReflejo);

        Log.v(TAG, "Rotor 1 Origen -> " + letraR1_O_R);

        int indexRotor1_R = buscarLetra(letraR1_O_R, rotor1D);


        String letraR2_O_R = rotor2O.get(indexRotor1_R);

        Log.v(TAG, "Rotor 2 Origen -> " + letraR2_O_R);


        int indexRotor2_R = buscarLetra(letraR2_O_R, rotor2D);


        String letraR3_O_R = rotor3O.get(indexRotor2_R);

        Log.v(TAG, "Rotor 3 Origen -> " + letraR3_O_R);


        int indexRotor3_R = buscarLetra(letraR3_O_R, rotor3D);

        String letraImput = input.get(indexRotor3_R);

        return letraImput;

    }


    public int buscarLetra(String letra, ArrayList<String> avc) {

        int indice = 0;
        for (int i = 0; i < avc.size(); i++) {
            if (avc.get(i).equals(letra)) {
                indice = i;
            }
        }

        Log.v(TAG, "Letra [" + letra + "] -> " + indice);
        return indice;

    }


    public int LetraReflejo(int index, String letra,ArrayList<String> refle) {


        int num = numLetraRefle(refle.get(index),refle);
        int indice = 0;
        if (num == 1) {

            indice = index;

            Log.v(TAG, "Letra [" + letra + "] no se Repite  (Posicion) -> " + indice);

        } else {


            for (int i = 0; i < refle.size(); i++) {


                if (refle.get(i).equals(letra)) {
                    if (i != index) {
                        indice = i;
                    }
                }
            }

            Log.v(TAG, "Letra [" + letra + "] se repite en (Posicion) -> " + indice);
        }


        return indice;

    }


    public int numLetraRefle(String letra,ArrayList<String> refle) {

        int num = 0;
        for (int i = 0; i < refle.size(); i++) {
            if (refle.get(i).equals(letra)) {
                num++;
            }
        }

        // Log.v(TAG, "Letra ["+letra+"] Se Repite -> "+num);
        return num;

    }


    public int contar(String cadena) {
        int contador = 0;
        char caracter = 0;
        while (cadena.length() != 0) { // mientras la cadena tenga algún carácter la recorremos
            int contadorAux = 0;
            for (int j = 0; j < cadena.length(); j++) { // recorremos la cadena para contar los caracteres del indice cero
                if (cadena.charAt(0) == cadena.charAt(j)) {
                    contadorAux++;
                }
            }
            if (contadorAux > contador) { // si el contador del nuevo caracter es mayor al que ya estaba guardado, lo cambiamos
                contador = contadorAux;
                caracter = cadena.charAt(0);
            }
            // borramos los carácteres contados para ahorrar entrar mas veces para contarlos otra vez
            cadena = cadena.replaceAll(cadena.charAt(0) + "", "");
        }
        return contador;
    }

}
