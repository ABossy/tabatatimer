package com.example.grafatabata.db;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabata")
public class Tabata implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int indexEtape = 0;
    private int workTime;
    private int cycleNb;
    private int tabataNb;
    private int prepareTime;
    private int restTime;
    private int longRestTime;

    public Tabata(){

    }

    protected Tabata(Parcel in) {


        indexEtape = in.readInt();
        name = in.readString();
        workTime = in.readInt();
        cycleNb = in.readInt();
        tabataNb = in.readInt();
        prepareTime = in.readInt();
        restTime = in.readInt();
        longRestTime = in.readInt();

    }

    public static final Creator<Tabata> CREATOR = new Creator<Tabata>() {
        @Override
        public Tabata createFromParcel(Parcel in) {
            return new Tabata(in);
        }

        @Override
        public Tabata[] newArray(int size) {
            return new Tabata[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIndexEtape() {
        return indexEtape;
    }

    public void setIndexEtape(int indexEtape) {
        this.indexEtape = indexEtape;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getCycleNb() {
        return cycleNb;
    }

    public void setCycleNb(int cycleNb) {
        this.cycleNb = cycleNb;
    }

    public int getTabataNb() {
        return tabataNb;
    }

    public void setTabataNb(int tabataNb) {
        this.tabataNb = tabataNb;
    }

    public int getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(int prepareTime) {
        this.prepareTime = prepareTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public int getLongRestTime() {
        return longRestTime;
    }

    public void setLongRestTime(int longRestTime) {
        this.longRestTime = longRestTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(indexEtape);
        dest.writeString(name);
        dest.writeInt(workTime);
        dest.writeInt(cycleNb);
        dest.writeInt(tabataNb);
        dest.writeInt(prepareTime);
        dest.writeInt(restTime);
        dest.writeInt(longRestTime);
    }

}

