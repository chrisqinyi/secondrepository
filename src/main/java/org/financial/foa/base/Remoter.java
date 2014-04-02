package org.financial.foa.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.Call;
import org.directwebremoting.extend.MethodDeclaration;
import org.directwebremoting.extend.Reply;
import org.directwebremoting.impl.DefaultRemoter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Remoter extends DefaultRemoter {
	static final String METHOD_CALL_HAS_PARAMETER = "methodCallHasParameter";
	private static final Logger log = LoggerFactory.getLogger(Remoter.class);

	private List<String> nonBatchCallList;

	public void setNonBatchCallList(List<String> nonBatchCallList) {
		this.nonBatchCallList = nonBatchCallList;
	}

	/**
	 * Execute a single call object
	 * 
	 * @param call
	 *            The call to execute
	 * @return A Reply to the Call
	 */
	public Reply execute(Call call) {
		MethodDeclaration method = call.getMethodDeclaration();
		Throwable exception = call.getException();
		// Do we already have an error?
		if (method == null || exception != null) {
			log.debug("[FOA Debug]Method execution failed: ", exception);
			if (exception instanceof org.directwebremoting.extend.MarshallException) {
				return new Reply(
						call.getCallId(),
						null,
						new RuntimeException(
								"You have entered a blank or an invalid value. Please check and try again."));
			} else if (exception instanceof IllegalArgumentException) {
				return new Reply(call.getCallId(), null);
			} else {
				return new Reply(call.getCallId(), null, exception);
			}
		}

		WebContext webcx = WebContextFactory.get();

		// make sure that the DWR parameters �c0-scriptName� and �c0-methodName�
		// are identical both in the URL and the POST parameters if the
		// access control is on the URL.
		HttpServletRequest httpServletRequest = webcx.getHttpServletRequest();
		httpServletRequest.setAttribute(METHOD_CALL_HAS_PARAMETER,
				0 != call.getParameters().length);
		String methodName = call.getMethodName();
		String scriptName = call.getScriptName();
		String pathInfo = httpServletRequest.getPathInfo();
		if (pathInfo.matches("/call/plaincall/Multiple\\.[0-9]+\\.dwr")) {
			if (nonBatchCallList.contains(scriptName + "." + methodName)) {
				throw new RuntimeException(
						"This method not allowed in Batch call!");
			}
		} else if (!pathInfo.equals("/call/plaincall/" + scriptName + "."
				+ methodName + ".dwr")) {
			throw new RuntimeException(
					"DWR parameters not identical in the URL!");
		}

		return super.execute(call);

	}
}
