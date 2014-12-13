import java.io.Serializable;

/**
 Copyright (c) 2014, Matěj Račinský
 All rights reserved.

 Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 disclaimer in the documentation and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class Question implements Serializable {

    private int questionNumber;
    private CorrectAnswer correctAnswer;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;

    /**
     *
     * @param questionNumber
     * @param correctAnswer
     * @param firstAnswer
     * @param secondAnswer
     * @param thirdAnswer
     */
    public Question(int questionNumber, CorrectAnswer correctAnswer, String firstAnswer, String secondAnswer, String thirdAnswer) {
        this.questionNumber = questionNumber;
        this.correctAnswer = correctAnswer;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public CorrectAnswer getCorrectAnswer() {
        return correctAnswer;
    }

    public int getFirstAnswerLength() {
        return firstAnswer.length();
    }

    public int getSecondAnswerLength() {
        return secondAnswer.length();
    }

    public int getThirdAnswerLength() {
        return thirdAnswer.length();
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (questionNumber != question.questionNumber) return false;
        if (correctAnswer != question.correctAnswer) return false;
        if (!firstAnswer.equals(question.firstAnswer)) return false;
        if (!secondAnswer.equals(question.secondAnswer)) return false;
        if (!thirdAnswer.equals(question.thirdAnswer)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = questionNumber;
        result = 31 * result + correctAnswer.hashCode();
        result = 31 * result + firstAnswer.hashCode();
        result = 31 * result + secondAnswer.hashCode();
        result = 31 * result + thirdAnswer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionNumber=" + questionNumber +
                ", correctAnswer=" + correctAnswer +
                ", firstAnswer='" + firstAnswer + '\'' +
                ", secondAnswer='" + secondAnswer + '\'' +
                ", thirdAnswer='" + thirdAnswer + '\'' +
                '}';
    }

}
