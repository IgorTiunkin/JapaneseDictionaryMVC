package com.phantom.japanese_dictionary_mvc.models;

import javax.persistence.*;

@Entity
@Table(name = "Japan")
public class Note {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //todo check influence
    @Column(name = "id")
    private int id;

    @Column(name = "romadji")
    private String romadji;

    @Column(name = "kanji")
    private String kanji;

    @Column(name = "hiragana")
    private String hiragana;

    @Column(name = "translation")
    private String translation;

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRomadji() {
        return romadji;
    }

    public void setRomadji(String romadji) {
        this.romadji = romadji;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getHiragana() {
        return hiragana;
    }

    public void setHiragana(String hiragana) {
        this.hiragana = hiragana;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return  "Иероглифы - " + kanji + ", " +
                "Хирагана - " + hiragana + ", " +
                "Перевод - " + translation + ", " +
                "Транслитерация - " + romadji;
    }
}
