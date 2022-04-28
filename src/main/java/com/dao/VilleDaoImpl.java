package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dto.Ville;

public class VilleDaoImpl implements VilleDao {

	public VilleDaoImpl() {
	}

	private static DaoFactory daoFactory;
	private Connection connexion;

	public ResultSet getVille(String codeINSEE) throws SQLException {

		connexion = null;
		daoFactory = DaoFactory.getInstance();
		
		String requete = null;
		if ( codeINSEE == null) {
			requete = "SELECT * FROM ville_france;";
		} else {
			requete = "SELECT * FROM ville_france WHERE Code_INSEE = "+codeINSEE+";";
		}

		try (Statement statement = connexion.createStatement()) {
			connexion = daoFactory.getConnection();
			try (ResultSet resultat = statement.executeQuery(requete)){
				return resultat;
			}

		} catch (SQLException e) {
			try {
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {
				System.out.println(e2);
			}
		}

		return null;
	}

	public void saveVille(Ville ville) throws SQLException {
		daoFactory = DaoFactory.getInstance();
		Connection connexion = null;

		try {
			connexion = daoFactory.getConnection();
			try (PreparedStatement preparedStatement = connexion.prepareStatement(
					"INSERT INTO ville_france (Code_commune_INSEE, Nom_commune, Code_postal, Libelle_acheminement,Ligne_5, Latitude, Longitude) VALUES(?,?,?,?,?,?,?);")) {
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
			System.err.println(e.getMessage());
			try {
				connexion.rollback();

			} catch (SQLException e2) {

			}
		}

	}

	public void updateVille(Ville oldVille, Ville newVille) throws SQLException {

		Connection connexion = null;

		daoFactory = DaoFactory.getInstance();

		try {
			connexion = daoFactory.getConnection();
			try (PreparedStatement preparedStatement = connexion
					.prepareStatement("UPDATE ville_france SET Nom_commune =" + newVille.getNomCommune()
							+ "SET Nom_commune =" + newVille.getNomCommune() + "SET Libelle_acheminement ="
							+ newVille.getLibelleAcheminement() + "SET Latitude ="
							+ Double.toString(newVille.getLatitude()) + "SET Longitude ="
							+ Double.toString(newVille.getLongitude()) + "SET Code_postal =" + newVille.getCodePostal()
							+ "SET Ligne_5 =" + newVille.getLigne5() + "SET Code_commune_INSEE ="
							+ newVille.getCodeCommuneINSEE() + " WHERE Code_commune_INSEE = "
							+ oldVille.getCodeCommuneINSEE() + ";")) {
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			try {
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {

			}
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
			System.err.println(e.getMessage());
			try {
				if (connexion != null) {
					connexion.rollback();
				}
			} catch (SQLException e2) {

			}

		}
	}
}
