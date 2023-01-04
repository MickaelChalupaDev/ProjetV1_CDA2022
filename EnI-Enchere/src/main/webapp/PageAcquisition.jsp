<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Enchérir</title>
    <style>
        h1 {
            margin-left: 200px;
            margin-top: 0px;
        }

        .table0 {
        
            margin-left: auto auto;
            width: 500px;
            height: 100px;
        }
         .table2 {
         	
            margin-left: 200px;
            width: 850px;
            height: 100px;
        }

        .input0 {
            margin: 5px;
            height: 25px;
            width: 250px;
        }

        .table1 {
            margin-left: 0px;
            width: 430px;
            height: 80px;
           
        }

        .input3 {
            margin-top: 0px;
            height: 50px;
            width: 120px
        }

    </style>
</head>
<body>
<header>
    <h1> ENI-Enchères</h1>
    <h2 Style="text-align: center"> Vous avez remporté la vente </h2>
</header>

<table class="table2">
<tr>
<td style="vertical-align:top; width:300px"> 
 
 <c:if test = "${requestScope.article.getNomPhoto()!= null}">
 
     <img src="http://localhost:8080/${pageContext.request.contextPath}${requestScope.article.getNomPhoto()}"  alt="Photo de l'article à vendre" style="max-height : 300px">
 		
 </c:if>
 
</td>
<td>
	<table class="table0">
        <tr>
            <td><p> ${requestScope.article.getNomArticle()} </p> </td>
        </tr>
        <tr>        
        	<table>
        		<tr>
            		<td><label> Description : </label></td>
            		<td><input style="height : 100px"class="input0" type="text" name="description" value="${requestScope.article.getDescription()}" /></td>
        		</tr>
        		
         		<tr>
              		<td><label> Meilleure offre :</label> </td>
              		<td style="margin-left: 70px; width:205px"> ${requestScope.meilleureOffre} </td>
                </tr>
        		<tr>
            		<td><label> Mise à prix : </label></td>
            		<td style="margin-left: 0px;width:80px"> ${requestScope.article.getMiseAPrix()} </td>
        		</tr>
        		
             	<tr>
             				<td><label> Retrait : </label> </td>
            	   			<td style="margin-left: 25px;width:210px"> ${requestScope.article.getAdresse().get(0)} <br>
            				${requestScope.article.getAdresse().get(1)} &nbsp ${requestScope.article.getAdresse().get(2)}   
				   			</td>
                </tr>
        		<tr>
        			<table>
        			<tr>
        		   		<td><label> Vendeur : </label> </td>
            			<td style="margin-left: 25px;width:210px"> ${requestScope.article.getVendeur()} </td>
                	</tr>
                	<tr>
        		   		<td><label> Tél : </label> </td>
            			<td style="margin-left: 25px;width:210px"> ${sessionScope.user.getTelephone()} </td>
                	</tr>
               		
        	  		</table>
        	  	</tr>
        	  	
		</table>
 	</tr>    
</table>
</tr>
</table>
<div style="margin-left: 500px;margin-top: 80px;width:210px" > 
		<a href="${pageContext.request.contextPath}/PageListeEncheresConnecte.jsp"> <input class="input3" type="button" value="Back"/>  </a>
</div>
               

</body>
</html>