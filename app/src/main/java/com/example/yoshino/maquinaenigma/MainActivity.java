package com.example.yoshino.maquinaenigma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Comparator.comparing;

public class MainActivity extends AppCompatActivity {

Metodos metodos = new Metodos();
    static final String PREFERENCIAS_COMPARTIDAD = "preferenciaCompartida";
    static final String LIST_INPUT = "listimput";
    static final String LIST_R1_O = "listR1O";
    static final String LIST_R2_O = "listR2O";
    static final String LIST_R3_O = "listR3O";
    static final String LIST_R1_D = "listR1D";
    static final String LIST_R2_D = "listR2D";
    static final String LIST_R3_D = "listR3D";
    static final String LIST_REF = "listRef";

    private static final String TAG = "MyActivity";
    ArrayList<String> input = new ArrayList<String>();
    ArrayList<String> rotor1O = new ArrayList<String>();
    ArrayList<String> rotor1D = new ArrayList<String>();
    ArrayList<String> rotor2D = new ArrayList<String>();
    ArrayList<String> rotor2O = new ArrayList<String>();
    ArrayList<String> rotor3D = new ArrayList<String>();
    ArrayList<String> rotor3O = new ArrayList<String>();
    ArrayList<String> refle = new ArrayList<String>();
    ArrayList<String> num = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("se llena","lol");
        llenadoInput();
        llenadoReflector();
        llenadoRotor1_O();
        llenadoRotor1_D();
        llenadoRotor2_O();
        llenadoRotor2_D();
        llenadoRotor3_O();
        llenadoRotor3_D();
        //visualizar(input);

        //  llenadoNum();
        // doTrama(input,"13",false);
        //visualizarAvc(doTrama(input, "13", false));

        //visualizarAvc(doTramposicion(input,"132",false));

        //visualizar(num);
        // visualizarAvc(su);
        // visualizarAvc(avc);


//        llenadoAV();
//        Collections.sort(avc, new SortbyAsc());
//        visualizarAvc(avc);
//
//        String l ="a";
//        String l1 ="e";
//        cripLetra(l);
//
//        Log.v(TAG, "Letra Encriptada -> "+cripLetra(l));
//        Log.v(TAG, "Letra des Encriptada -> "+descCripLetra(l1));
       // String clave = "123";


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


//                saveArraySharedPreference(input, LIST_INPUT);
//                saveArraySharedPreference(rotor1D, LIST_R1_D);
//                saveArraySharedPreference(rotor2D, LIST_R2_D);
//                saveArraySharedPreference(rotor3D, LIST_R3_D);
//                saveArraySharedPreference(rotor1O, LIST_R1_O);
//                saveArraySharedPreference(rotor2O, LIST_R2_O);
//                saveArraySharedPreference(rotor3O, LIST_R3_O);
//                saveArraySharedPreference(refle, LIST_REF);


                Intent intent = new Intent(getApplicationContext(), EnigmaMain.class);
                startActivity(intent);
                finish();

            }
        }, 1000);


    }


    public void saveArraySharedPreference(ArrayList<String> lista, String name) {


        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_COMPARTIDAD, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lista);
        editor.putString(name, json);
        editor.apply();
    }




    public ArrayList<Avc> doTramposicion(ArrayList<String> avcEntra, String clave, boolean isAsc) {

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


    public ArrayList<Avc> doTrama(ArrayList<String> avcEntra, String clave, boolean isAsc) {

        ArrayList<Avc> avcSale = new ArrayList<>();
        String p = clave;
        char[] l = p.toCharArray();
        int g = 0;

        int h = 0;
        int so = avcEntra.size() % l.length;

        for (int i = 0; i < avcEntra.size(); i++) {

            if (so != 0 && i >= avcEntra.size() - so) {
                avcSale.add(new Avc(avcEntra.get(i)));
            } else {
                avcSale.add(new Avc(l[g], avcEntra.get(i)));
                h++;
                if (h == l.length) {
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


    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_COMPARTIDAD, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_INPUT, null);

        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        input = gson.fromJson(json, type);

        if (input == null) {
            input = new ArrayList<>();
        }
    }


    public String cripLetra(String l) {

        int indexImput = buscarLetra(l, input);
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


        int indexReflejo = LetraReflejo(indexRotor1, letraR);


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


    public String descCripLetra(String l) {

        int indexImput = buscarLetra(l, input);

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


        int indexReflejo = LetraReflejo(indexRotor1, letraR);


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

        return letraR;

    }


    //LLenado Input
    public void llenadoInput() {
        input.add("a");
        input.add("b");
        input.add("c");
        input.add("d");
        input.add("e");
    }


    //LLenado Rotor1 Origen y Destino

    public void llenadoRotor1_O() {
        rotor1O.add("c");
        rotor1O.add("d");
        rotor1O.add("e");
        rotor1O.add("a");
        rotor1O.add("b");

    }

    public void llenadoRotor1_D() {

        rotor1D.add("e");
        rotor1D.add("b");
        rotor1D.add("d");
        rotor1D.add("a");
        rotor1D.add("c");

    }


    //LLenado Rotor1 Origen y Destino

    public void llenadoRotor2_O() {
        rotor2O.add("a");
        rotor2O.add("b");
        rotor2O.add("c");
        rotor2O.add("d");
        rotor2O.add("e");

    }

    public void llenadoRotor2_D() {

        rotor2D.add("e");
        rotor2D.add("d");
        rotor2D.add("b");
        rotor2D.add("a");
        rotor2D.add("c");

    }


    //LLenado Rotor1 Origen y Destino

    public void llenadoRotor3_O() {
        rotor3O.add("e");
        rotor3O.add("d");
        rotor3O.add("c");
        rotor3O.add("b");
        rotor3O.add("a");

    }

    public void llenadoRotor3_D() {

        rotor3D.add("c");
        rotor3D.add("b");
        rotor3D.add("a");
        rotor3D.add("d");
        rotor3D.add("e");

    }


    //LLenado Reflector

    public void llenadoReflector() {
        refle.add("b");
        refle.add("c");
        refle.add("a");
        refle.add("c");
        refle.add("b");

    }


    private void visualizar(ArrayList<String> avc) {

        Log.v(TAG, "------");

        for (int i = 0; i < avc.size(); i++) {
            Log.v(TAG, String.valueOf(avc.get(i)));
        }

        Log.v(TAG, "------");
    }


    private void visualizarAvc(ArrayList<Avc> avc) {

        Log.v(TAG, "------Avc");

        for (int i = 0; i < avc.size(); i++) {
            Log.v(TAG, String.valueOf(avc.get(i).getLetra()) + " " + String.valueOf(avc.get(i).getIndex()));
        }

        Log.v(TAG, "------FinAvc");
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


    public int LetraReflejo(int index, String letra) {


        int num = numLetraRefle(refle.get(index));
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


    public int numLetraRefle(String letra) {

        int num = 0;
        for (int i = 0; i < refle.size(); i++) {
            if (refle.get(i).equals(letra)) {
                num++;
            }
        }

        // Log.v(TAG, "Letra ["+letra+"] Se Repite -> "+num);
        return num;

    }


}
