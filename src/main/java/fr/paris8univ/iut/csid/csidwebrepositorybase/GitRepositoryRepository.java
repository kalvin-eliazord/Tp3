package fr.paris8univ.iut.csid.csidwebrepositorybase;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;

@Repository
public class GitRepositoryRepository {
	
	private final GitRepositoryDao gitRepositoryDao;
	private final GitHubRepositoryDao gitHubRepositoryDao;
	private final StatistiqueDAO statistiqueDAO;
	
	@Autowired
	public GitRepositoryRepository(GitRepositoryDao gitRepositoryDao, GitHubRepositoryDao gitHubRepositoryDao, StatistiqueDAO statistiqueDAO) {
		this.gitRepositoryDao=gitRepositoryDao;
		this.gitHubRepositoryDao=gitHubRepositoryDao;
		this.statistiqueDAO = statistiqueDAO;
	}
	
	public List<GitRepository> getRepositories(){
		List<GitRepositoryEntity> repositoryEntities = gitRepositoryDao.findAll();
		return repositoryEntities.stream()
				.map(x -> new GitRepository(x.getName(),x.getOwner(),x.getIssues(),x.getForks(),x.getLastUpdate()))
				.collect(Collectors.toList());
	}
	
	public Optional<GitRepository> findOneRepoForPatch(String name){
		return Optional.of(gitRepositoryDao.findById(name).map(x-> new GitRepository(x.getName(),x.getOwner(),x.getIssues(),x.getForks(),x.getLastUpdate())).get());
	}
	
	public Optional<GitRepository> findOneRepository(String name) throws RestClientException, URISyntaxException{
		GitRepositoryEntity gitRepository = gitRepositoryDao.findById(name).get();
		GitRepository return_gitRepository = new GitRepository(gitRepository.getName(),gitRepository.getOwner(),gitRepository.getIssues(),gitRepository.getForks(),gitRepository.getLastUpdate());
		
		if((Instant.now().getEpochSecond()-return_gitRepository.getLastUpdate())>300) {
			GitRepositoryDTO gitInfo = gitHubRepositoryDao.getGitInfo(return_gitRepository.getName(), return_gitRepository.getOwner());
			return_gitRepository.setIssues(gitInfo.getIssues());
			return_gitRepository.setForks(gitInfo.getForks());
			return_gitRepository.setLastUpdate(Instant.now().getEpochSecond());

			patchRepository(return_gitRepository);
		}
		
		return Optional.of(return_gitRepository);
	}
	
	public void createRepository(GitRepository gitRepository){
		gitRepositoryDao.save(new GitRepositoryEntity(gitRepository.getName(),gitRepository.getOwner(),gitRepository.getIssues(),gitRepository.getForks(),gitRepository.getLastUpdate()));
	}
	
	public void putRepository(String name,GitRepository gitRepository) {
		
		Optional<GitRepositoryEntity> repository = gitRepositoryDao.findById(name);
		
		if(repository.isEmpty()) {
			createRepository(gitRepository);
		}
		else {
			
			GitRepositoryEntity repositoryModified = repository.get();
			
			repositoryModified.setOwner(gitRepository.getOwner());
			repositoryModified.setIssues(gitRepository.getIssues());
			repositoryModified.setForks(gitRepository.getForks());
			
			gitRepositoryDao.save(repositoryModified);
		}
		
	}
	
	public void patchRepository(GitRepository gitRepo) {
		GitRepositoryEntity repositoryPatched = new GitRepositoryEntity(gitRepo.getName(),gitRepo.getOwner(),gitRepo.getIssues(),gitRepo.getForks(),gitRepo.getLastUpdate());
		gitRepositoryDao.save(repositoryPatched);
	}
	
	public void deleteRepository(String name) {
		gitRepositoryDao.deleteById(name);
	}

}
