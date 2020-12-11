package fr.paris8univ.iut.csid.csidwebrepositorybase;

public class StatistiqueDTO {

    private String id;

    private String repository_name;

    private String entry_type;

    private String dateTime;

    private int value;

    public StatistiqueDTO() {}

    public StatistiqueDTO(String id,String repository_name,
                          String entry_type, String dateTime,int value) {
        this.id=id;
        this.entry_type=entry_type;
        this.repository_name=repository_name;
        this.dateTime=dateTime;
        this.value=value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepository_name() {
        return repository_name;
    }

    public void setRepository_name(String repository_name) {
        this.repository_name = repository_name;
    }

    public String getEntry_type() {
        return entry_type;
    }

    public void setEntry_type(String entry_type) {
        this.entry_type = entry_type;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

