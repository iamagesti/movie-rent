package movierent.models;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.sql.DataSource;

import movierent.entities.RentEntity;
import movierent.utils.PrintUtils;

public class RentModel {
    private DataSource dataSource;
    private PrintUtils printUtils;

    public RentModel(DataSource dataSource){
        this.dataSource = dataSource;
    }
    
    public RentEntity[]findAllRent(){
        String sql = "SELECT r.rent_id, m.movies_id, m.title, r.rent_date, r.renter,u.users_id, u.username FROM rents r, movies m, users u WHERE m.movies_id = r.movies_id and r.renter = u.users_id and r.return_date IS NULL";
        printUtils = new PrintUtils(dataSource);
        try(
            Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
        ){
            ResultSet resultSet = stmt.executeQuery(sql);
            List<RentEntity> list = new ArrayList<>();
            while (resultSet.next()){
                RentEntity rent = new RentEntity();
                rent.setRentID(resultSet.getInt("rent_id"));
                rent.setMoviesID(resultSet.getInt("movies_id"));
                rent.setMoviesTitle(resultSet.getString("title"));
                rent.setRentDate(resultSet.getDate("rent_date"));
                rent.setRenterID(resultSet.getInt("renter"));
                rent.setUsername(resultSet.getString("username"));
                list.add(rent);
            }
            resultSet = stmt.executeQuery(sql);
            //printUtils.PrintResult(resultSet);
            return list.toArray(new RentEntity[]{});
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }
   
    public void CreateRent(RentEntity rent){
        Integer maxRentID = maxRentID();
        Integer movies_id = getMoviesID(rent.getMoviesTitle());
        Integer userID = getUserID(rent.getUsername());
        String sql = "INSERT INTO rents (rent_id,rent_date,return_date, movies_id, renter) VALUES (?,?,NULL,?,?)";
        try(
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            
            stmt.setInt(1, maxRentID+1);
            stmt.setDate(2, new java.sql.Date(rent.getRentDate().getTime()));
            stmt.setInt(3, movies_id);
            stmt.setInt(4, userID);
            stmt.executeUpdate();
            }catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        
    }

    public Integer maxRentID(){
        String sql = "SELECT max(rent_id) FROM rents";
        int maxRentId = 0;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                maxRentId = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return maxRentId;
    }

    public Integer getUserID(String Username){
        String sql = "SELECT users_id FROM users WHERE username = ?";
        int UserID = 0;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                UserID = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return UserID;
    }

    public Integer getMoviesID(String title){
        String sql = "SELECT movies_id FROM movies WHERE title =?";
        int movieID = 0;
         try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                movieID = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return movieID;
    }


    public void ReturnRent(Integer rent_id, java.sql.Date return_date){
        String sql = "UPDATE rents SET return_date = ? WHERE rent_id =?";
        try(
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            
            stmt.setDate(1, new java.sql.Date(return_date.getTime()));
            stmt.setInt(2, rent_id);
            stmt.executeUpdate();
            }catch (SQLException ex){
                throw new RuntimeException(ex);
            }
    }

    public boolean CheckRentExist(Integer rent_id){
        String sql = "SELECT * FROM rents WHERE rent_id = ?";
         try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rent_id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                resultSet.close();
                return true;
            } else {
                resultSet.close();
                return false;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
       /*public void DeleteRent(Integer rent_id){
        String sql = "DELETE FROM rents WHERE rent_id =?";
         try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rent_id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }*/
}