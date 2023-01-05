<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="fr.eni.encheres.bo.Utilisateur" %>
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
    <td> ${utilisateur.pseudo}</td>
  </tr>
  <tr>
    <td><label> Nom : </label></td>
    <td>  ${utilisateur.nom} </td>
  </tr>
  <tr>
    <td > Prénom : </td>
    <td> ${utilisateur.prenom} </td>
  </tr>
  <tr>
    <td>Email : </td>
    <td>${utilisateur.email}</td>
  </tr>
  <tr>
    <td> Rue : </td>
    <td> ${utilisateur.rue}</td>
  </tr>
  <tr>
    <td>Code postal</td>
    <td>${utilisateur.codePostal} </td>
  </tr>
  <tr>
    <td>Ville  </td>
    <td> ${utilisateur.ville }</td>
  </tr>
</table>
<table class="table0">
  <tr>
    <td> <a href="${pageContext.request.contextPath}/profile/modifier"> <input class="input3" type="submit" value="Modifier"/> </a>
  </tr>
</table>


</body>
</html>