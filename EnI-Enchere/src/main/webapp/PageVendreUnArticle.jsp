<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.eni.encheres.bo.Categorie" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nouvelle vente</title>
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
    <h3 class="titlePage">Vendre un article</h3>
    <form method="post" action="${pageContext.request.contextPath}/vendre"
          enctype="multipart/form-data">
        <table class="table2">
            <tr>
                <td style="vertical-align:top; width:300px">

                    <c:if test="${requestScope.article.getNomPhoto()!= null}">

                        <img src="${pageContext.request.contextPath}/images?image=${article.getNomPhoto()}"
                             alt="Photo de l'article à vendre" style="max-height : 300px">

                    </c:if>

                </td>

                    <td>
                        <table class="table0">

                            <tr>
                                <td><label> Article : </label><input style="margin-left: 35px" class="input0" type="text"
                                                                     name="nomArticle"
                                                                     value="${requestScope.article.getNomArticle()}"
                                                                     required/>
                                </td>
                            </tr>
                            <tr>
                                <td><label> Description : </label><input style="height : 100px" class="input0" type="text"
                                                                         name="description"
                                                                         value="${requestScope.article.getDescription()}"/>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="categorie"> Catégorie </label>
                                    <select id="categorie" name="categorie">
                                        <c:forEach var="categorie" items="${categories}">
                                            <option value="${categorie.libelle}"
                                                    <c:if test="${requestScope.article.getCategorie() == categorie.libelle.toString()}">selected</c:if>>${categorie.libelle}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td style="color:red"><label style="color:black"> Photo de l'article : </label>
                                    <input style="margin-left: 15px; width:205px;color:black" type="file" id="myFile"
                                           name="fileName" accept="image/*">
                                    <input type="hidden" name="noArticle" value="${requestScope.article.getNoArticle() }">
                                    ${requestScope.messagePhoto}
                                </td>


                            </tr>
                            <tr>
                                <td><label> Mise à prix : </label><input style="margin-left: 50px;width:80px" class="input0"
                                                                         type="number" id="quantity" name="miseAPrix"
                                                                         min="0"
                                                                         max="1000"
                                                                         value="${requestScope.article.getMiseAPrix()}"
                                                                         required/></td>
                            </tr>
                            <tr>
                                <% SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    String date = sdf.format(new Date(System.currentTimeMillis())); %>
                                <td><label> Début de l'enchère : </label><input style="width:210px" class="input0"
                                                                                type="date"
                                                                                name="dateDebutEncheres" value="<%=date%>"
                                                                                required/></td>
                            </tr>
                            <tr>

                                <td><label> Fin de l'enchère : </label>
                                    <input style="margin-left: 25px;width:210px" class="input0" type="date"
                                           name="dateFinEncheres" value="<%=date%>" required/>

                                </td>
                            </tr>
                            <tr style="height : 40px">
                                <td>
                                    <p style="margin-left : 200px; margin-top:0px;color:red"> ${requestScope.messageDateFinEnchere } </p>
                                </td>
                            </tr>
                        </table>
                        <fieldset style="width:380px">
                            <legend>Retrait</legend>

                            <label> Rue : </label><input style="margin-left: 90px;width:210px" class="input0" type="text"
                                                         name="rue"
                                                         value="${sessionScope.utilisateur.getRue()}" required/> <br/>
                            <label> Code postal : </label><input style="margin-left: 40px;width:210px" class="input0"
                                                                 type="text"
                                                                 name="codePostal"
                                                                 value="${sessionScope.utilisateur.getCodePostal()}"
                                                                 required/>
                            <br/>
                            <label> Ville : </label><input style="margin-left: 85px;width:210px" class="input0" type="text"
                                                           name="ville" value="${sessionScope.utilisateur.getVille()}"
                                                           required/>
                            <br/>
                        </fieldset>

                        <table class="table1">
                            <tr>
                                <td><input class="input3" type="submit" name="creation" value="Enregistrer"/></td>
                                <td><a class="input3" href="${pageContext.request.contextPath}/">Annuler</a></td>

                            </tr>
                        </table>


                </td>
            </tr>
        </table>
    </form>
</main>
</body>

</html>