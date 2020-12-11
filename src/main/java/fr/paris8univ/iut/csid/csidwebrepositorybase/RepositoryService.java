package fr.paris8univ.iut.csid.csidwebrepositorybase;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class RepositoryService {
	
	private final GitRepositoryRepository gitRepositoryRepository;
	
	public RepositoryService(GitRepositoryRepository gitRepositoryRepository) {
		this.gitRepositoryRepository=gitRepositoryRepository;
	}
	
	public List<GitRepository> getRepositories(){
		return gitRepositoryRepository.getRepositories();
	}
	
	public Optional<GitRepository>  findOneRepository(String name) throws RestClientException, URISyntaxException{	
		return gitRepositoryRepository.findOneRepository(name);
	}
	
	public void createRepository(GitRepository gitRepository){
		gitRepositoryRepository.createRepository(gitRepository);
	}
	
	public void putRepository(String name,GitRepository gitRepository) {
		gitRepositoryRepository.putRepository(name, gitRepository);
	}
	
	public void patchRepository(String name, GitRepository gitRepository) {
		GitRepository gitRepoPatched = merge(this.gitRepositoryRepository.findOneRepoForPatch(name),gitRepository);
		gitRepositoryRepository.patchRepository(gitRepoPatched);
	}
	
	public void deleteRepository(String name) {
		gitRepositoryRepository.deleteRepository(name);
	}
	
	private GitRepository merge(Optional<GitRepository> oldRepOptional,GitRepository newRep) {	
		GitRepository oldRep=oldRepOptional.get();
		if(newRep.getOwner() != null)
			oldRep.setOwner(newRep.getOwner());	
		if(newRep.getIssues() != 0 )
			oldRep.setIssues(newRep.getIssues());
		if(newRep.getFork() != 0)
			oldRep.setFork(newRep.getFork());
		
		return oldRep;
	}

}
