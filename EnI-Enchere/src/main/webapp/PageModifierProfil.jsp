<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier un compte</title>
    <style>
        h1 {
            margin-left: 200px;
            margin-top: 80px;
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
<header>
    <h1> ENI-Enchères</h1>
</header>
<h2 Style="text-align: center"> Mon profil </h2>
<table class="table0">
    <form method="post" action="ServletPageModifierProfil">

        <tr>
            <td><label> Pseudo : </label></td>
            <td><input class="input0" type="text" name="pseudo" value = "${sessionScope.utilisateur.getPseudo()}" placeholder="Entrez un pseudo" required/></td>
            <td><label> Nom : </label></td>
            <td><input class="input0" type="text" name="nom" value = "${sessionScope.utilisateur.getNom()}" required/></td>
        </tr>
        <tr>
            <td><label> Prénom </label></td>
            <td><input class="input0" type="text" name="prenom" value = "${sessionScope.utilisateur.getPrenom()}" required/></td>
            <td><label> Email : </label></td>
            <td><input class="input0" type="email" name="email" value = "${sessionScope.utilisateur.getEmail()}" required/></td>
        </tr>
        <tr>
            <td><label> Téléphone : </label></td>
            <td><input class="input0" type="text" name="telephone" value = "${sessionScope.utilisateur.getTelephone()}"  required/></td>
            <td><label> Rue : </label></td>
            <td><input class="input0" type="text" name="rue" value = "${sessionScope.utilisateur.getRue()}" required/></td>
        </tr>
        <tr>
            <td><label> Code postal : </label></td>
            <td><input class="input0" type="text" name="codePostal" value = "${sessionScope.utilisateur.getCodePostal()}"  required/></td>
            <td><label> Ville : </label></td>
            <td><input class="input0" type="text" name="ville" value = "${sessionScope.utilisateur.getVille()}"  required/></td>
        </tr>
        <tr>
            <td><label> Mot de passe actuel : </label></td>
            <td><input class="input0" type="password" name="motDePasse" placeholder="*******" /></td>
             <td colspan="2" style="color:red"> ${requestScope.messageMotDePasse} </td>
        </tr>
        <tr>
            <td><label> Noueavu mot de passe : </label></td>
            <td><input class="input0" type="password" name="nouveauMotDePasse" placeholder="*******" /></td>
            <td><label> Confirmation : </label></td>
            <td><input class="input0" type="password" name="confirmation" placeholder="*******"/></td>
        </tr>
        <tr>
            <td><label> Crédit : </label></td>
            <td> ${sessionScope.utilisateur.getCredit()}   </td>
            <td colspan="2" style="color:red"> ${requestScope.messageCompatibiliteMotDePasse} </td>
           
        </tr>
</table>
<table class="table1">
    <tr>

        <td><input class="input3" type="submit" name="enregistrement" value="Enregistrer"/></td>
        <td><input class="input3" type="submit" name="enregistrement" value="Supprimer"/></td>
        

    </tr>
</table>

</form>


</body>

</html>