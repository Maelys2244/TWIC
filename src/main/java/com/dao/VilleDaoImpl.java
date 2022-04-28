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

	public ResultSet getVille(String codeINSEE) {

		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;

		daoFactory = DaoFactory.getInstance();

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();

			if (codeINSEE == null) {
				resultat = statement.executeQuery("SELECT * FROM ville_france;");
			} else {
				resultat = statement
						.executeQuery("SELECT * FROM ville_france WHERE Code_commune_INSEE =" + codeINSEE + ";");
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

		return resultat;
	}

	public void saveVille(Ville ville) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		daoFactory = DaoFactory.getInstance();

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement(
					"INSERT INTO ville_france (Code_commune_INSEE, Nom_commune, Code_postal, Libelle_acheminement,Ligne_5, Latitude, Longitude) VALUES(?,?,?,?,?,?,?);");
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

	public void updateVille(Ville oldVille, Ville newVille) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		daoFactory = DaoFactory.getInstance();

		try {
			connexion = daoFactory.getConnection();
			if (!oldVille.getNomCommune().equals(newVille.getNomCommune())) {
				preparedStatement = connexion
						.prepareStatement("UPDATE ville_france SET Nom_commune =" + newVille.getNomCommune()
								+ " WHERE Code_commune_INSEE = " + oldVille.getCodeCommuneINSEE() + ";");
			}
			if (!oldVille.getLibelleAcheminement().equals(newVille.getLibelleAcheminement())) {
				preparedStatement = connexion.prepareStatement(
						"UPDATE ville_france SET Libelle_acheminement =" + newVille.getLibelleAcheminement()
								+ " WHERE Code_commune_INSEE = " + oldVille.getCodeCommuneINSEE() + ";");
			}
			if (Double.compare(oldVille.getLatitude(), newVille.getLatitude()) != 0) {
				preparedStatement = connexion
						.prepareStatement("UPDATE ville_france SET Latitude =" + Double.toString(newVille.getLatitude())
								+ " WHERE Code_commune_INSEE = " + oldVille.getCodeCommuneINSEE() + ";");
			}
			if (Double.compare(oldVille.getLongitude(), newVille.getLongitude()) != 0) {
				preparedStatement = connexion.prepareStatement(
						"UPDATE ville_france SET Longitude =" + Double.toString(newVille.getLongitude())
								+ " WHERE Code_commune_INSEE = " + oldVille.getCodeCommuneINSEE() + ";");
			}
			if (!oldVille.getCodePostal().equals(newVille.getCodePostal())) {
				preparedStatement = connexion
						.prepareStatement("UPDATE ville_france SET Code_postal =" + newVille.getCodePostal()
								+ " WHERE Code_commune_INSEE = " + oldVille.getCodeCommuneINSEE() + ";");
			}
			if (!oldVille.getCodeCommuneINSEE().equals(newVille.getCodeCommuneINSEE())) {
				preparedStatement = connexion.prepareStatement(
						"UPDATE ville_france SET Code_commune_INSEE =" + newVille.getCodeCommuneINSEE()
								+ " WHERE Code_commune_INSEE = " + oldVille.getCodeCommuneINSEE() + ";");
			}
			preparedStatement.executeUpdate();

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
		PreparedStatement preparedStatement = null;
		daoFactory = DaoFactory.getInstance();

		try {
			connexion = daoFactory.getConnection();
			System.out.println(connexion.prepareStatement(
					"DELETE FROM ville_france WHERE Code_commune_INSEE = " + ville.getCodeCommuneINSEE() + ";"));
			preparedStatement = connexion.prepareStatement(
					"DELETE FROM ville_france WHERE Code_commune_INSEE = " + ville.getCodeCommuneINSEE() + ";");
			preparedStatement.executeUpdate();

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
