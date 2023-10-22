package com.phantom.japanese_dictionary_mvc.models;

import lombok.*;


import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "Grammarnotes")
public class GrammarNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int grammarNoteId;

    @Column(name = "source")
    private String source;

    @Column(name = "grammarrule")
    private String rule;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "example")
    private String example;
}
