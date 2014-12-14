import com.sun.javaws.exceptions.InvalidArgumentException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

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
abstract public class BaseAnswerResolver {

    public boolean haveAllAnswersSameLength(Question question) {
        return this.getFirstAnswerLength(question) == this.getSecondAnswerLength(question) && this.getSecondAnswerLength(question) == this.getThirdAnswerLength(question);
    }

    public boolean isCorrectAnswerAsLongAsAnother(Question question) throws Exception {
        if (this.getCorrectAnswer(question) == CorrectAnswerLength.SHORT) {
            return this.getShortestAnswerLength(question) == this.getMediumAnswerLength(question);
        } else if (this.getCorrectAnswer(question) == CorrectAnswerLength.MEDIUM) {
            return (this.getLongestAnswerLength(question) == this.getMediumAnswerLength(question)) || (this.getShortestAnswerLength(question) == this.getMediumAnswerLength(question));
        } else if (this.getCorrectAnswer(question) == CorrectAnswerLength.LONG) {
            return this.getLongestAnswerLength(question) == this.getMediumAnswerLength(question);
        } else {
            throw new InvalidPropertiesFormatException("wrong correct answer type");
        }
    }

    public CorrectAnswerLength getCorrectAnswer(Question question) throws Exception {
        int correct = 0;

        if (question.getCorrectAnswer() == CorrectAnswer.FIRST) {
            correct = getFirstAnswerLength(question);
        } else if(question.getCorrectAnswer() == CorrectAnswer.SECOND) {
            correct = getSecondAnswerLength(question);
        } else if (question.getCorrectAnswer() == CorrectAnswer.THIRD) {
            correct = getThirdAnswerLength(question);
        }

        if (correct == getShortestAnswerLength(question)) {
            return CorrectAnswerLength.SHORT;
        } else if(correct == getMediumAnswerLength(question)) {
            return CorrectAnswerLength.MEDIUM;
        } else if (correct == getLongestAnswerLength(question)) {
            return CorrectAnswerLength.LONG;
        }else {
            throw new InvalidArgumentException(new String[]{"Can not determine which answer is correct.", String.valueOf(getFirstAnswerLength(question)), String.valueOf(getSecondAnswerLength(question)), String.valueOf(getThirdAnswerLength(question)), String.valueOf(correct)});
        }
    }


    public int getShortestAnswerLength(Question question) {
        return Math.min(Math.min(this.getFirstAnswerLength(question), this.getSecondAnswerLength(question)), this.getThirdAnswerLength(question));
    }

    public int getMediumAnswerLength(Question question) throws Exception {
        List<Integer> answers = new ArrayList<Integer>();
        answers.add(this.getFirstAnswerLength(question));
        answers.add(this.getSecondAnswerLength(question));
        answers.add(this.getThirdAnswerLength(question));
        answers.remove(answers.indexOf(this.getLongestAnswerLength(question)));
        answers.remove(answers.indexOf(this.getShortestAnswerLength(question)));
        if (answers.size() != 1) {
            throw new Exception("wrong sizes");
        } else {
            return answers.get(0);
        }
    }

    public int getLongestAnswerLength(Question question) {
        return Math.max(Math.max(this.getFirstAnswerLength(question), this.getSecondAnswerLength(question)), this.getThirdAnswerLength(question));
    }


    public int getFirstAnswerLength(Question question) {
        return this.getLength(question.getFirstAnswer());
    }

    public int getSecondAnswerLength(Question question) {
        return this.getLength(question.getSecondAnswer());
    }

    public int getThirdAnswerLength(Question question) {
        return  this.getLength(question.getThirdAnswer());
    }

    abstract public int getLength(String string);
}
