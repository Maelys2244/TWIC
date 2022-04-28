package com.dao;

import java.sql.ResultSet;

public interface VilleDao {
	ResultSet getVille(String codePostal);
}
