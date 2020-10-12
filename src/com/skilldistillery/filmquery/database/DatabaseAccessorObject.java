package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {

		Film film = null;
		try {
			String user = "student";
			String pass = "student";
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * FROM film \n" + 
					"JOIN film_actor ON film_actor.film_id = film.id \n" + 
					"JOIN actor ON film_actor.actor_id = actor.id \n" + 
					"WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {
				film = new Film(); 
				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getInt("release_year"));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				film.setActors(findActorsByFilmId(filmId));
				film.setLanguage(getLanguage(filmId));
				
				filmResult.close();
				stmt.close();
				conn.close();
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;

	}

	
	@Override 
	public List<Film> findFilmBySearchWord (String keyword) {
		Film film = null;
		List<Film> filmsList = new ArrayList<>();
		try {
			String user = "student";
			String pass = "student";
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.* FROM film WHERE film.title like ? or film.description like ?; ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet filmResult = stmt.executeQuery();
				while (filmResult.next()) {
					film = new Film();
					
					film.setId(filmResult.getInt("id"));
					film.setTitle(filmResult.getString("title"));
					film.setDescription(filmResult.getString("description"));
					film.setReleaseYear(filmResult.getInt("release_year"));
					film.setLanguageId(filmResult.getInt("language_id"));
					film.setRentalDuration(filmResult.getInt("rental_duration"));
					film.setRentalRate(filmResult.getDouble("rental_rate"));
					film.setLength(filmResult.getInt("length"));
					film.setReplacementCost(filmResult.getDouble("replacement_cost"));
					film.setRating(filmResult.getString("rating"));
					film.setSpecialFeatures(filmResult.getString("special_features"));
					film.setActors(findActorsByFilmId(filmResult.getInt("id")));
					film.setLanguage(getLanguage(film.getId()));

					filmsList.add(film);
					
					
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filmsList;
		
	}
	
	
	
	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String user = "student";
		String pass = "student";
		String sql = "SELECT * from actor WHERE actor.id = ?";
		if (actorId <= 0) {
			return null;
		}
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			while(actorResult.next()) {
				actor = new Actor();
				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));
				
				actorResult.close();
				stmt.close();
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actorsList = new ArrayList<>();
		if (filmId <= 0 ) {
			return null;
		}
		try {
			String user = "student";
			String pass = "student";
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT actor.id, actor.first_name, actor.last_name FROM film \n" + 
					"JOIN film_actor ON film_actor.film_id = film.id \n" + 
					"JOIN actor ON film_actor.actor_id = actor.id \n" + 
					"WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet actorResult = stmt.executeQuery();
			while (actorResult.next()) {
//				Actor actor = new Actor(); 
				int id = actorResult.getInt("id");
				String firstName = actorResult.getString("first_name");
				String lastName = actorResult.getString("last_name");
				Actor actor = new Actor (id, firstName, lastName);
				actorsList.add(actor);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return actorsList;
	}

	@Override
	public String getLanguage(int filmId) {
		if (filmId <= 0) {
			return null;
		}
		String sql = "select language.name from film join language on film.language_id=language.id where film.id = ?";
		String language = "";
		
		String user = "student";
		String pass = "student";
		
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			 
			if (rs.next()) {
				language = rs.getString("language.name");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		return language;
		
		
		
		
		
	}
	

}
