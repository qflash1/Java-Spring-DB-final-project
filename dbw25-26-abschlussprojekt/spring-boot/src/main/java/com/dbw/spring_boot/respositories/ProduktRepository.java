package com.dbw.spring_boot.respositories;

import com.dbw.spring_boot.model.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProduktRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProduktRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class ProduktRowMapper implements RowMapper<Produkt> {
        @Override
        public Produkt mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Produkt(
                    rs.getString("sku"),
                    rs.getString("name"),
                    rs.getBigDecimal("preis"),
                    rs.getInt("lagerbestand"),
                    rs.getInt("angelegt_von")
            );
        }
    }

    public List<Produkt> findAll() {
        String sql = "SELECT * FROM produkt";
        return jdbcTemplate.query(sql, new ProduktRowMapper());
    }

    public Produkt findBySku(String sku) {
        String sql = "SELECT * FROM produkt WHERE sku = ?";
        List<Produkt> results = jdbcTemplate.query(sql, new ProduktRowMapper(), sku);
        return results.isEmpty() ? null : results.get(0);
    }

    public Produkt save(Produkt produkt) {
        String sql = "INSERT INTO produkt (sku, name, preis, lagerbestand, angelegt_von) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                produkt.getSku(),
                produkt.getName(),
                produkt.getPreis(),
                produkt.getLagerbestand(),
                produkt.getAngelegtVon());
        return produkt;
    }

    public void deleteBySku(String sku) {
        String sql = "DELETE FROM produkt WHERE sku = ?";
        jdbcTemplate.update(sql, sku);
    }

    public void updateLagerbestand(String sku, Integer lagerbestand) {
        String sql = "UPDATE produkt SET lagerbestand = ? WHERE sku = ?";
        jdbcTemplate.update(sql, lagerbestand, sku);
    }
}
