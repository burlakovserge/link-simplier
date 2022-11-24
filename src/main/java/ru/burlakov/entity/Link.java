package ru.burlakov.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Link implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "linkSeq")
    @SequenceGenerator(name = "linkSeq", sequenceName = "link_seq", allocationSize = 1)
    private Long id;
    @Column(name = "short")
    private String shortLink;
    private String original;
    private LocalDate created;
    private Long count;

    public Link(String shortLink, String original, LocalDate created) {
        this.shortLink = shortLink;
        this.original = original;
        this.created = created;
        count = 0L;
    }
}
