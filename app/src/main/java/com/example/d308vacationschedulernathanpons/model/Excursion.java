package com.example.d308vacationschedulernathanpons.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "excursion_name")
    private String excursionName;
    @ColumnInfo(name = "excursion_date")
    private String excursionDate;
    @ColumnInfo(name = "vacation_id")
    private int vacationId;
    @ColumnInfo(name = "user_id")
    private int userId;

    public Excursion(int id, String excursionName, String excursionDate, int vacationId, int userId) {
        this.id = id;
        this.excursionName = excursionName;
        this.excursionDate = excursionDate;
        this.vacationId = vacationId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Excursion{" +
                "id=" + id +
                ", excursionName='" + excursionName + '\'' +
                ", excursionDate='" + excursionDate + '\'' +
                ", vacationId=" + vacationId +
                ", userId=" + userId +
                '}';
    }
}
