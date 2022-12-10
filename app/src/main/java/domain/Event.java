package domain;


import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {


    private int id;
    private String name;
    private Date dateEvent;
    private Date dateCreate;
    private int status;


    public Event(){}

    public Event(int id, String name, Date dateEvent, Date dateCreate, int status) {
        this.id = id;
        this.name = name;
        this.dateEvent = dateEvent;
        this.dateCreate = dateCreate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateEvent=" + dateEvent +
                ", dateCreate=" + dateCreate +
                ", status=" + status +
                '}';
    }
}

