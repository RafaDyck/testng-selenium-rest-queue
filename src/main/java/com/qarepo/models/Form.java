package com.qarepo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Form implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String toJson() {
        return "{\n" +
                "    \"id\": \"" + id + "\",\n" +
                "    \"name\": \"" + name + "\"\n" +
                "}";
    }
}
