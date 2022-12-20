<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Création d'un compte</title>
<STYLE type="text/css">
  h1 { margin-left: 200px;
  	margin-top : 80px;}
  .table0{
  margin : auto;
  margin-top : 40px;
  width: 1100px;
  height : 100px;
  }
  .input0{
   margin : 10px;
  height : 25px;
  width : 250px;
  }
  .table1{
  margin : auto;
  margin-top : 40px;
  width: 700px;
  height : 100px;
  }
   .input3{
  margin-top : 0px;
height:80px; width:250px
  }
  
 </STYLE>
</head>
<body>
<header>
<h1> ENI-Enchères</h1>
</header>
<h2 Style="text-align: center"> Mon profil </h2>
<table class="table0">
<form method="get" action="ServletCreationCompte">

<tr>
	<td> <label> Pseudo : </label> </td> 
	<td> <input class="input0" type="text" name="pseudo" placeholder="Entrez un pseudo" required /> </td>
	<td> <label> Nom : </label> </td> 
	<td> <input class="input0" type="text" name="nom" required /> </td>
</tr> 
<tr>
	<td> <label> Prénom </label> </td> 
	<td> <input class="input0" type="text" name="prenom" required /> </td>
	<td> <label> Email : </label> </td> 
	<td> <input class="input0" type="email" name="email" required /> </td>
</tr>
<tr >
	<td> <label> Téléphone : </label> </td> 
	<td> <input class="input0" type="text" name="telephone" required /> </td>
	<td> <label> Rue : </label> </td> 
	<td> <input class="input0" type="text" name="rue" required /> </td>
</tr>
<tr >
	<td> <label> Code postal : </label> </td> 
	<td> <input class="input0" type="text" name="codePostal" required /> </td>
	<td> <label> Ville : </label> </td> 
	<td> <input class="input0" type="text" name="ville" required /> </td>
</tr>
<tr >
	<td> <label> Mot de passe : </label> </td> 
	<td> <input class="input0" type="password" name="motDePasse" placeholder="*******"  required /> </td>
	<td> <label> Confirmation : </label> </td> 
	<td> <input class="input0" type="password" name="confirmation" placeholder="*******" required /> </td>
</tr>
</table>
<table class="table1">
<tr>

	<td> <input class="input3" type="submit" name="creation" value="Créer" /> </td>
	<td>  <a href="PageAcceuilNonConnecte.jsp"> <input class="input3" type="button" value= "Annuler"   /> </a> </td>

</tr>
</table>

</form>




</body>

</html>