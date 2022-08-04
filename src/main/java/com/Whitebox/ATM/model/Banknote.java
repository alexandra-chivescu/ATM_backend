package com.Whitebox.ATM.model;

public enum Banknote {
    ONE_LEI("1LEU", 1),
    FIVE_LEI("5LEI", 5),
    TEN_LEI("10LEI", 10),
    TWENTY_LEI("20LEI", 20),
    FIFTY_LEI("50LEI", 50),
    ONE_HUNDREAD_LEI("100LEI", 100);

    private final String name;
    private final int value;

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    Banknote(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static int[] getValuesOfBanknotes() {
        int[] banknoteValues = new int[6];
        int i = 0;
        for( Banknote banknote : Banknote.values()) {
            banknoteValues[i] = banknote.value;
            i++;
        }
        return banknoteValues;
    }
}
