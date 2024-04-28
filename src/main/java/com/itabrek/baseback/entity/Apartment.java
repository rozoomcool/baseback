package com.itabrek.baseback.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("apartment")
public class Apartment extends Property{
    private Long price;
    private short countOfRooms;
    private boolean installmentPlan;
    private float area;
    private int floor;
    private int houseFloors;
    private float kitchenArea;
    private float ceilingHeight;
    @Enumerated(EnumType.STRING)
    private ApartmentState apartmentState;

    @Enumerated(EnumType.STRING)
    private BalconyType balconyType;

}