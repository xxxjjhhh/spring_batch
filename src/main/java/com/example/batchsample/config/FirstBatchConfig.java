package com.example.batchsample.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class FirstBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    public FirstBatchConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {

        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean(name = "FirstJob")
    public Job firstJob() {

        System.out.println("first job");

        return new JobBuilder("FirstJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .build();
    }

    @Bean
    public Step firstStep() {

        System.out.println("first step");

        return new StepBuilder("FirstStep", jobRepository)
                .<String, String> chunk(3, platformTransactionManager)
                .reader(firstReader())
                .processor(firstProcessor())
                .writer(firstWriter())
                .build();
    }

    @Bean
    public ItemReader<String> firstReader() {

        return new ListItemReader<>(Arrays.asList("kim", "lee", "park", "choi"));
    }

    @Bean
    public ItemProcessor<String, String> firstProcessor() {

        return item -> item.toUpperCase();
    }

    @Bean
    public ItemWriter<String> firstWriter() {

        return item -> item.forEach(System.out::println);
    }
}
