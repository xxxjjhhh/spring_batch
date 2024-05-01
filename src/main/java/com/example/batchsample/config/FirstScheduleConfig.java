package com.example.batchsample.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class FirstScheduleConfig {

    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;

    public FirstScheduleConfig(JobLauncher jobLauncher, ApplicationContext applicationContext) {

        this.jobLauncher = jobLauncher;
        this.applicationContext = applicationContext;
    }

    @Scheduled(cron = "10 * * * * *", zone = "Asia/Seoul")
    public void runJob() throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobParametersInvalidException,
            JobRestartException {

        System.out.println("first schedule");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String date = dateFormat.format(new Date());
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

        Job job = applicationContext.getBean("FirstJob", Job.class);

        jobLauncher.run(job, jobParameters);
    }
}
