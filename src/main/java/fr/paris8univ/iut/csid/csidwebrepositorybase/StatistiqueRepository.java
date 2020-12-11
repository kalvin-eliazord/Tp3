package fr.paris8univ.iut.csid.csidwebrepositorybase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StatistiqueRepository {

    private final StatistiqueDAO statistiqueDAO;
    private final GitHubRepositoryDao gitHubRepositoryDao;

    @Autowired
    public StatistiqueRepository(StatistiqueDAO statistiqueDAO,GitHubRepositoryDao gitHubRepositoryDao) {
        this.statistiqueDAO=statistiqueDAO;
        this.gitHubRepositoryDao=gitHubRepositoryDao;
    }

    public List<GitRepository> getRepositories(){
        List<GitRepositoryEntity> repositoryEntities = statistiqueDAO.findAll();
        return repositoryEntities.stream()
                .map(x -> new GitRepository(x.getName(),x.getOwner(),x.getIssues(),x.getFork(),x.getlastUpdate()))
                .collect(Collectors.toList());
    }

    public Optional<GitRepository> findOneRepoForPatch(String name){
        return Optional.of(statistiqueDAO.findById(name).map(x-> new GitRepository(x.getName(),x.getOwner(),x.getIssues(),x.getFork(),x.getlastUpdate())).get());
    }

    public Optional<GitRepository> findOneRepository(String name) throws RestClientException, URISyntaxException {
        GitRepositoryEntity actualRepository = statistiqueDAO.findById(name).get();
        GitRepository toReturn= new GitRepository(actualRepository.getName(),actualRepository.getOwner(),actualRepository.getIssues(),actualRepository.getFork(),actualRepository.getlastUpdate());

        if((Instant.now().getEpochSecond()-toReturn.getLastUpdate())>300) {
            GitRepositoryDTO gitInfo = gitHubRepositoryDao.getGitInfo(toReturn.getName(), toReturn.getOwner());
            toReturn.setIssues(gitInfo.getIssues());
            toReturn.setFork(gitInfo.getForks());
            toReturn.setLastUpdate(Instant.now().getEpochSecond());
            patchRepository(toReturn);
        }

        return Optional.of(toReturn);
    }

    public void creatRepository(GitRepository gitRepository){
        statistiqueDAO.save(new GitRepositoryEntity(gitRepository.getName(),gitRepository.getOwner(),gitRepository.getIssues(),gitRepository.getFork(),gitRepository.getLastUpdate()));
    }

    public void putRepository(String name,GitRepository gitRepository) {

        Optional<GitRepositoryEntity> repository = statistiqueDAO.findById(name);

        if(repository.isEmpty()) {
            creatRepository(gitRepository);
        }
        else {

            GitRepositoryEntity repositoryModified = repository.get();

            repositoryModified.setOwner(gitRepository.getOwner());
            repositoryModified.setIssues(gitRepository.getIssues());
            repositoryModified.setFork(gitRepository.getFork());

            statistiqueDAO.save(repositoryModified);
        }

    }

    public void patchRepository(GitRepository gitRepo) {
        GitRepositoryEntity repositoryPatched = new GitRepositoryEntity(gitRepo.getName(),gitRepo.getOwner(),gitRepo.getIssues(),gitRepo.getFork(),gitRepo.getLastUpdate());
        statistiqueDAO.save(repositoryPatched);
    }

    public void deleteRepository(String name) {
        statistiqueDAO.deleteById(name);
    }

}