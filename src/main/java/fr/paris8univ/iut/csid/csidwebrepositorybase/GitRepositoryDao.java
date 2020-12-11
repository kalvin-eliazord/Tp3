package fr.paris8univ.iut.csid.csidwebrepositorybase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitRepositoryDao extends JpaRepository<GitRepositoryEntity,String>{
	
}
