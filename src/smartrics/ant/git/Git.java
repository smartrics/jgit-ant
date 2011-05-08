package smartrics.ant.git;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;
import org.eclipse.jgit.lib.ProgressMonitor;

public class Git extends Task implements TaskContainer {

    private boolean verbose = false;
    private File directory;

    private List<Task> tasks = new ArrayList<Task>();

    public void setVerbose(boolean v) {
        this.verbose = v;
    }
    
    public void setDirectory(File dir) {
        this.directory = dir;
    }

    @Override
    public void execute() throws BuildException {
        ProgressMonitor pm = null;
        if (verbose) {
            pm = new SimpleProgressMonitor();
        }
        for (Task t : tasks) {
            if (t instanceof GitTask) {
                ((GitTask) t).setProgressMonitor(pm);
                ((GitTask) t).setDirectory(directory);
            }
            t.execute();
        }
    }

    @Override
    public void addTask(Task t) {
        tasks.add(t);
    }

}
