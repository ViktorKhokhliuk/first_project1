/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/10.0.14
 * Generated at: 2022-03-30 15:00:45 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final jakarta.servlet.jsp.JspFactory _jspxFactory =
          jakarta.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("file:/C:/Users/vikto/.m2/repository/org/glassfish/web/jakarta.servlet.jsp.jstl/2.0.0/jakarta.servlet.jsp.jstl-2.0.0.jar", Long.valueOf(1644435947506L));
    _jspx_dependants.put("/WEB-INF/tag/language.tld", Long.valueOf(1648481357065L));
    _jspx_dependants.put("jar:file:/C:/Users/vikto/.m2/repository/org/glassfish/web/jakarta.servlet.jsp.jstl/2.0.0/jakarta.servlet.jsp.jstl-2.0.0.jar!/META-INF/c.tld", Long.valueOf(1602863172000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("jakarta.servlet");
    _jspx_imports_packages.add("jakarta.servlet.http");
    _jspx_imports_packages.add("jakarta.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody;

  private volatile jakarta.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public jakarta.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
    _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.release();
  }

  public void _jspService(final jakarta.servlet.http.HttpServletRequest request, final jakarta.servlet.http.HttpServletResponse response)
      throws java.io.IOException, jakarta.servlet.ServletException {

    if (!jakarta.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final jakarta.servlet.jsp.PageContext pageContext;
    jakarta.servlet.http.HttpSession session = null;
    final jakarta.servlet.ServletContext application;
    final jakarta.servlet.ServletConfig config;
    jakarta.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    jakarta.servlet.jsp.JspWriter _jspx_out = null;
    jakarta.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<title>Authorization</title>\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("	href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\"\r\n");
      out.write("	integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\"\r\n");
      out.write("	crossorigin=\"anonymous\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<header>\r\n");
      out.write("		<nav class=\"navbar navbar-expand-md navbar-dark\"\r\n");
      out.write("			style=\"background-color: black\">\r\n");
      out.write("                <form action=\"/tax-office/service/changeLocale\" method=\"POST\" class = \"locale\">\r\n");
      out.write("                <input type=\"hidden\" name=\"view\" value=\"/index.jsp\"/>\r\n");
      out.write("                   <select name=\"selectedLocale\">\r\n");
      out.write("                      ");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("                   </select>\r\n");
      out.write("                   <button type=\"submit\" class=\"btn btn-primary btn-sm\">");
      if (_jspx_meth_lan_005fprint_005f0(_jspx_page_context))
        return;
      out.write("</button>\r\n");
      out.write("                </form>\r\n");
      out.write("		</nav>\r\n");
      out.write("	</header>\r\n");
      out.write(" <div class=\"container\">\r\n");
      out.write("  <h1>");
      if (_jspx_meth_lan_005fprint_005f1(_jspx_page_context))
        return;
      out.write(":</h1>\r\n");
      out.write("  <div class=\"card\">\r\n");
      out.write("   <div class=\"card-body\">\r\n");
      out.write("    <form accept-charset=\"UTF-8\" method=\"POST\" action=\"/tax-office/service/login\">\r\n");
      out.write("\r\n");
      out.write("     <div class=\"form-group row\">\r\n");
      out.write("      <label for=\"login\" class=\"col-sm-2 col-form-label\">");
      if (_jspx_meth_lan_005fprint_005f2(_jspx_page_context))
        return;
      out.write(":</label>\r\n");
      out.write("      <div class=\"col-sm-7\">\r\n");
      out.write("       <input type=\"text\" class=\"form-control\" name=\"login\"\r\n");
      out.write("       placeholder = \"");
      if (_jspx_meth_lan_005fprint_005f3(_jspx_page_context))
        return;
      out.write("\"\r\n");
      out.write("        required>\r\n");
      out.write("      </div>\r\n");
      out.write("     </div>\r\n");
      out.write("\r\n");
      out.write("      <div class=\"form-group row\">\r\n");
      out.write("      <label for=\"password\" class=\"col-sm-2 col-form-label\">");
      if (_jspx_meth_lan_005fprint_005f4(_jspx_page_context))
        return;
      out.write(":</label>\r\n");
      out.write("      <div class=\"col-sm-7\">\r\n");
      out.write("       <input type=\"password\" class=\"form-control\" name=\"password\"\r\n");
      out.write("       placeholder = \"");
      if (_jspx_meth_lan_005fprint_005f5(_jspx_page_context))
        return;
      out.write("\"\r\n");
      out.write("        required>\r\n");
      out.write("      </div>\r\n");
      out.write("     </div>\r\n");
      out.write("\r\n");
      out.write("     <button type=\"submit\" class=\"btn btn-primary\">");
      if (_jspx_meth_lan_005fprint_005f6(_jspx_page_context))
        return;
      out.write("</button>\r\n");
      out.write("     </form>\r\n");
      out.write("     <p>\r\n");
      out.write("     <form action = \"user/registration.jsp\">\r\n");
      out.write("     <button type=\"submit\" class=\"btn btn-primary\">");
      if (_jspx_meth_lan_005fprint_005f7(_jspx_page_context))
        return;
      out.write("</button>\r\n");
      out.write("     </form>\r\n");
      out.write("    </div>\r\n");
      out.write("   </div>\r\n");
      out.write("  </div>\r\n");
      out.write(" </body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof jakarta.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fforEach_005f0(jakarta.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    jakarta.servlet.jsp.PageContext pageContext = _jspx_page_context;
    jakarta.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    boolean _jspx_th_c_005fforEach_005f0_reused = false;
    try {
      _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fforEach_005f0.setParent(null);
      // /index.jsp(21,22) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setVar("locale");
      // /index.jsp(21,22) name = items type = jakarta.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setItems(new org.apache.jasper.el.JspValueExpression("/index.jsp(21,22) '${sessionScope.locales}'",_jsp_getExpressionFactory().createValueExpression(_jspx_page_context.getELContext(),"${sessionScope.locales}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
      int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
        if (_jspx_eval_c_005fforEach_005f0 != jakarta.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n");
            out.write("                          <option value=\"");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${locale}", java.lang.String.class, (jakarta.servlet.jsp.PageContext)_jspx_page_context, null));
            out.write("\">\r\n");
            out.write("                             ");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${locale}", java.lang.String.class, (jakarta.servlet.jsp.PageContext)_jspx_page_context, null));
            out.write("\r\n");
            out.write("                          </option>\r\n");
            out.write("                      ");
            int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
            if (evalDoAfterBody != jakarta.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_c_005fforEach_005f0.doEndTag() == jakarta.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (java.lang.Throwable _jspx_exception) {
        while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_c_005fforEach_005f0.doFinally();
      }
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
      _jspx_th_c_005fforEach_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fforEach_005f0, _jsp_getInstanceManager(), _jspx_th_c_005fforEach_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_lan_005fprint_005f0(jakarta.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    jakarta.servlet.jsp.PageContext pageContext = _jspx_page_context;
    //  lan:print
    epam.project.app.infra.web.tag.LanguageTag _jspx_th_lan_005fprint_005f0 = (epam.project.app.infra.web.tag.LanguageTag) _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.get(epam.project.app.infra.web.tag.LanguageTag.class);
    boolean _jspx_th_lan_005fprint_005f0_reused = false;
    try {
      _jspx_th_lan_005fprint_005f0.setPageContext(_jspx_page_context);
      _jspx_th_lan_005fprint_005f0.setParent(null);
      // /index.jsp(27,72) name = message type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_lan_005fprint_005f0.setMessage("update");
      int _jspx_eval_lan_005fprint_005f0 = _jspx_th_lan_005fprint_005f0.doStartTag();
      if (_jspx_th_lan_005fprint_005f0.doEndTag() == jakarta.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.reuse(_jspx_th_lan_005fprint_005f0);
      _jspx_th_lan_005fprint_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_lan_005fprint_005f0, _jsp_getInstanceManager(), _jspx_th_lan_005fprint_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_lan_005fprint_005f1(jakarta.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    jakarta.servlet.jsp.PageContext pageContext = _jspx_page_context;
    //  lan:print
    epam.project.app.infra.web.tag.LanguageTag _jspx_th_lan_005fprint_005f1 = (epam.project.app.infra.web.tag.LanguageTag) _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.get(epam.project.app.infra.web.tag.LanguageTag.class);
    boolean _jspx_th_lan_005fprint_005f1_reused = false;
    try {
      _jspx_th_lan_005fprint_005f1.setPageContext(_jspx_page_context);
      _jspx_th_lan_005fprint_005f1.setParent(null);
      // /index.jsp(32,6) name = message type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_lan_005fprint_005f1.setMessage("please_sign_in");
      int _jspx_eval_lan_005fprint_005f1 = _jspx_th_lan_005fprint_005f1.doStartTag();
      if (_jspx_th_lan_005fprint_005f1.doEndTag() == jakarta.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.reuse(_jspx_th_lan_005fprint_005f1);
      _jspx_th_lan_005fprint_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_lan_005fprint_005f1, _jsp_getInstanceManager(), _jspx_th_lan_005fprint_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_lan_005fprint_005f2(jakarta.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    jakarta.servlet.jsp.PageContext pageContext = _jspx_page_context;
    //  lan:print
    epam.project.app.infra.web.tag.LanguageTag _jspx_th_lan_005fprint_005f2 = (epam.project.app.infra.web.tag.LanguageTag) _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.get(epam.project.app.infra.web.tag.LanguageTag.class);
    boolean _jspx_th_lan_005fprint_005f2_reused = false;
    try {
      _jspx_th_lan_005fprint_005f2.setPageContext(_jspx_page_context);
      _jspx_th_lan_005fprint_005f2.setParent(null);
      // /index.jsp(38,57) name = message type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_lan_005fprint_005f2.setMessage("login");
      int _jspx_eval_lan_005fprint_005f2 = _jspx_th_lan_005fprint_005f2.doStartTag();
      if (_jspx_th_lan_005fprint_005f2.doEndTag() == jakarta.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.reuse(_jspx_th_lan_005fprint_005f2);
      _jspx_th_lan_005fprint_005f2_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_lan_005fprint_005f2, _jsp_getInstanceManager(), _jspx_th_lan_005fprint_005f2_reused);
    }
    return false;
  }

  private boolean _jspx_meth_lan_005fprint_005f3(jakarta.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    jakarta.servlet.jsp.PageContext pageContext = _jspx_page_context;
    //  lan:print
    epam.project.app.infra.web.tag.LanguageTag _jspx_th_lan_005fprint_005f3 = (epam.project.app.infra.web.tag.LanguageTag) _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.get(epam.project.app.infra.web.tag.LanguageTag.class);
    boolean _jspx_th_lan_005fprint_005f3_reused = false;
    try {
      _jspx_th_lan_005fprint_005f3.setPageContext(_jspx_page_context);
      _jspx_th_lan_005fprint_005f3.setParent(null);
      // /index.jsp(41,22) name = message type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_lan_005fprint_005f3.setMessage("enter_login");
      int _jspx_eval_lan_005fprint_005f3 = _jspx_th_lan_005fprint_005f3.doStartTag();
      if (_jspx_th_lan_005fprint_005f3.doEndTag() == jakarta.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.reuse(_jspx_th_lan_005fprint_005f3);
      _jspx_th_lan_005fprint_005f3_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_lan_005fprint_005f3, _jsp_getInstanceManager(), _jspx_th_lan_005fprint_005f3_reused);
    }
    return false;
  }

  private boolean _jspx_meth_lan_005fprint_005f4(jakarta.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    jakarta.servlet.jsp.PageContext pageContext = _jspx_page_context;
    //  lan:print
    epam.project.app.infra.web.tag.LanguageTag _jspx_th_lan_005fprint_005f4 = (epam.project.app.infra.web.tag.LanguageTag) _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.get(epam.project.app.infra.web.tag.LanguageTag.class);
    boolean _jspx_th_lan_005fprint_005f4_reused = false;
    try {
      _jspx_th_lan_005fprint_005f4.setPageContext(_jspx_page_context);
      _jspx_th_lan_005fprint_005f4.setParent(null);
      // /index.jsp(47,60) name = message type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_lan_005fprint_005f4.setMessage("password");
      int _jspx_eval_lan_005fprint_005f4 = _jspx_th_lan_005fprint_005f4.doStartTag();
      if (_jspx_th_lan_005fprint_005f4.doEndTag() == jakarta.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.reuse(_jspx_th_lan_005fprint_005f4);
      _jspx_th_lan_005fprint_005f4_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_lan_005fprint_005f4, _jsp_getInstanceManager(), _jspx_th_lan_005fprint_005f4_reused);
    }
    return false;
  }

  private boolean _jspx_meth_lan_005fprint_005f5(jakarta.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    jakarta.servlet.jsp.PageContext pageContext = _jspx_page_context;
    //  lan:print
    epam.project.app.infra.web.tag.LanguageTag _jspx_th_lan_005fprint_005f5 = (epam.project.app.infra.web.tag.LanguageTag) _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.get(epam.project.app.infra.web.tag.LanguageTag.class);
    boolean _jspx_th_lan_005fprint_005f5_reused = false;
    try {
      _jspx_th_lan_005fprint_005f5.setPageContext(_jspx_page_context);
      _jspx_th_lan_005fprint_005f5.setParent(null);
      // /index.jsp(50,22) name = message type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_lan_005fprint_005f5.setMessage("enter_password");
      int _jspx_eval_lan_005fprint_005f5 = _jspx_th_lan_005fprint_005f5.doStartTag();
      if (_jspx_th_lan_005fprint_005f5.doEndTag() == jakarta.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.reuse(_jspx_th_lan_005fprint_005f5);
      _jspx_th_lan_005fprint_005f5_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_lan_005fprint_005f5, _jsp_getInstanceManager(), _jspx_th_lan_005fprint_005f5_reused);
    }
    return false;
  }

  private boolean _jspx_meth_lan_005fprint_005f6(jakarta.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    jakarta.servlet.jsp.PageContext pageContext = _jspx_page_context;
    //  lan:print
    epam.project.app.infra.web.tag.LanguageTag _jspx_th_lan_005fprint_005f6 = (epam.project.app.infra.web.tag.LanguageTag) _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.get(epam.project.app.infra.web.tag.LanguageTag.class);
    boolean _jspx_th_lan_005fprint_005f6_reused = false;
    try {
      _jspx_th_lan_005fprint_005f6.setPageContext(_jspx_page_context);
      _jspx_th_lan_005fprint_005f6.setParent(null);
      // /index.jsp(55,51) name = message type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_lan_005fprint_005f6.setMessage("sign_in");
      int _jspx_eval_lan_005fprint_005f6 = _jspx_th_lan_005fprint_005f6.doStartTag();
      if (_jspx_th_lan_005fprint_005f6.doEndTag() == jakarta.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.reuse(_jspx_th_lan_005fprint_005f6);
      _jspx_th_lan_005fprint_005f6_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_lan_005fprint_005f6, _jsp_getInstanceManager(), _jspx_th_lan_005fprint_005f6_reused);
    }
    return false;
  }

  private boolean _jspx_meth_lan_005fprint_005f7(jakarta.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    jakarta.servlet.jsp.PageContext pageContext = _jspx_page_context;
    //  lan:print
    epam.project.app.infra.web.tag.LanguageTag _jspx_th_lan_005fprint_005f7 = (epam.project.app.infra.web.tag.LanguageTag) _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.get(epam.project.app.infra.web.tag.LanguageTag.class);
    boolean _jspx_th_lan_005fprint_005f7_reused = false;
    try {
      _jspx_th_lan_005fprint_005f7.setPageContext(_jspx_page_context);
      _jspx_th_lan_005fprint_005f7.setParent(null);
      // /index.jsp(59,51) name = message type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_lan_005fprint_005f7.setMessage("registration");
      int _jspx_eval_lan_005fprint_005f7 = _jspx_th_lan_005fprint_005f7.doStartTag();
      if (_jspx_th_lan_005fprint_005f7.doEndTag() == jakarta.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005flan_005fprint_0026_005fmessage_005fnobody.reuse(_jspx_th_lan_005fprint_005f7);
      _jspx_th_lan_005fprint_005f7_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_lan_005fprint_005f7, _jsp_getInstanceManager(), _jspx_th_lan_005fprint_005f7_reused);
    }
    return false;
  }
}
