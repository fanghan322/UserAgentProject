package com.codingchallenge.code.service;

import com.codingchallenge.code.model.UserAgent;

import java.util.List;
import java.util.Map;

public interface DataRetrievingService {

    /**
     * retrieve data from csv file
     *
     * @return
     */
    Map<String, UserAgent> retrieveDataFromFile(String filename);

    /**
     * retrieve data from csv file
     *
     * @return
     */
    List<UserAgent> retrieveAndFilterData(String filename, Map<String, UserAgent> userAgentMap);
}
