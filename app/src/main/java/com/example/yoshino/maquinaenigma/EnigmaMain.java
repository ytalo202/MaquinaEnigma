package com.example.yoshino.maquinaenigma;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EnigmaMain extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    ArrayList<String> input = new ArrayList<String>();
    ArrayList<String> rotor1O = new ArrayList<String>();
    ArrayList<String> rotor1D = new ArrayList<String>();
    ArrayList<String> rotor2D = new ArrayList<String>();
    ArrayList<String> rotor2O = new ArrayList<String>();
    ArrayList<String> rotor3D = new ArrayList<String>();
    ArrayList<String> rotor3O = new ArrayList<String>();
    ArrayList<String> refle = new ArrayList<String>();

    static final String PREFERENCIAS_COMPARTIDAD= "preferenciaCompartida";
    static final String LIST_INPUT ="listimput";
    static final String LIST_R1_O ="listR1O";
    static final String LIST_R2_O ="listR2O";
    static final String LIST_R3_O ="listR3O";
    static final String LIST_R1_D ="listR1D";
    static final String LIST_R2_D ="listR2D";
    static final String LIST_R3_D ="listR3D";
    static final String LIST_REF="listRef";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    EditText txtCrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enigma_main);

        loadData3R_Destino();
        loadData2R_Destino();
        loadData1R_Destino();
        loadData1R_Origen();
        loadData2R_Origen();
        loadData3R_Origen();
        loadData_Input();
        loadData_Refle();
        txtCrip = findViewById(R.id.txtCripto);
        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpaper_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.addFragment(new InputActivityFragment(),"");
        adapter.addFragment(new Rotor3ActivityFragment(),"");
        adapter.addFragment(new Rotor2ActivityFragment(),"");
        adapter.addFragment(new Rotor1ActivityFragment(),"");
        adapter.addFragment(new ReflectorActivityFragment(),"");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

       tabLayout.getTabAt(0).setIcon(R.drawable.ic_input_white);
      tabLayout.getTabAt(1).setIcon(R.drawable.ic_rotor_3_black);
      tabLayout.getTabAt(2).setIcon(R.drawable.ic_rotor_2_black);
      tabLayout.getTabAt(3).setIcon(R.drawable.ic_rotor_1_black);
      tabLayout.getTabAt(4).setIcon(R.drawable.ic_reflector_black);




        viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

                    @Override
                    public void onPageSelected(int position) {
                        switch (position) {
                            case 0:
                                tabLayout.getTabAt(0).setIcon(R.drawable.ic_input_white);
                                tabLayout.getTabAt(1).setIcon(R.drawable.ic_rotor_3_black);
                                tabLayout.getTabAt(2).setIcon(R.drawable.ic_rotor_2_black);
                                tabLayout.getTabAt(3).setIcon(R.drawable.ic_rotor_1_black);
                                tabLayout.getTabAt(4).setIcon(R.drawable.ic_reflector_black);
                                break;
                            case 1:
                                tabLayout.getTabAt(0).setIcon(R.drawable.ic_input_black);
                                tabLayout.getTabAt(1).setIcon(R.drawable.ic_rotor_3_white);
                                tabLayout.getTabAt(2).setIcon(R.drawable.ic_rotor_2_black);
                                tabLayout.getTabAt(3).setIcon(R.drawable.ic_rotor_1_black);
                                tabLayout.getTabAt(4).setIcon(R.drawable.ic_reflector_black);
                                break;
                            case 2:
                                tabLayout.getTabAt(0).setIcon(R.drawable.ic_input_black);
                                tabLayout.getTabAt(1).setIcon(R.drawable.ic_rotor_3_black);
                                tabLayout.getTabAt(2).setIcon(R.drawable.ic_rotor_2_white);
                                tabLayout.getTabAt(3).setIcon(R.drawable.ic_rotor_1_black);
                                tabLayout.getTabAt(4).setIcon(R.drawable.ic_reflector_black);
                                break;


                            case 3:
                                tabLayout.getTabAt(0).setIcon(R.drawable.ic_input_black);
                                tabLayout.getTabAt(1).setIcon(R.drawable.ic_rotor_3_black);
                                tabLayout.getTabAt(2).setIcon(R.drawable.ic_rotor_2_black);
                                tabLayout.getTabAt(3).setIcon(R.drawable.ic_rotor_1_white);
                                tabLayout.getTabAt(4).setIcon(R.drawable.ic_reflector_black);
                                break;


                            case 4:
                                tabLayout.getTabAt(0).setIcon(R.drawable.ic_input_black);
                                tabLayout.getTabAt(1).setIcon(R.drawable.ic_rotor_3_black);
                                tabLayout.getTabAt(2).setIcon(R.drawable.ic_rotor_2_black);
                                tabLayout.getTabAt(3).setIcon(R.drawable.ic_rotor_1_black);
                                tabLayout.getTabAt(4).setIcon(R.drawable.ic_reflector_white);
                                break;
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                }
        );

    }

    public void Cripto(View view) {



        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Encriptar");
        dialog.setContentView(R.layout.input_box);

        TextView txtMesaje = dialog.findViewById(R.id.txtmensaje);
        txtMesaje.setText("Letra");
        txtMesaje.setTextColor(Color.parseColor("#ff2222"));
        final EditText editText = dialog.findViewById(R.id.txtinput);


        Button bt = dialog.findViewById(R.id.btdone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              String  txtC = txtCrip.getText().toString();
                    String l = editText.getText().toString();
                    String nuevo = txtC+cripLetra(l);

                Toast.makeText(EnigmaMain.this, "L letra Encriptada es -> "+cripLetra(l), Toast.LENGTH_SHORT).show();

                txtCrip.setText(nuevo);

                dialog.dismiss();


            }
        });

        dialog.show();
    }







    public String cripLetra(String l){

        int  indexImput =buscarLetra(l,input);
        String letraR3_D = rotor3D.get(indexImput);
        Log.v(TAG, "Rotor 3 Destino -> "+letraR3_D);

        int  indexRotor3 =buscarLetra(letraR3_D,rotor3O);

        String letraR2_D = rotor2D.get(indexRotor3);
        Log.v(TAG, "Rotor 2 Destino -> "+letraR2_D);


        int  indexRotor2 =buscarLetra(letraR2_D,rotor2O);


        String letraR1_D = rotor1D.get(indexRotor2);
        Log.v(TAG, "Rotor 2 Destino -> "+letraR1_D);

        int  indexRotor1 =buscarLetra(letraR1_D,rotor1O);


        String letraR = refle.get(indexRotor1);



        Log.v(TAG, "Reflector -> "+letraR);



        int indexReflejo =  LetraReflejo(indexRotor1,letraR);



        String letraR1_O_R = rotor1O.get(indexReflejo);

        Log.v(TAG, "Rotor 1 Origen -> "+letraR1_O_R);

        int  indexRotor1_R =buscarLetra(letraR1_O_R,rotor1D);



        String letraR2_O_R = rotor2O.get(indexRotor1_R);

        Log.v(TAG, "Rotor 2 Origen -> "+letraR2_O_R);


        int  indexRotor2_R =buscarLetra(letraR2_O_R,rotor2D);



        String letraR3_O_R = rotor3O.get(indexRotor2_R);

        Log.v(TAG, "Rotor 3 Origen -> "+letraR3_O_R);


        int  indexRotor3_R =buscarLetra(letraR3_O_R,rotor3D);

        String letraImput = input.get(indexRotor3_R);

        return letraImput;

    }









    public void loadData3R_Destino()
    {    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_R3_D,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        rotor3D = gson.fromJson(json  ,type);

        if (rotor3D == null){
            rotor3D =new ArrayList<>();
        }
    }

    public void loadData2R_Destino()
    {    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_R2_D,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        rotor2D = gson.fromJson(json  ,type);

        if (rotor2D == null){
            rotor2D =new ArrayList<>();
        }
    }

    public void loadData1R_Destino()
    {    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_R1_D,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        rotor1D = gson.fromJson(json  ,type);

        if (rotor1D == null){
            rotor1D =new ArrayList<>();
        }
    }



    public void loadData3R_Origen()
    {    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_R3_O,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        rotor3O = gson.fromJson(json  ,type);

        if (rotor3O == null){
            rotor3O =new ArrayList<>();
        }
    }


    public void loadData2R_Origen()
    {    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_R2_O,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        rotor2O = gson.fromJson(json  ,type);

        if (rotor2O == null){
            rotor2O =new ArrayList<>();
        }
    }


    public void loadData1R_Origen()
    {    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_R1_O,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        rotor1O = gson.fromJson(json  ,type);

        if (rotor1O == null){
            rotor1O =new ArrayList<>();
        }
    }



    public void loadData_Input()
    {    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_INPUT,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        input = gson.fromJson(json  ,type);

        if (input == null){
            input =new ArrayList<>();
        }
    }


    public void loadData_Refle()
    {    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_REF,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        refle = gson.fromJson(json  ,type);

        if (refle == null){
            refle =new ArrayList<>();
        }
    }


    public int buscarLetra(String letra,ArrayList<String> avc){

        int indice=0;
        for(int i=0;i<avc.size();i++) {
            if (avc.get(i).equals(letra)) {
                indice = i;
            }
        }

        Log.v(TAG, "Letra ["+letra+"] -> "+indice);
        return indice;

    }


    public int LetraReflejo(int index,String letra){


        int num =  numLetraRefle(refle.get(index));
        int indice=0;
        if (num == 1){

            indice = index;

            Log.v(TAG, "Letra ["+letra+"] no se Repite  (Posicion) -> "+indice);

        }
        else {



            for (int i = 0; i < refle.size(); i++) {


                if (refle.get(i).equals(letra)) {
                    if (i!=index) {
                        indice = i;
                    }
                }
            }

            Log.v(TAG, "Letra ["+letra+"] se repite en (Posicion) -> "+indice);
        }


        return indice;

    }

    public int numLetraRefle(String letra){

        int num=0;
        for(int i=0;i<refle.size();i++) {
            if (refle.get(i).equals(letra)) {
                num++;
            }
        }

        // Log.v(TAG, "Letra ["+letra+"] Se Repite -> "+num);
        return num;

    }

}
