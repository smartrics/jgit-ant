package smartrics.ant.git;

import org.eclipse.jgit.lib.ProgressMonitor;

public class SimpleProgressMonitor implements ProgressMonitor {

    @Override
    public void update(int arg0) {
    }

    @Override
    public void start(int arg0) {
        System.out.println("START: " + arg0);
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void endTask() {
        System.out.println("END");
    }

    @Override
    public void beginTask(String arg0, int arg1) {
        System.out.println("BEGIN: " + arg0 + " (" + arg1 + ")");
    }

}
