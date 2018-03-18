package se.pontusoberg.kepsjakten;

/**
 * Created by pontu on 2018-01-11.
 */

// Skapar klassen Report med dess värden
public class Report {
    private int id;
    private String station;
    private String amount;
    private String formatted_timer;
    private String city;

    public Report(int id, String station, String amount, String formatted_timer, String city) {
        this.id = id;
        this.station = station;
        this.amount = amount;
        this.formatted_timer = formatted_timer;
        this.city = city;
    }
//test
    public int getId() {
        return id;
    }

    public String getStation() {
        return station;
    }

    public String getAmount() {
        return amount;
    }

    public String getFormatted_timer() {
        return formatted_timer;
    }

    public String getCity() {
        return city;
    }


}