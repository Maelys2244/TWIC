package com.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.VilleDaoImpl;
import com.dto.Ville;
import com.google.gson.Gson;

@RestController
public class VilleController {
	VilleDaoImpl villeDaoImpl = new VilleDaoImpl();

	@RequestMapping(value = "/ville", method = RequestMethod.GET)
	public ArrayList<Ville> get(@RequestParam(required = false, value = "codeINSEE") String codeINSEE)
			throws SQLException {

		ArrayList<Ville> listeVille = new ArrayList<>();

		ResultSet resultat = villeDaoImpl.getVille(codeINSEE);

		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeVille;
	}

	@RequestMapping(value = "/ville", method = RequestMethod.POST)
	public ResponseEntity<Object> post(@RequestBody String request) throws SQLException {
		Gson gson = new Gson();
		Ville ville = gson.fromJson(request, Ville.class);
		villeDaoImpl.saveVille(ville);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/ville/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> put(@RequestBody String request, @PathVariable String id) throws SQLException {
		Gson gson = new Gson();
		Ville oldVille = get(id).get(0);
		Ville newVille = gson.fromJson(request, Ville.class);
		villeDaoImpl.updateVille(oldVille, newVille);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/ville/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable String id) throws SQLException {
		Ville ville = get(id).get(0);
		villeDaoImpl.deleteVille(ville);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}