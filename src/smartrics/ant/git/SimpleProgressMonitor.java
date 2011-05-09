package smartrics.ant.git;

import org.eclipse.jgit.lib.ProgressMonitor;

public class SimpleProgressMonitor implements ProgressMonitor {

    private GitTask owner;
    private int progress;
    private String name;
    private int lastSz = -1;

    public SimpleProgressMonitor(GitTask t) {
        this.owner = t;
    }

    @Override
    public void update(int sz) {
        if (lastSz == sz) {
            return;
        }
        lastSz = sz;
        int p = sz * 100 / progress;
        if (p % 10 == 0) {
            owner.log("  update: " + p);
        }
        progress = sz;
    }

    @Override
    public void start(int sz) {
        progress = sz;
        owner.log("  start: " + sz);
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void endTask() {
        owner.log("end: " + name);
    }

    @Override
    public void beginTask(String what, int sz) {
        name = what;
        owner.log("begin: " + what + " (" + sz + ")");
    }

}
