# Application GestionDaily

## Titre
GestionDaily

## Description
un app pour generer ses plans

## Donnes initiales
sont dans le fichier csv   (src/Daily/DailyDatas), a ajuster au besoin, ou just tout supprimer


## Installation
- Version Java: 8 ou superieure
- librairies externes a telecharger: https://www.oracle.com/java/technologies/install-javafx-sdk.html
  


## Execution
D'abord, il faut le telecharger et telecharger un SDK de javafx 


Ensuite, verifier
  -> File -> projet structure -> project -> (ajuster le SDK)
  -> File -> projet structure -> Libraries -> + -> path vers votre SDK **terminer par /lib


Finalement, suivre https://openjfx.io/openjfx-docs/ pour ajuster les configuration
  OU
En resumer: 
run -> edit configurations -> ajouter app -> modify option -> add VM -> 
                    VM: --module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml   et name: Daily.Main

Il faut editer ('PATH_TO_FX') un genre de racourcir pour acceder au sdk
  -> itelliji settings -> Appearance & behavior -> path variables -> + -> ajouter: PATH_TO_FX et le path



  
  

