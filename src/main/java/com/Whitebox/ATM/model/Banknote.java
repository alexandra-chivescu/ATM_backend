package com.Whitebox.ATM.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public enum Banknote {
    ONE_LEI("1LEU", 1, 10),
    FIVE_LEI("5LEI", 5, 10),
    TEN_LEI("10LEI", 10, 10),
    TWENTY_LEI("20LEI", 20, 10),
    FIFTY_LEI("50LEI", 50, 10),
    ONE_HUNDREAD_LEI("100LEI", 100, 10);

    private final String name;
    private final int value;
    private final int amount;

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getAmount() {
        return amount;
    }

    Banknote(String name, int value, int amount) {
        this.name = name;
        this.value = value;
        this.amount = amount;
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

    public static int[] getAmountsOfBanknotes() {
        int[] banknoteAmounts= new int[6];
        int i = 0;
        for( Banknote banknote : Banknote.values()) {
            banknoteAmounts[i] = banknote.amount;
            i++;
        }
        return banknoteAmounts;
    }
}
