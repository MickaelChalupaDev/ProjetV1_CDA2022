<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Création d'un compte</title>
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
            margin: 40px auto auto;
            width: 1100px;
            height: 100px;
        }

        .input0 {
            margin: 10px;
            height: 25px;
            width: 250px;
        }

        .table1 {
            margin: 40px auto auto;
            width: 700px;
            height: 100px;
        }

        .input3 {
            margin-top: 0px;
            height: 80px;
            width: 250px
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
    <h2 Style="text-align: center"> S'enregistrer </h2>
    <form method="post" action="${pageContext.request.contextPath}/creerCompte">
        <table class="table0">
            <tr>
                <td><label> Pseudo : </label></td>
                <td><input class="input0" type="text" name="pseudo" placeholder="Entrez un pseudo" required pattern="^[a-zA-Zçéèêëàîïûü.\- 0-9]{2,}$"/></td>
                <td><label> Nom : </label></td>
                <td><input class="input0" type="text" name="nom" required pattern="^[a-zA-Zçéèêëàîïûü.\- ]{2,}$"/></td>
            </tr>
            <tr>
                <td><label> Prénom </label></td>
                <td><input class="input0" type="text" name="prenom" required pattern="^[a-zA-Zçéèêëàîïûü.\- ]{2,}$"/></td>
                <td><label> Email : </label></td>
                <td><input class="input0" type="email" name="email" required pattern="^([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+)$"/></td>
            </tr>
            <tr>
                <td><label> Téléphone : </label></td>
                <td><input class="input0" type="text" name="telephone" required pattern="^[0-9]{6,}$"/></td>
                <td><label> Rue : </label></td>
                <td><input class="input0" type="text" name="rue" required pattern="^[a-zA-Zçéèêëàîïûü.\- 0-9]{2,}$"/></td>
            </tr>
            <tr>
                <td><label> Code postal : </label></td>
                <td><input class="input0" type="text" name="codePostal" required pattern="^[a-zA-Z0-9]{2,}$"/></td>
                <td><label> Ville : </label></td>
                <td><input class="input0" type="text" name="ville" required pattern="^[a-zA-Zçéèêëàîïûü.\- ]{2,}$"/></td>
            </tr>
            <tr>
                <td><label> Mot de passe : </label></td>
                <td><input class="input0" type="password" name="motDePasse" placeholder="*******" required pattern="^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,}$"/></td>
                <td><label> Confirmation : </label></td>
                <td><input class="input0" type="password" name="confirmation" placeholder="*******" required pattern="^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,}$"/></td>
            </tr>

        </table>
        <table class="table1">
            <tr>

                <td><input class="input3" type="submit" name="creation" value="Créer"/></td>
                <td><a href="${pageContext.request.contextPath}/"> <input class="input3" type="button" value="Annuler"/>
                </a>
                </td>

            </tr>
        </table>
    </form>
</main>


</body>

</html>