
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Vente non commencée</title>
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
  <h2 Style="text-align: center"> Nouvelle vente </h2>
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
        <form method="post" action="ServletPageEnchereNonCommencee" enctype="multipart/form-data">

          <tr>
            <td><label > Article : </label><input style="margin-left: 35px" class="input0" type="text" name="nomArticle" value="${requestScope.article.getNomArticle()}" required/></td>
          </tr>
          <tr>
            <td><label> Description : </label><input style="height : 100px"class="input0" type="text" name="description" value="${requestScope.article.getDescription()}" /></td>
          </tr>
          <tr>
            <td><label> Catégorie </label>
              <input style="margin-left: 70px; width:205px" list="categories" name="categorie" id="categorie" placeholder="Informatique" value="${requestScope.article.getCategorie()}"required>
              <datalist id="categories">
                <option value="Informatique">
                <option value="Ameublement">
                <option value="Vêtement">
                <option value="Sport&Loisirs">
              </datalist>
            </td>
          </tr>
          <tr>
            <td style="color:red"><label style="color:black"> Photo de l'article : </label>
              <input style="margin-left: 15px; width:205px;color:black" type="file" id="myFile" name="fileName"  accept="image/*">
              <input type="hidden" name="noArticle" value="${requestScope.article.getNoArticle() }">
              <input type="hidden" name="noArticleInit" value="${requestScope.articleInit.getNoArticle() }">
              ${requestScope.messagePhoto}
            </td>


          </tr>
          <tr>
            <td><label> Mise à prix : </label><input  style="margin-left: 50px;width:80px" class="input0" type="number" id="quantity" name="miseAPrix" min="0" max="1000" value="${requestScope.article.getMiseAPrix()}"  required/></td>
          </tr>
          <tr>
            <td><label> Début de l'enchère : </label>
              <input style="width:210px" class="input0" type="date" name="dateDebutEncheres" value = "${requestScope.article.getDateDebutEncheres() }" required/>
            </td>
          </tr>
          <tr style="height : 40px">
            <td> <p style="margin-left : 200px; margin-top:0px;color:red"> ${requestScope.messageDateDebutEnchere } </p> </td>
          </tr>
          <tr>

            <td><label> Fin de l'enchère : </label>
              <input style="margin-left: 25px;width:210px" class="input0" type="date" name="dateFinEncheres" value="${requestScope.article.getDateFinEncheres() }" required/>

            </td>
          </tr>
          <tr style="height : 40px">
            <td> <p style="margin-left : 200px; margin-top:0px;color:red"> ${requestScope.messageDateFinEnchere } </p> </td>
          </tr>
      </table>
      <fieldset style="width:380px">
        <legend>Retrait</legend>

        <label> Rue : </label><input style="margin-left: 90px;width:210px"  class="input0" type="text" name="rue" value = "${sessionScope.user.getRue()}"  required/> <br/>
        <label> Code postal : </label><input style="margin-left: 40px;width:210px" class="input0" type="text" name="codePostal" value = "${sessionScope.user.getCodePostal()}"  required/>  <br/>
        <label> Ville : </label><input style="margin-left: 85px;width:210px" class="input0" type="text" name="ville" value = "${sessionScope.user.getVille()}"  required/>  <br/>
      </fieldset>

      <table class="table1">
        <tr>
          <td><input class="input3" type="submit" name="creation" value="Enregistrer"/></td>
          <td><input class="input3" type="submit" name="creation" value="Annuler"/></td>
          <td><input class="input3" type="submit" name="creation" value="Annuler la vente"/></td>

        </tr>
      </table>
      </form>

    </td>
  </tr>
</table>
</body>

</html>