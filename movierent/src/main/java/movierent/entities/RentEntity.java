package movierent.entities;

import java.util.Date;

public class RentEntity extends UserEntity {
    private Integer rent_id;
    private Date rent_date;
    private Date return_date;
    private Integer movies_id;
    private Integer renter;
    private String title;

    public Integer getRentID(){
        return rent_id;
    }
    public void setRentID(Integer rent_id){
        this.rent_id = rent_id;
    }
    public Date getRentDate(){
        return rent_date;
    }
    public void setRentDate(Date rent_date){
        this.rent_date = rent_date;
    }
    public Date getReturnDate(){
        return return_date;
    }
    public void setReturnDate(Date return_date){
        this.return_date = return_date;
    }
    public Integer getMoviesID(){
        return movies_id;
    }
    public void setMoviesID(Integer movies_id){
        this.movies_id = movies_id;
    }
    public Integer getRenterID(){
        return renter;
    }
    public void setRenterID(Integer renter){
        this.renter = renter;
    }
    public String getMoviesTitle(){
        return title;
    } 
    public void setMoviesTitle(String title){
        this.title =title;
    }
}