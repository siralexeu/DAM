package ro.ase.semdam_1087;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MasiniDAO {
    @Insert
    void insert(Masina masina);

    @Insert
    void insert(List<Masina> masinaList);

    @Query("select * from masini")
    List<Masina> getAll();

    @Update
    void update(Masina masina);

    @Query("SELECT COUNT(*) FROM masini WHERE pret > :pretMin")
    int getNumarMasiniPret(float pretMin);

    /*@Query("SELECT * FROM masini WHERE pret > :minPret")
    List<Masina> getMasiniCuPretMaiMareDecat(float minPret);*/
    @Query("delete from masini")
    void deleteAll();

}
