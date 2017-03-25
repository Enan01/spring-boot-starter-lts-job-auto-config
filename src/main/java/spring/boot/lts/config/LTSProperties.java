package spring.boot.lts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by Enan on 17/3/24.
 */
@ConfigurationProperties(prefix = "spring.boot.lts.job")
public class LTSProperties {

    private List<JobProperties> joblist;

    public List<JobProperties> getJoblist() {
        return joblist;
    }

    public void setJoblist(List<JobProperties> joblist) {
        this.joblist = joblist;
    }

    public static class JobProperties {
        private String taskId;
        private String name;
        private String cron;
        private String className;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }

    @ConfigurationProperties(prefix = "lts.tasktracker")
    public static class LTSTaskTrakerProperties {
        private String nodeGroup;

        public String getNodeGroup() {
            return nodeGroup;
        }

        public void setNodeGroup(String nodeGroup) {
            this.nodeGroup = nodeGroup;
        }
    }
}
