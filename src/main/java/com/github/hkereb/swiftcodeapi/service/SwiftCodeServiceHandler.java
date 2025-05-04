package com.github.hkereb.swiftcodeapi.service;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.repository.SwiftCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SwiftCodeServiceHandler implements SwiftCodeService {

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @Override
    public String upsert(SwiftCode swiftCode) {
        swiftCodeRepository.save(swiftCode);
        return "success";
    }
    @Override
    public String upsertAll(List<SwiftCode> records) {
        swiftCodeRepository.saveAll(records);
        return "bulk upsert success";
    }

    @Override
    public SwiftCode getById(Integer id) {
        Optional<SwiftCode> findById = swiftCodeRepository.findById(id);
        return findById.orElse(null);
    }
    @Override
    public SwiftCode getBySwiftCode(String swiftCode) {
        return swiftCodeRepository.getBySwiftCode(swiftCode);
    }
    @Override
    public List<SwiftCode> getByCountryISO2(String iso2Code) {
        return swiftCodeRepository.getByCountryISO2(iso2Code);
    }

    @Override
    public List<SwiftCode> getAllSwiftRecords() {
         return swiftCodeRepository.findAll();
    }

    @Override
    public String deleteById(Integer id) {
        if (swiftCodeRepository.existsById(id)) {
            swiftCodeRepository.deleteById(id);
            return "Delete Success";
        }
        return "No Record";
    }

    @Override
    public String deleteBySwiftCode(String swiftCode) {
        Optional<SwiftCode> optional = Optional.ofNullable(swiftCodeRepository.getBySwiftCode(swiftCode));
        if (optional.isPresent()) {
            swiftCodeRepository.delete(optional.get());
            return "Delete Success";
        }
        return "No Record";
    }


    @Override
    public boolean isDatabaseInitialized() {
        return swiftCodeRepository.count() > 0;
    }
}
