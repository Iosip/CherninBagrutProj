package com.example.cherninbagrutproj;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SentenceManager {                              //saving and uploading sentences in one file
    public final String FILE_NAME = "SentencesFile";
    public Context context;

    public SentenceManager(Context context){
        this.context = context;
    }

    public void saveSentence(ArrayList<SentenceGroup> sntgList)     //checked
    {
        String str = "";

        for(SentenceGroup  s : sntgList)
            str += s.toString() + "&&" + "\n";

        saveToFile(FILE_NAME, str);
    }



    public boolean saveToFile(String fileName, String text)
    {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE );
            try {
                fos.write(text.getBytes());
                fos.close();

            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public String readFromFile(String fileName)
    {

        StringBuffer retBuf = new StringBuffer();
        try {
            FileInputStream fstream = context.openFileInput(fileName);
            int i;
            while ((i = fstream.read())!= -1){
                retBuf.append((char)i);
            }
            fstream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retBuf.toString();
    }


    // load the task list
    public ArrayList<SentenceGroup> loadSentence()
    {
        ArrayList<SentenceGroup> arrBig = new ArrayList<>();
        String str = "\n" + readFromFile(FILE_NAME);
        if (str.length() > 0) {
            String[] arrBigBeta = str.split("&&");
            for (int i = 0; i < arrBigBeta.length-1; i++){          //the length must be arrBigBeta.length-1 because the last part of arrBigBeta which is after the last && is empty
                arrBig.add(new SentenceGroup(arrBigBeta[i]));
                int a = 2;
            }
        }
        return arrBig;
    }

    public static Integer findSntgIndexByName(ArrayList<SentenceGroup> sntgList, String name){
        for (int i = 0 ; i < sntgList.size(); i++){
            if (sntgList.get(i).getName().equals(name))
                return i;
        }
        return -1;          //if there is not such sntg in the list
    }
        //find sentence in list of sntg by coordinates
    public static Sentence findSentence(ArrayList<SentenceGroup> sntgList, Coordinates coordinates){
        return sntgList.get(coordinates.getGroupIndex()).getList().get(coordinates.sentenceIndex);
    }
}
