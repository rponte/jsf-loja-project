package br.com.triadworks.loja.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

public class MockFacesContext extends FacesContext {

	Map<Severity, List<FacesMessage>> messagesBag = new LinkedHashMap<Severity, List<FacesMessage>>();
	
	public MockFacesContext() {
		messagesBag.put(FacesMessage.SEVERITY_INFO, new ArrayList<FacesMessage>());
		messagesBag.put(FacesMessage.SEVERITY_ERROR, new ArrayList<FacesMessage>());
	}
	
	@Override
	public void addMessage(String target, FacesMessage msg) {
		List<FacesMessage> messages = messagesBag.get(msg.getSeverity());
		messages.add(msg);
	}

	@Override
	public Application getApplication() {
		return null;
	}
	@Override
	public Iterator<String> getClientIdsWithMessages() {
		return null;
	}
	@Override
	public ExternalContext getExternalContext() {
		return null;
	}
	@Override
	public Severity getMaximumSeverity() {
		return null;
	}

	@Override
	public Iterator<FacesMessage> getMessages() {
		List<FacesMessage> allMessages = new ArrayList<FacesMessage>();
		for (List<FacesMessage> messages : messagesBag.values()) {
			allMessages.addAll(messages);
		}
		return allMessages.iterator();
	}

	@Override
	public Iterator<FacesMessage> getMessages(String arg0) {
		return null;
	}
	@Override
	public RenderKit getRenderKit() {
		return null;
	}
	@Override
	public boolean getRenderResponse() {
		return false;
	}
	@Override
	public boolean getResponseComplete() {
		return false;
	}
	@Override
	public ResponseStream getResponseStream() {
		return null;
	}
	@Override
	public ResponseWriter getResponseWriter() {
		return null;
	}
	@Override
	public UIViewRoot getViewRoot() {
		return null;
	}
	@Override
	public void release() {
	}
	@Override
	public void renderResponse() {
	}
	@Override
	public void responseComplete() {
	}
	@Override
	public void setResponseStream(ResponseStream arg0) {
	}
	@Override
	public void setResponseWriter(ResponseWriter arg0) {
	}
	@Override
	public void setViewRoot(UIViewRoot arg0) {
	}

}
