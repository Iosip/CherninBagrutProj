package com.example.cherninbagrutproj;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SentenceManager {                              //saving and uploading sentences in one file
    public final String FILE_NAME = "SentencesFile";
    public Context context;

    public SentenceManager(Context context){
        this.context = context;
    }

    public void saveSentence(ArrayList<SentenceGroup> sntgList, Context context)     //checked
    {
        String str = "";

        for(SentenceGroup  s : sntgList)
            str += s.toString() + "&&" + "\n";

        saveToFile(FILE_NAME, str, context);
    }



//    public boolean saveToFile(String fileName, String text)
//    {
//        try {
//            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE );
//            try {
//                fos.write(text.getBytes());
//                fos.close();
//
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//                return false;
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    public void saveToFile(String fileName, String text, Context context){
        Log.d("SentenceManager save", "save "+ "NIGGAAAA");
        String filePath = "yourFilePath";

        // Sample data with non-English characters
        String dataToSave = text;

        // Specify the character encoding
        String encoding = "UTF-8"; // Use the appropriate encoding for your needs
        // Save data to file
        File path = context.getFilesDir();

        try (FileOutputStream fos = new FileOutputStream(new File(path, filePath))
             ) {
            Log.d("SentenceManager save", "FileOutputStream WORKEDDDDDDDD");
            try (OutputStreamWriter osw = new OutputStreamWriter(fos, encoding)) {
                Log.d("SentenceManager save", "OutputStreamWriter WORKEDDDDDDDD");
                Log.d("SentenceManager save", "save "+ dataToSave);
                osw.write(dataToSave);
                osw.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public String readFromFile(String fileName)
//    {
//
//        StringBuffer retBuf = new StringBuffer();
//        try {
//            FileInputStream fstream = context.openFileInput(fileName);
//            int i;
//            while ((i = fstream.read())!= -1){
//                retBuf.append((char)i);
//            }
//            fstream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return retBuf.toString();
//    }

    public String readFromFile(String fileName, Context context){
        String filePath = "yourFilePath";

        // Sample data with non-English characters
        String dataToSave = "你好，世界! Hello, World!";

        // Specify the character encoding
        String encoding = "UTF-8"; // Use the appropriate encoding for your needs

        File path = context.getFilesDir();

        try (FileInputStream fis = new FileInputStream(new File(path, filePath));
             InputStreamReader isr = new InputStreamReader(fis, encoding);
             BufferedReader reader = new BufferedReader(isr)) {
            Log.d("SentenceManager load", "FileOutputStream WORKEDDDDDDDD");

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
//            int i;
//            while ((i = fis.read())!= -1){
//                stringBuilder.append((char)i);
//            }

            String dataRead = stringBuilder.toString();
            Log.d("SentenceManager save", "save "+ dataRead);
            return dataRead;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    // load the task list
    public ArrayList<SentenceGroup> loadSentence(Context context)
    {
        ArrayList<SentenceGroup> arrBig = new ArrayList<>();
        String str = "\n" + readFromFile(FILE_NAME, context);
        //todo - becasuse of "str = "\n" + " str.length() is always bigger then 0
        if (str.length() > 0) {
            String[] arrBigBeta = str.split("&&");
            for (int i = 0; i < arrBigBeta.length - 1; i++){          //the length must be arrBigBeta.length-1 because the last part of arrBigBeta which is after the last && is empty
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
