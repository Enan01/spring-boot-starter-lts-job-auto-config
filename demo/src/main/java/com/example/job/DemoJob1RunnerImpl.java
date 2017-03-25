package com.example.job;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Enan on 17/3/24.
 */
@Component
public class DemoJob1RunnerImpl implements JobRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoJob1RunnerImpl.class);

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        LOGGER.info("我要执行：" + jobContext);
        return new Result(Action.EXECUTE_SUCCESS, jobContext + "：执行成功了");
    }
}
