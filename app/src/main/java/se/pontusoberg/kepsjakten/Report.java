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
    private String number;
    private String way;
    private String otherinfo;

    public Report(int id, String station, String amount, String formatted_timer, String city, String number, String way, String otherinfo) {
        this.id = id;
        this.station = station;
        this.amount = amount;
        this.formatted_timer = formatted_timer;
        this.city = city;
        this.number = number;
        this.way = way;
        this.otherinfo = otherinfo;
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

    public String getNumber(){return number;}

    public String getWay(){return way;}

    public String getOtherinfo(){return otherinfo;}


}