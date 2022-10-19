<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
   <head>
      <title><spring:message code="pageName"/> - <spring:message code="pleaseVerify.title"/></title>
      <%@ include file="../../components/includes/headers.jsp" %>
   </head>

   <body>
      <div class="container-main">
         <%@ include file="../../components/simpleNavbar.jsp" %>

<%--         <div class="container-fluid p-0 h-100 d-flex justify-content-center align-items-center">--%>
<%--            <div class="container-fluid p-0 d-flex flex-column justify-content-center align-items-center" style="width: 60%">--%>
<%--               <h1 class="font-weight-bold text-center" style="font-size: 5vh;">--%>
<%--                  <spring:message code="verify.account"/>--%>
<%--               </h1>--%>
<%--               <button type="button" class="btn btn-error" onclick="history.back()">--%>
<%--                  <spring:message code="goBack"/>--%>
<%--               </button>--%>
<%--            </div>--%>
<%--         </div>--%>

         <div class="container-fluid p-0 my-auto h-auto w-100 d-flex justify-content-center align-items-center">
            <div class="w-100 modalContainer">
               <div class="row w-100 h-100 py-5 px-3 m-0 align-items-center justify-content-center">
                  <div class="col-12">
                     <h1 class="text-center title">
                        <spring:message code="pleaseVerify.description"/>
                     </h1>
                  </div>
                  <div class="col-12 px-0 mt-4 d-flex align-items-center justify-content-center">
                     <div class="d-flex justify-content-around">
                        <a href="<c:url value = "/"/>">
                           <button type="button" class="btn btn-continue">
                              <spring:message code="pleaseVerify.homeBtn"/>
                           </button>
                        </a>
                        <a href="<c:url value = "/user/verifyAccount/status/resend"/>">
                           <button type="button" class="btn btn-continue">
                              <spring:message code="pleaseVerify.sentAgain"/>
                           </button>
                        </a>
                     </div>
                  </div>
               </div>
            </div>
         </div>
         <%@ include file="../../components/footer.jsp" %>
      </div>
      <%@ include file="../../components/includes/bottomScripts.jsp" %>
   </body>
</html>

