package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dto.Ville;

public interface VilleDao {
	ResultSet getVille(String codePostal) throws SQLException;

	void saveVille(Ville ville) throws SQLException;

	void updateVille(Ville oldVille, Ville newVille) throws SQLException;

	void deleteVille(Ville ville);
}
