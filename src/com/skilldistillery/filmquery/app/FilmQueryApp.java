package com.skilldistillery.filmquery.app;

import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

//  private void test() {
//    Film film = db.findFilmById(1);
//    System.out.println(film);
//  }

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		System.out.println("\uD83C\uDFA5 Welcome to the Film Lookup App! \uD83C\uDFA5");
		System.out.println();

		boolean keepGoing = true;

		while (keepGoing) {
			printMenu();
			int choice = input.nextInt();
			switch (choice) {
			case 1:
				userFilmId(input);
				break;
			case 2:
				getFilmByKeyword(input);
				break;
			case 3: 
				System.out.println("You have chosen to exit. --thank you--");
				keepGoing= false;
				break;
			}

		}

	}

	private void printMenu() {
		System.out.println("*Please select a menu option*");
		System.out.println("1. Look up a film by its ID");
		System.out.println("2. Look up a film by a search keyword.");
		System.out.println("3. Exit the app. ");
		System.out.println();
		System.out.println("Please enter your selection (1-3): ");
		
	}
	
	private void userFilmId(Scanner input) {
		System.out.println("Please enter the film ID: ");
		int filmId = input.nextInt();
		Film result = (db.findFilmById(filmId));

		if (result != null) {
			System.out.println("INFO FOR FILM " + filmId);
			System.out.println("------------------------");
			result.printInfo();
			System.out.println("------------------------");
			System.out.println();
		} else {
			System.out.println("There is no film by that film ID. Returning to main menu:");
			System.out.println();

		}
	}
	
	private Film getFilmByKeyword(Scanner input) {
		java.util.List<Film> films;
		System.out.println("Please enter a search keyword: ");

		try {
			String keyword = input.next();
			films= db.findFilmBySearchWord(keyword);
			if (films.size() > 0) {
				for (Film film : films) {
					System.out.println();
					film.printInfo();
					System.out.println();
				}
			System.out.println();
			System.out.println("There are " + films.size() + " movies with the keyword '" + keyword+ "'");
			System.out.println();
			}
			else {
				System.out.println("No films with '" + keyword + "' exist in our database.");
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println("Input is invalid.");
			input.next();
			startUserInterface(input);
		}
		return null;

	}
	

}
