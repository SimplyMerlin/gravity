package net.beetonia.minigame.gravity.util;

public enum CircleNumber {

    FIVE('➎'),
    FOUR('➍'),
    THREE('➌'),
    TWO('➋'),
    ONE('➊');

    char c;

    CircleNumber(char c) {
        this.c = c;
    }

    public static String get(int i) {
        switch (i) {
            case 5:
                return CircleNumber.FIVE.toString();
            case 4:
                return CircleNumber.FOUR.toString();
            case 3:
                return CircleNumber.THREE.toString();
            case 2:
                return CircleNumber.TWO.toString();
            case 1:
            default:
                return CircleNumber.ONE.toString();
        }
    }

    public String toString() {
        return c + "";
    }

}
