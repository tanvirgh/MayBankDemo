package com.accord.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;


/**
 * User : Tanvir Ahmed
 * Date: 11/21/2024.
 */
@Component
public class TruncateExitMessageListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
        String exitMessage = jobExecution.getExitStatus().getExitDescription();
        if (exitMessage != null && exitMessage.length() > 250) {
            jobExecution.getExitStatus().addExitDescription(exitMessage.substring(0, 250));
        }
    }
}
