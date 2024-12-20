package com.example.cavaleralexandru;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "joburi")
public class Job implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Job(int id, String denumire, String firma, float salariu) {
        this.id=id;
        this.firma = firma;
        this.salariu = salariu;
        this.denumire = denumire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String denumire;
    private String firma;
    private float salariu;

    public Job(){}
    public Job(String firma, float salariu, String denumire) {
        this.firma = firma;
        this.salariu = salariu;
        this.denumire = denumire;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public float getSalariu() {
        return salariu;
    }

    public void setSalariu(float salariu) {
        this.salariu = salariu;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                "denumire='" + denumire + '\'' +
                ", firma='" + firma + '\'' +
                ", salariu=" + salariu +
                '}';
    }

}
