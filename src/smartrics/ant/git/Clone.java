package smartrics.ant.git;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.transport.URIish;

public class Clone extends Task implements GitTask {

    private String uri;
    private ProgressMonitor progressMonitor;
    private File directory;

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public void setDirectory(File dir) {
        this.directory = dir;
    }

    @Override
    public void setProgressMonitor(ProgressMonitor pm) {
        this.progressMonitor = pm;
    }

    @Override
    public void execute() {
        try {
            new URIish(uri);
            CloneCommand clone = new CloneCommand();
            clone.setURI(uri);
            clone.setDirectory(directory);
            clone.setProgressMonitor(progressMonitor);
            clone.call();
        } catch (URISyntaxException e) {
            throw new BuildException("Invalid URI: " + uri);
        } catch (Exception e) {
            throw new BuildException("Unexpected exception: " + e.getMessage());
        }

    }
}
