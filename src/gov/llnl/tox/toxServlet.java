package gov.llnl.tox;
//---------------------------------------------------
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
//---------------------------------------------------
import gov.llnl.tox.api.*;
import gov.llnl.tox.util.*;
//----------------------------------------------------
public class toxServlet extends HttpServlet
	{
	//-----------------------------------------------
	private static final long serialVersionUID = 1961071705050000001L;
	//-----------------------------------------------
	public void init(ServletConfig config) throws ServletException
		{
		//-------------------------------------------
		try
			{
			debug.init(config);
			debug.logger("gov.llnl.tox.toxServlet","initialized tox version: "+toxServlet.class.getPackage().getImplementationVersion()+" with "+config.getInitParameter("debugLevel")+" logging");
			}
		catch (Exception e)
			{
			throw(new ServletException(e));
			}
		}
	//-----------------------------------------------
	private void doVerb(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		String verb = req.getMethod();
		PrintWriter out = res.getWriter();
		//-------------------------------------------
		String path = req.getPathInfo().substring(1);
		String[] execute = path.split("/");
		apiVerbage v = new apiVerbage(verb);
		res.setContentType(v.getOutputMIME());
		out.println(v.api(execute[0],execute[1],req.getParameterMap()));
		//-------------------------------------------
		out.close();
		}
	//-----------------------------------------------
	private void notImplemented(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		}
	//-----------------------------------------------
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		doVerb(req,res);
		}
	//-----------------------------------------------
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		doVerb(req,res);
		}
	//-----------------------------------------------
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		doVerb(req,res);
		}
	//-----------------------------------------------
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		doVerb(req,res);
		}
	//-----------------------------------------------
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		notImplemented(req,res);
		}
	//-----------------------------------------------
	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
		{
		notImplemented(req,res);
		}
	//-----------------------------------------------
	private String getPostPayload(HttpServletRequest req)
		{
		StringBuilder postBuffer = new StringBuilder(512);
		String result = "";
		try
			{
			BufferedReader postReader = req.getReader();
			String line = null;
			//---------------------------------------
			do
				{
				line = postReader.readLine();
				if (line != null)
					postBuffer.append(line);
				}
			while(line != null);
			//---------------------------------------
			String post = postBuffer.toString();
			if (post.startsWith("xml=%"))
				{
				result = URLDecoder.decode(postBuffer.toString().substring(4),"UTF-8");
				}
			else
				{
				result = post;
				}
			//---------------------------------------
			}
		catch(Exception e)
			{
			result = debug.logger("gov.llnl.tox.toxServlet","error: getPostPayload>> ",e);
			return(result);
			}
		return(result);
		}
	//-----------------------------------------------
	}
//---------------------------------------------------
