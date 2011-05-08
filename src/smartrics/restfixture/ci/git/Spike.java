package smartrics.restfixture.ci.git;

import java.io.File;
import java.util.Date;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;

public class Spike {
    public static void main(String[] args) throws Exception {
        Git git = Git.open(new File("./Fitnesse-readonly"));
        LogCommand logCommand = git.log();
        Iterable<RevCommit> revCommits = logCommand.call();
        RevCommit revCommit = revCommits.iterator().next();
        System.out.println(revCommit.getFullMessage());
        System.out.println(new Date(revCommit.getCommitTime() * 1000));
        System.out.println((System.currentTimeMillis() / 1000 - revCommit.getCommitTime()) / 60 / 60);
    }

    public static void _main(String[] args) throws Exception {
        ProgressMonitor monitor = new ProgressMonitor() {

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
        };

        System.out.println("RestFixture ====");
        CloneCommand clone = new CloneCommand();
        clone.setURI("git://github.com/smartrics/RestFixture.git");
        clone.setDirectory(new File("./RestFixture-readonly"));
        clone.setProgressMonitor(monitor);
        clone.call();

        System.out.println("FitNesse ====");
        clone = new CloneCommand();
        clone.setURI("git://github.com/unclebob/fitnesse.git");
        clone.setDirectory(new File("./Fitnesse-readonly"));
        clone.setProgressMonitor(monitor);
        clone.call();
    }
}
