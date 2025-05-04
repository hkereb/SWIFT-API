package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.model.SwiftRecord;

import java.util.List;

public interface SwiftRecordService {
    String upsert(SwiftRecord swiftRecord);
    String upsertAll(List<SwiftRecord> records);

    SwiftRecord getById(Integer id);
    SwiftRecord getBySwiftCode(String swiftCode);
    List<SwiftRecord> getAllSwiftRecords();
    List<SwiftRecord> getByCountryISO2(String iso2Code);

    String deleteById(Integer id);
    String deleteBySwiftCode(String swiftCode);

    boolean isDatabaseInitialized();
}
