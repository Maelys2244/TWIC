package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.DaoFactory;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@RestController
public class VilleController {
	private static DaoFactory daoFactory;

	@RequestMapping(value = "/ville", method = RequestMethod.GET)
	public String get(@RequestParam(required = false, value = "codePostal") String codePostal) {
		System.out.println("get");

		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		daoFactory = DaoFactory.getInstance();
		String listeNom = null;



		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			if (codePostal == null) {
				listeNom = "Pas de code postal entré, voici toutes les villes de la table : ";
				resultat = statement.executeQuery("SELECT Nom_commune FROM ville_france;");
			}
			else {
				listeNom = "La/les ville(s) pour le code postal " + codePostal  + " sont/est : ";
				resultat= statement.executeQuery("SELECT Nom_commune FROM ville_france WHERE Code_postal =" + codePostal +  ";");
			}
			while (resultat.next()) {
				listeNom += "<p>" + resultat.getString("Nom_commune") + "</p>";
			}
		} catch (SQLException e) {
			System.out.println(e);
			try {
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
				System.out.println(e2);
			}

		}

		return listeNom;
	}
	
	  @RequestMapping(value = "/ville", method = RequestMethod.POST)
	  public ResponseEntity<Object> post(@RequestBody String request){
	    	daoFactory = DaoFactory.getInstance();
	    	System.out.println(request);

	    	//try (Connection connexion = daoFactory.getConnection();) {

//				try (PreparedStatement preparedStatement = connexion.prepareStatement(
//					"INSERT INTO ville_france(Code_commune_INSEE, Nom_commune, Code_postal, Libelle_acheminement, Ligne_5, Latitude, Longitude) VALUES(?,?,?,?,?,?,?);", Statement.NO_GENERATED_KEYS);) {
//					System.out.println("et là aussi");
//					preparedStatement.setString(1, codeCommune);
//					preparedStatement.setString(2, nomCommune);
//					preparedStatement.setString(3, codePostal);
//					preparedStatement.setString(4, libelle);
//					preparedStatement.setString(5, ligne);
//
//					preparedStatement.setString(6, lattitude);
//					preparedStatement.setString(7, longitude);
//					
//		
//
//					preparedStatement.executeUpdate();
//
//					connexion.commit();
//				} catch (SQLException e) {
//					System.err.println(e.getMessage());
//					try {
//						if (connexion != null) {
//							connexion.rollback();
//						}
//					} catch (SQLException e2) {
//
//					}
//				}
//			} catch (SQLException e) {
//				System.err.println("problème lors de la création de la connexion à la bdd");
//				e.printStackTrace();
//				
//			}
	    	return ResponseEntity.status(HttpStatus.CREATED).build();
	    	  }

	// TODO : 
	// fonction pour enregistrer un element dans la BDD

	  }
