package ro.ase.semdam_1087;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
@Entity(tableName = "masini")
public class Masina implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private String marca;
    private Date dataFabricatiei;
    private float pret;
    private String culoare; //ALB, NEGRU, ROSU, GRI, ALBASTRU
    private String motorizare; //BENZINA, DIESEL, ELECTRIC, HIBRID

    @Ignore
    public Masina(){}

    public Masina(String marca, Date dataFabricatiei, float pret, String culoare, String motorizare) {
        this.marca = marca;
        this.dataFabricatiei = dataFabricatiei;
        this.pret = pret;
        this.culoare = culoare;
        this.motorizare = motorizare;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getDataFabricatiei() {
        return dataFabricatiei;
    }

    public void setDataFabricatiei(Date dataFabricatiei) {
        this.dataFabricatiei = dataFabricatiei;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public String getMotorizare() {
        return motorizare;
    }

    public void setMotorizare(String motorizare) {
        this.motorizare = motorizare;
    }

    @Override
    public String toString() {
        return "Masina{" +
                "marca='" + marca + '\'' +
                ", dataFabricatiei=" + dataFabricatiei +
                ", pret=" + pret +
                ", culoare='" + culoare + '\'' +
                ", motorizare='" + motorizare + '\'' +
                '}';
    }
}
