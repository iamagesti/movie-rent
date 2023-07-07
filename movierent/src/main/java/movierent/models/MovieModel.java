package movierent.models;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import movierent.entities.MovieEntity;
import movierent.entities.UserEntity;
import movierent.utils.PrintUtils;

public class MovieModel {
    private DataSource dataSource;
    private PrintUtils printUtils;

    public MovieModel(DataSource dataSource){
        this.dataSource = dataSource;
    }


    public MovieEntity[]findAllMovies(){
        String sql = "SELECT * FROM movies";
        printUtils = new PrintUtils(dataSource);
        try (
                Connection connection = dataSource.getConnection();
                Statement stmt = connection.createStatement();
                ) {
            ResultSet resultSet = stmt.executeQuery(sql);       
            List<MovieEntity> list = new ArrayList<>();
            while (resultSet.next()) {
                MovieEntity movie = new MovieEntity();
                movie.setMoviesID(resultSet.getInt("movies_id"));
                movie.setMoviesTitle(resultSet.getString("title"));
                movie.setMoviesGenre(resultSet.getString("genre"));
                
                list.add(movie);
            }
            resultSet = stmt.executeQuery(sql);
            printUtils.PrintResult(resultSet);
            return list.toArray(new MovieEntity[] {});
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void CreateMovie(MovieEntity movie){
        String sql = "INSERT INTO movies (title, genre) VALUES (?,?)";
        try(
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);){
                stmt.setString(1,movie.getMoviesTitle());
                stmt.setString(2, movie.getMoviesGenre());
                stmt.executeUpdate();
            }catch(SQLException ex){
                throw new RuntimeException(ex);
            }
    }
    public void DeleteMovie(Integer movies_id) {
        if (!IsRentUseThisMovie(movies_id)) {
            String sql = "DELETE FROM movies WHERE movies_id = ?";
            try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)
            ) {
                stmt.setInt(1, movies_id);
                stmt.executeUpdate();
                System.out.println("Delete Movie Succeed");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new MovieDeletionException("This movie is currently being rented. Delete movie failed!");
        }
    }
    public class MovieDeletionException extends RuntimeException {
        public MovieDeletionException(String message) {
            super(message);
        }
    }
    

    public boolean CheckMovieTitleExist(String title){
        String sql = "SELECT * FROM movies WHERE title = ?";
        try(
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, title);
                ResultSet resultSet = stmt.executeQuery();

                if(resultSet.next()){
                    resultSet.close();;
                    return true;
                }else{
                    resultSet.close();;
                    return false;
                }
            }catch (SQLException ex){
                throw new RuntimeException(ex);
            }
    }

    public boolean CheckMovieExist(Integer movies_id){
        String sql = "SELECT * FROM movies WHERE movies_id =?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, movies_id);
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

    public boolean IsRentUseThisMovie(Integer movies_id){
        String sql = "SELECT * FROM rents r, movies m WHERE r.movies_id = m.movies_id and m.movies_id = ?";
        try(
            Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setInt(1, movies_id);
                ResultSet resultSet = stmt.executeQuery();

                if(resultSet.next()){
                    resultSet.close();
                    return true;
                }else{
                    resultSet.close();
                    return false;
                }
            }catch(SQLException ex){
                throw new RuntimeException(ex);
            }
    }

    public MovieEntity[] GetRentedMovies(UserEntity user){
        String sql = "SELECT * FROM rents r JOIN movies m ON r.movies_id = m.movies_id WHERE r.user_id = ?";
        printUtils = new PrintUtils(dataSource);
        try (
        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, user.getUserID());
        ResultSet resultSet = stmt.executeQuery();
        printUtils.PrintResult(resultSet);
        
        List<MovieEntity> list = new ArrayList<>();
        while (resultSet.next()) {
            MovieEntity movie = new MovieEntity();
            movie.setMoviesID(resultSet.getInt("movies_id"));
            movie.setMoviesTitle(resultSet.getString("title"));
            movie.setMoviesGenre(resultSet.getString("genre"));
            movie.setRentDate(resultSet.getDate("rent_date"));
            
            list.add(movie);
        }
        
        resultSet.close();
        return list.toArray(new MovieEntity[]{});
    } catch (SQLException ex) {
        throw new RuntimeException(ex);
    }
    }
}
