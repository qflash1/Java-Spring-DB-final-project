package com.dbw.spring_boot.respositories;

import com.dbw.spring_boot.model.Adresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AdresseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdresseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class AdresseRowMapper implements RowMapper<Adresse> {
        @Override
        public Adresse mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Adresse(
                    rs.getInt("adresse_id"),
                    rs.getBoolean("aktiv"),
                    rs.getString("strasse"),
                    rs.getString("hausnummer"),
                    rs.getString("plz"),
                    rs.getString("ort"),
                    rs.getString("land")
            );
        }
    }

    public List<Adresse> findAll() {
        String sql = "SELECT * FROM adresse";
        return jdbcTemplate.query(sql, new AdresseRowMapper());
    }

    public Adresse findById(Integer id) {
        String sql = "SELECT * FROM adresse WHERE adresse_id = ?";
        List<Adresse> results = jdbcTemplate.query(sql, new AdresseRowMapper(), id);
        return results.isEmpty() ? null : results.get(0);
    }

    public Adresse save(Adresse adresse) {
        String sql = "INSERT INTO adresse (aktiv, strasse, hausnummer, plz, ort, land) VALUES (?, ?, ?, ?, ?, ?) RETURNING adresse_id";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                adresse.getAktiv(),
                adresse.getStrasse(),
                adresse.getHausnummer(),
                adresse.getPlz(),
                adresse.getOrt(),
                adresse.getLand());
        adresse.setAdresseId(id);
        return adresse;
    }

    public void update(Adresse adresse) {
        String sql = "UPDATE adresse SET aktiv = ?, strasse = ?, hausnummer = ?, plz = ?, ort = ?, land = ? WHERE adresse_id = ?";
        jdbcTemplate.update(sql,
                adresse.getAktiv(),
                adresse.getStrasse(),
                adresse.getHausnummer(),
                adresse.getPlz(),
                adresse.getOrt(),
                adresse.getLand(),
                adresse.getAdresseId());
    }
}
