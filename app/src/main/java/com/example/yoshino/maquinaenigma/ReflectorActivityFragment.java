package com.example.yoshino.maquinaenigma;

import android.app.Dialog;
import android.content.Context;
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

public class ReflectorActivityFragment extends Fragment {

    View v;
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

    ArrayList<String> arrayList_Refle;
    ArrayAdapter<String> arrayAdapter;


    public ReflectorActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.reflector_fragment, container, false);

        ListView listView = v.findViewById(R.id.listRefle);
        TextView btnTrama = v.findViewById(R.id.btnTramaReflec);
        TextView btnTransposicion = v.findViewById(R.id.btnTransReflec);
        FloatingActionButton floatrefresh = v.findViewById(R.id.cbtnRefresh);
        Button newAvc = v.findViewById(R.id.newAvcR);
        TextView btnSaltosRefle = v.findViewById(R.id.btnSaltosRefle);

        FloatingActionButton cbtrefreshSave = v.findViewById(R.id.cbtrefreshSave);
        cbtrefreshSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Desea Continuar ...");
                builder.setTitle("Mensaje:");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        saveArraySharedPreference(arrayList_Refle, LIST_REF);
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

        newAvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNewAvc();
            }
        });
        btnSaltosRefle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSaltos(arrayList_Refle);
            }
        });

        floatrefresh.setOnClickListener(new View.OnClickListener() {
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

//        arrayList_Refle = metodos.llenarAvc();

        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.txtLetra, arrayList_Refle);

        listView.setAdapter(arrayAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                showInputBox(arrayList_Refle.get(position),position);
//            }
//        });

        btnTrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogTrama(arrayList_Refle);
            }
        });

        btnTransposicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogTranposicion(arrayList_Refle);
            }
        });

        return v;
    }


    public void refresh() {
        arrayList_Refle.clear();
        arrayList_Refle.addAll(metodos.llenarAvcReflector());
        arrayAdapter.notifyDataSetChanged();
    }

    boolean isAsc;


    public void showDialogTrama(final ArrayList<String> avc) {
        isAsc = true;
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
                        isAsc = true;
                        Toast.makeText(getActivity(), "Verdadero", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.rdDesc:
                        isAsc = false;
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
                    avc.addAll(metodos.parsearList(metodos.doTrama(espejo, editText.getText().toString(), isAsc)));
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
        String json = sharedPreferences.getString(LIST_REF, null);

        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        arrayList_Refle = gson.fromJson(json, type);

        if (arrayList_Refle == null) {
            arrayList_Refle = new ArrayList<>();
        }
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
                arrayList_Refle.set(index, editText.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();

                saveArraySharedPreference(arrayList_Refle, LIST_REF);
            }
        });

        dialog.show();
    }


    public void saveArraySharedPreference(ArrayList<String> lista, String name) {


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCIAS_COMPARTIDAD, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lista);
        editor.putString(name, json);
        editor.apply();
    }


    public void btnNewAvc() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Nuevo Abecedario");
        dialog.setContentView(R.layout.dialog_new_avc);
        final EditText editText = dialog.findViewById(R.id.txtAvc);
        final TextView txtnum1 = dialog.findViewById(R.id.txtnum1);


        Button bt = dialog.findViewById(R.id.btnOk);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                // ArrayList<String> mensaje =  new ArrayList<String>(Arrays.asList(editText.getText().toString()));
                if (text.length() == 19) {
                    if (metodos.contar(text) == 1) {
                        ArrayList<String> list = metodos.convertStringToArraylist(text);
                        arrayList_Refle.clear();
                        arrayAdapter.addAll(list);
                        arrayAdapter.addAll(list);
                        arrayAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Abecedario agregagado correctamente "
                                , Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Hay una letra o numero que se esta repiendo " + metodos.contar(text)
                                , Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Nuevo abecedario debe tener 19 elementos "
                            , Toast.LENGTH_SHORT).show();
                    txtnum1.setVisibility(View.VISIBLE);
                    txtnum1.setText("N# "+editText.getText().toString().length());
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
