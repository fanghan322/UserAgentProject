package com.codingchallenge.code.service.impl;

import com.codingchallenge.code.CodeApplication;
import com.codingchallenge.code.model.UserAgent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Map;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DataRetrievingServiceImpl.class})
public class DataRetrievingServiceImplTest {

    @InjectMocks
    DataRetrievingServiceImpl dataRetrievingServiceImpl;

    @Before
    public void setUp() {

    }

    @Test
    public void testRetrieveDataFromFile(){
        Map<String, UserAgent> userAgentMap = dataRetrievingServiceImpl.retrieveDataFromFile(CodeApplication.CCDS1_FILE);
        assertNotNull(userAgentMap);
        assertTrue(userAgentMap.size() > 0);
    }
}
