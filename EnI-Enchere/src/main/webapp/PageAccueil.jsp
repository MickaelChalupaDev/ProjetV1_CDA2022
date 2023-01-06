<%@ page import="fr.eni.encheres.bo.Utilisateur" %>
<%@ page import="fr.eni.encheres.controllers.objectSent.ObjectSentAccueil"%>
<%@ page import="fr.eni.encheres.bo.Enchere" %>
<%@ page import="java.util.List"%>
<%@ page import="fr.eni.encheres.bo.Article"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="articles" scope="request" type="java.util.List<fr.eni.encheres.bo.Article>" />
<jsp:useBean id="categories" scope="request" type="java.util.List<fr.eni.encheres.bo.Categorie>" />
<html>
    <head>
        <title>Accueil</title>
        <style>
            body{
                font-family: 'Roboto', sans-serif;
            }
            nav{
                display: flex;
                flex-direction: row;
                justify-content: space-between;
                font-size: 16px;
                padding: 15px;
            }
            nav > div{
                display:inline-flex;
                gap:15px;
            }
            nav div:not(:first-child) > a, .venteCard a{
                text-decoration: underline;
            }
            a:visited {
                text-decoration: none;
                color:black;
            }
            button:not(:disabled), select, option, input[type="checkbox"], label + select, label + input[type="checkbox"], a, label{
                cursor: pointer;
            }
            .titleEni{
                font-size: 20px;
                text-decoration: none;
            }
            main{
                padding: 15px;
            }
            .titlePage{
                text-align: center;
                width: 100%;
            }
            .filtresContainer{
                display: flex;
                flex-direction: row;
                justify-content: space-evenly;
            }
            .filtre{
                display: flex;
                flex-direction: column;
                min-width: 30%;
                gap: 10px;
            }
            .buttonSearchContainer{
                display: flex;
                flex-direction: row;
                align-items: center;
            }
            .buttonSearchContainer button{
                outline: none;
                width: 250px;
                height: 50%;
                border: black 2px solid;
                border-right-width: 3px;
                border-bottom-width:3px;
                background-color: #d1d1cf;
                border-radius:4px;
                text-transform: uppercase;
                font-weight: bold;
                font-size: 15px;
            }
            .buttonSearchContainer button:hover{
                background-color: #c7c6c6;
                box-shadow: 22px 26px 20px -12px rgba(0,0,0,0.1);
            }
            .buttonSearchContainer button:active{
                border-width:3px;
                border-right-width:2px;
                border-bottom-width:2px;
            }
            .inputSearchContainer{
                min-width: 250px;
                display:flex;
            }
            .inputSearchContainer input{
                outline:none;
                width: 100%;
                height: 100%;
                border:none;
                padding: 5px;
                border-radius: 10px;
            }
            .inputSearchContainer span{
                display: inline-flex;
                width: 100%;
                height: 100%;
                border: 2px solid #6a6a6a;
                border-radius: 10px;
            }
            .inputSearchContainer span:focus-within{
                border-color:#2a2a2a;
            }
            .inputSearchContainer span::before{
                content: '🔍';
                position: relative;
                width: 20px;
                display: block;
                aspect-ratio: 1/1;
                padding: 5px;
                border:none;
                border-radius: 10px;
            }
            @supports (selector(:has(*))) {
                .inputSearchContainer span:has(input:invalid){
                    border-color:red;
                }
                .inputSearchContainer span:has(input:valid){
                    border-color:green;
                }
                .inputSearchContainer span:has(input:placeholder-shown){
                    border-color:#6a6a6a;
                }
            }
            .categorieDropdownContainer{
                display: flex;
                flex-direction: row;
                justify-content: space-between;
                align-items: center;
                gap: 15px;
            }
            .categorieDropdownContainer label{
                width:25%;
                text-wrap: avoid;
            }
            .categorieDropdownContainer select{
                min-width: 75%;
                padding:5px;
            }
            .groupOptions{
                display: flex;
                flex-direction: row;
                justify-content: space-between;

            }
            .groupOptions > div{
                width: 50%;
                display: flex;
                flex-direction: column;
                gap:10px;
            }
            .groupOptions > div > div:last-child{
                padding-left: 50px;
            }
            .venteCard{
                display:grid;
                grid-template-columns: auto auto auto;
                grid-column-gap: 10%;
                grid-row-gap: 30px;
                margin: 30px;
            }
            .venteCard > div{
                display: flex;
                flex-direction: row;
                padding: 20px;
                border: 2px solid;
                align-items: center;
                gap:15px
            }
            .venteCard img{
                max-height: 200px;
                max-width: 200px;
                aspect-ratio: 1/1;
            }
        </style>
        <link rel="icon" href="https://www.eni-ecole.fr/wp-content/uploads/2021/01/logoENI.png" sizes="32x32">
        <link rel="icon" href="https://www.eni-ecole.fr/wp-content/uploads/2021/01/logoENI.png" sizes="192x192">
    </head>
    <body>
    <%-- Get the object from the request --%>
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
        <h3 class="titlePage">Listes des enchères</h3>
        <form action="${pageContext.request.contextPath}/" method="post">
            <div class="filtresContainer">
                <div class="filtre">
                    <h3>Filtres :</h3>
                    <div class="inputSearchContainer">
                        <span><input placeholder="Le nom de l'article contient" name="search" pattern="^([a-zA-Z éèçàùôöïîÉÈÇÀÙÔÎÖÏ]){0,30}$" value="${filtresRecherches.searched}"/></span>
                    </div>
                    <div class="categorieDropdownContainer">
                        <label for="categorie">Catégorie : </label>
                        <select id="categorie" name="categorie">
                            <c:forEach  var="categorie" items="${categories}">
                                <option value="${categorie.noCategorie}" <c:if test="${filtresRecherches.categorieSelected == categorie.noCategorie.toString()}">selected</c:if>>${categorie.libelle}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <!-- Note les prochaines divs ne seront pas affichées si user pas connecté -->
                    <c:if test="${utilisateur.isValid}">
                        <div class="groupOptions">
                            <div>
                                <div>
                                    <input id="achat" type="radio" name="filtreVentesAffichees" value="achats" <c:if test="${filtresRecherches.filtreVenteAffichees == \"achats\"}">checked</c:if> onclick="onclickAchat()"/>
                                    <label for="achat">Achats</label>
                                </div>
                                <div>
                                    <div>
                                        <input id="encheresOuvertes" type="checkbox" name="encheresOuvertes" <c:if test="${filtresRecherches.checkedEnchereOuverte}">checked</c:if> onclick="onclickAchat(1)" />
                                        <label for="encheresOuvertes">Enchères Ouvertes</label>
                                    </div>
                                    <div>
                                        <input id="mesEncheres" type="checkbox" name="mesEncheres" <c:if test="${filtresRecherches.checkedMesEncheres}">checked</c:if> onclick="onclickAchat(2)"/>
                                        <label for="mesEncheres">Mes enchères</label>
                                    </div>
                                    <div>
                                        <input id="encheresRemportees" type="checkbox" name="encheresRemportees" <c:if test="${filtresRecherches.checkedMesEncheresRemportees}">checked</c:if> onclick="onclickAchat(3)"/>
                                        <label for="encheresRemportees">Enchères Remportées</label>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <div>
                                    <input id="mesVentes" type="radio" name="filtreVentesAffichees" value="mesVentes" <c:if test="${filtresRecherches.filtreVenteAffichees != \"achats\"}">checked</c:if> onclick="onclickMesVentes()"/>
                                    <label for="mesVentes">Mes ventes</label>
                                </div>
                                <div>
                                    <div>
                                        <input id="ventesEnCours" type="checkbox" name="ventesEnCours" <c:if test="${filtresRecherches.checkedMesVentesEnCours}">checked</c:if> onclick="onclickMesVentes(1)"/>
                                        <label for="ventesEnCours">Mes ventes en cours</label>
                                    </div>
                                    <div>
                                        <input id="ventesNonDebutees" type="checkbox" name="ventesNonDebutees" <c:if test="${filtresRecherches.checkedVentesNonDebutees}">checked</c:if> onclick="onclickMesVentes(2)"/>
                                        <label for="ventesNonDebutees">Ventes non débutées</label>
                                    </div>
                                    <div>
                                        <input id="ventesTerminees" type="checkbox" name="ventesTerminees" <c:if test="${filtresRecherches.checkedVentesTerminees}">checked</c:if> onclick="onclickMesVentes(3)"/>
                                        <label for="ventesTerminees">Ventes terminées</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div class="buttonSearchContainer">
                    <button type="submit">
                        Rechercher
                    </button>
                </div>
            </div>
        </form>
        <div class="venteCard">
            <!-- Note : si le radio button est mis sur "mes ventes", la page affichée sera celle de modification de vente, sinon celle d'enchérir -->
            <c:forEach var="article" items="${articles}">
                <div>
                    <div>
                        <c:if test = "${article.getNomPhoto()!= null}">
                            <img src="${pageContext.request.contextPath}/images?image=${article.getNomPhoto()}"  alt="l'article à vendre">
                        </c:if>
                        
                    </div>
                    <div>
                    	<c:if test="${utilisateur!= null}"> 
                            <a href="ServletPageEncherir?noArticle=${article.noArticle}">${article.nomArticle}</a>
                        </c:if>
                        <c:if test="${utilisateur== null}"> 
                            <a href="">${article.nomArticle}</a>
                        </c:if>
                        
                        <p>Prix : ${article.miseAPrix} points<c:if test="${article.miseAPrix != article.getUpdatedPrix() && article.getUpdatedPrix() > 0}">, (enchéri à ${article.getUpdatedPrix()} points)</c:if></p>
                        <p>Fin de l'enchère : ${article.dateFinEncheres.toLocaleString()}</p>
                        <c:if test="${utilisateur!= null}">
                        	<c:if test="${utilisateur.pseudo != article.vendeur.pseudo}">  
                        		<p>Vendeur : <a href="${pageContext.request.contextPath}/profile?pseudo=${article.vendeur.pseudo}">${article.vendeur.pseudo}</a></p>
                        	</c:if>
                        	<c:if test="${utilisateur.pseudo == article.vendeur.pseudo}">  
                        		<p>Vendeur : <a href="${pageContext.request.contextPath}/profile">${article.vendeur.pseudo}</a></p>
                        	</c:if>
                        </c:if>
                        <c:if test="${utilisateur== null}"> 
                           <p>Vendeur :  <a href="">${article.vendeur.pseudo }</a> </p>
                        </c:if>

                    </div>
                </div>
            </c:forEach>
        </div>
        <c:if test="${articles.size() == 0}">
            <h3 class="titlePage">Aucun article trouvé</h3>
        </c:if>
    </main>
    </body>
    <footer>
        <script>
            function onclickAchat(ck = undefined){
                let radioBtn = document.getElementsByName("filtreVentesAffichees")[0];//première
                radioBtn.checked = true;
                radioBtn = document.getElementsByName("filtreVentesAffichees")[1];//deuxième
                radioBtn.checked = false;
                let mesVentesEnCours = document.getElementsByName("ventesEnCours")[0];
                let ventesNonDebutees = document.getElementsByName("ventesNonDebutees")[0];
                let ventesTerminees = document.getElementsByName("ventesTerminees")[0];
                if(mesVentesEnCours.hasAttribute("checked")){
                    mesVentesEnCours.removeAttribute("checked");
                }
                if(ventesNonDebutees.hasAttribute("checked")){
                    ventesNonDebutees.removeAttribute("checked");
                }
                if(ventesTerminees.hasAttribute("checked")){
                    ventesTerminees.removeAttribute("checked");
                }
                mesVentesEnCours.checked = false;
                ventesNonDebutees.checked = false;
                ventesTerminees.checked = false;
                if(ck != undefined){
                    let encheresOuvertes = document.getElementsByName("encheresOuvertes")[0];
                    let mesEncheres = document.getElementsByName("mesEncheres")[0];
                    let encheresRemportees = document.getElementsByName("encheresRemportees")[0];
                    switch (ck){
                        case 1 : {
                            if(encheresRemportees.hasAttribute("checked")){
                                encheresRemportees.removeAttribute("checked");
                            }
                            encheresRemportees.checked = false
                            break;
                        }
                        case 3 : {
                            mesEncheres.checked = true;
                            encheresOuvertes.checked = false;
                            break
                        }
                    }
                }
            }
            function onclickMesVentes(ck = undefined){
                let radioBtn = document.getElementsByName("filtreVentesAffichees")[0];//première
                radioBtn.checked = false;
                radioBtn = document.getElementsByName("filtreVentesAffichees")[1];//deuxième
                radioBtn.checked = true;
                let encheresOuvertes = document.getElementsByName("encheresOuvertes")[0];
                let mesEncheres = document.getElementsByName("mesEncheres")[0];
                let encheresRemportees = document.getElementsByName("encheresRemportees")[0];
                if(encheresOuvertes.hasAttribute("checked")){
                    encheresOuvertes.removeAttribute("checked");
                }
                if(mesEncheres.hasAttribute("checked")){
                    mesEncheres.removeAttribute("checked");
                }
                if(encheresRemportees.hasAttribute("checked")){
                    encheresRemportees.removeAttribute("checked");
                }
                encheresOuvertes.checked = false;
                mesEncheres.checked = false;
                encheresRemportees.checked = false
                if(ck != undefined){
                    let mesVentesEnCours = document.getElementsByName("ventesEnCours")[0];
                    let ventesNonDebutees = document.getElementsByName("ventesNonDebutees")[0];
                    let ventesTerminees = document.getElementsByName("ventesTerminees")[0];
                    switch (ck){
                        case 1: {
                            if(ventesNonDebutees.hasAttribute("checked")){
                                ventesNonDebutees.removeAttribute("checked");
                            }
                            if(ventesTerminees.hasAttribute("checked")){
                                ventesTerminees.removeAttribute("checked");
                            }
                            ventesNonDebutees.checked = false;
                            ventesTerminees.checked = false;
                            break;
                        }
                        case 2 : {
                            if(mesVentesEnCours.hasAttribute("checked")){
                                mesVentesEnCours.removeAttribute("checked");
                            }
                            if(ventesTerminees.hasAttribute("checked")){
                                ventesTerminees.removeAttribute("checked");
                            }
                            mesVentesEnCours.checked = false;
                            ventesTerminees.checked = false;
                            break;
                        }
                        case 3 : {
                            if(mesVentesEnCours.hasAttribute("checked")){
                                mesVentesEnCours.removeAttribute("checked");
                            }
                            if(ventesNonDebutees.hasAttribute("checked")){
                                ventesNonDebutees.removeAttribute("checked");
                            }
                            mesVentesEnCours.checked = false;
                            ventesNonDebutees.checked = false;
                            break;
                        }
                    }
                }
            }
        </script>
    </footer>
</html>