package com.accord.schedular;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * User : Tanvir Ahmed
 * Date: 11/21/2024.
 */
@Component
public class BatchJobRunner implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importAccountJob;

    // if you want to run your batch job through schedular  use the commented code bloclk

    /*@Scheduled(cron = "0 0 0 * * ?")  // This will run the job at midnight every day
    public void runJobScheduled() throws Exception {
        jobLauncher.run(importAccountJob, new JobParameters());
        System.out.println("Scheduled batch job started successfully!");
    }*/

    // this will run the batch job when the application starts.
    @Override
    public void run(String... args) throws Exception {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis()) // Add unique parameter
                    .toJobParameters();
            // Start the job when the application starts
            jobLauncher.run(importAccountJob, jobParameters);
            System.out.println("Batch job started successfully!");
        } catch (Exception e) {
            System.err.println("Error occurred while running batch job: " + e.getMessage());
        }
    }
}
