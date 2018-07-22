package superpancake.contentdetectorservice.actuator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;

import org.apache.juli.logging.Log;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfig.Type;
import org.apache.tomcat.util.net.SocketEvent;
import org.apache.tomcat.util.net.SocketProcessorBase;
import org.apache.tomcat.util.net.SocketWrapperBase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnResource(resources = "git.properties")
public class GitEndpoint extends AbstractEndpoint<GitRepositoryState> {

	private GitRepositoryState state = new GitRepositoryState();

	public GitEndpoint() {
//		super("git");

		try {
			Properties git = new Properties();
			git.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("git.properties"));

			state.git = git;
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

//	@Override
//	public GitRepositoryState invoke() {
//		return state;
//	}

	@Override
	protected Type getSslConfigType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void createSSLContext(SSLHostConfig sslHostConfig) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void releaseSSLContext(SSLHostConfig sslHostConfig) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected InetSocketAddress getLocalAddress() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAlpnSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean getDeferAccept() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected SocketProcessorBase<GitRepositoryState> createSocketProcessor(
			SocketWrapperBase<GitRepositoryState> socketWrapper, SocketEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bind() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unbind() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startInternal() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopInternal() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Acceptor createAcceptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Log getLog() {
		// TODO Auto-generated method stub
		return null;
	}

}