<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="fr.eni.encheres.bo.Article" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Enchérir</title>
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
    <h3 class="titlePage">Enchérir</h3>
    <table class="table2">
        <tr>
            <td style="vertical-align:top; width:300px">

                <c:if test="${article.getNomPhoto()!= null}">
                    <img src="${pageContext.request.contextPath}/images?image=${article.getNomPhoto()}"
                         alt="Photo de l'article à vendre" style="max-height : 300px">
                </c:if>

            </td>
            <td>
                <table class="table0">
                    <tr>
                        <td><p> ${article.getNomArticle()} </p></td>
                    </tr>
                    <tr>
                        <table>
                            <tr>
                                <td><label> Description : </label></td>
                                <td><input style="height : 100px" class="input0" type="text" name="description"
                                           value="${article.getDescription()}"/></td>
                            </tr>
                            <tr>
                                <td><label> Catégorie </label></td>
                                <td style="margin-left: 70px; width:205px"> ${article.getCategorie()} </td>
                            </tr>
                            <tr>
                                <td><label> Meilleure offre :</label></td>
                                <td style="margin-left: 70px; width:205px"> ${(meilleureOffre)} </td>
                            </tr>
                            <tr>
                                <td><label> Mise à prix : </label></td>
                                <td style="margin-left: 0px;width:80px"> ${article.getMiseAPrix()} </td>
                            </tr>
                            <tr>
                                <td><label> Fin de l'enchère : </label></td>
                                <td style="margin-left: 25px;width:210px"> ${article.getDateFinEncheres() } </td>
                            </tr>
                            <tr>
                                <td><label> Retrait : </label></td>
                                <td style="margin-left: 25px;width:210px"> ${article.getAdresse().get(0)} <br>
                                    ${article.getAdresse().get(1)} ${requestScope.article.getAdresse().get(2)}
                                </td>
                            </tr>
                            <tr>
                                <table>
                                    <tr>
                                        <td><label> Vendeur : </label></td>
                                        <td style="margin-left: 25px;width:210px"> ${article.getVendeur().getPseudo()} </td>
                                    </tr>
                                    <tr>
                                        <td><label> Ma proposition : </label></td>
                                        <form method="post" action="${pageContext.request.contextPath}/encherir">
                                            <td><input style="margin-left: 50px;width:80px" class="input0" type="number"
                                                       id="quantity" name="maProposition" min="${meilleureOffre+1}"
                                                       value="${(meilleureOffre+1)}" required/>
                                            <td><input class="input3" type="submit" name="encherir" value="Enchérir"/>
                                            </td>
                                            <td><input class="input3" type="hidden" name="noArticle"
                                                       value="${article.getNoArticle()}"/></td>
                                        </form>
                                        </td>
                                    </tr>
                                </table>
                            </tr>
                        </table>
                    </tr>
                </table>
        </tr>
    </table>

</main>
</body>
</html>