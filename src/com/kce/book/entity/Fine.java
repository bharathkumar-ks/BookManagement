package com.kce.book.entity;

public class Fine {
    private String fineId;
    private String memberId;
    private String recordId;
    private int fineAmount;

    public Fine(String fineId, String memberId, String recordId, int fineAmount) {
        this.fineId = fineId;
        this.memberId = memberId;
        this.recordId = recordId;
        this.fineAmount = fineAmount;
    }

    public String getFineId() {
        return fineId;
    }

    public void setFineId(String fineId) {
        this.fineId = fineId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(int fineAmount) {
        this.fineAmount = fineAmount;
    }
}