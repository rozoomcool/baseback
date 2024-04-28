package com.itabrek.baseback.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("apartment_announcement")
public class ApartmentAnnouncement extends Property{

}
