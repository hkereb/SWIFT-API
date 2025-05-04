package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;

import java.util.List;

public interface SwiftCodeService {
    String upsert(SwiftCode swiftCode);
    String upsertAll(List<SwiftCode> records);

    SwiftCode getById(Integer id);
    SwiftCode getBySwiftCode(String swiftCode);
    List<SwiftCode> getAllSwiftRecords();
    List<SwiftCode> getByCountryISO2(String iso2Code);

    String deleteById(Integer id);
    String deleteBySwiftCode(String swiftCode);

    boolean isDatabaseInitialized();
}
