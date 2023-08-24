package com.phantom.japanese_dictionary_mvc.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@EqualsAndHashCode
@Table(name = "Japan")
public class Note {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


}
