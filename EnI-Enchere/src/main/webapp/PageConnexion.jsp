<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
            margin-top: 40px;


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
    <h3 class="titlePage">Connexion</h3>
    <table class="table0">
        <form method="post" action="${pageContext.request.contextPath}/connexion">
            <tr>
                <td colspan="2"><p style="color:red; margin-left: 100px"> &nbsp ${requestScope.messageErreur}</p></td>
            </tr>
            <tr>
                <td><label> Identifiant :</label></td>
                <td><input class="input0" type="text" name="pseudo" placeholder="Entrez un pseudo" required/></td>
            </tr>
            <tr>
                <td><label> Mot de passe :</label></td>
                <td><input class="input0" type="password" name="motDePasse" placeholder="*********" required/></td>
            </tr>
            <tr>
                <td rowspan="2"><input class="input1" type="submit" value="Connexion"/></td>
                <td><input class="input2" type="checkbox" name="seSouvenirDeMoi"> <label> Se souvenir de moi</label>
                </td>
            </tr>
            <tr>
                <td><a href=""> Mot de passe oublié </a></td>
            </tr>

        </form>
    </table>
    <table class="table0">
        <tr>
            <form method="get" action="${pageContext.request.contextPath}/creerCompte">
                <td><input class="input3" type="submit" value="Créer un compte"/></td>
            </form>
        </tr>
    </table>

</main>

</body>
</html>