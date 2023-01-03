<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
    <form method="post" action="ServletPageConnexion">

		<tr>
		 <td colspan="2"> <p style="color:red; margin-left: 180px"> &nbsp ${requestScope.messageErreur}</p> </td>
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
            <td><input class="input2" type="checkbox" name="seSeouvenirDeMoi"> <label> Se souvenir de moi</label></td>
        </tr>
        <tr>
            <td><a href=""> Mot de passe oublié </a></td>
        </tr>

    </form>
</table>
<table class="table0">
    <tr>
        <form method="get" action="PageCreerCompte.jsp">
            <td><input class="input3" type="submit" value="Créer un compte"/></td>
        </form>
    </tr>
</table>


</body>
</html>