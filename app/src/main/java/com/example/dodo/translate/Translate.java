package com.example.dodo.translate;

import java.io.Serializable;

public class Translate implements Serializable{

    private  long id;
    private  String word;
    private  String translate;


    Translate(long id, String word, String translate) {
        this.id = id;
        this.word=word;
        this.translate = translate;
    }

    public long getId() {
        return id;
    }

    public void setTranslate(String translate){
        this.translate = translate;
    }

    public String getTranslate() {
        return translate;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        int b = 10 - word.toCharArray().length;
        String probels = "";
        for (int i = 0;i<b;i++) probels+=" ";
        return word+probels+translate;
    }
}
