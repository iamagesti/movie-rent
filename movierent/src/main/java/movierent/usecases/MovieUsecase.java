package movierent.usecases;

import com.zaxxer.hikari.HikariDataSource;

import movierent.entities.MovieEntity;
import movierent.entities.UserEntity;
import movierent.models.MovieModel;
import movierent.utils.DBConnectionUtil;

public class MovieUsecase {
    private HikariDataSource dataSource;
    private MovieModel movieModel;

    public MovieUsecase(){
        dataSource = DBConnectionUtil.getDataSource();
        movieModel = new MovieModel(dataSource);
    }

    public Integer GetMovieList(){

        MovieEntity[] movieList= movieModel.findAllMovies();
        return movieList.length;
    }

    public void AddMovie(String title, String genre){
        
        //movieData.setMoviesTitle(title);
        if(!IsMovieTitleExist(title)){
            MovieEntity movieData = new MovieEntity();
            movieData.setMoviesTitle(title);
            movieData.setMoviesGenre(genre);
            movieModel.CreateMovie(movieData);
            System.out.println("Create Movie Succeed !");
        }else{
            System.out.println("Movie already exist, add Movie failed");
        }
    }
    
    public void DeleteMovie(Integer movies_id){
        if(IsMovieExist(movies_id)){
            if(!IsRentUseThisMovie(movies_id)){
                movieModel.DeleteMovie(movies_id);
                System.out.println("");
            }else{
                System.out.println("This movie is currently being rented. Delete movie failed!");
            }
        }else{
            System.out.println(movies_id + " is not found, delete movie failed");
        }
    }
    
    public Boolean IsMovieTitleExist(String title){
        if(movieModel.CheckMovieTitleExist(title)){
            return true;
        }else{
            return false;
        }
    }
    public Boolean IsMovieExist(Integer movies_id){
        if(movieModel.CheckMovieExist(movies_id)){
            return true;
        }else{
            return false;
        }
    }

    public Boolean IsRentUseThisMovie(Integer movies_id){
        if(movieModel.IsRentUseThisMovie(movies_id)){
            return true;
        }else{
            return false;
        }
    }
    public MovieEntity[] GetRentedMovies(UserEntity user){
        return movieModel.GetRentedMovies(user);
    }
    

}
