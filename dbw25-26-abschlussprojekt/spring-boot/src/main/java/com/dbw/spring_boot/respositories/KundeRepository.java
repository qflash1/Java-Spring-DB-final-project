package com.dbw.spring_boot.respositories;

import com.dbw.spring_boot.model.Adresse;
import com.dbw.spring_boot.model.Kunde;
import com.dbw.spring_boot.model.KundeHatAdresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class KundeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public KundeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Kunde> findAll() {
        String sql = "SELECT k.kunde_id, k.email, k.vorname, k.nachname, k.passwort, " +
                "a.adresse_id, a.aktiv, a.strasse, a.hausnummer, a.plz, a.ort, a.land, kha.typ " +
                "FROM kunde k " +
                "LEFT JOIN kunde_hat_adressen kha ON k.kunde_id = kha.kunde_id " +
                "LEFT JOIN adresse a ON kha.adresse_id = a.adresse_id " +
                "ORDER BY k.kunde_id";

        return jdbcTemplate.query(sql, rs -> {
            Map<Integer, Kunde> kundenMap = new HashMap<>();

            while (rs.next()) {
                Integer kundeId = rs.getInt("kunde_id");
                Kunde kunde = kundenMap.get(kundeId);
                if (kunde == null) {
                    kunde = new Kunde();
                    kunde.setKundeId(kundeId);
                    kunde.setEmail(rs.getString("email"));
                    kunde.setVorname(rs.getString("vorname"));
                    kunde.setNachname(rs.getString("nachname"));
                    kunde.setPasswort(rs.getString("passwort"));
                    kunde.setAdressen(new ArrayList<>());
                    kundenMap.put(kundeId, kunde);
                }

                Integer adresseId = (Integer) rs.getObject("adresse_id");
                if (adresseId != null) {
                    Adresse adresse = new Adresse();
                    adresse.setAdresseId(adresseId);
                    adresse.setAktiv(rs.getBoolean("aktiv"));
                    adresse.setStrasse(rs.getString("strasse"));
                    adresse.setHausnummer(rs.getString("hausnummer"));
                    adresse.setPlz(rs.getString("plz"));
                    adresse.setOrt(rs.getString("ort"));
                    adresse.setLand(rs.getString("land"));

                    String typ = rs.getString("typ");
                    KundeHatAdresse kundeHatAdresse = new KundeHatAdresse(adresse, typ);
                    kunde.getAdressen().add(kundeHatAdresse);
                }
            }

            return new ArrayList<>(kundenMap.values());
        });
    }


    public Kunde findById(Integer id) {
        String sql = "SELECT k.kunde_id, k.email, k.vorname, k.nachname, k.passwort, " +
                "a.adresse_id, a.aktiv, a.strasse, a.hausnummer, a.plz, a.ort, a.land, kha.typ " +
                "FROM kunde k " +
                "LEFT JOIN kunde_hat_adressen kha ON k.kunde_id = kha.kunde_id " +
                "LEFT JOIN adresse a ON kha.adresse_id = a.adresse_id " +
                "WHERE k.kunde_id = ? " +
                "ORDER BY k.kunde_id";

        List<Kunde> kunden = jdbcTemplate.query(sql, rs -> {
            Map<Integer, Kunde> kundenMap = new HashMap<>();

            while (rs.next()) {
                Integer kundeId = rs.getInt("kunde_id");

                Kunde kunde = kundenMap.get(kundeId);
                if (kunde == null) {
                    kunde = new Kunde();
                    kunde.setKundeId(kundeId);
                    kunde.setEmail(rs.getString("email"));
                    kunde.setVorname(rs.getString("vorname"));
                    kunde.setNachname(rs.getString("nachname"));
                    kunde.setPasswort(rs.getString("passwort"));
                    kunde.setAdressen(new ArrayList<>());
                    kundenMap.put(kundeId, kunde);
                }

                Integer adresseId = (Integer) rs.getObject("adresse_id");
                if (adresseId != null) {
                    Adresse adresse = new Adresse();
                    adresse.setAdresseId(adresseId);
                    adresse.setAktiv(rs.getBoolean("aktiv"));
                    adresse.setStrasse(rs.getString("strasse"));
                    adresse.setHausnummer(rs.getString("hausnummer"));
                    adresse.setPlz(rs.getString("plz"));
                    adresse.setOrt(rs.getString("ort"));
                    adresse.setLand(rs.getString("land"));

                    String typ = rs.getString("typ");
                    KundeHatAdresse kundeHatAdresse = new KundeHatAdresse(adresse, typ);
                    kunde.getAdressen().add(kundeHatAdresse);
                }
            }

            return new ArrayList<>(kundenMap.values());
        }, id);

        return kunden.isEmpty() ? null : kunden.get(0);
    }


    public Kunde findByEmail(String email) {
        String sql = "SELECT k.kunde_id, k.email, k.vorname, k.nachname, k.passwort, " +
                "a.adresse_id, a.aktiv, a.strasse, a.hausnummer, a.plz, a.ort, a.land, kha.typ " +
                "FROM kunde k " +
                "LEFT JOIN kunde_hat_adressen kha ON k.kunde_id = kha.kunde_id " +
                "LEFT JOIN adresse a ON kha.adresse_id = a.adresse_id " +
                "WHERE k.email = ? " +
                "ORDER BY k.kunde_id";

        List<Kunde> kunden = jdbcTemplate.query(sql, rs -> {
            Map<Integer, Kunde> kundenMap = new HashMap<>();

            while (rs.next()) {
                Integer kundeId = rs.getInt("kunde_id");

                Kunde kunde = kundenMap.get(kundeId);
                if (kunde == null) {
                    kunde = new Kunde();
                    kunde.setKundeId(kundeId);
                    kunde.setEmail(rs.getString("email"));
                    kunde.setVorname(rs.getString("vorname"));
                    kunde.setNachname(rs.getString("nachname"));
                    kunde.setPasswort(rs.getString("passwort"));
                    kunde.setAdressen(new ArrayList<>());
                    kundenMap.put(kundeId, kunde);
                }

                Integer adresseId = (Integer) rs.getObject("adresse_id");
                if (adresseId != null) {
                    Adresse adresse = new Adresse();
                    adresse.setAdresseId(adresseId);
                    adresse.setAktiv(rs.getBoolean("aktiv"));
                    adresse.setStrasse(rs.getString("strasse"));
                    adresse.setHausnummer(rs.getString("hausnummer"));
                    adresse.setPlz(rs.getString("plz"));
                    adresse.setOrt(rs.getString("ort"));
                    adresse.setLand(rs.getString("land"));

                    String typ = rs.getString("typ");
                    KundeHatAdresse kundeHatAdresse = new KundeHatAdresse(adresse, typ);
                    kunde.getAdressen().add(kundeHatAdresse);
                }
            }

            return new ArrayList<>(kundenMap.values());
        }, email);

        return kunden.isEmpty() ? null : kunden.get(0);
    }


    public Kunde save(Kunde kunde) {
        String sql = "INSERT INTO kunde (email, vorname, nachname, passwort) VALUES (?, ?, ?, ?) RETURNING kunde_id";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                kunde.getEmail(),
                kunde.getVorname(),
                kunde.getNachname(),
                kunde.getPasswort());
        kunde.setKundeId(id);
        return kunde;
    }

    public void update(Kunde kunde) {
        String sql = "UPDATE kunde SET email = ?, vorname = ?, nachname = ?, passwort = ? WHERE kunde_id = ?";
        jdbcTemplate.update(sql,
                kunde.getEmail(),
                kunde.getVorname(),
                kunde.getNachname(),
                kunde.getPasswort(),
                kunde.getKundeId());
    }

    public Kunde findByEmailAndPasswort(String email, String passwort) {
        String sql = "SELECT k.kunde_id, k.email, k.vorname, k.nachname, k.passwort, " +
                "a.adresse_id, a.aktiv, a.strasse, a.hausnummer, a.plz, a.ort, a.land, kha.typ " +
                "FROM kunde k " +
                "LEFT JOIN kunde_hat_adressen kha ON k.kunde_id = kha.kunde_id " +
                "LEFT JOIN adresse a ON kha.adresse_id = a.adresse_id " +
                "WHERE k.email = ? AND k.passwort = ? " +
                "ORDER BY k.kunde_id";

        List<Kunde> kunden = jdbcTemplate.query(sql, rs -> {
            Map<Integer, Kunde> kundenMap = new HashMap<>();

            while (rs.next()) {
                Integer kundeId = rs.getInt("kunde_id");

                Kunde kunde = kundenMap.get(kundeId);
                if (kunde == null) {
                    kunde = new Kunde();
                    kunde.setKundeId(kundeId);
                    kunde.setEmail(rs.getString("email"));
                    kunde.setVorname(rs.getString("vorname"));
                    kunde.setNachname(rs.getString("nachname"));
                    kunde.setPasswort(rs.getString("passwort"));
                    kunde.setAdressen(new ArrayList<>());
                    kundenMap.put(kundeId, kunde);
                }

                Integer adresseId = (Integer) rs.getObject("adresse_id");
                if (adresseId != null) {
                    Adresse adresse = new Adresse();
                    adresse.setAdresseId(adresseId);
                    adresse.setAktiv(rs.getBoolean("aktiv"));
                    adresse.setStrasse(rs.getString("strasse"));
                    adresse.setHausnummer(rs.getString("hausnummer"));
                    adresse.setPlz(rs.getString("plz"));
                    adresse.setOrt(rs.getString("ort"));
                    adresse.setLand(rs.getString("land"));

                    String typ = rs.getString("typ");
                    KundeHatAdresse kundeHatAdresse = new KundeHatAdresse(adresse, typ);
                    kunde.getAdressen().add(kundeHatAdresse);
                }
            }

            return new ArrayList<>(kundenMap.values());
        }, email, passwort);

        return kunden.isEmpty() ? null : kunden.get(0);
    }


}
