# PoolingAround App

Una semplice applicazione Java per la gestione delle prenotazioni di viaggi, pensata per facilitare gli spostamenti di viaggiatori leggeri, studenti fuori sede e professionisti.

## Indice

- [Introduzione](#introduzione)
- [Prerequisiti](#prerequisiti)
- [Installazione](#installazione)
- [Esecuzione](#esecuzione)
- [Contributo](#contributo)

# Introduzione

PoolingAround è un'applicazione Java che permette di gestire le prenotazioni dei viaggi in modo semplice ed efficace. L'applicazione consente di visualizzare i viaggi disponibili, prenotare un viaggio, cancellare una prenotazione, aggiungere nuovi utenti e esportare i viaggi disponibili in un file CSV. Il progetto è stato sviluppato per comprendere i concetti fondamentali della programmazione in Java e l'uso di Maven come strumento di gestione del progetto.


# Prerequisiti
Assicurati di avere installato sul tuo sistema:

- Java Development Kit (JDK) 8 o superiore
- Apache Maven

## Configurazione dell'Ambiente
Assicurati che le variabili d'ambiente `JAVA_HOME` e `MAVEN_HOME` siano configurate correttamente.


# Installazione ed esecuzione
Istruzioni passo-passo su come installare e configurare il progetto:

## Clona il repository
```bash
git clone https://github.com/AlessioBerruto/Pooling-Around-App.git
```
## Estrai il file JAR 
Estrai dalla cartella zip il file JAR (PoolingAround-1.0-SNAPSHOT-shaded.jar) nella directory principale del progetto clonato (PoolingAround)


# Esecuzione

## Naviga nella directory del progetto
```bash
cd PoolingAround
```
## Esegui l'applicazione con il comando:
```bash
java -jar PoolingAround-1.0-SNAPSHOT-shaded.jar 
```

- Segui le istruzioni a schermo per:

- 1 : Visualizzare tutti i viaggi disponibili.
- 2 : Prenotare un viaggio specificando l'ID del viaggio e dell'utente.
- 3 : Cancellare una prenotazione esistente inserendo l'ID della prenotazione.
- 4 : Aggiungere un nuovo utente al sistema.
- 5 : Esportare i viaggi disponibili in un file CSV.
- 0 : Chiudere l'applicazione.
  
- Interagisci con il menu per eseguire le diverse operazioni.


# Contributo
Istruzioni su come contribuire al progetto:

- Fai una copia del progetto.
- Crea un branch per la tua funzionalità (git checkout -b feature/NuovaFunzionalità).
- Effettua i commit delle tue modifiche (git commit -m 'Aggiungi una nuova funzionalità').
- Fai push al branch (git push origin feature/NuovaFunzionalità).
- Apri una Pull Request.
