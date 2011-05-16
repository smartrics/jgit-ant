package smartrics.ant.git;

import org.apache.tools.ant.BuildException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.RebaseResult;
import org.eclipse.jgit.api.RebaseResult.Status;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.transport.FetchResult;

public class PullTask extends AbstractGitTask {
    private String mergeFailedProperty;

    private String pullFailedProperty;

    private String rebaseFailedProperty;

    public void setMergeFailedProperty(String p) {
        this.mergeFailedProperty = p;
    }

    public void setRebaseFailedProperty(String p) {
        this.rebaseFailedProperty = p;
    }

    public void setPullFailedProperty(String p) {
        this.pullFailedProperty = p;
    }

    @Override
    public void execute() {
        try {
            RepositoryBuilder builder = new RepositoryBuilder();
            Repository repo = builder.findGitDir(getDirectory()).build();
            Git g = new Git(repo);
            PullCommand pull = g.pull();
            PullResult result = pull.call();
            FetchResult fRes = result.getFetchResult();
            log("Fetch result: " + fRes.getMessages());
            MergeResult mRes = result.getMergeResult();
            RebaseResult rRes = result.getRebaseResult();
            Status rStatus = rRes.getStatus();
            MergeStatus mStatus = mRes.getMergeStatus();
            boolean mergeFailed = MergeStatus.FAILED.equals(mStatus) || MergeStatus.CONFLICTING.equals(mStatus);
            if (mergeFailed) {
                if (mergeFailedProperty != null) {
                    getProject().setProperty(mergeFailedProperty, mRes.toString());
                    log("Setting '" + mergeFailedProperty + "' to '" + mRes.toString() + "'", 4);
                }
            }
            boolean rebaseFailed = Status.ABORTED.equals(rStatus) || Status.FAILED.equals(rStatus) || Status.STOPPED.equals(rStatus);
            if (rebaseFailed) {
                if (rebaseFailedProperty != null) {
                    getProject().setProperty(rebaseFailedProperty, rRes.toString());
                    log("Setting '" + rebaseFailedProperty + "' to '" + mRes.toString() + "'", 4);
                }
            }
            if (rebaseFailed || mergeFailed && pullFailedProperty != null) {
                getProject().setProperty(pullFailedProperty, mRes.toString());
                log("Setting '" + pullFailedProperty + "' to 'failed'", 4);
            }
        } catch (Exception e) {
            throw new BuildException("Unexpected exception: " + e.getMessage(), e);
        }

    }
}
