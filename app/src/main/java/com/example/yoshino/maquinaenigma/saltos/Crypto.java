package com.example.yoshino.maquinaenigma.saltos;

import android.os.Build;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Crypto {
    // METODO PARA GENERAR UN ALFABETOS
    // para alfabeto de 26 letras: alphaEnum = Alphabet.EN
    // para alfabeto de 27 letras: alphaEnum = Alphabet.ES
    // para alfabeto ordenado: invert = false
    // para alfabeto ivertido: invert = true
    // para iniciar el alfabeto en una letra especifica (P por ejemplo): start = 'P'
    public static List<Character> generateAlphabet(Alphabet alphaEnum, boolean invert, char start) {
        List<Character> alphabet = new ArrayList<Character>();

        for (Character c : alphaEnum.toString().toCharArray()) {
            alphabet.add(c);
        }

        if (invert) {
            List<Character> aux = new ArrayList<Character>();
            ListIterator<Character> iterator = alphabet.listIterator(alphabet.size());

            while (iterator.hasPrevious()) {
                aux.add(iterator.previous());
            }

            alphabet = aux;
        }

        start = Character.toUpperCase(start);
        List<Character> subAlpha1 = alphabet.subList(alphabet.indexOf(start), alphabet.size());
        List<Character> subAlpha2 = alphabet.subList(0, alphabet.indexOf(start));

        List<Character> auxAlpha = new ArrayList<Character>();
        auxAlpha.addAll(subAlpha1);
        auxAlpha.addAll(subAlpha2);
        alphabet = auxAlpha;

        return alphabet;
    }

    // METODO PARA APLICAR TRANSPOSICION
    // alphaList es el alfabeto sobre el cual se aplica la transposicion
    // key es la variable o clave numerica
    public static List<Character> transposition(List<Character> alphaList, String key) {
        List<Character> alphabet = new ArrayList<Character>();

        int alphaListSize = alphaList.size();
        int keySize = key.length();
        char[] keyCharArray = key.toCharArray();

        Map<Character, Character> map = new HashMap<Character, Character>();
        for (int i = 0; i < alphaListSize; i++) {
            map.put(keyCharArray[i % keySize], alphaList.get(i));

            if ((i + 1) % keySize == 0 || i == alphaListSize - 1) {
                alphabet.addAll(map.values());
                map.clear();
            }
        }

        return alphabet;
    }

    // METODO PARA APLICAR TRAMA
    // alphaList es el alfabeto sobre el cual se aplica la trama
    // key es la variable o clave numerica
    public static List<Character> plot(List<Character> alphaList, String key) {
        List<Character> alphabet = new ArrayList<Character>();

        int alphaListSize = alphaList.size();
        int keySize = key.length();
        int groupSize = alphaListSize / keySize;
        List<Character> keyCharList = new ArrayList<Character>();

        for (char c : key.toCharArray()) {
            keyCharList.add(c);
        }

        Map<Character, Integer> auxMap = new HashMap<Character, Integer>();

        for (int i = 0; i < keySize; i++) {
            auxMap.put(keyCharList.get(i), i);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            keyCharList.sort(Comparator.naturalOrder());
        }
        Iterator<Character> iterator = keyCharList.iterator();
        List<Character> list = new ArrayList<Character>();
        Map<Integer, List<Character>> map = new HashMap<Integer, List<Character>>();
        for (int i = 0; i < alphaListSize; i++) {
            list.add(alphaList.get(i));

            if ((i + 1) % groupSize == 0 && iterator.hasNext()) {
                List<Character> auxList = new ArrayList<Character>();
                auxList.addAll(list);
                map.put(auxMap.get(iterator.next()), auxList);
                list.clear();
            }
        }

        for (List<Character> item : map.values()) {
            alphabet.addAll(item);
        }
        alphabet.addAll(list);

        return alphabet;
    }

    // METODO PARA APLICAR SALTOS SUCESIVOS
    // alphaList es el alfabeto sobre el cual se aplica los saltos sucesivos
    // key es la variable (352IDID por ejemplo)
    // usar start = false si dice EMPIEZA
    // usar start = true si dice INICIA
    public static List<Character> successiveJumps(List<Character> alphaList, String key, boolean start) {
        List<Character> alphabet = new ArrayList<Character>();

        int alphaListSize = alphaList.size();
        int keySize = key.length();
        char[] numericKey = key.toCharArray();
        char[] alphabeticKey = {};

        for (int i = 0; i < keySize; i++) {
            if (!Character.isDigit(key.toCharArray()[i])) {
                numericKey = key.substring(0, i).toCharArray();
                alphabeticKey = key.substring(i, keySize).toCharArray();
                break;
            }
        }

        int pointer = 0;
        int jump = 1;

        if (start) {
            alphabet.add(alphaList.get(0));
            alphaList.set(pointer, '-');
        }

        int startIndex = start ? 1 : 0;
        for (int i = startIndex; i < alphaListSize; i++) {
            if (alphabeticKey.length != 0)
                jump = (alphabeticKey[i % alphabeticKey.length] == 'I') ? -1 : 1;

            int counter = 0;
            while (counter < Character.getNumericValue(numericKey[i % numericKey.length])) {
                pointer = (alphaListSize + pointer + jump) % alphaListSize;

                if (alphaList.get(pointer) != '-') {
                    counter++;
                }
            }

            alphabet.add(alphaList.get(pointer));
            alphaList.set(pointer, '-');
        }

        return alphabet;
    }

    // METODO PARA CAMBIAR LA LETRA INICIAL DEL ALFABETO
    // alphaList es el alfabeto sobre el cual se aplicaran los cambios
    // start es la letra inicial con la cual inicia el alfabeto (por ejemplo start = 'M')
    public static List<Character> changeBeginningOfAlphabet(List<Character> alphaList, char start) {
        List<Character> alphabet = new ArrayList<>();
        start = Character.toUpperCase(start);
        List<Character> subList1 = alphaList.subList(alphaList.indexOf(start), alphaList.size());
        List<Character> subList2 = alphaList.subList(0, alphaList.indexOf(start));

        alphabet.addAll(subList1);
        alphabet.addAll(subList2);

        return alphabet;
    }
}