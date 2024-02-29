package com.example.cherninbagrutproj;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date
{
    public int year;
    public int month;
    public int day;

    public Date(int day, int month, int year)
    {
        this.day = day;
        this.year = year;
        this.month = month;

    }
    public Date(String str)
    {
        String [] arr = str.split("/");
        this.day = Integer.valueOf(arr[0]);
        this.month = Integer.valueOf(arr[1]);
        this.year = Integer.valueOf(arr[2]);

    }

    public int getYear()
    {
        return year;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public int compareTo(Date other)
    {
        if (other.day==this.day&&other.month==this.month&&other.year==this.year)
        {
            return 0;               //same date: return 0
        }
        else
        if (this.year>other.year)
        {
            return 1;               //this date is before: return 1
        }
        else
        if (this.month>other.month&&this.year==other.year)
        {
            return 1;
        }
        else
        if (this.day>other.day&&this.month==other.month&&this.year==other.year)
        {
            return 1;
        }
        else
            return -1;              //other date is after: return -1
    }

    @Override
    public String toString()
    {
        return +day+"/"+month+"/"+year;
    }




    public Date getTomorrow2()
    {
        int [] arr={0,31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (year%4==0&&(year%100!=0||year%400==0))
            arr[2]=29;


        if (day<arr[month])
        {
            Date d1=new Date((this.day+1),this.month,this.year);
            return d1;
        }
        else
        {
            if(month<12)
            {
                Date d1=new Date(1,this.month+1,this.year);
                return d1;
            }
            else
            {
                Date d1=new Date(1,1,(this.year+1));
                return d1;
            }
        }

    }

    public Date getYesterday(){
        int [] arr={0,31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (year%4==0&&(year%100!=0||year%400==0))
            arr[2]=29;

        if (day > 1)
            return new Date(day - 1,month,year);
        if (month > 1)
            return new Date(arr[month-1],month - 1,year);       //the last day of the previous month
        return new Date(31,12,year-1);          //the last day of the previous year
    }

    public Date(Date a)
    {
        this.day=a.day;
        this.month=a.month;
        this.year=a.year;

    }

    public Date()       //today
    {
        GregorianCalendar today = new GregorianCalendar();
        this.year = today.get(Calendar.YEAR);
        this.month = today.get(Calendar.MONTH)+1;
        this.day =today.get(Calendar.DAY_OF_MONTH);
    }
}
