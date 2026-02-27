package fr.itii.apiweb.domain.models.objets;

import fr.itii.apiweb.ui.Header;

public class Meteo {

    /*
    Exemple JSON attendu :
    {
  "latitude": 2,
  "longitude": 45,
  "generationtime_ms": 0.464081764221191,
  "utc_offset_seconds": 0,
  "timezone": "GMT",
  "timezone_abbreviation": "GMT",
  "elevation": 78,
  "current_weather_units": {
    "time": "iso8601",
    "interval": "seconds",
    "temperature": "°C",
    "windspeed": "km/h",
    "winddirection": "°",
    "is_day": "",
    "weathercode": "wmo code"
  },
  "current_weather": {
    "time": "2026-02-05T17:30",
    "interval": 900,
    "temperature": 24.4,
    "windspeed": 15.1,
    "winddirection": 102,
    "is_day": 0,
    "weathercode": 1
  }
}
    */
    private String nom;
    private Double temperature;

    // ===== Constructeurs =====
    public Meteo(String nom, double temperature) {
        this.nom = nom;
        this.temperature = temperature;
    }

    public Meteo() {}


    public String getNomCommune() {
        return this.nom;
    }

    public Double getTemperature() {
        return this.temperature;
    }


    // ===== Affichage =====
    @Override
    public String toString() {
    return String.format(Header.METEO.format,
                getNomCommune(),
                getTemperature() + " °C"
        );
    }
}
