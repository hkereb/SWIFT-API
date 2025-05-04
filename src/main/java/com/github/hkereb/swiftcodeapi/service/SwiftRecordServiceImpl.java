package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.model.SwiftRecord;
import com.github.hkereb.swiftcodeapi.repository.SwiftRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SwiftRecordServiceImpl implements SwiftRecordService {

    @Autowired
    private SwiftRecordRepository swiftRecordRepository;

    @Override
    public String upsert(SwiftRecord swiftRecord) {
        swiftRecordRepository.save(swiftRecord);
        return "success";
    }
    @Override
    public String upsertAll(List<SwiftRecord> records) {
        swiftRecordRepository.saveAll(records);
        return "bulk upsert success";
    }

    @Override
    public SwiftRecord getById(Integer id) {
        Optional<SwiftRecord> findById = swiftRecordRepository.findById(id);
        return findById.orElse(null);
    }
    @Override
    public SwiftRecord getBySwiftCode(String swiftCode) {
        return swiftRecordRepository.getBySwiftCode(swiftCode);
    }
    @Override
    public List<SwiftRecord> getByCountryISO2(String iso2Code) {
        return swiftRecordRepository.getByCountryISO2(iso2Code);
    }

    @Override
    public List<SwiftRecord> getAllSwiftRecords() {
         return swiftRecordRepository.findAll();
    }

    @Override
    public String deleteById(Integer id) {
        if (swiftRecordRepository.existsById(id)) {
            swiftRecordRepository.deleteById(id);
            return "Delete Success";
        }
        return "No Record";
    }

    @Override
    public String deleteBySwiftCode(String swiftCode) {
        Optional<SwiftRecord> optional = Optional.ofNullable(swiftRecordRepository.findBySwiftCode(swiftCode));
        if (optional.isPresent()) {
            swiftRecordRepository.delete(optional.get());
            return "Delete Success";
        }
        return "No Record";
    }


    @Override
    public boolean isDatabaseInitialized() {
        return swiftRecordRepository.count() > 0;
    }
}
