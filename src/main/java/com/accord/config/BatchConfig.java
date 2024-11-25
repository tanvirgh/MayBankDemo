package com.accord.config;


import com.accord.listener.AccountItemReadListener;
import com.accord.listener.JobCompletionNotificationListener;
import com.accord.listener.TruncateExitMessageListener;
import com.accord.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

/**
 * User : Tanvir Ahmed
 * Date: 11/21/2024.
 */


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    public static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);



    @Bean
    public FlatFileItemReader<Account> reader() {
        return new FlatFileItemReaderBuilder<Account>()
                .name("AccountReader")
                .resource(new FileSystemResource("src/main/resources/data.txt"))
                .delimited()
                .delimiter("|")
                .names("ACCOUNT_NUMBER", "TRX_AMOUNT", "DESCRIPTION", "TRX_DATE", "TRX_TIME", "CUSTOMER_ID")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Account.class);
                    setCustomEditors(Map.of(
                            LocalDate.class, new PropertyEditorSupport() {
                                @Override
                                public void setAsText(String text) {
                                    setValue(LocalDate.parse(text));
                                }
                            },
                            LocalTime.class, new PropertyEditorSupport() {
                                @Override
                                public void setAsText(String text) {
                                    setValue(LocalTime.parse(text));
                                }
                            }
                    ));
                }})
                .linesToSkip(1) // Skip header row
                .build();
    }


    @Bean
    public JdbcBatchItemWriter<Account> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Account>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO accounts (account_number, tran_amount, description, tran_date, tran_time, customer_id) " +
                        "VALUES (:ACCOUNT_NUMBER, :TRX_AMOUNT, :DESCRIPTION, :TRX_DATE, :TRX_TIME, :CUSTOMER_ID)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, FlatFileItemReader<Account> reader,
                     JdbcBatchItemWriter<Account> writer, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<Account, Account>chunk(10, transactionManager)
                .reader(reader)
                .writer(writer)
                .transactionManager(transactionManager)
                .listener(new AccountItemReadListener())
                .listener(new StepExecutionListenerSupport() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        if (stepExecution.getStartTime() == null) {
                            stepExecution.setStartTime(LocalDateTime.now()); // Manually setting for debugging
                        }
                        logger.info("Step Execution started at: " + stepExecution.getStartTime());
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        logger.info("Step Execution finished at: " + stepExecution.getEndTime());
                        return super.afterStep(stepExecution);
                    }
                })
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("importAccountJob", jobRepository)
                .listener(new TruncateExitMessageListener())
                .listener(new JobCompletionNotificationListener())
                .start(step)
                .build();
    }






}


