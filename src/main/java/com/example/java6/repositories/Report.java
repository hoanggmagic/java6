package com.example.java6.repositories;

import java.io.Serializable;

public interface Report {
    Serializable getGroup();

    Double getSum();

    Long getCount();
}
