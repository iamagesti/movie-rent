package movierent.usecases;


import com.zaxxer.hikari.HikariDataSource;


import movierent.entities.RentEntity;
import movierent.models.RentModel;
import movierent.utils.DBConnectionUtil;

public class RentUsecase {
    private HikariDataSource dataSource;
    private RentModel rentModel;

    public RentUsecase() {
        dataSource = DBConnectionUtil.getDataSource();
        rentModel = new RentModel(dataSource);

    }
    
    public Integer GetListActiveRent(){
        RentEntity[] rentList = rentModel.findAllRent();
        return rentList.length;
    }

    public RentEntity[] GetRentList(){
        return rentModel.findAllRent();
    }

    public void AddRent(String title, java.util.Date rent_date,String username ){
       RentUsecase rentUsecase = new RentUsecase();
        if(!rentUsecase.IsMovieRentExist(title)){
            RentEntity rentData = new RentEntity();
            rentData.setMoviesTitle(title);
            rentData.setRentDate(rent_date);
            rentData.setUsername(username);
            rentModel.CreateRent(rentData);
            System.out.println("Add Rent Succeed!");
       } else{
            System.out.println("This movies already rented, rent movie failed");
       }
        

    }

    public void ReturnRent(Integer rent_id,java.util.Date return_date){
        if(IsRentExist(rent_id)){
            java.sql.Date sqlReturnDate = new java.sql.Date(return_date.getTime());
            rentModel.ReturnRent(rent_id, sqlReturnDate);
            System.out.println("Returned Movie Succedd!");
        }else{
            System.out.println("Rent not found, Return Movie Failed");
        }
    }

    public Boolean IsRentExist(Integer rent_id){
        return rentModel.CheckRentExist(rent_id);
        /*if(rentModel.CheckRentExist(rent_id)){
            return true;
        }else{
            return false;
        }*/
    }

    public Boolean IsMovieRentExist(String title){
        
        RentEntity[] rentList = rentModel.findAllRent();
        for (RentEntity rent : rentList) {
            if (rent.getMoviesTitle().equals(title)) {
                return true;
            }
        }
        return false;
       
    }
     /*public void DeleteRent(Integer rent_id){
        if(IsRentExist(rent_id)){
            rentModel.DeleteRent(rent_id);
            System.out.println("Delete Rent Succeed!");
        }else{
            System.out.println(rent_id+ " is not found, delete rent failed!");
        }
    }*/
}
