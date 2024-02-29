package com.example.cherninbagrutproj;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SentenceGroup
{
    private String name;
    private ArrayList<Sentence> sentences;       //for easy work with the group the list is public

    /*
regular constructor,
!!!! a can be any number, i need it to make some difference with SentenceGroup(String text) !!!!
     */
    public SentenceGroup(String name, int a)
    {
        this.name = name;
        this.sentences = new ArrayList<>();
    }

    /*
The string that we get here is a hole sentence group saved as string
so we split the string by lines, the first line is the name of the list and all other lines is sentences
we take each line, spliting it with ## and converting it to a sentence with special constructor which gets arrays
     */
    public SentenceGroup(String text)
    {
        String[] arr = text.split("\n");
        name = arr[1];
        this.sentences = new ArrayList<>();

        for(int i = 2; i < arr.length; i++)
        {
            String [] str = arr[i].split("##");
            Sentence s = new Sentence(str);
            this.sentences.add(s);
            int a = 2;
        }
    }

    @Override
    public String toString()
    {
        String text = name + "\n";
        for(int i = 0; i < sentences.size(); i++)
        {
            text += sentences.get(i) + "\n";
        }
        return text;
    }

    public ArrayList<Sentence> getList()
    {
        return sentences;
    }
    public void setList(ArrayList<Sentence> newSentences){
        this.sentences = newSentences;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<Sentence> getFinished()
    {
        ArrayList<Sentence> a = new ArrayList<>();
        for(int i = 0; i < sentences.size(); i++)
        {
            if(sentences.get(i).getComplete() == 1)
            {
                a.add(sentences.get(i));
            }
        }

        return a;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Sentence> getNotToday()
    {
        ArrayList<Sentence> a = new ArrayList<>();
        for(int i = 0; i < sentences.size(); i++)
        {
            if(sentences.get(i).getComplete() == 0)
            {
                a.add(sentences.get(i));
            }
        }

        return a;
    }

    public ArrayList<Sentence> getToday()
    {
        ArrayList<Sentence> a = new ArrayList<>();
        for(int i = 0; i < sentences.size(); i++)
        {
            if(sentences.get(i).getComplete() == 2)
            {
                a.add(sentences.get(i));
            }
        }

        return a;
    }

    public ArrayList<Integer> getTodayIndexes(){            //this function is made to creatre coordinates list in todayActivity
        ArrayList<Integer> a = new ArrayList<>();
        for(int i = 0; i < sentences.size(); i++)
        {
            if(sentences.get(i).getComplete() == 2)
            {
                a.add(i);
            }
        }

        return a;
    }

}
