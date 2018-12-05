package com.example.yoshino.maquinaenigma;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class EnigmaMain extends AppCompatActivity {
    Metodos metodos = new Metodos();
    private static final String TAG = "MyActivity";
    ArrayList<String> input = new ArrayList<String>();
    ArrayList<String> rotor1O = new ArrayList<String>();
    ArrayList<String> rotor1D = new ArrayList<String>();
    ArrayList<String> rotor2D = new ArrayList<String>();
    ArrayList<String> rotor2O = new ArrayList<String>();
    ArrayList<String> rotor3D = new ArrayList<String>();
    ArrayList<String> rotor3O = new ArrayList<String>();
    ArrayList<String> refle = new ArrayList<String>();
    ArrayList<Diccionario> diccionario = new ArrayList<>();

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
    diccionario = metodos.llenarDiccionario();
//        txtCrip = findViewById(R.id.txtCripto);
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


    String mensajeEncritado;
    public void btnEncriptar(View view) {

        loadListas();

        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Encriptar");
        dialog.setContentView(R.layout.dialog_encriptar);
        final EditText editText = dialog.findViewById(R.id.txtMensaje);
        mensajeEncritado ="";


        Button bt = dialog.findViewById(R.id.btnOk);
       final TextView txtMsCrip = dialog.findViewById(R.id.txtmsEncriptado);
        RelativeLayout btcopi = dialog.findViewById(R.id.btncopy);


            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editText.getText().toString().trim().equals("")) {
                        Toast.makeText(getApplication(), "Ingrese mensaje ha encriptar", Toast.LENGTH_SHORT).show();
                    } else {



                        String msj = editText.getText().toString();


                        for (int i = 0; i < msj.length(); i++) {
                            String letra = String.valueOf(msj.charAt(i));
                            String letraEncriptada = metodos.cripLetra(letra, input, rotor3O, rotor3D, rotor2O, rotor2D, rotor1O, rotor1D, refle, i);
                            mensajeEncritado = mensajeEncritado + letraEncriptada;
                        }
                        txtMsCrip.setText(mensajeEncritado);
                        mensajeEncritado= "";
                    }
                }
            });

            btcopi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplication(), "Mensaje Encriptado Copiado", Toast.LENGTH_SHORT).show();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    clipboard.setText(txtMsCrip.getText().toString());
                }
            });

            Button btcancel = dialog.findViewById(R.id.btnCancelar);
            btcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

    }
    public void loadListas(){
        loadData3R_Destino();
        loadData2R_Destino();
        loadData1R_Destino();
        loadData1R_Origen();
        loadData2R_Origen();
        loadData3R_Origen();
        loadData_Input();
        loadData_Refle();
    }

    public void btnDesenciptar(View view) {



        loadListas();
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Desenciptar");
        dialog.setContentView(R.layout.dialog_encriptar);
        final EditText editText = dialog.findViewById(R.id.txtMensaje);
        mensajeEncritado = "";


        Button bt = dialog.findViewById(R.id.btnOk);
        final TextView txtMsCrip = dialog.findViewById(R.id.txtmsEncriptado);
        RelativeLayout btcopi = dialog.findViewById(R.id.btncopy);


            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editText.getText().toString().trim().equals("")) {
                        Toast.makeText(getApplication(), "Ingrese mensaje ha desencriptar", Toast.LENGTH_SHORT).show();
                    } else {
                        String msj = editText.getText().toString();
                        for (int i = 0; i < msj.length(); i++) {
                            String letra = String.valueOf(msj.charAt(i));
                            String letraEncriptada = metodos.cripLetra(letra, input, rotor3O, rotor3D, rotor2O, rotor2D, rotor1O, rotor1D, refle, i);
                            mensajeEncritado = mensajeEncritado + letraEncriptada;
                        }
                        txtMsCrip.setText(mensajeEncritado);
                        // dialog.dismiss();
                    }
                }
            });

            btcopi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplication(), "Mensaje Copiado", Toast.LENGTH_SHORT).show();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    clipboard.setText(txtMsCrip.getText().toString());
                }
            });

            Button btcancel = dialog.findViewById(R.id.btnCancelar);
            btcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

    }


}
