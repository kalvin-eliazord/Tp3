package fr.paris8univ.iut.csid.csidwebrepositorybase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;



@Component
public class GitHubRepositoryDao {
	
	private final RestTemplate restTemplate;
	
	@Autowired
	public GitHubRepositoryDao(RestTemplateBuilder restTemplate) {

		this.restTemplate=restTemplate.build();
	}
	
	public GitRepositoryDTO getGitInfo(String repositoy,String owner) throws RestClientException, URISyntaxException{
		return restTemplate.getForEntity(new URI("https://api.github.com/repos/"+owner+"/"+repositoy),GitRepositoryDTO.class).getBody();
	}
	
}
