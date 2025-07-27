package dao;

import models.AlgorithmResult;

import java.util.List;

public interface AlgorithmResultDAO {
    void save(AlgorithmResult result);

    List<AlgorithmResult> findAll();

    void clear();
}
