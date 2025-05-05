package com.github.hkereb.swiftcodeapi.util;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.exceptions.InvalidExcelFormatException;
import com.github.hkereb.swiftcodeapi.exceptions.InvalidInputException;
import com.github.hkereb.swiftcodeapi.repository.SwiftCodeRepository;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Setter
@Service
public class SwiftCodeExcelParser {

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    public void parseExcelFile(String filePath) throws IOException, InvalidExcelFormatException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);

        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        Map<String, Integer> headerMap = new HashMap<>();
        if (rowIterator.hasNext()) {
            Row headerRow = rowIterator.next();
            for (Cell cell : headerRow) {
                headerMap.put(simplifyColumnHeader(cell.getStringCellValue()), cell.getColumnIndex());
            }
        }

        // validate obligatory columns
        String[] requiredHeaders = {"countryiso2code", "swiftcode", "name", "countryname"};
        for (String requiredHeader : requiredHeaders) {
            if (!headerMap.containsKey(requiredHeader)) {
                throw new InvalidExcelFormatException("Missing required column: " + requiredHeader);
            }
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // read data from excel sheet
            String countryISO2 = getCellString(row, headerMap.get("countryiso2code")).toUpperCase();
            String swiftCode = getCellString(row, headerMap.get("swiftcode")).toUpperCase();
            String codeType = getCellString(row, headerMap.get("codetype")).toUpperCase();
            String bankName = getCellString(row, headerMap.get("name")).toUpperCase();
            String address = getCellString(row, headerMap.get("address")).toUpperCase();
            String townName = getCellString(row, headerMap.get("townname")).toUpperCase();
            String countryName = getCellString(row, headerMap.get("countryname")).toUpperCase();
            String timeZone = getCellString(row, headerMap.get("timezone")).toUpperCase();

            // validate obligatory fields
            if (countryISO2.isEmpty()) throw new InvalidInputException("Missing required field: countryISO2");
            if (swiftCode.isEmpty()) throw new InvalidInputException("Missing required field: swiftCode");
            if (bankName.isEmpty()) throw new InvalidInputException("Missing required field: bankName");
            if (countryName.isEmpty()) throw new InvalidInputException("Missing required field: countryName");

            // create new entity and push to database
            SwiftCode swiftCodeEntity = new SwiftCode();

            swiftCodeEntity.setCountryISO2(countryISO2);
            swiftCodeEntity.setSwiftCode(swiftCode);
            swiftCodeEntity.setCodeType(codeType.isEmpty() ? null : codeType);
            swiftCodeEntity.setBankName(bankName);
            swiftCodeEntity.setBankAddress(address.isEmpty() ? null : address);
            swiftCodeEntity.setTownName(townName.isEmpty() ? null : townName);
            swiftCodeEntity.setCountryName(countryName);
            swiftCodeEntity.setTimeZone(timeZone.isEmpty() ? null : timeZone);
            swiftCodeEntity.setIsHeadquarter(swiftCode.endsWith("XXX"));

            swiftCodeRepository.save(swiftCodeEntity);
        }

        workbook.close();
        fis.close();
    }

    private String getCellString(Row row, Integer cellIndex) {
        if (cellIndex == null) return "";
        Cell cell = row.getCell(cellIndex);
        return cell != null ? cell.toString().trim() : "";
    }

    private String simplifyColumnHeader(String header) {
        return header.trim().replaceAll(" ", "").toLowerCase();
    }

}
