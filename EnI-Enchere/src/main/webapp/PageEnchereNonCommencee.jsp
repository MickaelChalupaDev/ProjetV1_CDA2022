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
    <title>Vente non commencée</title>
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
    <h3 class="titlePage">Enchère non commencée / modifier</h3>
    <table class="table2">
        <tr>
            <td style="vertical-align:top; width:300px">

                <c:if test="${articleInit.getNomPhoto()!= null}">

                    <img src="${pageContext.request.contextPath}/images?image=${articleInit.getNomPhoto()}"
                         alt="Photo de l'article à vendre" style="max-height : 300px">

                </c:if>

            </td>
            <td>
                <table class="table0">
                    <form method="post" action="ServletPageEnchereNonCommencee" enctype="multipart/form-data">

                        <tr>
                            <td><label> Article : </label><input style="margin-left: 35px" class="input0" type="text"
                                                                 name="nomArticle"
                                                                 value="${articleInit.getNomArticle()}"
                                                                 required/></td>
                        </tr>
                        <tr>
                            <td><label> Description : </label><input style="height : 100px" class="input0" type="text"
                                                                     name="description"
                                                                     value="${articleInit.getDescription()}"/></td>
                        </tr>
                        <tr>
                            <td><label for="categorie"> Catégorie </label>
                                <select id="categorie" name="categorie">
                                    <c:forEach var="categorie" items="${categories}">
                                        <option value="${categorie.libelle}"
                                                <c:if test="${articleInit.getCategorie() == categorie.libelle.toString()}">selected</c:if>>${categorie.libelle}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="color:red"><label style="color:black"> Photo de l'article : </label>
                                <input style="margin-left: 15px; width:205px;color:black" type="file" id="myFile"
                                       name="fileName" accept="image/*">
                                <input type="hidden" name="noArticle" value="${articleInit.getNoArticle() }">
                                <input type="hidden" name="noArticleInit" value="${articleInit.getNoArticle() }">
                                ${messagePhoto}
                            </td>


                        </tr>
                        <tr>
                            <td><label> Mise à prix : </label><input style="margin-left: 50px;width:80px" class="input0"
                                                                     type="number" id="quantity" name="miseAPrix"
                                                                     min="0"
                                                                     max="1000" value="${articleInit.getMiseAPrix()}"
                                                                     required/></td>
                        </tr>
                        <tr>
                            <td><label> Début de l'enchère : </label>
                                <input style="width:210px" class="input0" type="date" name="dateDebutEncheres"
                                       value="${articleInit.getDateDebutEncheres() }" required/>
                            </td>
                        </tr>
                        <tr style="height : 40px">
                            <td>
                                <p style="margin-left : 200px; margin-top:0px;color:red"> ${messageDateDebutEnchere } </p>
                            </td>
                        </tr>
                        <tr>

                            <td><label> Fin de l'enchère : </label>
                                <input style="margin-left: 25px;width:210px" class="input0" type="date"
                                       name="dateFinEncheres" value="${articleInit.getDateFinEncheres() }" required/>

                            </td>
                        </tr>
                        <tr style="height : 40px">
                            <td><p style="margin-left : 200px; margin-top:0px;color:red"> ${messageDateFinEnchere } </p>
                            </td>
                        </tr>
                </table>
                <fieldset style="width:380px">
                    <legend>Retrait</legend>

                    <label> Rue : </label><input style="margin-left: 90px;width:210px" class="input0" type="text"
                                                 name="rue"
                                                 value="${utilisateur.getRue()}" required/> <br/>
                    <label> Code postal : </label><input style="margin-left: 40px;width:210px" class="input0"
                                                         type="text"
                                                         name="codePostal" value="${utilisateur.getCodePostal()}"
                                                         required/>
                    <br/>
                    <label> Ville : </label><input style="margin-left: 85px;width:210px" class="input0" type="text"
                                                   name="ville" value="${utilisateur.getVille()}" required/> <br/>
                </fieldset>

                <table class="table1">
                    <tr>
                        <td><input class="input3" type="submit" name="creation" value="Enregistrer"/></td>
                        <td><a class="input3" href="${pageContext.request.contextPath}/">Annuler</a></td>
                        <td><input class="input3" type="submit" name="creation" value="Annuler la vente"/></td>

                    </tr>
                </table>
                </form>

            </td>
        </tr>
    </table>
</main>
</body>

</html>