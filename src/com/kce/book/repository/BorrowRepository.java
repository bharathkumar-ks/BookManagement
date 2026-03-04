package com.kce.book.repository;
import java.util.ArrayList;
import com.kce.book.entity.BorrowRecord;

public class BorrowRepository {
    private ArrayList<BorrowRecord> borrowRecords = new ArrayList<>();
    public void addBorrowRecord(BorrowRecord record) {
        borrowRecords.add(record);
    }
    public ArrayList<BorrowRecord> getAllBorrowRecords() {
        return borrowRecords;
    }
    public BorrowRecord findRecordById(String recordId) {
        for (BorrowRecord r : borrowRecords) {
            if (r.getRecordId().equals(recordId)) {
                return r;
            }
        }
        return null;
    }
}