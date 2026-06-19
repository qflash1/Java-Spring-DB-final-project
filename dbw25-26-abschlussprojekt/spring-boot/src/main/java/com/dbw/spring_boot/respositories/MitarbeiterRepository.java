package com.dbw.spring_boot.respositories;

import com.dbw.spring_boot.model.Mitarbeiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MitarbeiterRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MitarbeiterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class MitarbeiterRowMapper implements RowMapper<Mitarbeiter> {

        @Override
        public Mitarbeiter mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Mitarbeiter(
                    rs.getInt("personal_nr"),
                    rs.getString("passwort"),
                    rs.getString("email"),
                    rs.getString("vorname"),
                    rs.getString("nachname")
            );
        }
    }

    public List<Mitarbeiter> findAll() {
        String sql = "SELECT * FROM mitarbeiter";
        return jdbcTemplate.query(sql,new MitarbeiterRowMapper());
    }

    public Mitarbeiter findById(Integer id) {
        String sql = "SELECT * FROM mitarbeiter WHERE personal_nr = ?";
        List<Mitarbeiter> results = jdbcTemplate.query(sql, new MitarbeiterRowMapper(), id);
        return results.isEmpty() ? null : results.get(0);
    }

    public Mitarbeiter save(Mitarbeiter mitarbeiter) {
        String sql = "INSERT INTO mitarbeiter (passwort, email, vorname, nachname) VALUES(?, ?, ?, ?) RETURNING personal_nr";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                mitarbeiter.getPasswort(),
                mitarbeiter.getEmail(),
                mitarbeiter.getVorname(),
                mitarbeiter.getNachname());
        mitarbeiter.setPersonalNr(id);
        return mitarbeiter;
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM  mitarbeiter WHERE personal_nr = ?";
        jdbcTemplate.update(sql, id);
    }

    public Mitarbeiter findByPersonalNrAndPasswort(Integer personalNr, String passwort) {
        String sql = "SELECT * FROM mitarbeiter WHERE personal_nr = ? AND passwort = ?";
        List<Mitarbeiter> results = jdbcTemplate.query(sql, new MitarbeiterRowMapper(), personalNr, passwort);
        return results.isEmpty() ? null : results.get(0);
    }
}
