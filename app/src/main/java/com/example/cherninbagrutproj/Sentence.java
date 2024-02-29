package com.example.cherninbagrutproj;

import java.util.ArrayList;
import java.util.Arrays;

public class Sentence
{
    protected String beforeWord;
    protected String word;
    protected String afterWord;
    protected String translate;
    protected ArrayList<Integer> nextDays;
    protected Date date;
    protected int complete;   //0 is not complete, 1 is complete and 2 if we need to study the sentence



    public Sentence(String sentence, String word, String translate)       //create new sentence
    {
        this.word = word;
        String[] arr = sentence.split(word , 2);        // the limit in split is for situation then we have the word a few times in the sentences. for example "My name is my name" and the word is name. If i will not use the limit it was spliting with all "name" and not just the first
        this.beforeWord = arr[0];
        this.afterWord = "";
        this.translate = translate;
        for(int i = 1; i < arr.length; i++)
        {
            this.afterWord += arr[i];
        }
        this.nextDays = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 7, 14));
        this.date = new Date();
        this.complete = 2;
    }
    //convert array to sentence
    public Sentence(String[] arr)
    {
        this.beforeWord = arr[0];
        this.word = arr[1];
        this.afterWord = arr[2];
        this.translate = arr[3];

        //return ArrayList from string to normal
        this.nextDays = new ArrayList<>();
        arr[4] = arr[4].substring(1, arr[4].length() - 1);
        if (arr[4].length() > 0){                   //only if there is still days
            String[] arr2 = arr[4].split(", ");
            for(int i = 0; i < arr2.length; i++)
            {
                this.nextDays.add(Integer.valueOf(arr2[i]));
            }
        }
        this.date = new Date(arr[5]);
        this.complete = Integer.valueOf(arr[6]);

        Date today = new Date();
        if(date.compareTo(today) == -1)      //if the user didnt do the study in the past, the study will be mooved to today
        {
            this.date = today;
        }
        if(this.complete == 0 && date.compareTo(today) == 0)    //if we need to study the sentence today, complete will become 2
        {
            this.complete = 2;
        }

    }

    public void changeDay()
    {        //command which will be used then user click that he studied
        if(!nextDays.isEmpty()){
            for(int i = 0; i < nextDays.get(0); i++)
                date = date.getTomorrow2();
            nextDays.remove(0);
        }
        else
            complete = 1;
    }

    public void returnDays(){       //if the user as a mistake checked the check box of learning, i want him to have the option to take the days back
        if (nextDays.size() >= 2){
            nextDays.add(0,1);
            date = date.getYesterday();
        }
        else if (nextDays.size() == 1){
            nextDays.add(0,7);
            for (int i=0; i<7; i++)
                date = date.getYesterday();
        }
        else if (nextDays.size() == 0){
            nextDays.add(0,14);
            for (int i=0; i<14; i++)
                date = date.getYesterday();
            complete = 2;
        }

    }

    public void setBeforeWord(String beforeWord)
    {
        this.beforeWord = beforeWord;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public void setAfterWord(String afterWord)
    {
        this.afterWord = afterWord;
    }

    public void setNextDays(ArrayList<Integer> nextDays)
    {
        this.nextDays = nextDays;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setComplete(int complete)
    {
        this.complete = complete;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getTranslate() {
        return translate;
    }

    public String getBeforeWord()
    {
        return beforeWord;
    }

    public String getWord() { return word; }

    public String getAfterWord()
    {
        return afterWord;
    }

    public void setAll(String sentence, String word, String translate){
        this.word = word;
        String[] arr = sentence.split(word , 2);        // the limit in split is for situation then we have the word a few times in the sentences. for example "My name is my name" and the word is name. If i will not use the limit it was spliting with all "name" and not just the first
        this.beforeWord = arr[0];
        this.afterWord = "";
        this.translate = translate;
        for(int i = 1; i < arr.length; i++)
        {
            this.afterWord += arr[i];
        }
    }

    public ArrayList<Integer> getNextDays()
    {
        return nextDays;
    }

    public Date getDate()
    {
        return date;
    }

    public int getComplete()
    {
        return complete;
    }

    public String toString()
    {
        String str = beforeWord + "##";
        str += word + "##";
        str += afterWord + "##";
        str += translate + "##";
        str += nextDays + "##";
        str += date + "##";
        if (complete == 1)
            str += 1;
        else
            str += 0;
        return str;
    }

}
