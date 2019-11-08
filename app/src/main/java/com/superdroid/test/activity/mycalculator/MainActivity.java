package com.superdroid.test.activity.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    TextView expression;                                //수식 TextView
    TextView result;                                    //결과 TextView
    String s_expression;                                //수식 저장
    String s_result;                                    //결과 저장
    boolean demical = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expression = (TextView)findViewById(R.id.textView);
        result = (TextView)findViewById(R.id.textView2);
        s_expression = expression.getText().toString();
        s_result = result.getText().toString();

        /////10진수 상태로 시작/////
        RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton3);
        rb1.setChecked(true);

    }

    public void radioClick(View v){
        RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton4);
        if(v.getId() == R.id.radioButton3)      //10진수 on
        {
            rb2.setChecked(false);
            demical = true;
            if(result.length() > 0)
            {
                    result.setText("="+s_result);
            }
        }
        else                                    //16진수 on
        {
            rb1.setChecked(false);
            demical = false;
            if(result.length() > 0)
            {
                String after = s_result;
                if (after.contains("."))
                {
                    after = after.substring(0,after.indexOf("."));
                }
                int num = (int)Integer.parseInt(after);
                String string = Integer.toHexString(num).toUpperCase();
                result.setText("="+string);
            }
        }
    }

    public void onClick(View v){

        double num;
        char last;
        if(s_expression.length() == 0)
        {
             last = ' ';
        }
        else
        {
            last = s_expression.charAt(s_expression.length()-1);
        }
        switch (v.getId()) {
            case R.id.button :
                if(last == ' ')
                    expression.append("( ");
                else
                    expression.append(" ( ");

                    break ;
            case R.id.button2 :
                if(last == ' ')
                    expression.append(") ");
                else
                    expression.append(" ) ");
                break ;
            case R.id.button3 :                     //<-
            {
                if (result.length() == 0 && expression.length() > 0)        //result가 비어있고 expression에 수식이 있을 경우
                {
                    if(s_expression.length() == 1)              //길이가 1이면 그냥 지움
                    {
                        expression.setText(s_expression.substring(0,s_expression.length()-1));
                        break;
                    }
                    if(s_expression.substring(s_expression.length()-1,s_expression.length()).equals(" ") )
                    {   //마지막 문자가 공백이면 2개를 지움
                        expression.setText(s_expression.substring(0, s_expression.length() - 2));

                        if(s_expression.length()==2)
                            break;

                        if(s_expression.substring(s_expression.length()-3,s_expression.length()-2).equals(" "))
                        {
                            String ch = s_expression.substring(s_expression.length()-4,s_expression.length()-3);
                            if (!ch.equals("+") && !ch.equals("-") && !ch.equals("*") && !ch.equals("/")) {
                                expression.setText(s_expression.substring(0, s_expression.length() - 3));
                            }
                        }
                    }

                    else
                        expression.setText(s_expression.substring(0, s_expression.length()-1));
                }
                else
                    {
                        expression.setText("");
                        result.setText("");
                        s_expression="";
                        s_result="";
                    }
                    break;
            }
            case R.id.button4 :
                expression.append("1");
                break ;
            case R.id.button5 :
                expression.append("2");
                break ;
            case R.id.button6 :
                expression.append("3");
                break ;
            case R.id.button7 :
                if(last == ' ')
                    expression.append("+ ");
                else
                    expression.append(" + ");
                break ;
            case R.id.button8 :
                expression.append("4");
                break ;
            case R.id.button9 :
                expression.append("5");
                break ;
            case R.id.button10 :
                expression.append("6");
                break ;
            case R.id.button11 :
                if(last == ' ')
                    expression.append("- ");
                else
                    expression.append(" - ");
                break ;
            case R.id.button12 :
                expression.append("7");
                break ;
            case R.id.button13 :
                expression.append("8");
                break ;
            case R.id.button14 :
                expression.append("9");
                break ;
            case R.id.button15 :
                if(last == ' ')
                    expression.append("X ");
                else
                    expression.append(" X ");
                break ;
            case R.id.button16 :
                expression.append("0");
                break ;
            case R.id.button17 :
                expression.append(".");
                break ;
            case R.id.button18 :                        // = 버튼
            {
                String final_string;
                num = calculate(s_expression.replace("X","*").trim());
                final_string = Double.toString(num);
                if(final_string.length() - final_string.indexOf(".") > 4)
                {
                    final_string = String.format("%.3f", num);
                    result.setText("="+final_string);
                }
                else if(final_string.length() - final_string.indexOf(".") == 2 && final_string.substring(final_string.length()-1, final_string.length()).equals("0"))
                {
                    final_string = Integer.toString((int)num);
                    result.setText("="+final_string);
                }
                else
                {
                    result.setText("="+final_string);
                }
                s_result = final_string;
                if(!demical){
                    String before = s_result;
                    String after = s_result;
                    if (before.contains("."))
                    {
                        after = before.substring(0,before.indexOf("."));

                    }
                    int number = (int)Integer.parseInt(after);
                    String string = Integer.toHexString(number).toUpperCase();
                    result.setText("="+string);
                }
                break;
            }
            case R.id.button19 :
                if(last == ' ')
                    expression.append("/ ");
                else
                    expression.append(" / ");
                break ;
        }
        s_expression = expression.getText().toString();
    }

    public double calculate(String string) {
        int start, end;
        double result;

        while(true)
        {
            if(string.length() == 0 || !string.contains(")"))
                break;
            end = string.indexOf(")");
            start = string.lastIndexOf("(", end);       // "("를 뒤에서부터 찾아야 처음 나오는 "("와 짝을 이룸

            result = cal(string.substring(start+2, end-1));
            string = string.substring(0, start)  + result + string.substring(end+1, string.length());
        }
        result = cal(string);
        return result;
    }

    public double cal(String str) {
        ArrayList<String> string = transform(str);
        Stack<String> st = new Stack<>();
        double left, right;

        for(String value : string) {
            switch (value) {
                case "+":
                    right = Double.parseDouble(st.pop());
                    left = Double.parseDouble(st.pop());
                    st.push(Double.toString(left + right));
                    break;
                case "-":
                    right = Double.parseDouble(st.pop());
                    left = Double.parseDouble(st.pop());
                    st.push(Double.toString(left - right));
                    break;
                case "*":
                    right = Double.parseDouble(st.pop());
                    left = Double.parseDouble(st.pop());
                    st.push(Double.toString(left * right));
                    break;
                case "/":
                    right = Double.parseDouble(st.pop());
                    left = Double.parseDouble(st.pop());
                    st.push(Double.toString(left / right));
                    break;
                default:
                    st.push(value);

            }
        }
        return Double.parseDouble(st.pop());
    }

    public ArrayList<String> transform(String str) {
        ArrayList<String> transform_result = new ArrayList<>();
        Stack<String> st = new Stack<>();
        String[] target = str.split(" ");
        String string;

        for (int i=0; i<target.length; i++) {
            switch (target[i]) {
                case "(":
                    st.push(target[i]);
                    break;
                case ")":
                    string = st.pop();
                    while(!string.equals("("))      //"("가 나올때까지
                    {
                        transform_result.add(string);
                        string = st.pop();
                    }
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    while( !st.isEmpty() && ( priority(st.peek()) >= priority(target[i]) ) )
                    {
                        string = st.pop();
                        transform_result.add(string);
                    }
                    st.push(target[i]);
                    break;
                default:
                    transform_result.add(target[i]);
                    break;
            }
        }
        while(!st.isEmpty())
        {
            transform_result.add(st.pop());
        }
        return transform_result;
    }
    public int priority(String string) {

        if(string.equals("(") || string.equals(")"))
            return 0;
        else if(string.equals("+") || string.equals("-"))
            return 1;
        else if(string.equals("*") || string.equals("/"))
            return 2;
        else
            return 0;
    }

}
