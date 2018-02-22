package com.ExodiaSolutions.numeric.testme;

/**
 * Created by Sunny Narang on 03-09-2016.
 */

public class TestData {
    String question,a,b,c,d,ans,quesImage;

    public TestData(String question, String a, String b, String c, String d, String ans, String quesImage) {
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.ans = ans;
        this.quesImage = quesImage;
    }

    public String getQuestion() {
        return question;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public String getAns() {
        return ans;
    }
    public String getQuesImage() {
        return quesImage;
    }

}
