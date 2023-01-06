<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="fr.eni.encheres.bo.Utilisateur" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <style>
        body {
            font-family: 'Roboto', sans-serif;
        }

        nav {
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            font-size: 16px;
            padding: 15px;
        }

        nav > div {
            display: inline-flex;
            gap: 15px;
        }

        nav div:not(:first-child) > a, .venteCard a {
            text-decoration: underline;
        }

        a:visited {
            text-decoration: none;
            color: black;
        }

        button:not(:disabled), select, option, input[type="checkbox"], label + select, label + input[type="checkbox"], a, label {
            cursor: pointer;
        }

        .titleEni {
            font-size: 20px;
            text-decoration: none;
        }

        main {
            padding: 15px;
        }

        .titlePage {
            text-align: center;
            width: 100%;
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

        .input3 {
            margin-top: 0px;
            height: 80px;
            width: 350px
        }
    </style>
</head>
<body>
<nav>
    <div>
        <a class="titleEni" href="${pageContext.request.contextPath}/">ENI-Enchères</a>
    </div>
    <c:choose>
        <c:when test="${utilisateur.isValid}">
            <div><!-- Affiché que si User != null -->
                <a href="${pageContext.request.contextPath}/">Enchères</a>
                <a href="${pageContext.request.contextPath}/vendre">Vendre un article</a>
                <a href="${pageContext.request.contextPath}/profile">Mon Profil</a>
                <a href="${pageContext.request.contextPath}/Deconnexion">Déconnexion</a>
            </div>
        </c:when>
        <c:otherwise>
            <div><!-- Affiché que si User == null -->
                <a href="${pageContext.request.contextPath}/connexion">S'inscrire - Se connecter</a>
            </div>
        </c:otherwise>
    </c:choose>
</nav>
<hr/>
<main>
    <h3 class="titlePage">Mon Profile</h3>
    <table class="table0">


        <tr>
            <td><label> Pseudo :</label></td>
            <td> ${utilisateur.pseudo}</td>
        </tr>
        <tr>
            <td><label> Nom : </label></td>
            <td> ${utilisateur.nom} </td>
        </tr>
        <tr>
            <td> Prénom :</td>
            <td> ${utilisateur.prenom} </td>
        </tr>
        <tr>
            <td>Email :</td>
            <td>${utilisateur.email}</td>
        </tr>
        <tr>
            <td> Rue :</td>
            <td> ${utilisateur.rue}</td>
        </tr>
        <tr>
            <td>Code postal :</td>
            <td>${utilisateur.codePostal} </td>
        </tr>
        <tr>
            <td>Ville :</td>
            <td> ${utilisateur.ville }</td>
        </tr>
    </table>
    <table class="table0">
        <tr>
            <td>
                <form method="get" action="${pageContext.request.contextPath}/modifierProfile">
                    <button class="input3" type="submit">Modifier</button>
                </form>
            </td>
        </tr>
    </table>

</main>

</body>
</html>