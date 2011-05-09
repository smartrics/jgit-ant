package smartrics.ant.git;

import org.eclipse.jgit.lib.ProgressMonitor;

public class SimpleProgressMonitor implements ProgressMonitor {

    private GitTask owner;
    private int progress;

    public SimpleProgressMonitor(GitTask t) {
        this.owner = t;
    }

    @Override
    public void update(int sz) {
        int p = sz * 100 / progress;
        if (p % 10 == 0) {
            owner.log("UPDATe: " + p);
        }
    }

    @Override
    public void start(int sz) {
        progress = sz;
        owner.log("START: " + sz);
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void endTask() {
        owner.log("END");
    }

    @Override
    public void beginTask(String what, int sz) {
        owner.log("BEGIN: " + what + " (" + sz + ")");
    }

}
