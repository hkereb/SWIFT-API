package com.github.hkereb.swiftcodeapi.util;

import com.github.hkereb.swiftcodeapi.domain.SwiftCode;
import com.github.hkereb.swiftcodeapi.repository.SwiftCodeRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class SwiftCodeExcelParser {

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    public void parseExcelFile(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);

        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        Map<String, Integer> headerMap = new HashMap<>();
        if (rowIterator.hasNext()) {
            Row headerRow = rowIterator.next();
            for (Cell cell : headerRow) {
                String simplifiedHeader = cell.getStringCellValue().trim().replaceAll(" ", "").toLowerCase();
                headerMap.put(simplifiedHeader, cell.getColumnIndex());
            }
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // read data from excel sheet
            String countryISO2 = getCellString(row, headerMap.get("countryiso2code")).toUpperCase();
            String swiftCode = getCellString(row, headerMap.get("swiftcode")).toUpperCase();
            String codeType = getCellString(row, headerMap.get("codetype")).toUpperCase();
            String bankName = getCellString(row, headerMap.get("name"));
            String address = getCellString(row, headerMap.get("address"));
            String townName = getCellString(row, headerMap.get("townname"));
            String countryName = getCellString(row, headerMap.get("countryname")).toUpperCase();
            String timeZone = getCellString(row, headerMap.get("timezone"));

            // create new entity and push to database
            SwiftCode swiftCodeEntity = new SwiftCode();

            swiftCodeEntity.setCountryISO2(countryISO2);
            swiftCodeEntity.setSwiftCode(swiftCode);
            swiftCodeEntity.setCodeType(codeType);
            swiftCodeEntity.setBankName(bankName);
            swiftCodeEntity.setBankAddress(address);
            swiftCodeEntity.setTownName(townName);
            swiftCodeEntity.setCountryName(countryName);
            swiftCodeEntity.setTimeZone(timeZone);
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
}
