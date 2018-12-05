package com.example.yoshino.maquinaenigma;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoshino.maquinaenigma.saltos.Crypto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Rotor3ActivityFragment extends Fragment {
    View v;
    Metodos metodos = new Metodos();
    static final String PREFERENCIAS_COMPARTIDAD= "preferenciaCompartida";
    static final String LIST_INPUT ="listimput";
    static final String LIST_R1_O ="listR1O";
    static final String LIST_R2_O ="listR2O";
    static final String LIST_R3_O ="listR3O";
    static final String LIST_R1_D ="listR1D";
    static final String LIST_R2_D ="listR2D";
    static final String LIST_R3_D ="listR3D";
    static final String LIST_REF="listRef";

    ArrayList<String> arrayListRotor3_O;
    ArrayList<String> arrayListRotor3_D;
    ArrayAdapter<String> arrayAdapterOrigen;
    ArrayAdapter<String> arrayAdapterDestino;
    ListView listOrigen;
    ListView listDestino;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDataOrigen();
        loadDataDestino();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v("se crea","nuevo");
        v = inflater.inflate(R.layout.rotor3_fragment,container,false);

//        arrayListRotor3_O = metodos.llenarAvc();
//        arrayListRotor3_D = metodos.llenarAvc();

         listOrigen = v.findViewById(R.id.listRotor3O);
         listDestino = v.findViewById(R.id.listRotor3D);
        TextView btnTranspD3 = v.findViewById(R.id.btnTranspD3);
        TextView btnTramaD3 = v.findViewById(R.id.btnTramaD3);
        TextView btnTranspO3 = v.findViewById(R.id.btnTranspO3);
        TextView btnTramaO3 = v.findViewById(R.id.btnTramaO3);
        TextView btnSaltoO3 = v.findViewById(R.id.btnSaltoO3);
        TextView btnSaltoD3 = v.findViewById(R.id.btnSaltoD3);

        arrayAdapterOrigen = new ArrayAdapter<String>(getActivity(),R.layout.list_item,R.id.txtLetra,arrayListRotor3_O);
        arrayAdapterDestino = new ArrayAdapter<String>(getActivity(),R.layout.list_item,R.id.txtLetra,arrayListRotor3_D);

        listOrigen.setAdapter(arrayAdapterOrigen);
        listDestino.setAdapter(arrayAdapterDestino);

        FloatingActionButton floatR3refresh = v.findViewById(R.id.cbtnr3Refresh);
        FloatingActionButton cbtnr3Save = v.findViewById(R.id.cbtnr3Save);
        cbtnr3Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Desea Continuar ...");
                builder.setTitle("Mensaje:");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        saveArraySharedPreference(arrayListRotor3_D,LIST_R3_D);
                        saveArraySharedPreference(arrayListRotor3_O,LIST_R3_O);
                        Toast.makeText(getActivity(), "Abecedarios Guardados", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        floatR3refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Desea Continuar ...");
                builder.setTitle("Mensaje:");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.v("se actualiza","nuevo");
                        refresh();
                        Toast.makeText(getActivity(), "Hecho", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });






        btnTramaD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogTrama(arrayListRotor3_D,arrayAdapterDestino);
            }
        });

        btnTranspD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogTranposicion(arrayListRotor3_D,arrayAdapterDestino);
            }
        });


        btnTramaO3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogTrama(arrayListRotor3_O,arrayAdapterOrigen);
            }
        });

        btnTranspO3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogTranposicion(arrayListRotor3_O,arrayAdapterOrigen);
            }
        });

        btnSaltoO3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSaltos(arrayListRotor3_O,arrayAdapterOrigen);
            }
        });

        btnSaltoD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSaltos(arrayListRotor3_D,arrayAdapterDestino);
            }
        });


//        listOrigen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                showInputBoxOrigen(arrayListRotor3_O.get(position),position);
//            }
//        });
//
//        listDestino.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                showInputBoxDestino(arrayListRotor3_D.get(position),position);
//            }
//        });

        return v;
    }


    public void refresh(){
        arrayListRotor3_O.clear();
        arrayListRotor3_D.clear();
        arrayListRotor3_O.addAll( metodos.llenarAvc());
        arrayListRotor3_D.addAll( metodos.llenarAvc());
        arrayAdapterOrigen.notifyDataSetChanged();
        arrayAdapterDestino.notifyDataSetChanged();
    }
    public void loadDataOrigen()
    {    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_R3_O,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        arrayListRotor3_O = gson.fromJson(json  ,type);

        if (arrayListRotor3_O == null){
            arrayListRotor3_O =new ArrayList<>();
        }
    }

    public void loadDataDestino()
    {    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCIAS_COMPARTIDAD,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_R3_D,null);

        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        arrayListRotor3_D = gson.fromJson(json  ,type);

        if (arrayListRotor3_D == null){
            arrayListRotor3_D =new ArrayList<>();
        }
    }





    public void showDialogTrama(final ArrayList<String> avc, final ArrayAdapter<String> arrayAdapter) {
        final boolean[] isAsc = new boolean[1];
        isAsc[0] = true;
        final ArrayList<String> espejo = new ArrayList<>();

        final Dialog dialog = new Dialog(getActivity());
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Trama");
        dialog.setContentView(R.layout.dialog_metodo);
        final EditText editText = dialog.findViewById(R.id.txtClave);
        final RadioGroup group = dialog.findViewById(R.id.rdGrupo);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rdAsc:
                        isAsc[0] = true;
                        Toast.makeText(getActivity(), "Verdadero", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.rdDesc:
                        isAsc[0] = false;
                        Toast.makeText(getActivity(), "Falso", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        // Your code
                        break;
                }
            }
        });
        Button bt = dialog.findViewById(R.id.btnOk);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Valor de Clave Incompleta", Toast.LENGTH_SHORT).show();
                } else {

                    espejo.addAll(avc);
                    avc.clear();
                    avc.addAll(metodos.parsearList(metodos.doTrama(espejo, editText.getText().toString(), isAsc[0])));
                    arrayAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
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


    public void showDialogTranposicion(final ArrayList<String> avc, final ArrayAdapter<String> arrayAdapter) {
        final boolean[] isAsc = new boolean[1];
        isAsc[0] = true;
        final ArrayList<String> espejo = new ArrayList<>();

        final Dialog dialog = new Dialog(getActivity());
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Transpoción");
        dialog.setContentView(R.layout.dialog_metodo);
        final EditText editText = dialog.findViewById(R.id.txtClave);
        final RadioGroup group = dialog.findViewById(R.id.rdGrupo);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rdAsc:
                        isAsc[0] = true;
                        Toast.makeText(getActivity(), "Verdadero", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.rdDesc:
                        isAsc[0] = false;
                        Toast.makeText(getActivity(), "Falso", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        // Your code
                        break;
                }
            }
        });
        Button bt = dialog.findViewById(R.id.btnOk);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Valor de Clave Incompleta", Toast.LENGTH_SHORT).show();
                } else {

                    espejo.addAll(avc);
                    avc.clear();
                    avc.addAll(metodos.parsearList(metodos.doTransposicion(espejo, editText.getText().toString(),  isAsc[0])));
                    arrayAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
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
                arrayListRotor3_O.set(index,editText.getText().toString());
                arrayAdapterOrigen.notifyDataSetChanged();
                dialog.dismiss();
                saveArraySharedPreference(arrayListRotor3_O,LIST_R3_O);
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
                arrayListRotor3_D.set(index,editText.getText().toString());
                arrayAdapterDestino.notifyDataSetChanged();
                dialog.dismiss();

                saveArraySharedPreference(arrayListRotor3_D,LIST_R3_D);
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

    public void showDialogSaltos(final ArrayList<String> avc,final ArrayAdapter<String> arrayAdapter) {
        String newAvc = metodos.parsearString(avc);

        final ArrayList<String> espejo = new ArrayList<>();

        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Saltos Sucesivos");
        dialog.setContentView(R.layout.dialog_saltos);
        final EditText txtNum = dialog.findViewById(R.id.txtNum);
        final EditText txtIdi = dialog.findViewById(R.id.txtIdi);
        Button bt = dialog.findViewById(R.id.btnOk);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtNum.getText().length() == 3 && txtIdi.getText().length() == 3) {
                    List<Character> charList = new ArrayList<Character>();
                    for (char c : newAvc.toCharArray()) {
                        charList.add(c);
                    }
                    charList = Crypto.successiveJumps(charList, txtNum.getText().toString() + txtIdi.getText().toString(), false);
                    String newAvc = metodos.getStringListCharacters(charList);
                    ArrayList<String> lista = metodos.convertStringToArraylist(newAvc);
                    espejo.addAll(lista);
                    avc.clear();
                    avc.addAll(espejo);
                    arrayAdapter.notifyDataSetChanged();
                    dialog.dismiss();

                } else {
                    Toast.makeText(getActivity(), "complete las distintas claves", Toast.LENGTH_SHORT).show();
                }
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
