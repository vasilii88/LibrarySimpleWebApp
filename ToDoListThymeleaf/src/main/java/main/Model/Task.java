package main.Model;
import javax.persistence.*;


@Entity
public class Task {

    @Id
    @GeneratedValue()
    private Integer id;

    @Column
    private String name;

    @Column
    private String date;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
