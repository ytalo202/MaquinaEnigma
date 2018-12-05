package com.example.yoshino.maquinaenigma;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yoshino.maquinaenigma.saltos.Crypto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class InputActivityFragment extends Fragment {
    View v;

    Metodos metodos = new Metodos();
    static final String PREFERENCIAS_COMPARTIDAD = "preferenciaCompartida";
    static final String LIST_INPUT = "listimput";

    ArrayList<String> input;
    ArrayAdapter<String> arrayAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.input_fragment, container, false);


        ListView listView = v.findViewById(R.id.listInput);
        TextView btnTrama = v.findViewById(R.id.btnTramImput);
        TextView btnTransposicion = v.findViewById(R.id.btnTranpImput);
        TextView btnSaltosImput = v.findViewById(R.id.btnSaltosImput);
        FloatingActionButton floatRInputrefresh = v.findViewById(R.id.cbtninputRefresh);

        FloatingActionButton cbtinputSave = v.findViewById(R.id.cbtinputSave);
        cbtinputSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Desea Continuar ...");
                builder.setTitle("Mensaje:");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        saveArraySharedPreference(input, LIST_INPUT);
                        Toast.makeText(getActivity(), "Abecedario Guardado", Toast.LENGTH_SHORT).show();
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

        final CharSequence[] items = {"27 Letras", "26 Letras"};

        floatRInputrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Desea Continuar ...");
                builder.setTitle("Mensaje:");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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

        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.txtLetra, input);
        listView.setAdapter(arrayAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                showInputBox(input.get(position), position);
//            }
//        });

        btnTrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogTrama(input);
            }
        });

        btnTransposicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogTranposicion(input);
            }
        });


        btnSaltosImput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSaltos(input);
            }
        });

        return v;
    }


    public void refresh() {
        input.clear();
        input.addAll(metodos.llenarAvc());
        arrayAdapter.notifyDataSetChanged();
    }

    public void showInputBox(String oldItem, final int index) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Input Box");
        dialog.setContentView(R.layout.input_box);

        TextView txtMesaje = dialog.findViewById(R.id.txtmensaje);
        txtMesaje.setText("Update item");
        txtMesaje.setTextColor(Color.parseColor("#cc3131"));
        final EditText editText = dialog.findViewById(R.id.txtinput);
        editText.setText(oldItem);

        Button bt = dialog.findViewById(R.id.btdone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.set(index, editText.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
                saveArraySharedPreference(input, LIST_INPUT);
            }
        });

        dialog.show();
    }


    public void showDialogTrama(final ArrayList<String> avc) {

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


    public void showDialogTranposicion(final ArrayList<String> avc) {


        final boolean[] isAsc = new boolean[1];
        isAsc[0] = true;

        final ArrayList<String> espejo = new ArrayList<>();

        final Dialog dialog = new Dialog(getActivity());
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Tranposición");
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
                    avc.addAll(metodos.parsearList(metodos.doTransposicion(espejo, editText.getText().toString(), isAsc[0])));
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


    public void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCIAS_COMPARTIDAD, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LIST_INPUT, null);

        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        input = gson.fromJson(json, type);
        // Collections.rotate(input, -1);
        if (input == null) {
            input = new ArrayList<>();

        }
    }


    public void saveArraySharedPreference(ArrayList<String> lista, String name) {


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCIAS_COMPARTIDAD, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lista);
        editor.putString(name, json);
        editor.apply();
    }


    public void showDialogSaltos(final ArrayList<String> avc) {
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
