package com.dbw.spring_boot.respositories;

import com.dbw.spring_boot.model.Bestellposition;
import com.dbw.spring_boot.model.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BestellpositionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProduktRepository produktRepository;

    @Autowired
    public BestellpositionRepository(JdbcTemplate jdbcTemplate, ProduktRepository produktRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.produktRepository = produktRepository;
    }

    private static class BestellpositionRowMapper implements RowMapper<Bestellposition> {
        @Override
        public Bestellposition mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Bestellposition(
                    rs.getInt("position_id"),
                    rs.getInt("bestellung_id"),
                    rs.getString("sku"),
                    rs.getInt("menge"),
                    null
            );
        }
    }

    public List<Bestellposition> findAll() {
        String sql = "SELECT * FROM bestellposition";
        List<Bestellposition> positionen = jdbcTemplate.query(sql, new BestellpositionRowMapper());

        for (Bestellposition position : positionen) {
            BigDecimal preis = produktRepository.findBySku(position.getProduktSku()).getPreis();
            position.setGesamtpreis(preis.multiply(BigDecimal.valueOf(position.getMenge())));
        }

        return positionen;
    }

    public Bestellposition findById(Integer id) {
        String sql = "SELECT * FROM bestellposition WHERE position_id = ?";
        List<Bestellposition> results = jdbcTemplate.query(sql, new BestellpositionRowMapper(), id);

        if (results.isEmpty()) {
            return null;
        }Bestellposition position = results.get(0);
        Produkt produkt = produktRepository.findBySku(position.getProduktSku());
        if (produkt != null) {
            BigDecimal preis = produkt.getPreis();
            position.setGesamtpreis(preis.multiply(BigDecimal.valueOf(position.getMenge())));
        }

        return position;
    }

    public Bestellposition save(Bestellposition position) {
        String sql = "INSERT INTO bestellposition (bestellung_id, sku, menge) VALUES (?, ?, ?) RETURNING position_id";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                position.getBestellungId(),
                position.getProduktSku(),
                position.getMenge());
        position.setPositionsId(id);

        BigDecimal preis = produktRepository.findBySku(position.getProduktSku()).getPreis();
        position.setGesamtpreis(preis.multiply(BigDecimal.valueOf(position.getMenge())));

        return position;
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM bestellposition WHERE position_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
