package com.example.contentprovider_db;

public class toDo {
    private long id;
    private String toDo;
    private String place;

    public toDo(){
        super();
    }

    public toDo(long id, String toDo, String place){
        this.id=id;
        this.toDo=toDo;
        this.place=place;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}

}
