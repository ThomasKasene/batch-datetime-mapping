package com.thomaskasene.stackoverflow;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BatchConfigTest {

    @Autowired
    private Job testJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Test
    void startBatch() throws Exception {
        jobLauncher.run(testJob, new JobParameters());
    }
}
