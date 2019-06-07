package com.codingchallenge.code.service.impl;

import com.codingchallenge.code.model.UserAgent;
import com.codingchallenge.code.service.DataRetrievingService;
import com.codingchallenge.code.service.WriteOutDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class WriteOutDataServiceImpl implements WriteOutDataService {

    @Autowired
    private DataRetrievingService dataRetrievingService;

    @Override
    public void writeOutData(String file1, String file2, String output) {
        Map<String, UserAgent> userAgentMap = dataRetrievingService.retrieveDataFromFile(file1);
        List<UserAgent> userAgentList = dataRetrievingService.retrieveAndFilterData(file2, userAgentMap);
        BufferedWriter bw = null;
        try {
            String outputDir = System.getProperty("user.home") + "/" + output;
            FileWriter fw = new FileWriter(outputDir);
            for (UserAgent userAgent : userAgentList) {
                String outputString = new StringBuilder().append(userAgent.getId())
                                                        .append(",")
                                                        .append(userAgent.getCountry())
                                                        .append(",")
                                                        .append(userAgent.getUa())
                                                        .append(",")
                                                        .append((userAgent.getF1() == null) ? "" :  userAgent.getF1())
                                                        .append(",")
                                                        .append((userAgent.getF2() == null) ? "" :  userAgent.getF2())
                                                        .append(",")
                                                        .append((userAgent.getF3() == null) ? "" :  userAgent.getF3())
                                                        .append("\n")
                                                        .toString();
                log.info("user agent : " + outputString);
                fw.write(outputString);
                fw.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error of writing user agent information into the file : " + e.getMessage());
        }
        finally
        {
            try{
                if(bw!=null)
                    bw.close();
            }catch(Exception ex){
                ex.printStackTrace();
                log.error("Error in closing the BufferedWriter : " + ex);
            }
        }
    }
}
