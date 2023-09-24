package com.example.LaundrySystem.Controller.Filter;

import java.util.List;

public interface IFilter <T extends Comparable<T>> {
    public List<T> meetCriteria(String criteria, String toMeet, String laundryName);
}
