package com.example.cherninbagrutproj;

/*
this class made for todayAdapter ability to save files.
Because todayAdapter doesnt know from where the sentences which is clicked i created
array of coordinates which is like the todaySentencesList brother
 */
public class Coordinates {
    int groupIndex;             //the index of the sentenceGroup of the sentence in arrBig
    int sentenceIndex;          //the index of the sentence in the sntg

    public Coordinates(int groupIndex, int sentenceIndex) {
        this.groupIndex = groupIndex;
        this.sentenceIndex = sentenceIndex;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public int getSentenceIndex() {
        return sentenceIndex;
    }
}
