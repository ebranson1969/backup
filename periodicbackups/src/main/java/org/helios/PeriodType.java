package org.helios;

/**
 * Created by ebranson1969 on 08/25/2017.
 */
public enum PeriodType {
    START(-1),
    TWENTYFOURHOURS(86400),
    TWELVEHOURS(43200),
    EIGHTHOURS(28800),
    FOURHOURS(14400),
    TWOHOURS(7200),
    HOURLY(3600),
    QUARTERHOUR(900),
    TENMINUTES(600),
    FIVEMINUTES(300),
    TWOMINUTE30SECONDS(150),
    MINUTE(60),
    THIRTYSECONDS(30);
//    //Testing Only
//    TWENTYSECONDS(20),
//    TENSECONDS(10),
//    FIVESECONDS(5),
//    ONESECOND(1);

    private final int secondsPerPeriond;

    PeriodType(int secondsPerPeriond)
    {
        this.secondsPerPeriond = secondsPerPeriond;
    }

    public int getSecondsPerPeriond() {
        return secondsPerPeriond;
    }
}
