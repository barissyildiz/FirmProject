package com.example.bar.firmss;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Fragment_G extends Fragment implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    Context context;
    TextView textView_question;
    CheckBox checkBox_positive,checkBox_average,checkBox_negative;
    Button btn_continue,btn_returnback;
    String checkbox_text,paramater_industrialrisk,paramater_managementrisk,paramater_financialrisk,paramater_credibility,paramater_competitiveness,paramater_operatingrisk;
    int[] array = new int[6];

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_suggest,container,false);

        context = container.getContext();

        identifiers(view);

        listeners();

        return view;
    }

    public void identifiers(View view) {

        textView_question = view.findViewById(R.id.question_suggest);
        checkBox_positive = view.findViewById(R.id.positive_suggest);
        checkBox_average = view.findViewById(R.id.suggest_average);
        checkBox_negative = view.findViewById(R.id.suggest_negative);
        btn_continue = view.findViewById(R.id.suggest_continue);
        btn_returnback = view.findViewById(R.id.suggest_returnback);

        textView_question.setText("INDUSTRİAL RİSK");
    }

    public void listeners() {

        btn_continue.setOnClickListener(this);
        btn_returnback.setOnClickListener(this);
        checkBox_positive.setOnCheckedChangeListener(this);
        checkBox_average.setOnCheckedChangeListener(this);
        checkBox_negative.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == btn_continue.getId()) {

            if(textView_question.getText().toString().equalsIgnoreCase("INDUSTRİAL RİSK")) {

                paramater_industrialrisk = checkbox_text;
                array[0] = attempt(checkbox_text);
                textView_question.setText("MANAGEMENT RİSK");
            }
            else if(textView_question.getText().toString().equalsIgnoreCase("MANAGEMENT RİSK")) {

                paramater_managementrisk = checkbox_text;
                array[1] = attempt(checkbox_text);
                textView_question.setText("FİNANCİAL FLEXİBİLİTY");
            }
            else if(textView_question.getText().toString().equalsIgnoreCase("FİNANCİAL FLEXİBİLİTY")) {

                paramater_financialrisk = checkbox_text;
                array[2] = attempt(checkbox_text);
                textView_question.setText("CREDİBİLİTY");
            }
            else if(textView_question.getText().toString().equalsIgnoreCase("CREDİBİLİTY")) {

                paramater_credibility = checkbox_text;
                array[3] = attempt(checkbox_text);
                textView_question.setText("COMPETİTİVENESS");
            }
            else if(textView_question.getText().toString().equalsIgnoreCase("COMPETİTİVENESS")) {

                paramater_competitiveness = checkbox_text;
                array[4] = attempt(checkbox_text);
                textView_question.setText("OPERATİNG RİSK");
            }
            else if(textView_question.getText().toString().equalsIgnoreCase("OPERATİNG RİSK")) {

                textView_question.setText("LAST");
                array[5] = attempt(checkbox_text);
                paramater_operatingrisk = checkbox_text;
            }
            else if(textView_question.getText().toString().equalsIgnoreCase("LAST")) {

                SQLLiteHelperr database = new SQLLiteHelperr(context);
                ArrayList<StoreDecision> storeDecisionslist;
                storeDecisionslist = database.getStoreDecisionList();
                DecisionTree decisionTree = new DecisionTree(storeDecisionslist);

                System.out.println("----------------------");
                System.out.println("Data Set Sonuçları:");

                decisionTree.RunTree1();

                float dogruBildik = 0;
                float yanlisBildik = 0;

                for (StoreDecision h : storeDecisionslist) {
                    boolean StoreDecisionTahmini = decisionTree.StoreDecisionTestEt(h);
                    if (StoreDecisionTahmini) {
                        // System.out.println("Tahmin: Zararlı " + h);
                        if (h.başarılı == StoreDecisionTahmini) {
                            dogruBildik++;
                        } else {
                            yanlisBildik++;
                        }
                    } else {
                        // System.out.println("Tahmin: Zararsız " + h);
                        if (h.başarılı == StoreDecisionTahmini) {
                            dogruBildik++;
                        } else {
                            yanlisBildik++;
                        } } }

                        boolean value = decisionTree.StoreDecisionTestEt(new StoreDecision(new int[] {array[0],array[1],array[2],array[3],array[4],array[5]},"belirsiz"));

                        if(value) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setMessage("FİRMAYA ÜRÜN VERİLEBİLİR");
                            alert.setTitle("TAVSİYE");
                            alert.show();
                        }
                        else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setMessage("FİRMAYA ÜRÜN VERİLMEMELİ");
                            alert.setTitle("TAVSİYE");
                            alert.show();
                        }

                        System.out.println("DOĞRU SAYISI : "+dogruBildik + "\n" + "YANLIŞ SAYISI : "+yanlisBildik);
                        System.out.println("BAŞARI SONUCU : " + decisionTree.StoreDecisionTestEt(new StoreDecision(new int[] {array[0],array[1],array[2],array[3],array[4],array[5]},"belirsiz")));
            }
        }
        else if(v.getId() == btn_returnback.getId()) {

        }
    }

    public int attempt(String value1) {

        int value2 = 0;

        switch (value1) {

            case "POSİTİVE" :  value2 = 1; break;
            case "AVERAGE"  :  value2 = 2; break;
            case "NEGATİVE" :  value2 = 3; break;
        }
        return value2;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(buttonView.getId() == checkBox_positive.getId()) {

            if(isChecked) {

                checkBox_average.setChecked(false);
                checkBox_negative.setChecked(false);
                checkbox_text = buttonView.getText().toString();
            }
        }
        else if(buttonView.getId() == checkBox_average.getId()) {

            if(isChecked) {

                checkBox_positive.setChecked(false);
                checkBox_negative.setChecked(false);
                checkbox_text = buttonView.getText().toString();
            }
        }
        else if(buttonView.getId() == checkBox_negative.getId()) {

            if(isChecked) {

                checkBox_positive.setChecked(false);
                checkBox_average.setChecked(false);
                checkbox_text = buttonView.getText().toString();
            }
        }
    }
}
