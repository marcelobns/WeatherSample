package br.senai.rr.clima;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by marcelobarbosa on 7/17/15.
 */
public class Clima {
    private String icon;
    private long time;
    private double temperature;
    private double humidity;
    private double precipProbability;
    private String summary;
    private int iconId;
    private String timezone;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getIconId() {
        switch (icon){
            case "rain" :
                iconId = R.drawable.rain;
                break;
            case "clear-day" :
                iconId = R.drawable.clear_day;
                break;
            case "clear-night" :
                iconId = R.drawable.clear_night;
                break;
            case "cloudy" :
                iconId = R.drawable.cloudy;
                break;
            case "cloudy-night" :
                iconId = R.drawable.cloudy_night;
                break;
            case "fog" :
                iconId = R.drawable.fog;
                break;
            case "wind" :
                iconId = R.drawable.wind;
                break;
            case "sunny" :
                iconId = R.drawable.sunny;
                break;
            default:
                iconId = R.drawable.partly_cloudy;
                break;
        }

        return iconId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTime() {
        SimpleDateFormat simple = new SimpleDateFormat("H:mm");
        simple.setTimeZone(TimeZone.getTimeZone(timezone));
        Date date = new Date(time*1000);

        return simple.format(date);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTemperature() {
        temperature = Math.round(((temperature-32)*5)/9);
        return (int)temperature+"";
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return Math.round(humidity*100)+"";
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getPrecipProbability() {
        return Math.round(precipProbability*100)+"";
    }

    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTest(){
        return "{\n" +
                "\"latitude\": 2.820397,\n" +
                "\"longitude\": -60.672045,\n" +
                "\"timezone\": \"America/Boa_Vista\",\n" +
                "\"offset\": -4,\n" +
                "\"currently\": {\n" +
                "    \"time\": 1437333056,\n" +
                "    \"summary\": \"Light Rain\",\n" +
                "    \"icon\": \"rain\",\n" +
                "    \"precipIntensity\": 0.0132,\n" +
                "    \"precipProbability\": 0.7,\n" +
                "    \"precipType\": \"rain\",\n" +
                "    \"temperature\": 79.68,\n" +
                "    \"apparentTemperature\": 79.68,\n" +
                "    \"dewPoint\": 69.63,\n" +
                "    \"humidity\": 0.71,\n" +
                "    \"windSpeed\": 2.2,\n" +
                "    \"windBearing\": 77,\n" +
                "    \"visibility\": 6.21,\n" +
                "    \"cloudCover\": 0.46,\n" +
                "    \"pressure\": 1011.79,\n" +
                "    \"ozone\": 265.94\n" +
                "    }\n" +
                "}\n";
    }
}
