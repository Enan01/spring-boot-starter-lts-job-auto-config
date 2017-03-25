package spring.boot.lts.config;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import spring.boot.lts.annotation.EnableLTSJobAutoConfig;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Enan on 17/3/24.
 */
@Configuration
@ConditionalOnBean(annotation = EnableLTSJobAutoConfig.class)
@EnableConfigurationProperties({LTSProperties.class, LTSProperties.LTSTaskTrakerProperties.class})
@Order(Integer.MAX_VALUE)
public class LTSAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(LTSAutoConfiguration.class);

    @Autowired
    private LTSProperties ltsProperties;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private LTSProperties.LTSTaskTrakerProperties ltsTaskTrakerProperties;

    public static final ConcurrentHashMap<String, JobRunner> JOB_RUNNER_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        //当配置EnableLTSJobAutoConfig注解才会自动配置
        LOGGER.info("------[spring boot lts] auto config start------");
        if (ltsProperties.getJoblist() != null && ltsProperties.getJoblist().size() > 0) {
            ltsProperties.getJoblist().forEach(job -> {
                try {
                    JobRunner jobRunner = (JobRunner) context.getBean(Class.forName(job.getClassName()));
                    if (jobRunner != null) {
                        JOB_RUNNER_MAP.put(job.getName(), jobRunner);
                    }
                } catch (ClassNotFoundException e) {
                    LOGGER.error("------[spring boot lts] auto config error: {}------", e);
                }
            });
            submitJobs();
            LOGGER.info("------[spring boot lts] auto config success------");
        } else {
            LOGGER.error("------[spring boot lts] auto config is valid, please check your config------");
        }
    }

    // TODO 暴露接口，用户可以自己实现 提交任务的业务逻辑。
    private void submitJobs() {
        JobClient jobClient = context.getBean(JobClient.class);
        if (jobClient != null) {
            List<LTSProperties.JobProperties> jobPropertiesList = ltsProperties.getJoblist();
            List<Job> jobs = new ArrayList<>();

            jobPropertiesList.forEach(jobProperties -> {
                // 这里模拟提交任务
                Job job = new Job();
                job.setTaskId(jobProperties.getTaskId());
                // 设置用户属性，目前没用到
                job.setParam("name", jobProperties.getName());
                job.setTaskTrackerNodeGroup(ltsTaskTrakerProperties.getNodeGroup());
                job.setNeedFeedback(true);
                job.setReplaceOnExist(true);        // 当任务队列中存在这个任务的时候，是否替换更新
                job.setCronExpression(jobProperties.getCron());
                jobs.add(job);
            });

            Response response = jobClient.submitJob(jobs);
            LOGGER.info("------[spring boot lts] Job Client submit response: {}------", response);
        } else {
            LOGGER.warn("------[spring boot lts] could not find the bean JobClient in container ------");
        }
    }


}
