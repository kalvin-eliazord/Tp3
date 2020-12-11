package fr.paris8univ.iut.csid.csidwebrepositorybase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "repository")
public class GitRepositoryEntity {
	@Id
	@Column(name = "name", unique = true)
	private String name;
	
	@Column(name = "owner")
	private String owner;

	@Column(name = "issues")
	private int issues;

	@Column(name = "open_issues")
	private String open_issues;
	
	@Column(name = "forks")
	private int forks;

	@Column(name= "lastupdate")
	private long lastUpdate;
	
	public GitRepositoryEntity(String name,String owner, int issues,int forks ,long lastUpdate) {
		this.name=name;
		this.owner=owner;
		this.issues=issues;
		this.forks=forks;
		this.lastUpdate=lastUpdate;
	}


	public int getIssues() {
		return issues;
	}

	public void setIssues(int issues) {
		this.issues = issues;
	}

	public int getForks() {
		return forks;
	}

	public void setForks(int forks) {
		this.forks = forks;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner=owner;
	}

	public long getLastUpdate() {
		return this.lastUpdate;
	}

	public String getOpen_issues() {
		return open_issues;
	}


}
