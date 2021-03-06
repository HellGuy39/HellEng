package com.hg39.helleng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WordsFragment extends Fragment {

    private TextView word1,word2,word3,word4,word5,word6,word7,word8,word9,word10;
    private String[] wordsArr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordsArr = new String[10];

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView =
                inflater.inflate(R.layout.fragment_words,container,false);

        word1 = rootView.findViewById(R.id.word1);
        word2 = rootView.findViewById(R.id.word2);
        word3 = rootView.findViewById(R.id.word3);
        word4 = rootView.findViewById(R.id.word4);
        word5 = rootView.findViewById(R.id.word5);
        word6 = rootView.findViewById(R.id.word6);
        word7 = rootView.findViewById(R.id.word7);
        word8 = rootView.findViewById(R.id.word8);
        word9 = rootView.findViewById(R.id.word9);
        word10 = rootView.findViewById(R.id.word10);

        int testType = getArguments().getInt("testType");

        setWordsArr(testType);

        return rootView;
    }

    private void setWordsArr(int testType) {
        if (testType == 1) {
            wordsArr[0] = "Armchair [ ˈɑːmˈʧɛə ] кресло";
            wordsArr[1] = "Bed [ bed ] кровать";
            wordsArr[2] = "Bench [ benʧ ] скамья";
            wordsArr[3] = "Blanket [ ˈblæŋkɪt ] одеяло";
            wordsArr[4] = "Bookshelf [ ˈbʊkʃelf ] книжная полка";
            wordsArr[5] = "Bookcase [ ˈbʊkkeɪs ] книжный шкаф";
            wordsArr[6] = "Cabinet [ ˈkæbɪnɪt ] шкаф";
            wordsArr[7] = "Carpet [ ˈkɑːpɪt ] ковер";
            wordsArr[8] = "Chair [ ʧɛə ] стул";
            wordsArr[9] = "Lamp [ læmp ] лампа";
        }
        if (testType == 2) {
            wordsArr[0] = "Pencil case [ pɛnsl keɪs ] пенал";
            wordsArr[1] = "Backpack [ˈbækˌpæk ] рюкзак";
            wordsArr[2] = "Clip [ klɪp ] скрепка";
            wordsArr[3] = "Pencil [ ˈpɛnsl ] карандаш";
            wordsArr[4] = "Ballpoint [ ˈbɔːlˌpɔɪnt ] шариковая ручка";
            wordsArr[5] = "Eraser [ ɪˈreɪzə ] ластик";
            wordsArr[6] = "Ruler [ ˈruːlə ] линейка";
            wordsArr[7] = "Blackboard [ ˈblækbɔːd ] доска";
            wordsArr[8] = "Book [ bʊk ] книга";
            wordsArr[9] = "Stapler [ ˈsteɪplə ] степлер";
        }
        if (testType == 3) {
            wordsArr[0] = "Cheese [ ʧiːz ] сыр";
            wordsArr[1] = "Doughnut / Donut [ 'dənʌt ] пончик, жаренный пирожок";
            wordsArr[2] = "Eggs [ eg ] яйца";
            wordsArr[3] = "Ham [ ham ] ветчина";
            wordsArr[4] = "Jelly [ ˈjelē ] джем";
            wordsArr[5] = "Lemonade [ ˈleməˌnād ] лимонад";
            wordsArr[6] = "Burger [ 'bɜ:gə ] бургер";
            wordsArr[7] = "Pizza [ 'pi:tsə ] пицца ";
            wordsArr[8] = "Sandwich [ ˈsanˌ(d)wiCH ] сэндвич";
            wordsArr[9] = "Potato [ pəˈtātō ] картофель";
        }
        word1.setText(wordsArr[0]);
        word2.setText(wordsArr[1]);
        word3.setText(wordsArr[2]);
        word4.setText(wordsArr[3]);
        word5.setText(wordsArr[4]);
        word6.setText(wordsArr[5]);
        word7.setText(wordsArr[6]);
        word8.setText(wordsArr[7]);
        word9.setText(wordsArr[8]);
        word10.setText(wordsArr[9]);

    }
}
