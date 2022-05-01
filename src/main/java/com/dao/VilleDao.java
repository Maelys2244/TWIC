package com.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.dto.Ville;

public interface VilleDao {
	ArrayList<Ville> getVille(String codePostal) throws SQLException;

	void saveVille(Ville ville);

	void updateVille(Ville oldVille, Ville newVille);

	void deleteVille(Ville ville);
}
