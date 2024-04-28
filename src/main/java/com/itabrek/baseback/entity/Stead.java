package com.itabrek.baseback.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("stead")
public class Stead extends Property{

}