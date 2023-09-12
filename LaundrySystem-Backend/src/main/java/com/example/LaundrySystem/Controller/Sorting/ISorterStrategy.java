package com.example.LaundrySystem.Controller.Sorting;

import java.util.List;

public interface ISorterStrategy<T extends Comparable<T>> {
    public List<T> sort(String sortBy, boolean order);
}
