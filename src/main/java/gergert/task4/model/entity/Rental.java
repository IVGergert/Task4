package gergert.task4.model.entity;

import java.time.LocalDateTime;

public class Rental extends AbstractEntity{
    private User user;
    private Bike bike;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private OrderStatus status;
}
