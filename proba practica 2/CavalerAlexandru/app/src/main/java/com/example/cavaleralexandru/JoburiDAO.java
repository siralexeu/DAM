package com.example.cavaleralexandru;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface JoburiDAO {
    @Insert
    void insert(Job job);
    @Insert
    void insert(List<Job> jobList);
    @Query("select * from joburi")
    List<Job> getAll();

    @Query("delete from joburi")
    void deleteAll();

    @Update
    void update(Job job);

    @Query("SELECT * FROM joburi WHERE salariu >= :salariuMin")
    List<Job> getSalary(int salariuMin);

    @Query("DELETE FROM joburi WHERE salariu <= :salariuMax")
    int deleteJobs(float salariuMax);
}
