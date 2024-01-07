package ru.boganov.coursework.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BookDto {
    private double productionCosts;
    private double distributionCosts;
    private double otherPublishingCosts;
    private int quantity;
}