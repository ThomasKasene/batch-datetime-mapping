package com.thomaskasene.stackoverflow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
@EnableBatchProcessing
public class BatchConfig {

    public static void main(String[] args) {
        SpringApplication.run(BatchConfig.class, args);
    }

    @Bean
    Job testJob(JobBuilderFactory jobBuilderFactory, Step readWriteStep) {
        return jobBuilderFactory
                .get("test-job")
                .start(readWriteStep)
                .build();
    }

    @Bean
    Step readWriteStep(StepBuilderFactory stepBuilderFactory, ItemReader<User> itemReader, ItemWriter<User> itemWriter) {
        return stepBuilderFactory
                .get("read-write-step")
                .<User, User>chunk(1)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }

    @Bean
    FlatFileItemReader<User> itemReader(LineMapper<User> lineMapper) {
        FlatFileItemReader<User> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("input.csv"));
        itemReader.setName("CSV-Reader");
        itemReader.setLineMapper(lineMapper);
        return itemReader;
    }

    @Bean
    ItemWriter<User> itemWriter() {
        return items -> items.forEach(System.out::println);
    }

    @Bean
    LineMapper<User> lineMapper(LineTokenizer lineTokenizer, FieldSetMapper<User> fieldSetMapper) {
        DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    LineTokenizer lineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("firstName", "lastName", "dateOfBirth", "dateOfJoining", "timeStampReg");
        return lineTokenizer;
    }

    @Bean
    FieldSetMapper<User> fieldSetMapper() {
        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);
        fieldSetMapper.setConversionService(ApplicationConversionService.getSharedInstance());
        return fieldSetMapper;
    }
}
