<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <style>
        h1 {
            margin-left: 200px;
            margin-top: 80px;
        }

        .table0 {
            margin: auto;
            margin-top: 80px;


            width: 350px;
            height: 100px;
        }

        .input0 {
            margin: 10px;
            height: 25px;
        }

        .input1 {
            height: 50px;
            width: 150px

        }

        .input2 {
            margin-left: 10px;
        }

        a {
            margin-left: 30px;
        }

        .input3 {
            margin-top: 0px;
            height: 80px;
            width: 350px
        }

    </style>
</head>
<body>
<header>
    <h1> ENI-Enchères</h1>
</header>
	
<table class="table0">
    

        <tr>
            <td><label> Pseudo :</label></td>
            <td> ${sessionScope.utilisateur.getPseudo() }</td>
        </tr>
        <tr>
            <td><label> Nom : </label></td>
            <td>  ${sessionScope.utilisateur.getNom() } </td>
        </tr>
        <tr>
            <td > Prénom : </td>
            <td> ${sessionScope.utilisateur.getPrenom() } </td>
        </tr>
        <tr>
            <td>Email : </td>
            <td>${sessionScope.utilisateur.getEmail() }</td>
        </tr>
        <tr>
            <td> Rue : </td>
            <td> ${sessionScope.utilisateur.getRue() }</td>
        </tr>
        <tr>
            <td>Code postal</td>
            <td>${sessionScope.utilisateur.getCodePostal() } </td>
        </tr>
        <tr>
            <td>Ville  </td>
            <td> ${sessionScope.utilisateur.getVille() }</td>
        </tr>
 </table>
<table class="table0">
    <tr>
        <td> <a href="ServletPageModifierProfil"> <input class="input3" type="submit" value="Modifier"/> </a>
         
    </tr>
</table>


</body>
</html>