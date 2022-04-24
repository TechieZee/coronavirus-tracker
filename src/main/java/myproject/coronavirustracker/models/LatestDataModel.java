package myproject.coronavirustracker.models;


public class LatestDataModel {

    private String state;
    private String country;
    private int lastDayCases;
    private int last7DaysCases;
    private int diffFromPrevDay;

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLastDayCases() {
        return lastDayCases;
    }

    public void setLastDayCases(int lastDayCases) {
        this.lastDayCases = lastDayCases;
    }

    public int getLast7DaysCases() {
        return last7DaysCases;
    }

    public void setLast7DaysCases(int last7DaysCases) {
        this.last7DaysCases = last7DaysCases;
    }

    @Override
    public String toString() {
        return "LatestDataModel{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", lastDayCases=" + lastDayCases +
                '}';
    }
}
