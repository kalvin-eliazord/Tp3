package fr.paris8univ.iut.csid.csidwebrepositorybase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatistiqueDAO extends JpaRepository<GitRepositoryEntity,String> {

    @Query(value = "select * from Statistique as s where s.name= ?1 and s.entry_type= ?2 order by creationDate desc ",nativeQuery=true)
    List<StatistiqueEntity> findAllStatistiqueForNameOrderByDate(String repository, String statType);
}
