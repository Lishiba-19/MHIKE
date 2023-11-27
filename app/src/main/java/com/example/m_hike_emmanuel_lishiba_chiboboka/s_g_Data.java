package com.example.m_hike_emmanuel_lishiba_chiboboka;

public class s_g_Data {



    private String hikeid;
    private String hikeName;
    private String locationName;
    private String hikeDate;
    private String parking;
    private String hikeLenght;
    private String hikeDifficulty;
    private String hikeDesc;
    private String hikeDistance;
    private String hikeWeather;

    public s_g_Data(String hikeid, String hikeName, String locationName, String hikeDate,
                    String parking, String hikeLenght, String hikeDifficulty, String hikeDesc,
                    String hikeDistance, String hikeWeather) {
        this.hikeid = hikeid;
        this.hikeName = hikeName;
        this.locationName = locationName;
        this.hikeDate = hikeDate;
        this.parking = parking;
        this.hikeLenght = hikeLenght;
        this.hikeDifficulty = hikeDifficulty;
        this.hikeDesc = hikeDesc;
        this.hikeDistance = hikeDistance;
        this.hikeWeather = hikeWeather;
    }

    public String getHikeid() {
        return hikeid;
    }

    public void setHikeid(String hikeid) {
        this.hikeid = hikeid;
    }

    public String getHikeName() {
        return hikeName;
    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getHikeDate() {
        return hikeDate;
    }

    public void setHikeDate(String hikeDate) {
        this.hikeDate = hikeDate;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getHikeLenght() {
        return hikeLenght;
    }

    public void setHikeLenght(String hikeLenght) {
        this.hikeLenght = hikeLenght;
    }

    public String getHikeDifficulty() {
        return hikeDifficulty;
    }

    public void setHikeDifficulty(String hikeDifficulty) {
        this.hikeDifficulty = hikeDifficulty;
    }

    public String getHikeDesc() {
        return hikeDesc;
    }

    public void setHikeDesc(String hikeDesc) {
        this.hikeDesc = hikeDesc;
    }

    public String getHikeDistance() {
        return hikeDistance;
    }

    public void setHikeDistance(String hikeDistance) {
        this.hikeDistance = hikeDistance;
    }

    public String getHikeWeather() {
        return hikeWeather;
    }

    public void setHikeWeather(String hikeWeather) {
        this.hikeWeather = hikeWeather;
    }

}
