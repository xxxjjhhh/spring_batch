package com.example.batchsample.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class SecondBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    public SecondBatchConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {

        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean(name = "SecondJob")
    public Job secondJob() {

        System.out.println("second job");

        return new JobBuilder("SecondJob", jobRepository)
                .start(secondStep())
                .build();
    }

    @Bean
    public Step secondStep() {

        System.out.println("second step");

        return new StepBuilder("SecondStep", jobRepository)
                .<String, String> chunk(3, platformTransactionManager)
                .reader(secondReader())
                .processor(secondProcessor())
                .writer(secondWriter())
                .build();
    }

    @Bean
    public ItemReader<String> secondReader() {

        return new ListItemReader<>(Arrays.asList("a", "b", "c", "d"));
    }

    @Bean
    public ItemProcessor<String, String> secondProcessor() {

        return item -> item.toUpperCase();
    }

    @Bean
    public ItemWriter<String> secondWriter() {

        return item -> item.forEach(System.out::println);
    }
}
