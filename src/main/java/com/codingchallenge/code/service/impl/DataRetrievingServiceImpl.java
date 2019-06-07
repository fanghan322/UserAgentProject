package com.codingchallenge.code.service.impl;

import com.codingchallenge.code.model.UserAgent;
import com.codingchallenge.code.service.DataRetrievingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataRetrievingServiceImpl implements DataRetrievingService {

    private static final String CCDS1_SPLIT_BY = "\".\"";
    private static final String CCDS2_SPLIT_BY = "\t";
    private static final int ID_POSITION = 0;
    private static final int COUNTRY_POSITION = 1;
    private static final int F1_POSITION = 2;
    private static final int F2_POSITION = 3;
    private static final int F3_POSITION = 4;
    private static final List<String> EUC = new ArrayList<>();
    static {
        EUC.add("BE");
        EUC.add("BG");
        EUC.add("CZ");
        EUC.add("DK");
        EUC.add("DE");
        EUC.add("EE");
        EUC.add("IE");
        EUC.add("EL");
        EUC.add("ES");
        EUC.add("FR");
        EUC.add("HR");
        EUC.add("IE");
        EUC.add("CY");
        EUC.add("LV");
        EUC.add("LT");
        EUC.add("LU");
        EUC.add("HU");
        EUC.add("MT");
        EUC.add("NL");
        EUC.add("AT");
        EUC.add("PL");
        EUC.add("PT");
        EUC.add("RO");
        EUC.add("SI");
        EUC.add("SK");
        EUC.add("FI");
        EUC.add("SE");
        EUC.add("UK");
    }


    @Override
    public Map<String, UserAgent> retrieveDataFromFile(String filename) {
        ClassPathResource res = new ClassPathResource(filename);
        Map<String, UserAgent> userAgentMap = new HashMap<>();
        try {
            File inputF = res.getFile();
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputFS));
            userAgentMap = reader.lines().map(mapToUserAgent).collect(Collectors.toMap(UserAgent::getId, Function.identity(), (a1, a2) -> a1));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error during retrieving data from file " + filename);
        }
        return userAgentMap;
    }


    protected Function<String, UserAgent> mapToUserAgent = (line) -> {
        String[] splits = line.split(CCDS1_SPLIT_BY);
        UserAgent userAgent = new UserAgent();
        userAgent.setId(removeQuotes(splits[0]));
        userAgent.setUa(removeQuotes(splits[1]));
        return userAgent;
    };

    protected String removeQuotes(String input){
        String returnValue = input;
        if (returnValue.startsWith("\"")){
            returnValue = returnValue.substring(1, returnValue.length());
        }
        if (returnValue.endsWith("\"")){
            returnValue = returnValue.substring(0, returnValue.length() - 1);
        }
        return returnValue;
    }

    @Override
    public List<UserAgent> retrieveAndFilterData(String filename, Map<String, UserAgent> userAgentMap){
        ClassPathResource res = new ClassPathResource(filename);
        List<UserAgent> userAgentList = new ArrayList<>();
        try {
            File inputF = res.getFile();
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputFS));
            reader.lines().forEach(line -> {
                String[] splits = line.split(CCDS2_SPLIT_BY);
                String id = splits[ID_POSITION];
                UserAgent userAgent = userAgentMap.get(id);
                if (userAgent != null
                        && splits.length > COUNTRY_POSITION
                        && !EUC.contains(splits[COUNTRY_POSITION])){
                        userAgent.setCountry(splits[COUNTRY_POSITION]);
                    if (splits.length > F1_POSITION) {
                        userAgent.setF1(splits[F1_POSITION]);
                    }
                    if (splits.length > F2_POSITION) {
                        userAgent.setF2(splits[F2_POSITION]);
                    }
                    if (splits.length > F3_POSITION) {
                        userAgent.setF3(splits[F3_POSITION]);
                    }
                    userAgentList.add(userAgent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error during retrieving data from file " + filename);
        }
        return userAgentList;
    }


}
