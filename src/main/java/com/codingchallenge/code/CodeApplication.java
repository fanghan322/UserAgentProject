package com.codingchallenge.code;

import com.codingchallenge.code.service.WriteOutDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class CodeApplication implements CommandLineRunner {

    public static final String CCDS1_FILE = "ccds1.csv";
    public static final String CCDS2_FILE = "ccds2.tsv";
    public static final String OUTPUT_FILE = "output.txt";

    @Autowired
    private WriteOutDataService writeOutDataService;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        SpringApplication.run(CodeApplication.class, args);
        long endTime = System.nanoTime();
        //time elapsed
        long timeElapsed = endTime - startTime;
        System.out.println("Elapsed time of the application in milliseconds: " + timeElapsed / 1000000);
    }

    @Override
    public void run(String... args) throws Exception {

        //Set up the input data in the requirement
        writeOutDataService.writeOutData(CCDS1_FILE, CCDS2_FILE, OUTPUT_FILE);
    }

}
