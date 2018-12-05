package com.example.yoshino.maquinaenigma.saltos;

public enum Alphabet {
    EN("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    EN_MIN("abcdefghijklmnñopqrstuvwxyz"),
    ES("ABCDEFGHIJKLMNÑOPQRSTUVWXYZ"),
    EN_NUM("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    ES_NUM("0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ");

    private final String alphabetStr;

    Alphabet(String alphabetStr) {
        this.alphabetStr = alphabetStr;
    }

    @Override
    public String toString() {
        return alphabetStr;
    }
}
