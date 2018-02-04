# AndroidWheresMyPhone
Android code for an app that connects to a website built using ruby on rails with a mysql database.

Features:
  * Log in to account created on website.
  * Runs in the background and sends webserver location of phone every few minutes.
  * Website can ping phone for location and make the phones alarm go off. 

Future Features:
  * Account creation on android.
  * More customization.

Security is a priority. To create a secure connection we encrypt communication using AES 128 bit encryption. 
We use diffie hellman key-exchange algorithm to allow the server and phone to decrypt messages. We use a 
predetermined word to prevent ddos. We use jbcrypt to hash passwords before communication and we store 
passwords hashed on the server.
