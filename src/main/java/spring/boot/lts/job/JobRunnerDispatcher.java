package spring.boot.lts.job;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.spring.boot.annotation.JobRunner4TaskTracker;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.boot.lts.config.LTSAutoConfiguration;

@JobRunner4TaskTracker
public class JobRunnerDispatcher implements JobRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobRunnerDispatcher.class);

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        Job job = jobContext.getJob();
        String name = job.getParam("name");
        LOGGER.info("开始执行名称为[{}]的job..................", name);
        return LTSAutoConfiguration.JOB_RUNNER_MAP.get(name).run(jobContext);
    }
}