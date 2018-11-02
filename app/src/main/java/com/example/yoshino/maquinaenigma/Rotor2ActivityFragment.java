package com.example.yoshino.maquinaenigma;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class Rotor2ActivityFragment extends Fragment {
    View v;


    static final String PREFERENCIAS_COMPARTIDAD= "preferenciaCompartida";
    static final String LIST_INPUT ="listimput";
    static final String LIST_R1_O ="listR1O";
    static final String LIST_R2_O ="listR2O";
    static final String LIST_R3_O ="listR3O";
    static final String LIST_R1_D ="listR1D";
    static final String LIST_R2_D ="listR2D";
    static final String LIST_R3_D ="listR3D";
    static final String LIST_REF="listRef";

    ArrayList<String> arrayListRotor2_O;
    ArrayList<String> arrayListRotor2_D;
    ArrayAdapter<String> arrayAdapterOrigen;
    ArrayAdapter<String> arrayAdapterDestino;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.rotor2_fragment,container,false);


        loadDataOrigen();
        loadDataDestino();
        ListView listOrigen = v.findViewById(R.id.listRotor2O);
        ListView listDestino = v.findViewById(R.id.listRotor2D);



        arrayAdapterOrigen = new ArrayAdapter<String>(getActivity(),R.layout.list_item,R.id.txtLetra,arrayListRotor2_O);
        arrayAdapterDestino = new ArrayAdapter<String>(getActivity(),R.layout.list_item,R.id.txtLetra,arrayListRotor2_D);

        listOrigen.setAdapter(arrayAdapterOrigen);
        listDestino.setAdapter(arrayAdapterDestino);

        listOrigen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showInputBoxOrigen(arrayListRotor2_O.get(position),position);
            }
        });

        listDestino.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showInputBoxDestino(arrayListRotor2_D.get(position),position);
            }
        });

        return v;

    }



    public void loadDataOrigen()
    {    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_R2_O,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        arrayListRotor2_O = gson.fromJson(json  ,type);

        if (arrayListRotor2_O == null){
            arrayListRotor2_O =new ArrayList<>();
        }
    }

    public void loadDataDestino()
    {    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_R2_D,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        arrayListRotor2_D = gson.fromJson(json  ,type);

        if (arrayListRotor2_D == null){
            arrayListRotor2_D =new ArrayList<>();
        }
    }

    public  void showInputBoxOrigen(String oldItem,final int index){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.input_box);

        TextView txtMesaje = dialog.findViewById(R.id.txtmensaje);
        txtMesaje.setText("Update item");
        txtMesaje.setTextColor(Color.parseColor("#ff2222"));
        final EditText editText = dialog.findViewById(R.id.txtinput);
        editText.setText(oldItem);

        Button bt = dialog.findViewById(R.id.btdone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayListRotor2_O.set(index,editText.getText().toString());
                arrayAdapterOrigen.notifyDataSetChanged();
                dialog.dismiss();
                saveArraySharedPreference(arrayListRotor2_O,LIST_R2_O);
            }
        });

        dialog.show();
    }



    public  void showInputBoxDestino(String oldItem,final int index){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.input_box);

        TextView txtMesaje = dialog.findViewById(R.id.txtmensaje);
        txtMesaje.setText("Update item");
        txtMesaje.setTextColor(Color.parseColor("#ff2222"));
        final EditText editText = dialog.findViewById(R.id.txtinput);
        editText.setText(oldItem);

        Button bt = dialog.findViewById(R.id.btdone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayListRotor2_D.set(index,editText.getText().toString());
                arrayAdapterDestino.notifyDataSetChanged();
                dialog.dismiss();
                saveArraySharedPreference(arrayListRotor2_D,LIST_R2_D);
            }
        });

        dialog.show();
    }



    public void saveArraySharedPreference(ArrayList<String> lista,String name){


        SharedPreferences sharedPreferences = getActivity(). getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lista);
        editor.putString(name,json);
        editor.apply();
    }

}
