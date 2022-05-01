package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.dto.Ville;

public class VilleDaoImpl implements VilleDao {

	public VilleDaoImpl() {
		// do nothing because it's the constructor
	}

	private DaoFactory daoFactory;

	public ArrayList<Ville> getVille(String codeINSEE) throws SQLException{

		Connection connexion = null;
		daoFactory = DaoFactory.getInstance();
		ArrayList<Ville> listeVille = new ArrayList<>();

		String requete = null;
		if (codeINSEE == null) {
			requete = "SELECT * FROM ville_france;";
		} else {
			requete = "SELECT * FROM ville_france WHERE Code_commune_INSEE = " + codeINSEE + ";";
		}
		connexion = daoFactory.getConnection();

		try (Statement statement = connexion.createStatement()) {

			try (ResultSet resultat = statement.executeQuery(requete)) {
				while (resultat.next()) {
					Ville ville = new Ville();
					ville.setNomCommune(resultat.getString("Nom_Commune"));
					ville.setCodeCommuneINSEE(resultat.getString("Code_commune_INSEE"));
					ville.setCodePostal(resultat.getString("Code_postal"));
					ville.setLatitude(Float.valueOf(resultat.getString("Latitude")));
					ville.setLibelleAcheminement(resultat.getString("Libelle_acheminement"));
					ville.setLongitude(Float.valueOf(resultat.getString("Longitude")));
					listeVille.add(ville);
				}
			}

		} catch (SQLException e) {
			System.out.println(e);
		}

		return listeVille;
	}

	public void saveVille(Ville ville){
		daoFactory = DaoFactory.getInstance();
		Connection connexion = null;

		try {
			connexion = daoFactory.getConnection();
			try (PreparedStatement preparedStatement = connexion.prepareStatement(
					"INSERT INTO ville_france (Code_commune_INSEE, Nom_commune, Code_postal, Libelle_acheminement,Ligne_5, Latitude, Longitude) VALUES(? ,? ,? ,? ,? ,? ,? );")) {
				preparedStatement.setString(1, ville.getCodeCommuneINSEE());
				preparedStatement.setString(2, ville.getNomCommune());
				preparedStatement.setString(3, ville.getCodePostal());
				preparedStatement.setString(4, ville.getLibelleAcheminement());
				if (ville.getLigne5() == null) {
					preparedStatement.setString(5, "");
				} else {
					preparedStatement.setString(5, ville.getLigne5());
				}
				preparedStatement.setString(6, Double.toString(ville.getLatitude()));
				preparedStatement.setString(7, Double.toString(ville.getLongitude()));
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			System.out.println(e);
		}

	}

	public void updateVille(Ville oldVille, Ville newVille){

		Connection connexion = null;

		daoFactory = DaoFactory.getInstance();

		try {
			connexion = daoFactory.getConnection();
			try (PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE ville_france SET Code_commune_INSEE = ?, "
					+ "Nom_commune = ?, "
					+ "Code_postal=?, "
					+ "Libelle_acheminement=?, "
					+ "Ligne_5=?, "
					+ "Latitude=?, "
					+ "Longitude=? "
					+ "WHERE Code_commune_INSEE=?;")) {
				preparedStatement.setString(1, newVille.getCodeCommuneINSEE());
				preparedStatement.setString(2, newVille.getNomCommune());
				preparedStatement.setString(3, newVille.getCodePostal());
				preparedStatement.setString(4, newVille.getLibelleAcheminement());
				preparedStatement.setString(5, newVille.getLigne5());
				preparedStatement.setString(6, Double.toString(newVille.getLatitude()));
				preparedStatement.setString(7, Double.toString(newVille.getLongitude()));
				preparedStatement.setString(8, oldVille.getCodeCommuneINSEE());
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			System.out.println(e);
		}

	}

	public void deleteVille(Ville ville) {
		Connection connexion = null;

		daoFactory = DaoFactory.getInstance();

		try {
			connexion = daoFactory.getConnection();
			try (PreparedStatement preparedStatement = connexion.prepareStatement(
					"DELETE FROM ville_france WHERE Code_commune_INSEE = " + ville.getCodeCommuneINSEE() + ";")) {

				preparedStatement.executeUpdate();

			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
