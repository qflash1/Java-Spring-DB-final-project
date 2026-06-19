package com.dbw.spring_boot.respositories;

import com.dbw.spring_boot.model.Bestellung;
import com.dbw.spring_boot.model.BestellpositionMitProdukt;
import com.dbw.spring_boot.model.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BestellungRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BestellungRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Bestellung> findAll() {
        String sql = "SELECT b.bestellung_id, b.kunde_id, b.mitarbeiterzuweis, b.datum, b.status, " +
                "bp.position_id, bp.bestellung_id AS bp_bestellung_id, bp.menge, " +
                "p.sku, p.name, p.preis, p.lagerbestand, p.angelegt_von " +
                "FROM bestellung b " +
                "LEFT JOIN bestellposition bp ON b.bestellung_id = bp.bestellung_id " +
                "LEFT JOIN produkt p ON bp.sku = p.sku " +
                "ORDER BY b.bestellung_id";

        return jdbcTemplate.query(sql, new BestellungResultSetExtractor());
    }

    public Bestellung findById(Integer id) {
        String sql = "SELECT b.bestellung_id, b.kunde_id, b.mitarbeiterzuweis, b.datum, b.status, " +
                "bp.position_id, bp.bestellung_id AS bp_bestellung_id, bp.menge, " +
                "p.sku, p.name, p.preis, p.lagerbestand, p.angelegt_von " +
                "FROM bestellung b " +
                "LEFT JOIN bestellposition bp ON b.bestellung_id = bp.bestellung_id " +
                "LEFT JOIN produkt p ON bp.sku = p.sku " +
                "WHERE b.bestellung_id = ? " +
                "ORDER BY b.bestellung_id";

        List<Bestellung> bestellungen = jdbcTemplate.query(sql, new BestellungResultSetExtractor(), id);
        return bestellungen.isEmpty() ? null : bestellungen.get(0);
    }

    public Bestellung save(Bestellung bestellung) {
        String sql = "INSERT INTO bestellung (kunde_id, mitarbeiterzuweis, datum, status) VALUES (?, ?, ?, ?) RETURNING bestellung_id";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                bestellung.getKundeId(),
                bestellung.getPersonalnr(),
                Timestamp.from(bestellung.getDatum().toInstant()),
                bestellung.getStatus());
        bestellung.setBestellungId(id);
        return bestellung;
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM bestellung WHERE bestellung_id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class BestellungResultSetExtractor implements org.springframework.jdbc.core.ResultSetExtractor<List<Bestellung>> {
        @Override
        public List<Bestellung> extractData(ResultSet rs) throws SQLException {
            Map<Integer, Bestellung> bestellungMap = new HashMap<>();

            while (rs.next()) {
                Integer bestellungId = rs.getInt("bestellung_id");

                Bestellung bestellung = bestellungMap.get(bestellungId);
                if (bestellung == null) {
                    bestellung = new Bestellung();
                    bestellung.setBestellungId(bestellungId);
                    bestellung.setKundeId(rs.getInt("kunde_id"));
                    bestellung.setPersonalnr(rs.getInt("mitarbeiterzuweis"));
                    bestellung.setDatum(rs.getTimestamp("datum").toInstant().atZone(java.time.ZoneId.of("UTC")));
                    bestellung.setStatus(rs.getString("status"));
                    bestellung.setPositionen(new ArrayList<>());
                    bestellungMap.put(bestellungId, bestellung);
                }

                Integer positionId = (Integer) rs.getObject("position_id");
                if (positionId != null) {
                    Produkt produkt = new Produkt(
                            rs.getString("sku"),
                            rs.getString("name"),
                            rs.getBigDecimal("preis"),
                            rs.getInt("lagerbestand"),
                            rs.getInt("angelegt_von")
                    );

                    BestellpositionMitProdukt position = new BestellpositionMitProdukt();
                    position.setPositionsId(positionId);
                    position.setBestellungId(rs.getInt("bp_bestellung_id"));
                    position.setProdukt(produkt);
                    position.setMenge(rs.getInt("menge"));
                    position.setGesamtpreis(produkt.getPreis().multiply(java.math.BigDecimal.valueOf(position.getMenge())));

                    bestellung.getPositionen().add(position);
                }
            }

            return new ArrayList<>(bestellungMap.values());
        }
    }
}
