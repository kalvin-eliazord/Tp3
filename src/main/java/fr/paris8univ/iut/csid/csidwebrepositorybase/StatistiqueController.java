package fr.paris8univ.iut.csid.csidwebrepositorybase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/repositories")
public class StatistiqueController {

    private final StatistiqueService statistiqueService;

    @Autowired
    public StatistiqueController(StatistiqueService statistiqueService) {
        this.statistiqueService=statistiqueService;
    }

    @GetMapping
    public List<GitRepository> getRepositories(){
        return statistiqueService.getRepositories();
    }

    @GetMapping("/{name}")
    public ResponseEntity<GitRepository> findOneRepository(@PathVariable String name) throws RestClientException, URISyntaxException {
        return statistiqueService.findOneRepository(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<GitRepository> creatRepository(@RequestBody GitRepository gitRepository) throws URISyntaxException{
        statistiqueService.createRepository(gitRepository);
        return ResponseEntity.created(new URI("/repositories/"+gitRepository.getName())).build();

    }

    @PutMapping("/{name}")
    public ResponseEntity<Object> putRepository(@PathVariable String name,@RequestBody GitRepository gitRepository) {
        statistiqueService.putRepository( name, gitRepository);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{name}")
    public ResponseEntity<Object> patchRepository(@PathVariable String name,@RequestBody GitRepository gitRepository) {
        statistiqueService.patchRepository(name,gitRepository);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{name}")
    public void deleteRepository(@PathVariable String name) {
        statistiqueService.deleteRepository(name);
    }


}