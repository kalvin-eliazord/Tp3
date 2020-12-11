package fr.paris8univ.iut.csid.csidwebrepositorybase;

public class GitRepository {
	private String name;
	private String owner;
	private int issues;
	private int forks;
	private long lastUpdate;
	
	public GitRepository(String name,String owner, int issues,int forks,long open_issues) {
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
	
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate=lastUpdate;
	}


}
