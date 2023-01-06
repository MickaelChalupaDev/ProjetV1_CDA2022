USE [master]
GO
/****** Object:  Database [ENCHERES]    Script Date: 03/01/2023 16:40:47 ******/
CREATE DATABASE [ENCHERES]
    CONTAINMENT = NONE
    ON  PRIMARY
    ( NAME = N'ENCHERES', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\ENCHERES.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
    LOG ON
    ( NAME = N'ENCHERES_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\ENCHERES_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [ENCHERES] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
    begin
        EXEC [ENCHERES].[dbo].[sp_fulltext_database] @action = 'enable'
    end
GO
ALTER DATABASE [ENCHERES] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [ENCHERES] SET ANSI_NULLS OFF
GO
ALTER DATABASE [ENCHERES] SET ANSI_PADDING OFF
GO
ALTER DATABASE [ENCHERES] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [ENCHERES] SET ARITHABORT OFF
GO
ALTER DATABASE [ENCHERES] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [ENCHERES] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [ENCHERES] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [ENCHERES] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [ENCHERES] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [ENCHERES] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [ENCHERES] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [ENCHERES] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [ENCHERES] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [ENCHERES] SET  DISABLE_BROKER
GO
ALTER DATABASE [ENCHERES] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [ENCHERES] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [ENCHERES] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [ENCHERES] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [ENCHERES] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [ENCHERES] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [ENCHERES] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [ENCHERES] SET RECOVERY SIMPLE
GO
ALTER DATABASE [ENCHERES] SET  MULTI_USER
GO
ALTER DATABASE [ENCHERES] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [ENCHERES] SET DB_CHAINING OFF
GO
ALTER DATABASE [ENCHERES] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER DATABASE [ENCHERES] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER DATABASE [ENCHERES] SET DELAYED_DURABILITY = DISABLED
GO
ALTER DATABASE [ENCHERES] SET ACCELERATED_DATABASE_RECOVERY = OFF
GO
ALTER DATABASE [ENCHERES] SET QUERY_STORE = OFF
GO
USE [ENCHERES]
GO
/****** Object:  Table [dbo].[ARTICLES_VENDUS]    Script Date: 03/01/2023 16:40:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ARTICLES_VENDUS](
                                        [no_article] [int] IDENTITY(1,1) NOT NULL,
                                        [nom_article] [nvarchar](30) NOT NULL,
                                        [description] [nvarchar](300) NOT NULL,
                                        [date_debut_encheres] [date] NOT NULL,
                                        [date_fin_encheres] [date] NOT NULL,
                                        [prix_initial] [int] NULL,
                                        [prix_vente] [int] NULL,
                                        [etat_vente] [int] NULL,
                                        [no_utilisateur] [int] NOT NULL,
                                        [no_categorie] [int] NOT NULL,
                                        [lien_photo] [nvarchar](100) NULL,
                                        CONSTRAINT [articles_vendus_pk] PRIMARY KEY CLUSTERED
                                            (
                                             [no_article] ASC
                                                )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CATEGORIES]    Script Date: 03/01/2023 16:40:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CATEGORIES](
                                   [no_categorie] [int] IDENTITY(1,1) NOT NULL,
                                   [libelle] [nvarchar](30) NOT NULL,
                                   CONSTRAINT [categorie_pk] PRIMARY KEY CLUSTERED
                                       (
                                        [no_categorie] ASC
                                           )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ENCHERES]    Script Date: 03/01/2023 16:40:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ENCHERES](
                                [no_enchere] [int] NOT NULL PRIMARY KEY IDENTITY(1,1),
                                 [no_utilisateur] [int] NOT NULL,
                                 [no_article] [int] NOT NULL,
                                 [date_enchere] [datetime] NOT NULL,
                                 [montant_enchere] [int] NOT NULL,
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RETRAITS]    Script Date: 03/01/2023 16:40:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RETRAITS](
                                 [no_article] [int] NOT NULL,
                                 [rue] [nvarchar](100) NOT NULL,
                                 [code_postal] [nvarchar](15) NOT NULL,
                                 [ville] [nvarchar](30) NOT NULL,
                                 CONSTRAINT [retrait_pk] PRIMARY KEY CLUSTERED
                                     (
                                      [no_article] ASC
                                         )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UTILISATEURS]    Script Date: 03/01/2023 16:40:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UTILISATEURS](
                                     [no_utilisateur] [int] IDENTITY(1,1) NOT NULL,
                                     [pseudo] [nvarchar](30) NOT NULL,
                                     [nom] [nvarchar](30) NOT NULL,
                                     [prenom] [nvarchar](30) NOT NULL,
                                     [email] [nvarchar](100) NOT NULL,
                                     [telephone] [nvarchar](15) NULL,
                                     [rue] [nvarchar](100) NOT NULL,
                                     [code_postal] [nvarchar](10) NOT NULL,
                                     [ville] [nvarchar](30) NOT NULL,
                                     [mot_de_passe] [nvarchar](255) NOT NULL,
                                     [credit] [int] NOT NULL,
                                     [administrateur] [bit] NOT NULL,
                                     CONSTRAINT [utilisateur_pk] PRIMARY KEY CLUSTERED
                                         (
                                          [no_utilisateur] ASC
                                             )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[ARTICLES_VENDUS] ON

INSERT [dbo].[ARTICLES_VENDUS] ([no_article], [nom_article], [description], [date_debut_encheres], [date_fin_encheres], [prix_initial], [prix_vente], [etat_vente], [no_utilisateur], [no_categorie], [lien_photo]) VALUES (1, N'Téléphone', N'A10', CAST(N'2023-01-03' AS Date), CAST(N'2023-01-31' AS Date), 21, 0, 1, 2, 1, N'/photos/2-1672757640602-telephone.png')
INSERT [dbo].[ARTICLES_VENDUS] ([no_article], [nom_article], [description], [date_debut_encheres], [date_fin_encheres], [prix_initial], [prix_vente], [etat_vente], [no_utilisateur], [no_categorie], [lien_photo]) VALUES (2, N'Caméra', N'S10', CAST(N'2023-01-03' AS Date), CAST(N'2023-01-18' AS Date), 31, 0, 1, 1, 4, N'/photos/1-1672757716380-icone-camera.jpg')
INSERT [dbo].[ARTICLES_VENDUS] ([no_article], [nom_article], [description], [date_debut_encheres], [date_fin_encheres], [prix_initial], [prix_vente], [etat_vente], [no_utilisateur], [no_categorie], [lien_photo]) VALUES (3, N'Téléphone', N'A10', CAST(N'2023-01-03' AS Date), CAST(N'2023-01-31' AS Date), 21, 0, 1, 2, 1, N'/photos/2-1672757640602-telephone-copie.png')
SET IDENTITY_INSERT [dbo].[ARTICLES_VENDUS] OFF
GO
SET IDENTITY_INSERT [dbo].[CATEGORIES] ON

INSERT [dbo].[CATEGORIES] ([no_categorie], [libelle]) VALUES (1, N'Informatique')
INSERT [dbo].[CATEGORIES] ([no_categorie], [libelle]) VALUES (2, N'Ameublement')
INSERT [dbo].[CATEGORIES] ([no_categorie], [libelle]) VALUES (3, N'Vêtement')
INSERT [dbo].[CATEGORIES] ([no_categorie], [libelle]) VALUES (4, N'Sport&Loisirs')
SET IDENTITY_INSERT [dbo].[CATEGORIES] OFF
GO
INSERT [dbo].[ENCHERES] ([no_utilisateur], [no_article], [date_enchere], [montant_enchere]) VALUES (2, 1, CAST(N'2023-01-03T16:04:15.000' AS DateTime), 22)
GO
INSERT [dbo].[RETRAITS] ([no_article], [rue], [code_postal], [ville]) VALUES (1, N'5 rue des Pinsons', N'44000', N'Nantes')
INSERT [dbo].[RETRAITS] ([no_article], [rue], [code_postal], [ville]) VALUES (2, N'10 allée des allouettes', N'44800', N'Saint Herblain')
INSERT [dbo].[RETRAITS] ([no_article], [rue], [code_postal], [ville]) VALUES (3, N'5 rue des Pinsons', N'44000', N'Nantes')
GO
SET IDENTITY_INSERT [dbo].[UTILISATEURS] ON

INSERT [dbo].[UTILISATEURS] ([no_utilisateur], [pseudo], [nom], [prenom], [email], [telephone], [rue], [code_postal], [ville], [mot_de_passe], [credit], [administrateur]) VALUES (1, N'jojo44', N'JOJO', N'jojo', N'jojo@jojo.fr', N'0101010101', N'10 allée des allouettes', N'44800', N'Saint Herblain', N'sa', 100, 0)
INSERT [dbo].[UTILISATEURS] ([no_utilisateur], [pseudo], [nom], [prenom], [email], [telephone], [rue], [code_postal], [ville], [mot_de_passe], [credit], [administrateur]) VALUES (2, N'jiji55', N'JIJI', N'jiji', N'jiji@jiji.fr', N'0101010101', N'5 rue des Pinsons', N'44000', N'Nantes', N'sa', 100, 0)
SET IDENTITY_INSERT [dbo].[UTILISATEURS] OFF
GO
ALTER TABLE [dbo].[ARTICLES_VENDUS]  WITH CHECK ADD  CONSTRAINT [articles_vendus_categories_fk] FOREIGN KEY([no_categorie])
    REFERENCES [dbo].[CATEGORIES] ([no_categorie])
GO
ALTER TABLE [dbo].[ARTICLES_VENDUS] CHECK CONSTRAINT [articles_vendus_categories_fk]
GO
ALTER TABLE [dbo].[ARTICLES_VENDUS]  WITH CHECK ADD  CONSTRAINT [encheres_utilisateur_fk] FOREIGN KEY([no_utilisateur])
    REFERENCES [dbo].[UTILISATEURS] ([no_utilisateur])
GO
ALTER TABLE [dbo].[ARTICLES_VENDUS] CHECK CONSTRAINT [encheres_utilisateur_fk]
GO
ALTER TABLE [dbo].[ARTICLES_VENDUS]  WITH CHECK ADD  CONSTRAINT [ventes_utilisateur_fk] FOREIGN KEY([no_utilisateur])
    REFERENCES [dbo].[UTILISATEURS] ([no_utilisateur])
GO
ALTER TABLE [dbo].[ARTICLES_VENDUS] CHECK CONSTRAINT [ventes_utilisateur_fk]
GO
ALTER TABLE [dbo].[ENCHERES]  WITH CHECK ADD  CONSTRAINT [encheres_articles_vendus_fk] FOREIGN KEY([no_article])
    REFERENCES [dbo].[ARTICLES_VENDUS] ([no_article])
GO
ALTER TABLE [dbo].[ENCHERES] CHECK CONSTRAINT [encheres_articles_vendus_fk]
GO
ALTER TABLE [dbo].[RETRAITS]  WITH CHECK ADD  CONSTRAINT [retraits_articles_vendus_fk] FOREIGN KEY([no_article])
    REFERENCES [dbo].[ARTICLES_VENDUS] ([no_article])
GO
ALTER TABLE [dbo].[RETRAITS] CHECK CONSTRAINT [retraits_articles_vendus_fk]
GO
USE [master]
GO
ALTER DATABASE [ENCHERES] SET  READ_WRITE
GO
