package com.example.poolrest.Schedule;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalTime;

@MappedSuperclass
public abstract class Schedule {
    @Column(name = "open_time",nullable = true)
    private LocalTime openTime;
    @Column(name = "close_time",nullable = true)
    private LocalTime closeTime;
    @Column(name = "max_slots",nullable = false)
    private Long maxSlots; //сделать int?
    @Column(name = "is_work",nullable = false)
    private boolean isWork;

    public Schedule() {
    }

    public Schedule(LocalTime openTime, LocalTime closeTime, Long maxSlots, boolean isWork) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.maxSlots = maxSlots;
        this.isWork = isWork;
    }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean work) {
        isWork = work;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public Long getMaxSlots() {
        return maxSlots;
    }

    public void setMaxSlots(Long maxSlots) {
        this.maxSlots = maxSlots;
    }
}
