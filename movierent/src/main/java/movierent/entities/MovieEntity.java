package movierent.entities;

import java.sql.Date;

public class MovieEntity {
    private Integer movies_id;
    private String title;
    private String genre;
    private Date rent_date;

    public Integer getMoviesID(){
        return movies_id;
    }
    public void setMoviesID(Integer movies_id){
        this.movies_id = movies_id;
    }
    public String getMoviesTitle() {
        return title;
    }
    public void setMoviesTitle(String title) {
        this.title = title;
    }
    public String getMoviesGenre() {
        return genre;
    }

    public void setMoviesGenre(String genre) {
        this.genre = genre;
    }
    public Date getRentDate(){
        return rent_date;
    }
    public void setRentDate(Date rent_date){
        this.rent_date= rent_date;
    }

}
