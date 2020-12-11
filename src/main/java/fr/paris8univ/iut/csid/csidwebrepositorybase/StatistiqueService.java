package fr.paris8univ.iut.csid.csidwebrepositorybase;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
public class StatistiqueService {

    private final StatistiqueRepository statistiqueRepository;

    public StatistiqueService(StatistiqueRepository statistiqueRepository) {
        this.statistiqueRepository=statistiqueRepository;
    }

    public List<GitRepository> getRepositories(){
        return statistiqueRepository.getRepositories();
    }

    public Optional<GitRepository> findOneRepository(String name) throws RestClientException, URISyntaxException {
        return statistiqueRepository.findOneRepository(name);
    }

    public void createRepository(GitRepository gitRepository){
        statistiqueRepository.createRepository(gitRepository);
    }

    public void putRepository(String name,GitRepository gitRepository) {
        statistiqueRepository.putRepository(name, gitRepository);
    }

    public void patchRepository(String name, GitRepository gitRepository) {
        GitRepository gitRepoPatched = merge(this.statistiqueRepository.findOneRepoForPatch(name),gitRepository);
        statistiqueRepository.patchRepository(gitRepoPatched);
    }

    public void deleteRepository(String name) {
        statistiqueRepository.deleteRepository(name);
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
