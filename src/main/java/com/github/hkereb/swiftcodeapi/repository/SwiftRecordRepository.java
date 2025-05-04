package com.github.hkereb.swiftcodeapi.repository;

import com.github.hkereb.swiftcodeapi.model.SwiftRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface SwiftRecordRepository extends JpaRepository<SwiftRecord, Serializable> {
    SwiftRecord getBySwiftCode(String swiftCode);
    List<SwiftRecord> getByCountryISO2(String iso2Code);
}
