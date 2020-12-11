package fr.paris8univ.iut.csid.csidwebrepositorybase;

import javax.persistence.*;

@Entity
@Table(name = "repository")
public class StatistiqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "repository_name")
    private String repository_name;

    @Column(name = "entry_type")
    private String entry_type;

    @Column(name = "datetime")
    private String dateTime;

    @Column(name = "value")
    private int value;

    public StatistiqueEntity(int id, String entry_type, String dateTime, int value, String repository_name) {
        this.id=id;
        this.repository_name = repository_name;
        this.entry_type=entry_type;
        this.dateTime=dateTime;
        this.value=value;
    }


}
