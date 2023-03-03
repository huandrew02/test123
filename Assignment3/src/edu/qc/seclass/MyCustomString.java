package edu.qc.seclass;



public class MyCustomString implements MyCustomStringInterface {

    private String myString;

    public String getString(){

        if(this.myString.isEmpty()){
            return null;
        }

        else{
            return this.myString;
        }
    }

    @Override
    public void setString(String myString){
        this.myString = myString;
    }

    public int countNumbers(){
        int numberCount = 0;
        boolean isPreviousInt = false;
        int ifMoreThanTwoSetisPreviousIntToFalse = 0;

        if(this.myString.isEmpty()){
            return 0;
        }

        else {
            for(int i = 0; i < this.myString.length(); i++){
                if(Character.isDigit(this.myString.charAt(i))){
                    if(!isPreviousInt){
                        numberCount++;
                        isPreviousInt = true;
                    }

                    else {
                        Character.isDigit(this.myString.charAt(i + 1));
                    }
                }
                else{
                    isPreviousInt = false;
                }
            }
            return numberCount;
        }
    }

    @Override
    public String getEveryNthCharacterFromBeginningOrEnd(int n, boolean startFromEnd){
        StringBuilder resultingString = new StringBuilder();

        if(n <= 0){
            throw new IllegalArgumentException();
        }

        else if(this.myString == null){
            throw new NullPointerException();
        }

        else {
            if (n > this.myString.length()) {
                return "";
            } else {
                if (startFromEnd) {
                    for (int i = this.myString.length() - n; i >= 0; i -= n) {
                        resultingString.insert(0, this.myString.charAt(i));
                    }
                    return resultingString.toString();
                } else {
                    for (int i = n - 1; i < this.myString.length(); i += n) {
                        resultingString.append(this.myString.charAt(i));
                    }
                    return resultingString.toString();
                }
            }
        }
    }

    @Override
    public void convertDigitsToNamesInSubstring(int startPosition, int endPosition){
        if(startPosition > endPosition){
            throw new IllegalArgumentException();
        }

        else if(startPosition < 1 || endPosition > this.myString.length()){
            throw new MyIndexOutOfBoundsException();

        }

        else {
            StringBuilder resultingString = new StringBuilder();
            for(int i = 0; i < this.myString.length(); i++){
                if(Character.isDigit(myString.charAt(i)) && i >= startPosition-1 && i <= endPosition -1){
                    switch (myString.charAt(i)) {
                        case '0' -> resultingString.append("Zero");
                        case '1' -> resultingString.append("One");
                        case '2' -> resultingString.append("Two");
                        case '3' -> resultingString.append("Three");
                        case '4' -> resultingString.append("Four");
                        case '5' -> resultingString.append("Five");
                        case '6' -> resultingString.append("Six");
                        case '7' -> resultingString.append("Seven");
                        case '8' -> resultingString.append("Eight");
                        case '9' -> resultingString.append("Nine");
                    }
                }
                else{
                    resultingString.append(this.myString.charAt(i));
                }
            }
            this.myString = resultingString.toString();
        }
    }

}