package com.github.hkereb.swiftcodeapi.repository;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface SwiftCodeRepository extends JpaRepository<SwiftCode, Serializable> {
    SwiftCode getBySwiftCode(String swiftCode);
    List<SwiftCode> getByCountryISO2(String iso2Code);
    List<SwiftCode> getBranchesBySwiftCode(String swiftCode);
    List<SwiftCode> getBySwiftCodeStartingWith(String swiftCodePrefix);
}
