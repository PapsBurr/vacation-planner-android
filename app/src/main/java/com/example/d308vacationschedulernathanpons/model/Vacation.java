package com.example.d308vacationschedulernathanpons.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "vacation_title")
    private String vacationTitle;
    @ColumnInfo(name = "hotel_name")
    private String hotelName;
    @ColumnInfo(name = "start_date")
    private String startDate;
    @ColumnInfo(name = "end_date")
    private String endDate;

    public Vacation(int id, String vacationTitle, String hotelName, String startDate, String endDate) {
        this.id = id;
        this.vacationTitle = vacationTitle;
        this.hotelName = hotelName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVacationTitle() {
        return vacationTitle;
    }

    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Vacation{" +
                "id=" + id +
                ", vacationTitle='" + vacationTitle + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
